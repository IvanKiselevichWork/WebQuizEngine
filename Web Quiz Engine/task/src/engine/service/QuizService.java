package engine.service;

import engine.model.*;
import engine.repository.QuizRepository;
import engine.repository.SolutionRepository;
import engine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class QuizService {
    private static final int QUIZ_PER_PAGE = 10;
    private static final int SOLUTION_PER_PAGE = 10;

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final SolutionRepository solutionRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository, UserRepository userRepository, SolutionRepository solutionRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.solutionRepository = solutionRepository;
    }

    public Page<Quiz> getAllQuizzes(int page) {
        return quizRepository.findAll(PageRequest.of(page, QUIZ_PER_PAGE));
    }

    public Page<SolutionProjection> getCompletedQuizzes(String username, Integer page) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return solutionRepository.findByUserId(user.getId(), PageRequest.of(page, SOLUTION_PER_PAGE, Sort.by("completedAt").descending()));
    }

    public Optional<Quiz> getQuizById(Long id) {
        return quizRepository.findById(id);
    }

    public Quiz addQuiz(QuizRequest quizRequest, String username) {
        if (quizRequest.getAnswer() == null) {
            quizRequest.setAnswer(new ArrayList<>());
        }
        Quiz quiz = new Quiz();
        quiz.setTitle(quizRequest.getTitle());
        quiz.setText(quizRequest.getText());
        String[] options = quizRequest.getOptions().toArray(String[]::new);
        Integer[] answers = quizRequest.getAnswer().toArray(Integer[]::new);
        quiz.setOptions(options);
        quiz.setAnswers(answers);
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        quiz.setUser(user);
        return quizRepository.save(quiz);
    }

    public void deleteQuiz(Long id, String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!quiz.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        quizRepository.deleteById(id);
    }

    public Quiz updateQuiz(Long id, String username, QuizRequest quizRequest) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!quiz.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        quiz.setTitle(quizRequest.getTitle());
        quiz.setText(quizRequest.getText());
        String[] options = quizRequest.getOptions().toArray(String[]::new);
        Integer[] answers = quizRequest.getAnswer().toArray(Integer[]::new);
        quiz.setOptions(options);
        quiz.setAnswers(answers);
        return quizRepository.save(quiz);
    }

    public void addSolution(String username, Long quizId) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Solution solution = new Solution();
        solution.setUser(user);
        solution.setQuiz(quiz);
        solution.setCompletedAt(LocalDateTime.now());
        solutionRepository.save(solution);
    }
}

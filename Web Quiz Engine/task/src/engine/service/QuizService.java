package engine.service;

import engine.model.Quiz;
import engine.model.QuizRequest;
import engine.model.User;
import engine.repository.QuizRepository;
import engine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
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
}

package engine.service;

import engine.model.Quiz;
import engine.model.QuizRequest;
import engine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> getQuizById(Long id) {
        return quizRepository.findById(id);
    }

    public Quiz addQuiz(QuizRequest quizRequest) {
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
        return quizRepository.save(quiz);
    }
}

package engine.service;

import engine.model.Quiz;
import engine.model.QuizRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private static final Quiz DEFAULT_QUIZ_1 = new Quiz(
            0L,
            "The Java Logo",
            "What is depicted on the Java logo?",
            List.of("Robot", "Tea leaf", "Cup of coffee", "Bug"),
            2
    );

    private static final Quiz DEFAULT_QUIZ_2 = new Quiz(
            1L,
            "The Ultimate Question",
            "What is the answer to the Ultimate Question of Life, the Universe and Everything?",
            List.of("Everything goes right", "42", "2+2=4", "11011100"),
            1
    );

    private final List<Quiz> quizzes = new ArrayList<>();

    public List<Quiz> getAllQuizzes() {
        return List.copyOf(quizzes);
    }

    public Optional<Quiz> getQuizById(Long id) {
        return quizzes.stream().filter(quiz -> quiz.getId().equals(id)).findFirst();
    }

    public Quiz addQuiz(QuizRequest quizRequest) {
        Long newQuizId = quizzes.stream().mapToLong(Quiz::getId).max().orElse(-1L) + 1L;
        Quiz quiz = new Quiz(newQuizId, quizRequest.getTitle(), quizRequest.getText(), quizRequest.getOptions(), quizRequest.getAnswerIndex());
        quizzes.add(quiz);
        return quiz;
    }
}

package engine.service;

import engine.model.Quiz;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    private static final Quiz DEFAULT_QUIZ = new Quiz(
            "The Java Logo",
            "What is depicted on the Java logo?",
            List.of("Robot", "Tea leaf", "Cup of coffee", "Bug"),
            2
    );

    private static final List<Quiz> QUIZ_LIST = List.of(DEFAULT_QUIZ);

    public Quiz getQuiz() {
        return QUIZ_LIST.stream().findFirst().orElse(DEFAULT_QUIZ);
    }
}

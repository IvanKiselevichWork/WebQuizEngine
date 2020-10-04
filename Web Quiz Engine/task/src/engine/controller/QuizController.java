package engine.controller;

import engine.model.Quiz;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/quiz")
    public Quiz getQuiz() {
        return quizService.getQuiz();
    }

    @PostMapping("/quiz")
    public ResponseEntity<?> solveQuiz(@RequestParam(name = "answer") int answerIndex) {
        Map<Object, Object> response = new HashMap<>();
        if (quizService.getQuiz().getAnswerIndex() == answerIndex) {
            response.put("success", true);
            response.put("feedback", "Congratulations, you're right!");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("feedback", "Wrong answer! Please, try again.");
            return ResponseEntity.ok(response);
        }
    }
}

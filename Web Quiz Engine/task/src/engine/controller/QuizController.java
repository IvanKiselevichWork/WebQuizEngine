package engine.controller;

import engine.model.Quiz;
import engine.model.QuizRequest;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/quizzes/{id}")
    public Quiz getQuiz(@PathVariable Long id) {
        Optional<Quiz> optionalQuiz = quizService.getQuizById(id);
        if (optionalQuiz.isPresent()) {
            return optionalQuiz.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/quizzes")
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @PostMapping("/quizzes/{id}/solve")
    public ResponseEntity<?> solveQuiz(@RequestParam(name = "answer") int answerIndex, @PathVariable Long id) {
        Optional<Quiz> optionalQuiz = quizService.getQuizById(id);
        if (optionalQuiz.isPresent()) {
            Map<Object, Object> response = new HashMap<>();
            if (optionalQuiz.get().getAnswerIndex() == answerIndex) {
                response.put("success", true);
                response.put("feedback", "Congratulations, you're right!");
            } else {
                response.put("success", false);
                response.put("feedback", "Wrong answer! Please, try again.");
            }
            return ResponseEntity.ok(response);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping(value = "/quizzes", consumes = "application/json")
    public Quiz addQuiz(@RequestBody QuizRequest quizRequest) {
        return quizService.addQuiz(quizRequest);
    }
}

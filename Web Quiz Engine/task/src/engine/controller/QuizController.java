package engine.controller;

import engine.model.Quiz;
import engine.model.QuizRequest;
import engine.model.SolveRequest;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> solveQuiz(@RequestBody @Valid SolveRequest solveRequest, @PathVariable Long id) {
        Optional<Quiz> optionalQuiz = quizService.getQuizById(id);
        if (optionalQuiz.isPresent()) {
            Map<Object, Object> response = new HashMap<>();
            boolean isRequestAnswerRight =
                    Arrays.stream(optionalQuiz.get().getAnswers()).collect(Collectors.toList())
                    .equals(solveRequest.getAnswer());
            if (isRequestAnswerRight) {
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
    public Quiz addQuiz(@RequestBody @Valid QuizRequest quizRequest, Authentication authentication) {
        return quizService.addQuiz(quizRequest, authentication.getName());
    }

    @DeleteMapping(value = "/quizzes/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long id, Authentication authentication) {
        quizService.deleteQuiz(id, authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/quizzes/{id}")
    public Quiz updateQuiz(@PathVariable Long id, Authentication authentication, @RequestBody @Valid QuizRequest quizRequest) {
        return quizService.updateQuiz(id, authentication.getName(), quizRequest);
    }
}

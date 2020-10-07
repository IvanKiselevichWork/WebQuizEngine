package engine.model;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface SolutionProjection {

    @Value("#{target.quiz.id}")
    Long getId();
    LocalDateTime getCompletedAt();
}

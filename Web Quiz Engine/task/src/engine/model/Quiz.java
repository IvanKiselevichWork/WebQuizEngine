package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Quiz {
    private final String title;
    private final String text;
    private final List<String> options;
    @JsonIgnore
    private final int answerIndex;
}

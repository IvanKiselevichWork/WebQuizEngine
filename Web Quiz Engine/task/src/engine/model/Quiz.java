package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class Quiz {
    private Long id;
    private String title;
    private String text;
    private List<String> options;
    @JsonIgnore
    private List<Integer> answer;
}

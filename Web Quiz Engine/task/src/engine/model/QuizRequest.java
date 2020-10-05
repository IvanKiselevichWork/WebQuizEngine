package engine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    @NotEmpty
    @Size(min = 2)
    private List<String> options;
    private List<Integer> answer;
}

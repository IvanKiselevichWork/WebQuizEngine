package engine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequest {

    @Pattern(regexp = "[a-zA-Z\\d]+@[a-zA-Z\\d]+\\.[a-zA-Z]+")
    private String email;
    @Pattern(regexp = ".{5,}")
    private String password;
}

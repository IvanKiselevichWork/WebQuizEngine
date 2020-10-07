package engine.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Quiz> quizzes;
    @OneToMany(mappedBy = "user")
    private List<Solution> solutions;
}

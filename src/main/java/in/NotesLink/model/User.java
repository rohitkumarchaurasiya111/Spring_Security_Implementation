package in.NotesLink.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    //This will ensure that username is always unique -> which helps in authentication
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    private String role;
}

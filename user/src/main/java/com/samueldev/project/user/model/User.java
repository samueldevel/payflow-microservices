package com.samueldev.project.user.model;

import com.samueldev.project.user.role.SecurityRole;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Data
@Builder
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@With
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Email(message = "email format is does not valid")
    private String email;

    private String username;

    private String password;

    private SecurityRole role;

    private boolean enabled;

    private boolean locked;

    public User(User user) {
        this.id = user.id;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.email = user.email;
        this.username = user.username;
        this.password = user.password;
        this.role = user.role;
        this.enabled = user.enabled;
        this.locked = user.locked;
    }

    public void setupToSignUp(BCryptPasswordEncoder encoder) {
        this.password = encoder.encode(this.password);
        this.enabled = true;
        this.locked = true;
    }

    public void setupAfterConfirmed() {
        this.enabled = true;
        this.locked = false;
    }
}

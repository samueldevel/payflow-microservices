package com.samueldev.project.user.request;

import com.samueldev.project.user.role.SecurityRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestBody {
    private String firstName;

    private String lastName;

    @Email(message = "email format does not is valid")
    private String email;

    private String username;

    private String password;

    private SecurityRole role;
}
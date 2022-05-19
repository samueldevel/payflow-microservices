package com.samueldev.project.user.response;

import com.samueldev.project.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponseBody {
    private static final String SIGN_UP_SUCCESSFULLY_MSG =
            "Congratulations, your account was successfully saved in our database," +
                    " we will need which you confirm account in your email";

    private int status;
    private String email;
    private String username;
    private String message;

    public static SignUpResponseBody buildWithUser(User user) {
        return SignUpResponseBody.builder()
                .status(HttpStatus.OK.value())
                .email(user.getEmail())
                .username(user.getUsername())
                .message(SIGN_UP_SUCCESSFULLY_MSG).build();
    }
}

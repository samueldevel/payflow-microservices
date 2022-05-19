package com.samueldev.project.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class SignInResponseBody {
    private String role;
    private String token;


}

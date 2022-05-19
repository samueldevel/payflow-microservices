package com.samueldev.project.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivationEmailRequestBody {
    private String firstName;
    private String lastName;
    private String to;
    private String activationLink;
}

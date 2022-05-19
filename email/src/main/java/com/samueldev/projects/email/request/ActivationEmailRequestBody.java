package com.samueldev.projects.email.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivationEmailRequestBody {
    private String firstName;
    private String lastName;
    private String to;
    private String activationLink;
}

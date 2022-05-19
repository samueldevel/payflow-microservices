package com.samueldev.project.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BadRequestResponseBody {
    private int status;
    private String message;
    private String developerMessage;

}
package com.idms.commons.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 7383594478474168441L;

    private int status = 401;
    private String message = "";

    public AuthenticationException(String message) {
        super(message);
        this.message = message;
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}

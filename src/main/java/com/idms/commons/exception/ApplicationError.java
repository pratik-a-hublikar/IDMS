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
public class ApplicationError extends RuntimeException{


    @Serial
    private static final long serialVersionUID = -825011547495498931L;

    private int status = 500;
    private String message = "";

    public ApplicationError(String message) {
        super(message);
        this.message = message;
    }

    public ApplicationError(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

}

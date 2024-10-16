package com.idms.commons.handler;

import com.idms.commons.constant.ConstantMessages;
import com.idms.commons.exception.ApplicationError;
import com.idms.commons.exception.AuthenticationException;
import com.idms.model.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class IDMSExceptionHandler {

    @ExceptionHandler({Exception.class,RuntimeException.class})
    public ResponseEntity<CommonResponse<String>> handleAuthException(final Exception exception){
        return ResponseEntity.status(HttpStatus.valueOf(500)).body(new CommonResponse<>(500, ConstantMessages.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<CommonResponse<String>> handleAuthException(final AuthenticationException exception){
        return ResponseEntity.status(HttpStatus.valueOf(exception.getStatus())).body(new CommonResponse<>(exception.getStatus(),exception.getMessage()));
    }

    @ExceptionHandler({ApplicationError.class})
    public ResponseEntity<CommonResponse<String>> handleAuthException(final ApplicationError exception){
        return ResponseEntity.status(HttpStatus.valueOf(exception.getStatus())).body(new CommonResponse<>(exception.getStatus(),exception.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<CommonResponse<Map<String,String>>> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception){
        List<FieldError> fieldErrors = exception.getBindingResult().getAllErrors().stream().map(e -> (FieldError) e).toList();
        return buildValidationError(fieldErrors);
    }

    ResponseEntity<CommonResponse<Map<String,String>>> buildValidationError(List<FieldError> fieldErrors){
        HashMap<String, String> responseMap= new HashMap<>();
        fieldErrors.forEach(fieldError -> responseMap.put(fieldError.getField(),fieldError.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.valueOf(401)).body(new CommonResponse<>(401, Collections.singletonList(responseMap)));

    }
}

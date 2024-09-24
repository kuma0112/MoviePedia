package com.supershy.moviepedia.common.exception;

import com.supershy.moviepedia.member.dto.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // 에러 메시지들을 JSON으로 변환해 응답 메시지에 포함
        return ResponseEntity.badRequest().body(new MessageResponse(errors.toString()));
    }

    @ExceptionHandler({IllegalArgumentException.class, InvalidCredentialsException.class,
            MemberNotFoundException.class, RegistrationException.class})
    public ResponseEntity<MessageResponse> handleBadRequestExceptions(Exception ex) {
        return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> handleRuntimeException(Exception ex) {
        return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
    }
}

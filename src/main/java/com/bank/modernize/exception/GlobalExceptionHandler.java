package com.bank.modernize.exception;

import com.bank.modernize.dto.ApiResponse;
<<<<<<< HEAD
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
=======
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
>>>>>>> origin/main
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

<<<<<<< HEAD
=======
    // ================= VALIDATION ERROR (DTO @Valid) =================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidation(MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");

        ApiResponse response = new ApiResponse(
                "FAILED",
                errorMessage,
                null
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // ================= CONSTRAINT VIOLATION =================
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handleConstraint(ConstraintViolationException ex) {

        ApiResponse response = new ApiResponse(
                "FAILED",
                ex.getMessage(),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // ================= RUNTIME EXCEPTION =================
>>>>>>> origin/main
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntime(RuntimeException ex) {

        ApiResponse response = new ApiResponse(
                "FAILED",
                ex.getMessage(),
<<<<<<< HEAD
                null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

=======
                null
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // ================= GENERAL EXCEPTION =================
>>>>>>> origin/main
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGeneral(Exception ex) {

        ApiResponse response = new ApiResponse(
                "FAILED",
                "Unexpected server error",
<<<<<<< HEAD
                null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
=======
                null
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
>>>>>>> origin/main

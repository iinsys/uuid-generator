package com.iinsys.uuid.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

/**
 * Global exception handler for the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles IllegalArgumentException.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                Instant.now().toString()
        );
        
        return ResponseEntity.badRequest().body(error);
    }

    /**
     * Handles all other exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {
        
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred",
                request.getDescription(false).replace("uri=", ""),
                Instant.now().toString()
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * Error response record.
     */
    @Schema(description = "Error response object")
    public record ErrorResponse(
            @Schema(description = "HTTP status code", example = "400")
            int status,

            @Schema(description = "Error type", example = "Bad Request")
            String error,

            @Schema(description = "Error message", example = "Count must be between 1 and 1000")
            String message,

            @Schema(description = "Request path", example = "/api/uuid/bulk")
            String path,

            @Schema(description = "Timestamp of the error", example = "2025-12-12T10:30:00.000Z")
            String timestamp
    ) {}
}

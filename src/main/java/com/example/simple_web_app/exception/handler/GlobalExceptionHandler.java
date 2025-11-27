package com.example.simple_web_app.exception.handler;

import com.example.simple_web_app.exception.*;
import com.example.simple_web_app.exception.business.DuplicateProductException;
import com.example.simple_web_app.exception.business.InvalidProductStateException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // --- Not Found ---
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ProductNotFoundException ex, HttpServletRequest request) {
        log.error("ProductNotFoundException: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request, ex.getCode(), null);
    }

    // --- Business Exceptions ---
    @ExceptionHandler({DuplicateProductException.class, InvalidProductStateException.class})
    public ResponseEntity<ApiError> handleBusiness(BadRequestException ex, HttpServletRequest request) {
        log.error("BusinessException: {}", ex.getMessage(), ex);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request, ex.getCode(), null);
    }

    // --- Internal Server Error ---
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ApiError> handleInternal(InternalServerErrorException ex, HttpServletRequest request) {
        log.error("InternalServerErrorException: {}", ex.getMessage(), ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request, ex.getCode(), null);
    }

    // --- Validation Errors ---
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ValidationError> validationErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> ValidationError.builder()
                        .field(err.getField())
                        .rejectedValue(err.getRejectedValue())
                        .message(err.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        log.error("Validation failed: {}", validationErrors, ex);

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                request,
                "VALIDATION_ERROR",
                validationErrors
        );
    }

    // --- General / Unhandled Exceptions ---
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneral(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception: {}", ex.getMessage(), ex);
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred",
                request,
                "UNEXPECTED_ERROR",
                null
        );
    }

    // --- Helper to build ApiError consistently ---
    private ResponseEntity<ApiError> buildErrorResponse(HttpStatus status, String message, HttpServletRequest request, String code, Object details) {
        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.parse(OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)))
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .code(code)
                .details(details)
                .build();
        return new ResponseEntity<>(error, status);
    }
}

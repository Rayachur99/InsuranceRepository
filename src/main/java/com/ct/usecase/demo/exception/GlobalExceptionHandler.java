package com.ct.usecase.demo.exception;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ct.usecase.demo.dto.ApiError;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // =======================
    // 404 - Resource Not Found
    // =======================
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        log.warn("Resource not found. method={}, path={}, message={}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMessage());

        return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    // =======================
    // 409 - Duplicate Resource
    // =======================
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiError> handleDuplicate(
            DuplicateResourceException ex,
            HttpServletRequest request) {

        log.warn("Duplicate resource. method={}, path={}, message={}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMessage());

        return buildError(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    // =======================
    // 400 - Validation
    // =======================
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiError> handleValidation(
            ValidationException ex,
            HttpServletRequest request) {

        log.warn("Validation failed. method={}, path={}, message={}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMessage());

        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    // =======================
    // 401 - Authentication Failure
    // =======================
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiError> handleInvalidCredentials(
            InvalidCredentialsException ex,
            HttpServletRequest request) {

        log.warn("Authentication failure. method={}, path={}, reason={}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMessage());

        return buildError(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }

    // =======================
    // 422 - Business Rule Violation
    // =======================
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusiness(
            BusinessException ex,
            HttpServletRequest request) {

        log.warn("Business rule violation. method={}, path={}, message={}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMessage());

        return buildError(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), request);
    }

    // =======================
    // 403 - Forbidden
    // =======================
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiError> handleForbidden(
            ForbiddenException ex,
            HttpServletRequest request) {

        log.warn("Access denied. method={}, path={}, message={}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMessage());

        return buildError(HttpStatus.FORBIDDEN, ex.getMessage(), request);
    }

    // =======================
    // 409 - Generic Conflict
    // =======================
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> handleConflict(
            ConflictException ex,
            HttpServletRequest request) {

        log.warn("Conflict detected. method={}, path={}, message={}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMessage());

        return buildError(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    // =======================
    // 409 - Database Integrity Violation
    // =======================
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolation(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        String safeMessage = "Resource already exists";

        if (ex.getRootCause() != null &&
            ex.getRootCause().getMessage() != null &&
            ex.getRootCause().getMessage().toLowerCase().contains("username")) {
            safeMessage = "Username already exists for this organization";
        }

        log.error("Database integrity violation. method={}, path={}, rootCause={}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMostSpecificCause().getMessage(),
                ex);

        return buildError(HttpStatus.CONFLICT, safeMessage, request);
    }

    // =======================
    // 500 - Unexpected Exception
    // =======================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleFallback(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unhandled exception. method={}, path={}",
                request.getMethod(),
                request.getRequestURI(),
                ex);

        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected server error",
                request
        );
    }

    // =======================
    // Centralized Error Builder
    // =======================
    private ResponseEntity<ApiError> buildError(
            HttpStatus status,
            String message,
            HttpServletRequest request) {

        ApiError apiError = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                status.name(),
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(apiError);
    }
}

package com.bessisebzemeyve.exception;

import com.bessisebzemeyve.controller.UserController;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(HttpServletRequest httpServletRequest, RuntimeException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return new ResponseEntity<>(Map.of("message", "Access denied"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(HttpServletRequest httpServletRequest, RuntimeException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(HttpServletRequest httpServletRequest, RuntimeException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return new ResponseEntity<>(Map.of("message", "At least one content with this information exists."), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(HttpServletRequest httpServletRequest, RuntimeException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(HttpServletRequest httpServletRequest, RuntimeException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRunTimeException(HttpServletRequest httpServletRequest, RuntimeException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return new ResponseEntity<>(Map.of("message", "Unknown error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodNotValidException(HttpServletRequest httpServletRequest, RuntimeException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return new ResponseEntity<>(Map.of("message", "Validation error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

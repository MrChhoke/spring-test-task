package ua.bondar.controller.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void handleConflict(
            HttpServletResponse response,
            Exception exception) throws IOException {
        Map<String, String> errors = new HashMap<>();
        errors.put("error_message", exception.getMessage());

        setHeaders(response, exception.getMessage());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        log.info("Handle exception in GlobalControllerExceptionHandler: {}", exception.getMessage());
        new ObjectMapper().writeValue(response.getOutputStream(), errors);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public void handleValidationExceptions(
            HttpServletResponse response,
            MethodArgumentNotValidException exception) throws IOException {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        setHeaders(response, exception.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        log.info("Handle exception in GlobalControllerExceptionHandler: {}", exception.getMessage());
        new ObjectMapper().writeValue(response.getOutputStream(), errors);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public void handleValidationExceptions(
            HttpServletResponse response,
            ConstraintViolationException exception) throws IOException {
        Map<String, String> errors = new HashMap<>();
        String error = exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.joining(","));
        errors.put("error_message", error);

        setHeaders(response, error);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        log.info("Handle exception in GlobalControllerExceptionHandler: {}", exception.getMessage());
        new ObjectMapper().writeValue(response.getOutputStream(), errors);
    }

    private void setHeaders(HttpServletResponse response, String causeError) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("error", causeError);
    }
}

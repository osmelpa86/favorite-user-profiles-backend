package es.firmae.demo.core.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@RestControllerAdvice
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ExceptionResponseHandler {

    private static final int ERROR_CODE_AlreadyExistsException = 1;
    private static final int ERROR_CODE_ResourceNotFoundException = 2;
    private static final int ERROR_CODE_ConstraintViolationException_MODEL = 3;
    private final ErrorContentHandler contentHandler;

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorInfo> alreadyExist(HttpServletRequest request, AlreadyExistsException e) {
        String errorMessage = contentHandler.alreadyExistAlertMessage(e.getResourceName(), e.getFieldName(), String.valueOf(e.getFieldValue()));

        HttpStatus status = HttpStatus.CONFLICT;
        ErrorInfo errorInfo = new ErrorInfo(
                request.getRequestURI(),
                request.getMethod(),
                errorMessage,
                status.getReasonPhrase(),
                status.value(),
                ERROR_CODE_AlreadyExistsException,
                "ERROR_CODE_AlreadyExistsException");

        return new ResponseEntity<>(errorInfo, contentHandler.alreadyExistAlert(errorMessage), status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorInfo> notFound(HttpServletRequest request, ResourceNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String errorMessage = contentHandler.notFoundAlertMessage(e.getResourceName(), e.getFieldName(), String.valueOf(e.getFieldValue()));
        ErrorInfo errorInfo = new ErrorInfo(
                request.getRequestURI(),
                request.getMethod(),
                errorMessage,
                status.getReasonPhrase(),
                status.value(),
                ERROR_CODE_ResourceNotFoundException,
                "ERROR_CODE_ResourceNotFoundException");

        return new ResponseEntity<>(errorInfo, contentHandler.notFoundAlert(errorMessage), status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorInfo> methodValidationViolationConstraintHandler(HttpServletRequest request, MethodArgumentNotValidException e) {

        // get spring errors
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        // convert errors to standard string
        StringBuilder errorMessage = new StringBuilder();

        AtomicInteger s = new AtomicInteger(fieldErrors.size());
        fieldErrors.forEach(f -> {
            errorMessage.append(f.getDefaultMessage());
            s.getAndDecrement();
            if (s.getPlain() != 0) {
                errorMessage.append(", ");
            }
        });

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        String errorResponse = contentHandler.validationInModelAlertMessage(errorMessage.toString());
        ErrorInfo errorInfo = new ErrorInfo(
                request.getRequestURI(),
                request.getMethod(),
                errorResponse,
                status.getReasonPhrase(),
                status.value(),
                ERROR_CODE_ConstraintViolationException_MODEL,
                "ERROR_CODE_ConstraintViolationException-javax-validation");

        return new ResponseEntity<>(errorInfo, contentHandler.validationInModelAlert(errorResponse), status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorInfo> methodValidationViolationConstraintHandler(HttpServletRequest request, ConstraintViolationException e) {

        // get spring errors
        Set<ConstraintViolation<?>> result = e.getConstraintViolations();

        // convert errors to standard string
        StringBuilder error = new StringBuilder();

        AtomicInteger s = new AtomicInteger(result.size());
        result.forEach(f -> {
            error.append(f.getMessage());
            s.getAndDecrement();
            if (s.getPlain() != 0) {
                error.append(", ");
            }
        });

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        String errorMessage = contentHandler.validationInModelAlertMessage(error.toString());
        ErrorInfo errorInfo = new ErrorInfo(
                request.getRequestURI(),
                request.getMethod(),
                errorMessage,
                status.getReasonPhrase(),
                status.value(),
                ERROR_CODE_ConstraintViolationException_MODEL,
                "ERROR_CODE_ConstraintViolationException-javax-validation");

        return new ResponseEntity<>(errorInfo, contentHandler.validationInModelAlert(errorMessage), status);
    }
}


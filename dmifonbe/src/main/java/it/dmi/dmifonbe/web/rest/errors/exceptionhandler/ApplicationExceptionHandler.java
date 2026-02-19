package it.dmi.dmifonbe.web.rest.errors.exceptionhandler;

import it.dmi.dmifonbe.model.Response;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.HashMap;
import java.util.Map;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger log = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(value = { MicroServicesException.class })
    protected ResponseEntity<Object> MicroserviceExceptionHandler(MicroServicesException e) {
        Response error = new Response();
        error.setUserMessage(e.getUserMessage());
        if (e.getCode() == 570) e.setCode(500);
        return new ResponseEntity(error, HttpStatus.valueOf(e.getCode()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request
    ) {
        Response response = new Response();
        Map<String, String> errorMap = new HashMap<>();
        ex
            .getBindingResult()
            .getFieldErrors()
            .forEach(error -> {
                errorMap.put(error.getField(), error.getDefaultMessage());
            });
        String errorMessages = "";
        for (String key : errorMap.keySet()) {
            errorMessages += errorMap.get(key) + "; ";
        }
        response.setUserMessage(errorMessages);
        return new ResponseEntity(response, HttpStatus.valueOf(status.value()));
    }

    @ExceptionHandler(value = { ConstraintViolationException.class })
    protected ResponseEntity<Object> ConstraintViolationExceptionHandler(ConstraintViolationException e) {
        Response error = new Response();
        error.setUserMessage(e.getMessage());
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package example.api.contacts.exception;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ContactExceptionHandler extends ResponseEntityExceptionHandler{
  Logger log = LoggerFactory.getLogger(ContactExceptionHandler.class);
  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFound(
      EntityNotFoundException ex) {
    Error error = new Error(HttpStatus.NOT_FOUND);
    error.setMessage(ex.getMessage());
    log.error("{}",error.getMessage());
    return buildResponseEntity(error);
  }
  
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    BindingResult result = ex.getBindingResult();
    final List<FieldError> fieldErrors = result.getFieldErrors();
    List<String> errorList = new ArrayList<>();
    fieldErrors.forEach(error -> errorList.add(error.getField() + " : " + error.getDefaultMessage()+" "));
    Error error = new Error(HttpStatus.BAD_REQUEST);
    error.setMessage(errorList.toString());
    log.error("Bad Request {}",error.getMessage());
    return buildResponseEntity(error);
  }

  private ResponseEntity<Object> buildResponseEntity(Error error) {
    return new ResponseEntity<>(error, error.getStatus());
  }

}

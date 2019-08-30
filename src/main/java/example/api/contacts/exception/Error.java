package example.api.contacts.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Error {

  private HttpStatus status;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;
  private String message;


  private Error() {
    timestamp = LocalDateTime.now();
  }

  Error(HttpStatus status) {
    this();
    this.status = status;
  }
  
  Error(HttpStatus status, String message) {
    this();
    this.status = status;
    this.message = message;
  }

  Error(HttpStatus status, Throwable ex) {
    this();
    this.status = status;
    this.message = ex.getLocalizedMessage();
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
  
  

}

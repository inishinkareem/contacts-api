package example.api.contacts.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class Name {
  
  @NotEmpty(message = "First Name is missing")
  @Size(max = 50)
  @NotNull
  private String first;
  @Size(max = 50)
  private String middle;
  
  @NotEmpty(message = "Last Name is missing")
  @Size(max = 50)
  @NotNull
  private String last;

  public Name() {
    super();
  }

  public Name(String first, String middle, String last) {
    super();
    this.first = first;
    this.middle = middle;
    this.last = last;
  }

  public String getFirst() {
    return first;
  }

  public void setFirst(String first) {
    this.first = first;
  }

  public String getMiddle() {
    return middle;
  }

  public void setMiddle(String middle) {
    this.middle = middle;
  }

  public String getLast() {
    return last;
  }

  public void setLast(String last) {
    this.last = last;
  }

}

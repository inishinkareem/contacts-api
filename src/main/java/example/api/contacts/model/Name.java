package example.api.contacts.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Embeddable
@ApiModel(description = "Class representing Name of the Contact" )
public class Name {
  
  @NotEmpty(message = "First Name is missing")
  @Size(max = 50)
  @NotNull
  @ApiModelProperty(value = "Contact's First Name", 
  allowEmptyValue = false, dataType = "String", 
  position = 0, required = true, example = "Harold")
  private String first;
  
  @Size(max = 50)
  @ApiModelProperty(value = "Contact's Middle Name", 
  allowEmptyValue = false, dataType = "String", 
  position = 1, required = false, example = "Francis")
  private String middle;
  
  @NotEmpty(message = "Last Name is missing")
  @Size(max = 50)
  @NotNull
  @ApiModelProperty(value = "Contact's Last Name", 
  allowEmptyValue = false, dataType = "String", 
  position = 2, required = true, example = "Gilkey")
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((first == null) ? 0 : first.hashCode());
    result = prime * result + ((last == null) ? 0 : last.hashCode());
    result = prime * result + ((middle == null) ? 0 : middle.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Name other = (Name) obj;
    if (first == null) {
      if (other.first != null)
        return false;
    } else if (!first.equals(other.first))
      return false;
    if (last == null) {
      if (other.last != null)
        return false;
    } else if (!last.equals(other.last))
      return false;
    if (middle == null) {
      if (other.middle != null)
        return false;
    } else if (!middle.equals(other.middle))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Name [first=" + first + ", middle=" + middle + ", last=" + last + "]";
  }

  
  
}

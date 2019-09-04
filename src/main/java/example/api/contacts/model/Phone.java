package example.api.contacts.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;

@Entity
@Table(name = "phone")
@ApiModel(description = "Class representing Contact's Phone" )
public class Phone {


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "number")
  @Pattern(regexp = "[0-9]{3}-[0-9]{3}-[0-9]{4}", message = "Incorrect phone format. Correct Format is 111-111-1111")
  @NotEmpty(message = "Phone Number is missing")
  @NotNull
  @Size(max = 12)
  @ApiModelProperty(value = "Contact's Phone Number", 
  allowEmptyValue = false, dataType = "String", 
  position = 1, required = true, example = "302-611-9148")
  private String number;

  @Column(name = "type")
  @Pattern(regexp = "home|mobile|work", message = "Incorrect phone type. Phone Type must be one of home, work or mobile")
  @NotEmpty(message = "Phone Type is missing")
  @Size(max = 6)
  @NotNull
  @ApiModelProperty(value = "Contact's Phone Type", 
  allowEmptyValue = false, dataType = "String", 
  position = 2, required = true, example = "mobile")
  private String type;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="contact_id")
  @ApiModelProperty(value = "Phone's Contact ID",
  position =0, hidden = true , name = "contactId")
  private Contact contact;

  public Phone() {
  }

  public Phone(
      @Pattern(regexp = "[0-9]{3}-[0-9]{3}-[0-9]{4}", message = "Incorrect phone format. Correct Format is 111-111-1111") String number,
      @Pattern(regexp = "home|mobile|work", message = "Incorrect phone type. Phone Type must be one of home, work or mobile") String type) {
    super();
    this.number = number;
    this.type = type;
  }


  public String getNumber() {
    return number;
  }



  public void setNumber(String number) {
    this.number = number;
  }



  public String getType() {
    return type;
  }



  public void setType(String type) {
    this.type = type;
  }


  public void setContact(Contact contact) {
    this.contact = contact;
  }
  

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((number == null) ? 0 : number.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
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
    Phone other = (Phone) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (number == null) {
      if (other.number != null)
        return false;
    } else if (!number.equals(other.number))
      return false;
    if (type == null) {
      if (other.type != null)
        return false;
    } else if (!type.equals(other.type))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Phone [id=" + id + ", number=" + number + ", type=" + type + ", contact=" + contact + "]";
  }



}

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

@Entity
@Table(name = "phone")
public class Phone {


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "number")
  @Pattern(regexp = "[0-9]{3}-[0-9]{3}-[0-9]{4}", message = "Incorrect phone format. Correct Format is 111-111-1111")
  @NotEmpty(message = "Phone Number is missing")
  @NotNull
  @Size(max = 12)
  
  private String number;
  @Column(name = "type")
  @Pattern(regexp = "home|mobile|work", message = "Incorrect phone type. Phone Type must be one of home, work or mobile")
  @NotEmpty(message = "Phone Type is missing")
  @Size(max = 6)
  @NotNull
  private String type;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="contact_id")
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

  public Phone(
      @Pattern(regexp = "[0-9]{3}-[0-9]{3}-[0-9]{4}", message = "Incorrect phone format. Correct Format is 111-111-1111") String number,
      @Pattern(regexp = "home|mobile|work", message = "Incorrect phone type. Phone Type must be one of home, work or mobile") String type,
      Contact contact) {
    super();
    this.number = number;
    this.type = type;
    this.contact = contact;
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
  public String toString() {
    return "Phone [id=" + id + ", number=" + number + ", type=" + type + ", contact=" + contact + "]";
  }



}

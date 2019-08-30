package example.api.contacts.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class Address {
  
  @NotEmpty(message = "Street is missing")
  @Size(max = 100)
  @NotNull
  private String street;
  
  @NotEmpty(message = "City is missing")
  @Size(max = 35)
  @NotNull
  private String city;
  
  @NotEmpty(message = "State is missing")
  @Size(max = 35)
  @NotNull
  private String state;
  
  @NotEmpty(message = "Zip is missing")
  @Size(max = 5)
  @NotNull
  private String zip;
  

  public Address() {
    super();
  }

  public Address(String street, String city, String state, String zip) {
    super();
    this.street = street;
    this.city = city;
    this.state = state;
    this.zip = zip;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }
  
}

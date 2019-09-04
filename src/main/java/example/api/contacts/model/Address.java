package example.api.contacts.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Embeddable
@ApiModel(description = "Class representing the Address of the Contact" )
public class Address {
  
  @NotEmpty(message = "Street is missing")
  @Size(max = 100)
  @NotNull
  @ApiModelProperty(value = "Contact's Street", 
  allowEmptyValue = false, dataType = "String", 
  position = 0, required = true, example = "8360 High Autumn Row")
  private String street;
  
  @NotEmpty(message = "City is missing")
  @Size(max = 35)
  @NotNull
  @ApiModelProperty(value = "Contact's City", 
  allowEmptyValue = false, dataType = "String", 
  position = 1, required = true, example = "Cannon")
  private String city;
  
  @NotEmpty(message = "State is missing")
  @Size(max = 35)
  @NotNull
  @ApiModelProperty(value = "Contact's Street", 
  allowEmptyValue = false, dataType = "String", 
  position = 2, required = true, example = "Delaware")
  private String state;
  
  @NotEmpty(message = "Zip is missing")
  @Size(max = 5)
  @NotNull
  @ApiModelProperty(value = "Contact's Zip", 
  allowEmptyValue = false, dataType = "String", 
  position = 3, required = true, example = "19797")
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((city == null) ? 0 : city.hashCode());
    result = prime * result + ((state == null) ? 0 : state.hashCode());
    result = prime * result + ((street == null) ? 0 : street.hashCode());
    result = prime * result + ((zip == null) ? 0 : zip.hashCode());
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
    Address other = (Address) obj;
    if (city == null) {
      if (other.city != null)
        return false;
    } else if (!city.equals(other.city))
      return false;
    if (state == null) {
      if (other.state != null)
        return false;
    } else if (!state.equals(other.state))
      return false;
    if (street == null) {
      if (other.street != null)
        return false;
    } else if (!street.equals(other.street))
      return false;
    if (zip == null) {
      if (other.zip != null)
        return false;
    } else if (!zip.equals(other.zip))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Address [street=" + street + ", city=" + city + ", state=" + state + ", zip=" + zip + "]";
  }
  
  
}

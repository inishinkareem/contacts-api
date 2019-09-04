package example.api.contacts.model;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;

@Entity
@Table(name="contact")
@ApiModel(description = "Class representing the Contact" )
@NotNull
public class Contact {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @ApiModelProperty(value = "Unique identifier of the Contact. No two Contacts can have the same id.",
  accessMode = AccessMode.READ_ONLY, position =0, hidden = true)
  private Long id;

  @Embedded
  @Valid
  @NotNull
  @AttributeOverrides(value = {
      @AttributeOverride(name = "first", column = @Column(name="first_name")),
      @AttributeOverride(name = "middle", column = @Column(name="middle_name")),
      @AttributeOverride(name = "last", column = @Column(name="last_name"))
  })
  @ApiModelProperty(value = "Name of the Contact.", position =0, required = true)
  private Name name;

  @Embedded
  @Valid
  @NotNull
  @ApiModelProperty(value = "Address of the Contact.", position =1, required = true)
  private Address address;

  @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL)
  @Valid
  @NotNull
  @ApiModelProperty(value = "Phone number of the Contact.", position =2, required = true)
  private Set<Phone> phone;

  @Column(name = "email", unique = true)
  @Email(message = "Invalid email")
  @Size(max = 100)
  @NotEmpty
  @ApiModelProperty(value = "Email of the Contact", 
  allowEmptyValue = false, dataType = "String", 
  position = 3, required = true, example = "harold.gilkey@yahoo.com")
  private String email;



  public Contact() {
    super();
  }



  public Contact(@Valid @NotNull Name name, @Valid @NotNull Address address,
      @Email(message = "Invalid email") @Size(max = 100) @NotNull String email) {
    super();
    this.name = name;
    this.address = address;
    this.email = email;
  }




  public Contact(@Valid @NotNull Name name, @Valid @NotNull Address address, @Valid @NotEmpty Set<Phone> phone,
      @Email(message = "Invalid email") @Size(max = 100) @NotNull String email) {
    super();
    this.name = name;
    this.address = address;
    this.phone = phone;
    this.email = email;
  }



  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Name getName() {
    return name;
  }

  public void setName(Name name) {
    this.name = name;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public Set<Phone> getPhone() {
    return phone;
  }

  public void setPhone(Set<Phone> phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }




  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
    Contact other = (Contact) obj;
    if (address == null) {
      if (other.address != null)
        return false;
    } else if (!address.equals(other.address))
      return false;
    if (email == null) {
      if (other.email != null)
        return false;
    } else if (!email.equals(other.email))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }



  @Override
  public String toString() {
    return "Contact [id=" + id + ", name=" + name + ", address=" + address + ", phone=" + phone + ", email=" + email
        + "]";
  }
  
  

}

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

@Entity
@Table(name="contact")
public class Contact {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  
  @Embedded
  @Valid
  @NotNull
  @AttributeOverrides(value = {
  @AttributeOverride(name = "first", column = @Column(name="first_name")),
  @AttributeOverride(name = "middle", column = @Column(name="middle_name")),
  @AttributeOverride(name = "last", column = @Column(name="last_name"))
  })
  private Name name;
  
  @Embedded
  @Valid
  @NotNull
  private Address address;
  
  @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL)
  @Valid
  @NotEmpty
  private Set<Phone> phone;
  
  @Column(name = "email", unique = true)
  @Email(message = "Invalid email")
  @Size(max = 100)
  @NotNull
  private String email;
  
  

  public Contact() {
    super();
  }

  public Contact(Name name, Address address, String email, Set<Phone> phone ) {
    super();
    this.name = name;
    this.address = address;
    this.email = email;
    this.phone = phone;
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

}

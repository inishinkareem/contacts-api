package example.api.contacts;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.api.contacts.controller.ContactsController;
import example.api.contacts.model.Address;
import example.api.contacts.model.Contact;
import example.api.contacts.model.Name;
import example.api.contacts.model.Phone;
import example.api.contacts.repository.ContactRepository;
import example.api.contacts.service.ContactService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ContactsController.class)
public class ValidationUnitTests {
  
  @Autowired
  private MockMvc mvc;
  
  @MockBean
  private ContactService service;

  @MockBean
  private ContactRepository repository;
  
  @Test
  public void addContactEmailNullValidation() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<Phone> phoneSet1 = new HashSet<>();
    //incorrect email
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("West Creek", "Henrico", "Virginia", "23233"), null);
    phoneSet1.add(new Phone("804-352-4544","mobile"));
    contact1.setPhone(phoneSet1);
    mvc.perform(MockMvcRequestBuilders
        .post("/api/contacts/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contact1)))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.message").value("[email : must not be null ]"));
  }
  
  @Test
  public void addContactEmailFormatValidation() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<Phone> phoneSet1 = new HashSet<>();
    //incorrect email
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("West Creek", "Henrico", "Virginia", "23233"), "johnhotmail.com");
    phoneSet1.add(new Phone("804-352-4544","mobile"));
    contact1.setPhone(phoneSet1);
    mvc.perform(MockMvcRequestBuilders
        .post("/api/contacts/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contact1)))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.message").value("[email : Invalid email ]"));
  }
  
  @Test
  public void addContactNulNameValidation() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<Phone> phoneSet1 = new HashSet<>();
    //missing first name
    Contact contact1 = new Contact(null, new Address("West Creek", "Henrico", "Virginia", "23233"), "john@hotmail.com");
    phoneSet1.add(new Phone("804-352-4544","mobile"));
    contact1.setPhone(phoneSet1);
    mvc.perform(MockMvcRequestBuilders
        .post("/api/contacts/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contact1)))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.message").value("[name : must not be null ]"));
  }
  
  @Test
  public void addContactFirstNameValidation() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<Phone> phoneSet1 = new HashSet<>();
    //missing first name
    Contact contact1 = new Contact(new Name("", "A", "Tyler"), new Address("West Creek", "Henrico", "Virginia", "23233"), "john@hotmail.com");
    phoneSet1.add(new Phone("804-352-4544","mobile"));
    contact1.setPhone(phoneSet1);
    mvc.perform(MockMvcRequestBuilders
        .post("/api/contacts/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contact1)))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.message").value("[name.first : First Name is missing ]"));
  }
  
  @Test
  public void addContactMiddleNameValidation() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<Phone> phoneSet1 = new HashSet<>();
    //missing first name
    Contact contact1 = new Contact(new Name("John", "", "Gilkey"), new Address("West Creek", "Henrico", "Virginia", "23233"), "john@hotmail.com");
    phoneSet1.add(new Phone("804-352-4544","mobile"));
    contact1.setPhone(phoneSet1);
    mvc.perform(MockMvcRequestBuilders
        .post("/api/contacts/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contact1)))
    .andExpect(status().isCreated());
  }
  
  @Test
  public void addContactLastNameValidation() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<Phone> phoneSet1 = new HashSet<>();
    //missing first name
    Contact contact1 = new Contact(new Name("John", "Hay", ""), new Address("West Creek", "Henrico", "Virginia", "23233"), "john@hotmail.com");
    phoneSet1.add(new Phone("804-352-4544","mobile"));
    contact1.setPhone(phoneSet1);
    mvc.perform(MockMvcRequestBuilders
        .post("/api/contacts/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contact1)))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.message", containsString("Last Name is missing")));
  }
  
  
  
  
  @Test
  public void addContactAddressNullValidation() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<Phone> phoneSet1 = new HashSet<>();
    //incorrect address
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), null, "john@hotmail.com");
    phoneSet1.add(new Phone("804-352-4544","mobile"));
    contact1.setPhone(phoneSet1);
    mvc.perform(MockMvcRequestBuilders
        .post("/api/contacts/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contact1)))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.message").value("[address : must not be null ]"));
  }
  
  @Test
  public void addContactAddressStreetValidation() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<Phone> phoneSet1 = new HashSet<>();
    //incorrect address
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("", "Henrico", "Virginia", "23233"), "john@hotmail.com");
    phoneSet1.add(new Phone("804-352-4544","mobile"));
    contact1.setPhone(phoneSet1);
    mvc.perform(MockMvcRequestBuilders
        .post("/api/contacts/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contact1)))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.message").value("[address.street : Street is missing ]"));
  }
  
  @Test
  public void addContactAddressCityValidation() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<Phone> phoneSet1 = new HashSet<>();
    //incorrect address
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("test", "", "Virginia", "23233"), "john@hotmail.com");
    phoneSet1.add(new Phone("804-352-4544","mobile"));
    contact1.setPhone(phoneSet1);
    mvc.perform(MockMvcRequestBuilders
        .post("/api/contacts/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contact1)))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.message").value("[address.city : City is missing ]"));
  }
  
  @Test
  public void addContactAddressStateValidation() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<Phone> phoneSet1 = new HashSet<>();
    //incorrect address
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("test", "test", "", "23233"), "john@hotmail.com");
    phoneSet1.add(new Phone("804-352-4544","mobile"));
    contact1.setPhone(phoneSet1);
    mvc.perform(MockMvcRequestBuilders
        .post("/api/contacts/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contact1)))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.message").value("[address.state : State is missing ]"));
  }
  
  @Test
  public void addContactAddressZipValidation() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<Phone> phoneSet1 = new HashSet<>();
    //incorrect address
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("test", "test", "test", ""), "john@hotmail.com");
    phoneSet1.add(new Phone("804-352-4544","mobile"));
    contact1.setPhone(phoneSet1);
    mvc.perform(MockMvcRequestBuilders
        .post("/api/contacts/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contact1)))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.message").value("[address.zip : Zip is missing ]"));
  }
  
  @Test
  public void addContactPhoneNullValidation() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("West Creek", "Henrico", "Virginia", "23233"), "john@hotmail.com");
    System.out.println(contact1);
    mvc.perform(MockMvcRequestBuilders
        .post("/api/contacts/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contact1)))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.message").value("[phone : must not be null ]"));
  }
  
  @Test
  public void addContactPhoneTypeValidation() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<Phone> phoneSet1 = new HashSet<>();
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("test", "test", "test", "23233"), "john@hotmail.com");
    phoneSet1.add(new Phone("804-352-4544","mobil"));
    contact1.setPhone(phoneSet1);
    mvc.perform(MockMvcRequestBuilders
        .post("/api/contacts/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contact1)))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.message").value("[phone[].type : Incorrect phone type. Phone Type must be one of home, work or mobile ]"));
  }
  
  @Test
  public void addContactPhoneFormatValidation() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<Phone> phoneSet1 = new HashSet<>();
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("test", "test", "test", "23233"), "john@hotmail.com");
    phoneSet1.add(new Phone("804-3524544","mobile"));
    contact1.setPhone(phoneSet1);
    mvc.perform(MockMvcRequestBuilders
        .post("/api/contacts/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contact1)))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.message").value("[phone[].number : Incorrect phone format. Correct Format is 111-111-1111 ]"));
  }

}

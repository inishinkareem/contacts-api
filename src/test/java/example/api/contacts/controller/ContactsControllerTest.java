package example.api.contacts.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.api.contacts.model.Address;
import example.api.contacts.model.Contact;
import example.api.contacts.model.Name;
import example.api.contacts.model.Phone;
import example.api.contacts.repository.ContactRepository;
import example.api.contacts.service.ContactService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ContactsController.class)
public class ContactsControllerTest {


  @Autowired
  private MockMvc mvc;

  @MockBean
  private ContactService service;

  @MockBean
  private ContactRepository repository;
  
  

  @Test
  public void contexLoads() throws Exception {
    assertThat(ContactsController.class).isNotNull();
  }

  @Test
  public void getContactsWithDataTest() throws Exception {
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("West Creek", "Henrico", "Virginia", "23233"), "john@hotmail.com");
    Phone phone1 = new Phone("mobile", "804-352-4544", contact1);
    Set<Phone> phoneSet1 = new HashSet<>();
    phoneSet1.add(phone1);
    contact1.setPhone(phoneSet1);


    Contact contact2 = new Contact(new Name("Mike", "", "Patterson"), new Address("M2", "Henrico", "Virginia", "23294"), "mike@gmail.com");
    Phone phone2 = new Phone("work", "804-564-2323", contact2);
    Set<Phone> phoneSet2 = new HashSet<>();
    phoneSet2.add(phone2);
    contact2.setPhone(phoneSet2);


    Contact contact3 = new Contact(new Name("Sarah", "Tylor", "Parlow"), new Address("C1", "Henrico", "Virginia", "23058"), "sarah@yahoo.com");
    Phone phone3 = new Phone("home", "804-676-9787");
    Phone phone4 = new Phone("home", "804-376-1234");
    Set<Phone> phoneSet3 = new HashSet<>();
    phoneSet3.add(phone3);
    phoneSet3.add(phone4);
    contact3.setPhone(phoneSet3);

    Contact[] contArray = new Contact[] {contact1,contact2,contact3};
    given(service.getContacts()).willReturn(Arrays.asList(contArray));
    mvc.perform(get("/api/contacts"))
    .andExpect(status().isOk());

    verify(service, VerificationModeFactory.times(1)).getContacts();
    reset(service);
  }

  @Test
  public void getContactsWithoutDataTest() throws Exception {
    Contact[] contArray = new Contact[] {};
    given(service.getContacts()).willReturn(Arrays.asList(contArray));
    mvc.perform(get("/api/contacts"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$", hasSize(0)));

    verify(service, VerificationModeFactory.times(1)).getContacts();
    reset(service);
  }

  @Test
  public void getAContactSuccess() throws Exception {
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("West Creek", "Henrico", "Virginia", "23233"), "john@hotmail.com");
    Phone phone1 = new Phone("mobile", "804-352-4544", contact1);
    Set<Phone> phoneSet1 = new HashSet<>();
    phoneSet1.add(phone1);
    contact1.setPhone(phoneSet1);
    contact1.setId(1L);

    given(service.getContact(1L)).willReturn(contact1);

    mvc.perform(get("/api/contacts/{id}",1L))
    .andExpect(status().isOk())
    .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john@hotmail.com"));

    verify(service, VerificationModeFactory.times(1)).getContact(1L);
    reset(service);
  }

  @Test
  public void getAContactFailure() throws Exception {
    given(service.getContact(1L)).willThrow(new EntityNotFoundException());
    mvc.perform(get("/api/contacts/{id}",1L))
    .andExpect(status().isNotFound());

    verify(service, VerificationModeFactory.times(1)).getContact(1L);
    reset(service);
  }

  @Test
  public void addContactSuccess() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<Phone> phoneSet1 = new HashSet<>();
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("West Creek", "Henrico", "Virginia", "23233"), "john@hotmail.com");
    phoneSet1.add(new Phone("804-352-4544","mobile"));
    contact1.setId(1L);
    contact1.setPhone(phoneSet1);
    
    mvc.perform(MockMvcRequestBuilders
        .post("/api/contacts/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contact1)))
    .andExpect(status().isCreated());
  }
  
  
  @Test
  public void updateContactSuccessTest() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<Phone> phoneSet1 = new HashSet<>();
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("West Creek", "Henrico", "Virginia", "23233"), "john@hotmail.com");
    phoneSet1.add(new Phone("804-352-4544","mobile"));
    contact1.setId(1L);
    contact1.setPhone(phoneSet1);
    BDDMockito.doNothing().when(service).updateContact(1L, contact1);
    
    mvc.perform(MockMvcRequestBuilders
        .put("/api/contacts/{id}", 1L)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contact1)))
    .andExpect(status().isOk());
  }
  

  
  @Test
  public void deleteContactSuccessTest() throws Exception {
    
    mvc.perform(MockMvcRequestBuilders
        .delete("/api/contacts/{id}", 1L))
    .andExpect(status().isOk());
  }
  
  @Test
  public void deleteContactFailureTest() throws Exception {
    
    BDDMockito.doThrow(EntityNotFoundException.class).when(service).deleteContact(1L);
 
    mvc.perform(MockMvcRequestBuilders
        .delete("/api/contacts/{id}", 1L))
    .andExpect(status().isNotFound());
  }
  
  @Test
  public void addContactEmailValidation() throws Exception {
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
    .andExpect(status().isBadRequest());
  }
  
  @Test
  public void addContactNameValidation() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<Phone> phoneSet1 = new HashSet<>();
    //missing first name
    Contact contact1 = new Contact(new Name("", "A", "Tyler"), new Address("West Creek", "Henrico", "Virginia", "23233"), "johnhotmail.com");
    phoneSet1.add(new Phone("804-352-4544","mobile"));
    contact1.setPhone(phoneSet1);
    mvc.perform(MockMvcRequestBuilders
        .post("/api/contacts/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contact1)))
    .andExpect(status().isBadRequest());
  }
  
  @Test
  public void addContactAddressValidation() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<Phone> phoneSet1 = new HashSet<>();
    //incorrect address
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address(null, null, null, null), "johnhotmail.com");
    phoneSet1.add(new Phone("804-352-4544","mobile"));
    contact1.setPhone(phoneSet1);
    mvc.perform(MockMvcRequestBuilders
        .post("/api/contacts/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contact1)))
    .andExpect(status().isBadRequest());
  }
  
  @Test
  public void addContactPhoneValidation() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<Phone> phoneSet1 = new HashSet<>();
    Contact contact1 = new Contact(new Name("", "A", "Tyler"), new Address("West Creek", "Henrico", "Virginia", "23233"), "john@hotmail.com");
    //incorrect phone type
    phoneSet1.add(new Phone("804-352-4544","mobil"));
    contact1.setPhone(phoneSet1);
    mvc.perform(MockMvcRequestBuilders
        .post("/api/contacts/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contact1)))
    .andExpect(status().isBadRequest());
  }
 
}

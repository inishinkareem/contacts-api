package example.api.contacts.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

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
    Phone phone1 = new Phone("mobile", "804-352-4544");
    phone1.setContact(contact1);
    Set<Phone> phoneSet1 = new HashSet<>();
    phoneSet1.add(phone1);
    contact1.setPhone(phoneSet1);


    Contact contact2 = new Contact(new Name("Mike", "", "Patterson"), new Address("M2", "Henrico", "Virginia", "23294"), "mike@gmail.com");
    Phone phone2 = new Phone("work", "804-564-2323");
    phone2.setContact(contact2);
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

    when(service.getContacts()).thenReturn(Arrays.asList(contArray));

    mvc.perform(get("/api/contacts"))
    .andExpect(status().isOk());
  }

  @Test
  public void getContactsWithoutDataTest() throws Exception {
    Contact[] contArray = new Contact[] {};

    when(service.getContacts()).thenReturn(Arrays.asList(contArray));

    mvc.perform(get("/api/contacts"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  public void getAContactSuccess() throws Exception {
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("West Creek", "Henrico", "Virginia", "23233"), "john@hotmail.com");
    Phone phone1 = new Phone("mobile", "804-352-4544");
    phone1.setContact(contact1);
    Set<Phone> phoneSet1 = new HashSet<>();
    phoneSet1.add(phone1);
    contact1.setPhone(phoneSet1);

    when(service.getContact(1L)).thenReturn(contact1);

    mvc.perform(get("/api/contacts/{id}",1L))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.email").value("john@hotmail.com"));

  }

  @Test
  public void getAContactFailure() throws Exception {

    when(service.getContact(1L)).thenThrow(new EntityNotFoundException());

    mvc.perform(get("/api/contacts/{id}",1L))
    .andExpect(status().isNotFound());
  }

  @Test
  public void addContactSuccess() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<Phone> phoneSet1 = new HashSet<>();
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("West Creek", "Henrico", "Virginia", "23233"), "john@hotmail.com");
    phoneSet1.add(new Phone("804-352-4544","mobile"));
    contact1.setId(1L);
    contact1.setPhone(phoneSet1);

    doNothing().when(service).addContact(contact1);

    mvc.perform(post("/api/contacts/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contact1)))
    .andExpect(status().isCreated());
  }

  @Test
  public void addContactFailure() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<Phone> phoneSet1 = new HashSet<>();
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("West Creek", "Henrico", "Virginia", "23233"), "john@hotmail.com");
    phoneSet1.add(new Phone("804-352-4544","mobile"));
    contact1.setId(1L);
    contact1.setPhone(phoneSet1);

    doThrow(EntityExistsException.class).when(service).addContact(any(Contact.class));

    mvc.perform(post("/api/contacts/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contact1)))
    .andExpect(status().isBadRequest());
  }


    @Test
    public void updateContactSuccessTest() throws Exception {
      ObjectMapper objectMapper = new ObjectMapper();
      Set<Phone> phoneSet1 = new HashSet<>();
      Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("West Creek", "Henrico", "Virginia", "23233"), "john@hotmail.com");
      phoneSet1.add(new Phone("804-352-4544","mobile"));
      contact1.setId(1L);
      contact1.setPhone(phoneSet1);
      
      doNothing().when(service).updateContact(1L, contact1);
      
      mvc.perform(MockMvcRequestBuilders
          .put("/api/contacts/{id}", 1L)
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(contact1)))
      .andExpect(status().isOk());
    }

  @Test
  public void updateContactFailureTest() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Set<Phone> phoneSet1 = new HashSet<>();
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("West Creek", "Henrico", "Virginia", "23233"), "john@hotmail.com");
    phoneSet1.add(new Phone("804-352-4544","mobile"));
    contact1.setId(1L);
    contact1.setPhone(phoneSet1);

    doThrow(EntityNotFoundException.class).when(service).updateContact(eq(1L), any(Contact.class));

    mvc.perform(put("/api/contacts/{id}", 1L)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(contact1)))
    .andExpect(status().isNotFound());
  }



  @Test
  public void deleteContactSuccessTest() throws Exception {

    doNothing().when(service).deleteContact(1L);

    mvc.perform(delete("/api/contacts/{id}", 1L))
    .andExpect(status().isOk());
  }

  @Test
  public void deleteContactFailureTest() throws Exception {

    doThrow(EntityNotFoundException.class).when(service).deleteContact(1L);

    mvc.perform(delete("/api/contacts/{id}", 1L))
    .andExpect(status().isNotFound());
  }



}

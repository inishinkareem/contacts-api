package example.api.contacts.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;


import java.util.Arrays;

import example.api.contacts.model.Address;
import example.api.contacts.model.Contact;
import example.api.contacts.model.Name;
import example.api.contacts.model.Phone;
import example.api.contacts.repository.ContactRepository;
import example.api.contacts.service.ContactService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ContactsController.class)
//@SpringBootTest
public class ContactsControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private ContactService service;
  
  @MockBean
  private ContactRepository repository;

//  @Test
//  public void getContactsWithDataTest() throws Exception {
//    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("West Creek", "Henrico", "Virginia", "23233"), "john@hotmail.com", new Phone("mobile", "804-352-4544"));
//    Contact contact2 = new Contact(new Name("Mike", "", "Patterson"), new Address("M2", "Henrico", "Virginia", "23294"), "mike@gmail.com", new Phone("work", "804-564-2323"));
//    Contact contact3 = new Contact(new Name("Sarah", "Tylor", "Parlow"), new Address("C1", "Henrico", "Virginia", "23058"), "sarah@yahoo.com", new Phone("home", "804-676-9787"),new Phone("home", "804-376-1234"));
//    Contact[] contArray = new Contact[] {contact1,contact2,contact3};
//    given(service.getContacts()).willReturn(Arrays.asList(contArray));
//    mvc.perform(get("/api/contacts"))
//    .andExpect(status().isOk());
//  }
  
  @Test
  public void getContactsWithoutDataTest() throws Exception {
    Contact[] contArray = new Contact[] {};
    given(service.getContacts()).willReturn(Arrays.asList(contArray));
    mvc.perform(get("/api/contacts"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$", hasSize(0)));
  }
  

}

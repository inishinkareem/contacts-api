package example.api.contacts.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import example.api.contacts.model.Address;
import example.api.contacts.model.Contact;
import example.api.contacts.model.Name;
import example.api.contacts.model.Phone;
import example.api.contacts.repository.ContactRepository;

@ExtendWith(SpringExtension.class)
class ContactServiceImplTest {


  @Mock
  private ContactRepository repository;

  @InjectMocks
  private ContactServiceImpl serviceImpl;

  @BeforeEach
  public void setup(){
    MockitoAnnotations.initMocks(this);
  }



  @Test
  void getAllContactWithDataTest() {
    List<Contact> contactList = new ArrayList<Contact>();

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
    phone3.setContact(contact3);
    phone4.setContact(contact3);
    Set<Phone> phoneSet3 = new HashSet<>();
    phoneSet3.add(phone3);
    phoneSet3.add(phone4);
    contact3.setPhone(phoneSet3);

    contactList.add(contact1);
    contactList.add(contact2);
    contactList.add(contact3);

    when(repository.findAll()).thenReturn(contactList);

    List<Contact> contacts = serviceImpl.getContacts();
    
    assertEquals(3, contacts.size());

  }

  @Test
  void getAllContactWithoutDataTest() {

    List<Contact> contactList = new ArrayList<Contact>();

    when(repository.findAll()).thenReturn(contactList);

    List<Contact> contacts = serviceImpl.getContacts();
    assertTrue(contacts.isEmpty());

  }

  @Test
  void getAContactSuccessTest() {
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("West Creek", "Henrico", "Virginia", "23233"), "john@hotmail.com");
    Phone phone1 = new Phone("mobile", "804-352-4544");
    phone1.setContact(contact1);
    Set<Phone> phoneSet1 = new HashSet<>();
    phoneSet1.add(phone1);
    contact1.setPhone(phoneSet1);

    when(repository.findById(1L)).thenReturn(Optional.of(contact1));

    Contact contact = serviceImpl.getContact(1L);
    assertEquals("john@hotmail.com", contact.getEmail());
  }

  @Test
  void getAContactFailureTest() {

    when(repository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> {
      serviceImpl.getContact(1L);
    }); 
  }

  @Test
  void deleteAContactSuccessTest() {
    when(repository.existsById(1L)).thenReturn(true);
    doNothing().when(repository).deleteById(1L);
    
    assertDoesNotThrow(() -> {
      serviceImpl.deleteContact(1L);
    });

  }

  @Test
  void deleteAContactFailureTest() {
    when(repository.existsById(1L)).thenReturn(false);
    
    assertThrows(EntityNotFoundException.class, () -> {
      serviceImpl.deleteContact(1L);
    });
  }

  @Test
  void updateAContactSuccessTest() {
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("West Creek", "Henrico", "Virginia", "23233"), "john@hotmail.com");
    Phone phone1 = new Phone("mobile", "804-352-4544");
    phone1.setContact(contact1);
    Set<Phone> phoneSet1 = new HashSet<>();
    phoneSet1.add(phone1);
    contact1.setPhone(phoneSet1);
    contact1.setId(1L);
    
    when(repository.existsById(1L)).thenReturn(true);
    when(repository.save(contact1)).thenReturn(contact1);
    
    assertDoesNotThrow(() -> {
      serviceImpl.updateContact(1L, contact1);
    });
  }

  @Test
  void updateAContactFailureTest() {
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("West Creek", "Henrico", "Virginia", "23233"), "john@hotmail.com");
    Phone phone1 = new Phone("mobile", "804-352-4544");
    phone1.setContact(contact1);
    Set<Phone> phoneSet1 = new HashSet<>();
    phoneSet1.add(phone1);
    contact1.setPhone(phoneSet1);
    
    when(repository.existsById(1L)).thenReturn(false);
    
    assertThrows(EntityNotFoundException.class, () -> {
      serviceImpl.updateContact(1L, contact1);
    });
  }
  
  @Test
  void addAContactSuccessTest() {
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("West Creek", "Henrico", "Virginia", "23233"), "john@hotmail.com");
    Phone phone1 = new Phone("mobile", "804-352-4544");
    phone1.setContact(contact1);
    Set<Phone> phoneSet1 = new HashSet<>();
    phoneSet1.add(phone1);
    contact1.setPhone(phoneSet1);
    when(repository.save(contact1)).thenReturn(contact1);
    
    assertDoesNotThrow(() -> {
      serviceImpl.addContact(contact1);
    });
  }

  @Test
  void addAContactFailureTest() {
    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("West Creek", "Henrico", "Virginia", "23233"), "john@hotmail.com");
    Phone phone1 = new Phone("mobile", "804-352-4544");
    phone1.setContact(contact1);
    Set<Phone> phoneSet1 = new HashSet<>();
    phoneSet1.add(phone1);
    contact1.setPhone(phoneSet1);
    
    when(repository.save(contact1)).thenThrow(EntityExistsException.class);
    
    assertThrows(EntityExistsException.class, () -> {
      serviceImpl.addContact(contact1);
    });
  }

  




}

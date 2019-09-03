package example.api.contacts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import example.api.contacts.model.Address;
import example.api.contacts.model.Contact;
import example.api.contacts.model.Name;
import example.api.contacts.model.Phone;
import example.api.contacts.repository.ContactRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class ContactsApiIntegratoinTesting {

  @Autowired
  private ContactRepository repository;


  @Test
  public void addContact() {
    Set<Phone> phoneSet1 = new HashSet<>();
    Contact masterContact = new Contact(new Name("Nishin", "", "Kareem"), new Address("West Creek", "Henrico", "Virginia", "23233"), "nk@awesome.com");
    phoneSet1.add(new Phone("804-352-4544","mobile"));
    masterContact.setPhone(phoneSet1);
    Contact result = repository.save(masterContact);
    assertEquals(result.getEmail(),"nk@awesome.com");

  }

  @Test
  public void getAllContactTest() throws Exception {
    Set<Phone> phoneSet1 = new HashSet<>();
    Contact masterContact = new Contact(new Name("Nishin", "", "Kareem"), new Address("West Creek", "Henrico", "Virginia", "23233"), "nk@awesome.com");
    phoneSet1.add(new Phone("804-352-4544","mobile"));
    masterContact.setPhone(phoneSet1);
    repository.save(masterContact);
    List<Contact> result = repository.findAll();
    assertEquals(1,result.size());

  }
  
    @Test
    public void getContactTest() throws Exception {
      Set<Phone> phoneSet1 = new HashSet<>();
      Contact masterContact = new Contact(new Name("Nishin", "", "Kareem"), new Address("West Creek", "Henrico", "Virginia", "23233"), "nk@awesome.com");
      phoneSet1.add(new Phone("804-352-4544","mobile"));
      masterContact.setPhone(phoneSet1);
      repository.save(masterContact);
      
      Long id = repository.findAll().get(0).getId();
      
      Optional<Contact> result = repository.findById(id);
      assertEquals("nk@awesome.com", result.get().getEmail());
  
    }
    
    @Test
    public void updateContactTest() throws Exception {
      Set<Phone> phoneSet1 = new HashSet<>();
      Contact masterContact = new Contact(new Name("Nishin", "", "Kareem"), new Address("West Creek", "Henrico", "Virginia", "23233"), "nk@awesome.com");
      phoneSet1.add(new Phone("804-352-4544","mobile"));
      masterContact.setPhone(phoneSet1);
      repository.save(masterContact);
      
      Long id = repository.findAll().get(0).getId();
      
      Optional<Contact> result = repository.findById(id);
      masterContact.setEmail("greatnk@awesome.com");
      repository.save(masterContact);
      
      assertEquals("greatnk@awesome.com", result.get().getEmail());
  
    }
    
    @Test
    public void deleteContactTest() throws Exception {
      Set<Phone> phoneSet1 = new HashSet<>();
      Contact masterContact = new Contact(new Name("Nishin", "", "Kareem"), new Address("West Creek", "Henrico", "Virginia", "23233"), "nk@awesome.com");
      phoneSet1.add(new Phone("804-352-4544","mobile"));
      masterContact.setPhone(phoneSet1);
      repository.save(masterContact);
      
      Long id = repository.findAll().get(0).getId();
      
      repository.deleteById(id);
      
      assertEquals(0, repository.findAll().size());
  
    }

}

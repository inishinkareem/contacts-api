package example.api.contacts;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import example.api.contacts.model.Address;
import example.api.contacts.model.Contact;
import example.api.contacts.model.Name;
import example.api.contacts.model.Phone;
import example.api.contacts.repository.ContactRepository;

@SpringBootApplication
public class ContactsApiApplication implements CommandLineRunner{

  @Autowired
  private ContactRepository contactRepository;

  public static void main(String[] args) {
    SpringApplication.run(ContactsApiApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    // Create test contacts
    addContacts();
  }

  public void addContacts() {
//    Contact contact1 = new Contact(new Name("John", "A", "Tyler"), new Address("West Creek", "Henrico", "Virginia", "23233"), "john@hotmail.com");
//    contact1.setPhone( new Phone("804-352-4544","mobile", contact1));
//    Contact contact2 = new Contact(new Name("Mike", "", "Patterson"), new Address("M2", "Henrico", "Virginia", "23294"), "mike@gmail.com");
//    contact2.setPhone(new Phone("804-564-2323","work", contact2));
//    Contact contact3 = new Contact(new Name("Sarah", "Tylor", "Parlow"), new Address("C1", "Henrico", "Virginia", "23058"), "sarah@yahoo.com");
//    contact3.setPhone(new Phone("804-376-1234","home",contact3));
//    contactRepository.saveAll(Arrays.asList(contact1,contact2,contact3));
  }

}

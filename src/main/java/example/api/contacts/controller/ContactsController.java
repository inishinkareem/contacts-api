package example.api.contacts.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import example.api.contacts.model.Contact;
import example.api.contacts.service.ContactService;

@RestController
@RequestMapping("/api/contacts")
public class ContactsController {

  private ContactService contactService;

  public ContactsController(ContactService contactService) {
    this.contactService = contactService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Contact> getContacts() {
    return contactService.getContacts();
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(code = HttpStatus.CREATED)
  public void addContact(@Valid @RequestBody Contact contact) {
     contactService.addContact(contact);
  }

  @PutMapping(path = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
  public void updateContact(@Valid @RequestBody Contact contact, @PathVariable(name = "id") Long id) {
    contactService.updateContact(id, contact);
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Contact getContact(@PathVariable(name = "id") Long id) {
    return contactService.getContact(id);
  }

  @DeleteMapping(path = "/{id}")
  public void deleteContact(@PathVariable(name = "id") Long id) {
    contactService.deleteContact(id);
  }

}

package example.api.contacts.service;

import java.util.List;

import org.springframework.stereotype.Service;

import example.api.contacts.model.Contact;

@Service
public interface ContactService {

  public List<Contact> getContacts();

  public Contact getContact(Long id);

  public void deleteContact(Long id);

  public void addContact(Contact contact);

  public void updateContact(Long id, Contact contact);

}

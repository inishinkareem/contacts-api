package example.api.contacts.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import example.api.contacts.model.Contact;
import example.api.contacts.model.Phone;
import example.api.contacts.repository.ContactRepository;

@Component
public class ContactServiceImpl implements ContactService{

  private static final String CONTACT_NOT_FOUND= "Contact not found for id=";
  Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

  private ContactRepository contactRepository;

  public ContactServiceImpl(ContactRepository contactRepository) {
    this.contactRepository = contactRepository;
  }

  @Override
  public List<Contact> getContacts() {
    List<Contact> contactList = new ArrayList<>();
    contactRepository.findAll().forEach( contactList::add);
    return contactList;
  }

  @Override
  public Contact getContact(Long id) {
    Optional<Contact> optContact = contactRepository.findById(id);
    if(optContact.isPresent())
      return optContact.get();
    else
    {
      logger.error("Contact not found for id={}",id);
      throw new EntityNotFoundException(CONTACT_NOT_FOUND+id);
    }
  }

  @Override
  public void deleteContact(Long id) {
    if(contactRepository.existsById(id))
      contactRepository.deleteById(id);
    else
    {
      logger.error("Contact not found for id={}",id);
      throw new EntityNotFoundException(CONTACT_NOT_FOUND+id);
    }

  }

  @Override
  public void addContact(Contact contact) {
    Set<Phone> phoneToUpdate = new HashSet<>();
    contact.getPhone().stream().forEach(phone -> {
      phone.setContact(contact);
      phoneToUpdate.add(phone);
    });
    contact.setPhone(phoneToUpdate);
     contactRepository.save(contact);
    logger.info("Contact has been added with email {}", contact.getEmail());
  }

  @Override
  public void updateContact(Long id, Contact contact) {
    contact.setId(id);
    if(contactRepository.existsById(id)) {
      logger.info("Contact with id={} has been updated",id);
      contactRepository.save(contact);
    }
    else
    {
      logger.error("Contact not found for id={}",id);
      throw new EntityNotFoundException(CONTACT_NOT_FOUND+id);
    }

  }

}

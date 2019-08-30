package example.api.contacts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import example.api.contacts.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>{

}

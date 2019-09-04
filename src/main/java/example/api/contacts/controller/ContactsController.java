package example.api.contacts.controller;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/contacts")
public class ContactsController {

  private ContactService contactService;

  public ContactsController(ContactService contactService) {
    this.contactService = contactService;
  }

  /** 
   * 
   * Find all Contacts
   * 
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Finds all the Contacts within the system",
  produces = "application/json",
  response = List.class)
  public List<Contact> getContacts() {
    return contactService.getContacts();
  }

  /** 
   * 
   * Add a Contact
   * @param @contact
   * @throws EntityExistsException for contacts duplicate email address
   * 
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(code = HttpStatus.CREATED)
  @ApiOperation(value = "Add a Contact to the system",
  consumes = "application/json")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Contact Added"), 
      @ApiResponse(code = 400, message = "Contact Already Exists/Validation error")})
  public void addContact(@ApiParam(value = "Contact to be added", required = true) @Valid @RequestBody(required = true) Contact contact) {
     contactService.addContact(contact);
  }

  
  /** 
   * 
   * Update an existing Contact by Id
   * @param @contact, id
   * @throws EntityNotFoundException for contacts that doesn't exist
   * 
   */
  @PutMapping(path = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Update an existing Contact",
  consumes = "application/json")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Contact updated"), 
      @ApiResponse(code = 404, message = "Contact doesnt exist"),
      @ApiResponse(code = 400, message = "Validation error")})
  public void updateContact(@ApiParam(value = "updated Contact", required = true) @Valid @RequestBody(required = true) Contact contact, 
      @ApiParam(value = "Id of the contact to be updated", required = true)  @PathVariable(name = "id", required = true) Long id) {
    contactService.updateContact(id, contact);
  }

  /** 
   * 
   * find an existing Contact by Id
   * @param id
   * @throws EntityNotFoundException for contacts that doesn't exist
   * 
   */
  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Finds Contacts by id",
  notes = "Provide an id to look up specific contact in the system",
  produces = "application/json",
  response = Contact.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Contact found"), 
      @ApiResponse(code = 404, message = "Contact not found")})
  public Contact getContact(@ApiParam(value = "Id of the contact to be viewed", required = true) @PathVariable(name = "id", required = true) Long id) {
    return contactService.getContact(id);
  }

  
  /** 
   * 
   * delete an existing Contact by Id
   * @param id
   * @throws EntityNotFoundException for contacts that doesn't exist
   * 
   */
  @DeleteMapping(path = "/{id}")
  @ApiOperation(value = "Deletes Contacts by id",
  notes = "Provide an id to delete specific contact in the system")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Contact Deleted"), 
      @ApiResponse(code = 404, message = "Contact not found")})
  public void deleteContact(@ApiParam(value = "Id of the contact to be deleted", required = true) @PathVariable(name = "id") Long id) {
    contactService.deleteContact(id);
  }

}

package com.person.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.person.exception.ResourceNotFoundException;
import com.person.model.Person;
import com.person.repository.PersonRepository;

@RestController
@RequestMapping("/api/v1")
public class PersonController {
	
	@Autowired
	private PersonRepository personRepository;

	@GetMapping("/persons")
	public List<Person> getAllPersons() {
		return personRepository.findAll();
	}

	@GetMapping("/person/{id}")
	public ResponseEntity<Person> getPersonById(@PathVariable(value = "id") Long PersonId)
			throws ResourceNotFoundException {
		Person Person = personRepository.findById(PersonId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + PersonId));
		return ResponseEntity.ok().body(Person);
	}

	@PostMapping("/person")
	public Person createPerson(@RequestBody Person Person) {
		return personRepository.save(Person);
	}

	@PutMapping("/person/{id}")
	public ResponseEntity<Person> updatePerson(@PathVariable(value = "id") Long PersonId,
			 @RequestBody Person PersonDetails) throws ResourceNotFoundException {
		Person Person = personRepository.findById(PersonId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + PersonId));

		Person.setEmailId(PersonDetails.getEmailId());
		Person.setLastName(PersonDetails.getLastName());
		Person.setFirstName(PersonDetails.getFirstName());
		final Person updatedPerson = personRepository.save(Person);
		return ResponseEntity.ok(updatedPerson);
	}

	@DeleteMapping("/person/{id}")
	public Map<String, Boolean> deletePerson(@PathVariable(value = "id") Long PersonId)
			throws ResourceNotFoundException {
		Person Person = personRepository.findById(PersonId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + PersonId));

		personRepository.delete(Person);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}

package com.isakov.springboot.controller;

import com.isakov.springboot.model.Publisher;
import com.isakov.springboot.service.PublisherService;
import com.isakov.springboot.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	PublisherService publisherService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All Publishers---------------------------------------------

	@RequestMapping(value = "/publisher/", method = RequestMethod.GET)
	public ResponseEntity<List<Publisher>> listAllPublishers() {
		List<Publisher> publishers = publisherService.findAllPublishers();
		if (publishers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Publisher>>(publishers, HttpStatus.OK);
	}

	// -------------------Retrieve Single Publisher------------------------------------------

	@RequestMapping(value = "/publisher/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getPublisher(@PathVariable("id") long id) {
		logger.info("Fetching Publisher with id {}", id);
		Publisher publisher = publisherService.findById(id);
		if (publisher == null) {
			logger.error("Publisher with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Publisher with id " + id
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Publisher>(publisher, HttpStatus.OK);
	}

	// -------------------Create a Publisher-------------------------------------------

	@RequestMapping(value = "/publisher/", method = RequestMethod.POST)
	public ResponseEntity<?> createPublisher(@RequestBody Publisher publisher, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Publisher : {}", publisher);

		if (publisherService.isPublisherExist(publisher)) {
			logger.error("Unable to create. A Publisher with name {} already exist", publisher.getName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A Publisher with name " +
			publisher.getName() + " already exist."),HttpStatus.CONFLICT);
		}
		publisherService.savePublisher(publisher);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/publisher/{id}").buildAndExpand(publisher.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Publisher ------------------------------------------------

	@RequestMapping(value = "/publisher/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updatePublisher(@PathVariable("id") long id, @RequestBody Publisher publisher) {
		logger.info("Updating Publisher with id {}", id);

		Publisher currentPublisher = publisherService.findById(id);

		if (currentPublisher == null) {
			logger.error("Unable to update. Publisher with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. Publisher with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentPublisher.setName(publisher.getName());

		publisherService.updatePublisher(currentPublisher);
		return new ResponseEntity<Publisher>(currentPublisher, HttpStatus.OK);
	}

	// ------------------- Delete a Publisher-----------------------------------------

	@RequestMapping(value = "/publisher/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletePublisher(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Publisher with id {}", id);

		Publisher publisher = publisherService.findById(id);
		if (publisher == null) {
			logger.error("Unable to delete. Publisher with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Publisher with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		publisherService.deletePublisherById(id);
		return new ResponseEntity<Publisher>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Publishers-----------------------------

	@RequestMapping(value = "/publisher/", method = RequestMethod.DELETE)
	public ResponseEntity<Publisher> deleteAllPublishers() {
		logger.info("Deleting All Publishers");

		publisherService.deleteAllPublishers();
		return new ResponseEntity<Publisher>(HttpStatus.NO_CONTENT);
	}

}
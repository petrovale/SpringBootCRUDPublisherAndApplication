package com.isakov.springboot.web.publisher;


import com.isakov.springboot.model.Publisher;
import com.isakov.springboot.service.PublisherService;
import com.isakov.springboot.util.CustomErrorType;
import com.isakov.springboot.web.RestApiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = PublisherRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class PublisherRestController {
    static final String REST_URL = "/rest/publishers";

    public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    PublisherService publisherService;

    @GetMapping
    public ResponseEntity<List<Publisher>> listAllPublishers() {
        List<Publisher> publishers = publisherService.findAllPublishers();
        if (publishers.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Publisher>>(publishers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
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
}

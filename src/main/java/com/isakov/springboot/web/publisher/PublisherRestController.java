package com.isakov.springboot.web.publisher;


import com.isakov.springboot.model.Publisher;
import com.isakov.springboot.service.PublisherService;
import com.isakov.springboot.to.PublisherTo;
import com.isakov.springboot.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.isakov.springboot.util.PublisherUtil.createNewFromTo;

@RestController
@RequestMapping(value = PublisherRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class PublisherRestController {
    static final String REST_URL = "/rest/admin/publishers";

    public static final Logger logger = LoggerFactory.getLogger(PublisherRestController.class);

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
    public ResponseEntity<?> getPublisher(@PathVariable("id") int id) {
        logger.info("Fetching Publisher with id {}", id);
        Publisher publisher = publisherService.findById(id);
        if (publisher == null) {
            logger.error("Publisher with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Publisher with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Publisher>(publisher, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Publisher> createWithLocation(@RequestBody PublisherTo publisherTo) {
        logger.info("Creating Publisher : {}", publisherTo);

        Publisher created = publisherService.savePublisher(createNewFromTo(publisherTo));

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setLocation(uriOfNewResource);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        logger.info("Fetching & Deleting Publisher with id {}", id);

        if (publisherService.deletePublisherById(id) == 0) {
            logger.error("Unable to delete. Publisher with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Publisher with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Publisher>(HttpStatus.NO_CONTENT);
    }
}

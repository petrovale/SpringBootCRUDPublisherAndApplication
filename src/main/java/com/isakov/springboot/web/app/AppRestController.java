package com.isakov.springboot.web.app;

import com.isakov.springboot.AuthorizedPublisher;
import com.isakov.springboot.model.App;
import com.isakov.springboot.model.Publisher;
import com.isakov.springboot.service.AppService;
import com.isakov.springboot.service.GenreService;
import com.isakov.springboot.util.CustomErrorType;
import com.isakov.springboot.web.RestApiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = AppRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AppRestController {
    static final String REST_URL = "/rest/apps";

    public static final Logger logger = LoggerFactory.getLogger(AppRestController.class);

    @Autowired
    private AppService appService;

    @GetMapping
    public ResponseEntity<List<App>> getAll() {
        long publisherId = AuthorizedPublisher.id();
        logger.info("getAll for Publisher {}", publisherId);
        List<App> apps = appService.getAllWithGenre(publisherId);
        if (apps.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<App>>(apps, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createApp(@RequestBody App app, UriComponentsBuilder ucBuilder, @RequestParam(value = "genreId") long genreId) {
        long publisherId = AuthorizedPublisher.id();
        logger.info("Creating App : {}", app);

        appService.saveApp(app, publisherId, genreId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path(REST_URL + "/{id}").buildAndExpand(app.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApp(@PathVariable("id") long id) {
        long publisherId = AuthorizedPublisher.id();
        logger.info("Fetching & Deleting App with id {}", id);

        if (appService.deleteAppById(id, publisherId) == 0) {
            logger.error("Unable to delete. App with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. App with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Publisher>(HttpStatus.NO_CONTENT);
    }
}

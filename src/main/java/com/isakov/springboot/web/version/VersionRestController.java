package com.isakov.springboot.web.version;

import com.isakov.springboot.AuthorizedPublisher;
import com.isakov.springboot.model.Publisher;
import com.isakov.springboot.model.Version;
import com.isakov.springboot.service.VersionService;
import com.isakov.springboot.util.CustomErrorType;
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

import static com.isakov.springboot.web.version.VersionRestController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VersionRestController {
    static final String REST_URL = "/rest/apps";

    public static final Logger logger = LoggerFactory.getLogger(VersionRestController.class);

    @Autowired
    private VersionService versionService;

    @GetMapping("/{id}/versions")
    public ResponseEntity<List<Version>> getAll(@PathVariable("id") int id) {
        int publisherId = AuthorizedPublisher.id();
        logger.info("getAll versions for App {}", id);
        List<Version> versions = versionService.getAll(id, publisherId);
        if (versions.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Version>>(versions, HttpStatus.OK);
    }


    @PostMapping(value = "/{id}/versions", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createVersion(@RequestBody Version version, UriComponentsBuilder ucBuilder, @PathVariable("id") int id) {
        int publisherId = AuthorizedPublisher.id();
        logger.info("Creating Version {} for App : {}", version, id);

        versionService.saveVersion(version, id, publisherId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path(REST_URL + "/{id}").buildAndExpand(version.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}/versions/{versionId}")
    public ResponseEntity<?> deleteVersion(@PathVariable("id") int appId, @PathVariable("versionId") int versionId) {
        int publisherId = AuthorizedPublisher.id();
        logger.info("Fetching & Deleting Version with id {} for App {}", versionId, appId);

        if (versionService.deleteById(versionId, appId, publisherId) == 0) {
            logger.error("Unable to delete. Version with id {} not found.", versionId);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Version with id " + versionId + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Publisher>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}/versions", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody Version version, UriComponentsBuilder ucBuilder, @PathVariable("id") int appId) {
        int publisherId = AuthorizedPublisher.id();
        logger.info("update {} with id={} for App {}", version, version.getId(), appId);
        if (version.isNew()) {
            throw new IllegalArgumentException(version.getId() + " must be new (id=null)");
        }
        versionService.saveVersion(version, appId, publisherId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path(REST_URL + "/{id}").buildAndExpand(version.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.ACCEPTED);
    }
}

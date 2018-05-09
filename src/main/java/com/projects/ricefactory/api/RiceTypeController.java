package com.projects.ricefactory.api;

import com.projects.ricefactory.dto.RiceType;
import com.projects.ricefactory.service.RiceTypeServiceDao;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hearlapati on 2/28/17.
 */
@RestController
public class RiceTypeController {

    private final Logger logger = LoggerFactory.getLogger(RiceTypeController.class);

    @Autowired
    private RiceTypeServiceDao riceTypeServiceDao;

    @RequestMapping(
            value = "/v1/riceTypes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity getRiceType(@PathVariable("id") Long id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");

        logger.debug("Getting riceType with id : " + id);

        try {
            RiceType riceType = riceTypeServiceDao.getRiceTypeById(id);

            if(riceType == null) {
                return new ResponseEntity<RiceType>(responseHeaders, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity(riceType, responseHeaders, HttpStatus.OK);
        }
        catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin","*").body("Error while processing request");
        }
    }

    @RequestMapping(
            value = "/v1/riceTypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity getAllRiceTypes() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");

        logger.debug("Get all rice types");

        try {
            List<RiceType> allRiceTypes = riceTypeServiceDao.getAllRiceTypes();

            if(allRiceTypes == null || allRiceTypes.isEmpty()) {
                return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity(allRiceTypes, responseHeaders, HttpStatus.OK);
        }
        catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin","*").body("Error while processing request");
        }
    }

    @RequestMapping(
            value = "/v1/riceTypes",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity createRiceType(@RequestBody RiceType riceType) {

        logger.debug("Creating rice type");

        try {
            RiceType createdRiceType = riceTypeServiceDao.createRiceType(riceType);

            if(createdRiceType == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to create riceType record");
            }

            return new ResponseEntity<>(createdRiceType, HttpStatus.CREATED);

        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getLocalizedMessage() + "Error while processing request");
        }
    }

    @RequestMapping(
            value = "/v1/riceTypes/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity updateRiceType(@RequestBody RiceType updatedRiceType){

        logger.debug("Updating rice type");

        try {

            RiceType existingRiceType = riceTypeServiceDao.getRiceTypeById(updatedRiceType.getId());

            if(existingRiceType == null) {
                return new ResponseEntity<RiceType>(HttpStatus.NOT_FOUND);
            }

            RiceType riceTypeResponse = riceTypeServiceDao.updateRiceType(updatedRiceType);

            return new ResponseEntity<>(riceTypeResponse, HttpStatus.OK);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage() + "Error while processing request");
        }
    }
}

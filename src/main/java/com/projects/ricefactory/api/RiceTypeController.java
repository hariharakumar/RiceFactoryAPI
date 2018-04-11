package com.projects.ricefactory.api;

import com.projects.ricefactory.dto.RiceType;
import com.projects.ricefactory.service.RiceTypeDao;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hearlapati on 2/28/17.
 */
@RestController
public class RiceTypeController {

    @Autowired
    private RiceTypeDao riceTypeDao;

    @Autowired
    private HttpServletRequest request;

    public ResponseEntity getRiceType(@PathVariable("id") Long id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");

        try {
            RiceType riceType = riceTypeDao.getRiceTypeById(id);

            if(riceType == null) {
                return new ResponseEntity<RiceType>(responseHeaders, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity(riceType, responseHeaders, HttpStatus.OK);
        }
        catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin","*").body("Error while processing request");
        }
    }



}

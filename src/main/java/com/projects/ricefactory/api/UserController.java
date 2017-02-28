package com.projects.ricefactory.api;

import com.projects.ricefactory.dto.User;
import com.projects.ricefactory.service.UserServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpUtils;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hearlapati on 12/5/16.
 */
@RestController
public class UserController {

    // TODO : Write all exception logic.
    // TODO : Write validations for JSON fields
    // TODO : Write authorization for all requests
    // TODO : Encode the password.

    @Autowired
    private UserServiceDao userServiceDao;

    @Autowired(required=true)
    private HttpServletRequest request;

    @RequestMapping(
            value = "/v1/users/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity getUser(@PathVariable("id") Long id) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");

        try {
            User user = userServiceDao.getUserById(id);

            if (user == null) {
                return new ResponseEntity<User>(responseHeaders,HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<User>(user, responseHeaders, HttpStatus.OK);
        }catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin","*").body("Error while processing request");
        }

    }

    @RequestMapping(
            value = "/v1/users",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity getUsers() {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");

        try {
            Map<String, String[]> queryParamsMap = (Map<String, String[]>)request.getParameterMap();

            // TODO : Make it generic to get User by any attribute
            String email = queryParamsMap.get("email")[0];
            User user = userServiceDao.getUserByEmail(email);

            if (user == null) {
                return new ResponseEntity<User>(responseHeaders,HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<User>(user, responseHeaders, HttpStatus.OK);
        }catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin","*").body("Error while processing request");
        }

    }

    @CrossOrigin
    @RequestMapping(
            value = "/v1/users",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity createUser(@RequestBody User user) {

        /*HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");*/

        try {

            User createdUserObject = userServiceDao.createUser(user);
            if(createdUserObject == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to create User record");
            }

            return new ResponseEntity<User>(createdUserObject, HttpStatus.CREATED);

        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getLocalizedMessage() + "Error while processing request");
        }
    }

    @RequestMapping(
            value = "/v1/users/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity updateUser(@RequestBody User updatedUser, @PathVariable("id") Long userId) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");

        try {

            User existingUser = userServiceDao.getUserById(userId);
            if(existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Access-Control-Allow-Origin","*").body("User record you are trying to update doesn't exist");
            }

            User updatedUserObject = userServiceDao.updateUser(existingUser,updatedUser);

            return new ResponseEntity<User>(updatedUserObject, responseHeaders, HttpStatus.OK);

        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin","*").body("Error while processing request");
        }
    }

    @RequestMapping(
            value = "/v1/users/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {

        try {

            User user = userServiceDao.getUserById(id);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            userServiceDao.deleteUser(id);

            return ResponseEntity.status(HttpStatus.OK).body("User Deleted Sucessfully");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while processing request");
        }

    }

    // TODO : Uncomment this method if needed
    /*@RequestMapping(
            value = "/v1/users",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity getAllUsers() {

        try {

            List<User> users = userServiceDao.getAllUsers();

            if (users.size() == 0) {
                return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        }catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while processing request");
        }

    }*/

}

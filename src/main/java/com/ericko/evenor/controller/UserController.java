package com.ericko.evenor.controller;

import com.ericko.evenor.entity.EventComittee;
import com.ericko.evenor.entity.Review;
import com.ericko.evenor.entity.User;
import com.ericko.evenor.entity.Volunteer;
import com.ericko.evenor.service.user.UserService;
import com.ericko.evenor.util.response.ResponseWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static com.ericko.evenor.util.response.ResponseHandler.checkResourceFound;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get all user", notes = "List of all user")
    public @ResponseBody List<User> getUser(HttpServletRequest request, HttpServletResponse response) {
        List<User> result = userService.getUser();
        checkResourceFound(result);
        return result;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get user by id", notes = "the user is search by id")
    public @ResponseBody
    User getUser(
            @ApiParam(value = "the id of user")
            @PathVariable UUID id,
            HttpServletRequest request, HttpServletResponse response
    ) {
        User result = userService.getUser(id);
        checkResourceFound(result);
        return result;
    }

    @GetMapping("/volunteer")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get event by id and type", notes = "the event is search by id and type")
    public @ResponseBody
    List<Volunteer> getVolunteer(
            @ApiParam(value = "the id of event")
            @PathVariable UUID eventId,
            @ApiParam(value = "the type of volunteer")
            @PathVariable String type,
            HttpServletRequest request, HttpServletResponse response
    ) {
        List<Volunteer> result = userService.getVolunteer(eventId, type);
        return checkResourceFound(result);
    }

    @GetMapping("/review")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get review by user id", notes = "the review is search by user id")
    public @ResponseBody
    Review getReview(
            @ApiParam(value = "the id of user")
            @PathVariable UUID userId,
            HttpServletRequest request, HttpServletResponse response
    ) {
        Review result = userService.getReview(userId);
        return checkResourceFound(result);
    }

    @PostMapping("/account/")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get user by id", notes = "the user is search by id")
    public @ResponseBody
    User getUser(
            @ApiParam(value = "the username of user")
            @RequestParam String username,
            HttpServletRequest request, HttpServletResponse response
    ){
        User result = userService.getUserByEmail(username);
        checkResourceFound(result);
        return result;
    }

    @PostMapping("/comittee/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get user by id", notes = "the user is search by id")
    public @ResponseBody
    EventComittee getComittee(
            @ApiParam(value = "the user id")
            @PathVariable("id") UUID userId,
            @ApiParam(value = "the username of user")
            @RequestParam UUID eventId,
            HttpServletRequest request, HttpServletResponse response
    ){
        return checkResourceFound(userService.getComittee(userId, eventId));
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "user object", notes = "create new user")
    public @ResponseBody
    User createUser(
            @RequestBody User user,
            HttpServletRequest request, HttpServletResponse response
    ) {
        return userService.createUser(user);
    }

    @PostMapping("/volunteer/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "user object", notes = "create new user")
    public @ResponseBody
    Volunteer createVolunteer(
            @ApiParam(value = "the user id")
            @PathVariable("id") UUID userId,
            @ApiParam(value = "the event id")
            @RequestParam UUID eventId,
            @ApiParam(value = "type volunteer")
            @RequestParam String type,
            HttpServletRequest request, HttpServletResponse response
    ) {
        return userService.createVolunteer(userId, eventId, type);
    }

    @PostMapping("/review")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "event data", notes = "create new event")
    public @ResponseBody
    Review createReview(
            @ApiParam(value = "user id") @RequestParam("userId") UUID userId,
            @ApiParam(value = "reviewer id") @RequestParam("reviewerId") UUID reviewerId,
            @ApiParam(value = "event id") @RequestParam("eventId") UUID eventId,
            @ApiParam(value = "integer leadership score") @RequestParam("leadership") Integer leadership,
            @ApiParam(value = "integer authority score") @RequestParam("authority") Integer authority,
            @ApiParam(value = "integer decision score") @RequestParam("decision") Integer decision,
            @ApiParam(value = "integer flexibility score") @RequestParam("flexibility") Integer flexibility,
            HttpServletRequest request, HttpServletResponse response
    ) {
        Review result = userService.createReview(userId,reviewerId, eventId, leadership, authority, decision, flexibility);
        return result;
    }

    @PutMapping("/edit/photo/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "user object", notes = "create new user")
    public @ResponseBody
    User editUserProfile(
            @ApiParam("id user") @PathVariable("id") UUID id,
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam("file") MultipartFile file
    ) throws FileFormatException {
        return userService.uploadPhoto(id, file);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "user object", notes = "updating user data by id")
    public @ResponseBody User updateUser(
            @ApiParam(value = "the user id")
            @PathVariable("id") String id,
            @RequestBody User user,
            HttpServletRequest request, HttpServletResponse response
    ) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "user id" , notes = "delete user by id")
    public @ResponseBody
    void deleteUser(
            @ApiParam(value = "the user id")
            @PathVariable("id") UUID id,
            HttpServletRequest request, HttpServletResponse response
    ) {
        userService.deleteUser(id);
    }

    @DeleteMapping("/volunteer/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "user id" , notes = "delete user by id")
    public @ResponseBody
    void deleteVolunteer(
            @ApiParam(value = "the volunteer id")
            @PathVariable("id") UUID volunteerId,
            HttpServletRequest request, HttpServletResponse response
    ) {
        userService.deleteVolunteer(volunteerId);
    }

    @GetMapping("/search/name/{name}")
    public @ResponseBody
    List<User> searchByName(
            @PathVariable String name
    ) {
        return userService.searchByName(name);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public
    @ResponseBody
    ResponseWrapper handleDuplicateEmail(DataIntegrityViolationException ex, WebRequest request, HttpServletResponse response) {
        //log.info("Converting Data Store exception to RestResponse : " + ex.getMessage());

        return new ResponseWrapper(ex, "Data sama / constraint");
    }
}

package com.ericko.evenor.controller;

import com.ericko.evenor.entity.EventComittee;
import com.ericko.evenor.entity.User;
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

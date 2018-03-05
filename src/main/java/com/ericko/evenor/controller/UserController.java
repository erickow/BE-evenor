package com.ericko.evenor.controller;

import com.ericko.evenor.entity.User;
import com.ericko.evenor.service.UserService;
import com.ericko.evenor.util.response.ResponseWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.basic.BasicScrollPaneUI;
import java.util.List;
import java.util.zip.DataFormatException;

import static com.ericko.evenor.util.response.ResponseHandler.checkResourceFound;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    @ResponseStatus()
    public List<User> getUser() {
        return userService.getUser();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get user by id", notes = "the user is search by id")
    public @ResponseBody User getUser(
            @ApiParam(value = "the id of user")
            @PathVariable String id,
            HttpServletRequest request, HttpServletResponse response
    ) {
        User result = userService.getUser(id);
        checkResourceFound(result);
        return result;
    }

    @PostMapping("/")
    public User createUser(
            @RequestBody User user
    ) {
        User result = userService.createUser(user);
        return result;
    }

    @PutMapping("/{id}")
    public User updateUser(
            @PathVariable("id") String id,
            @RequestBody User user
    ) {
        User result = userService.getUser(id);
        try {

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            System.out.println("Duplicated course code");
        }

        return result;
    }

    @DeleteMapping("/{id}")
    public User deleteUser(
            @PathVariable("id") Integer id
    ) {
        User result = null;
        return result;
    }

    @GetMapping("/search/name/{name}")
    public @ResponseBody List<User> searchByName (@PathVariable String name) {
        return userService.searchByName(name);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public
    @ResponseBody
    ResponseWrapper handleDuplicateEmail(DataIntegrityViolationException ex, WebRequest request, HttpServletResponse response) {
        //log.info("Converting Data Store exception to RestResponse : " + ex.getMessage());

        return new ResponseWrapper(ex , "Datamu gak oleh kembar blog.");
    }
}

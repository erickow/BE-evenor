package com.ericko.evenor.controller;

import com.ericko.evenor.entity.Event;
import com.ericko.evenor.entity.User;
import com.ericko.evenor.service.event.EventService;
import com.ericko.evenor.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

import static com.ericko.evenor.util.response.ResponseHandler.checkResourceFound;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @GetMapping("/event/")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get the event data list", notes= "getting all data event")
    public @ResponseBody
    List<Event> getListEvent(
            HttpServletRequest request, HttpServletResponse response
    ){
        return eventService.getEvent();
    }

    @GetMapping("/detail/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get event by id", notes = "get an event by id event")
    public @ResponseBody
    Event getEventDetail(
            @ApiParam(value = "event id")
            @PathVariable("id") UUID id,
            HttpServletRequest request, HttpServletResponse response
    ){
        Event result = eventService.getEvent(id);
        checkResourceFound(result);
        return result;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "user object", notes = "create new user")
    public @ResponseBody
    User createUser(
            @RequestBody User user,
            HttpServletRequest request, HttpServletResponse response
    ) {
        return userService.createUser(user);

    }

}

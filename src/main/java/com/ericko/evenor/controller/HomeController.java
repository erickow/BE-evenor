package com.ericko.evenor.controller;

import com.ericko.evenor.entity.Event;
import com.ericko.evenor.entity.User;
import com.ericko.evenor.service.event.EventService;
import com.ericko.evenor.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/photo/")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get user by id", notes = "the user is search by id")
    public @ResponseBody
    ResponseEntity<ByteArrayResource>
    getImage(
            @ApiParam(value = "the String image path")
            @RequestParam("photo") String data,
            HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        File file = new File(data);
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + path);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}

package com.ericko.evenor.controller;

import com.ericko.evenor.entity.Event;
import com.ericko.evenor.entity.EventComittee;
import com.ericko.evenor.entity.EventParticipant;
import com.ericko.evenor.entity.User;
import com.ericko.evenor.service.event.EventService;
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
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "all event data", notes = "getting all event")
    public @ResponseBody
    List<Event> getEvent(
            HttpServletRequest request,HttpServletResponse response
    ){
        List<Event> result = eventService.getEvent();
        return result;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "event data", notes = "getting event data by id")
    public @ResponseBody
    Event getEvent(
            @ApiParam(value = "event id")
            @PathVariable("id")UUID id,
            @RequestBody Event event,
            HttpServletRequest request, HttpServletResponse response
    ){
        Event result = eventService.getEvent(id);
        checkResourceFound(result);
        return result;
    }

    @GetMapping("/upcoming/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "event data", notes = "getting upcomming event data by id")
    public @ResponseBody
    List<EventComittee> getUpcommingEvent(
            @ApiParam(value = "event id")
            @PathVariable("id")UUID id,
            HttpServletRequest request, HttpServletResponse response
    ){
        List<EventComittee> result = eventService.getUpcommingEvent(id);
        checkResourceFound(result);
        return result;
    }

    @GetMapping("/history/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "event data", notes = "getting history event data by id")
    public @ResponseBody
    List<EventComittee> getHistoryEvent(
            @ApiParam(value = "event id")
            @PathVariable("id")UUID id,
            HttpServletRequest request, HttpServletResponse response
    ){
        List<EventComittee> result = eventService.getHistoryEvent(id);
        checkResourceFound(result);
        return result;
    }

    @GetMapping("/comittee/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "all event data", notes = "getting all event")
    public @ResponseBody
    List<EventComittee> getComittee(
            @ApiParam(value = "event id")
            @PathVariable("id")UUID id,
            HttpServletRequest request, HttpServletResponse response
    ){
        List<EventComittee> result = eventService.getComittee(id);
        checkResourceFound(result);
        return result;
    }

    @GetMapping("/participant/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "all event data", notes = "getting all event")
    public @ResponseBody
    List<EventParticipant> getParticipant(
            @ApiParam(value = "event id")
            @PathVariable("id")UUID id,
            HttpServletRequest request, HttpServletResponse response
    ){
        List<EventParticipant> result = eventService.getParticipant(id);
        checkResourceFound(result);
        return result;
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "event data", notes = "create new event")
    public @ResponseBody
    Event createEvent(
            @ApiParam(value = "user id")
            @PathVariable("id") UUID id,
            @RequestBody Event event,
            HttpServletRequest request, HttpServletResponse response
    ) {
        Event result = eventService.createEvent(id, event);
        return result;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "event object", notes = "updating event data by id")
    public @ResponseBody
    Event updateEvent(
            @ApiParam(value = "the event id")
            @PathVariable("id") UUID id,
            @RequestBody Event event,
            HttpServletRequest request, HttpServletResponse response
    ) {
        return eventService.updateEvent(event);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "event id" , notes = "delete event by id")
    public @ResponseBody
    void deleteEvent(
            @ApiParam(value = "the event id")
            @PathVariable("id") UUID id,
            HttpServletRequest request, HttpServletResponse response
    ) {
        eventService.deleteEvent(id);
    }

}

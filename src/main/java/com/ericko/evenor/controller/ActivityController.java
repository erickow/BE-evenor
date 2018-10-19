package com.ericko.evenor.controller;

import com.ericko.evenor.entity.Activity;
import com.ericko.evenor.entity.Event;
import com.ericko.evenor.service.activity.ActivityService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

import static com.ericko.evenor.util.response.ResponseHandler.checkResourceFound;

@Controller
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/event/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "event data", notes = "getting event data by id")
    public @ResponseBody
    List<Activity> getActivityEvent(
            @ApiParam(value = "event id")
            @PathVariable("id")UUID id,
            HttpServletRequest request, HttpServletResponse response
    ){
        List<Activity> result = activityService.getActivityByEvent(id);
        checkResourceFound(result);
        return result;
    }

    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "user data", notes = "getting activity data by id")
    public @ResponseBody
    List<Activity> getActivityUser(
            @ApiParam(value = "user id")
            @PathVariable("id")UUID id,
            HttpServletRequest request, HttpServletResponse response
    ){
        List<Activity> result = activityService.getActivityByUser(id);
        checkResourceFound(result);
        return result;
    }

    @GetMapping("/all/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "user data", notes = "getting user data by id")
    public @ResponseBody
    List<Activity> getEvent(
            @ApiParam(value = "event id")
            @PathVariable("id")UUID id,
            HttpServletRequest request, HttpServletResponse response
    ){
        List<Activity> result = activityService.getActivityByAllEvent(id);
        checkResourceFound(result);
        return result;
    }
}

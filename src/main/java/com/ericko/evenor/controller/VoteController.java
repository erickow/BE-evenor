package com.ericko.evenor.controller;

import com.ericko.evenor.entity.Vote;
import com.ericko.evenor.service.vote.VoteService;
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
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @GetMapping("/upcoming/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "upcoming vote", notes = "List of all upcoming vote")
    public @ResponseBody
    List<Vote> getUpcomingTask(
            @ApiParam(value = "the id of event")
            @PathVariable UUID id,
            HttpServletRequest request, HttpServletResponse response
    ) {
      return checkResourceFound(voteService.getUpcomingVote(id));
    }

    @GetMapping("/history/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "history vote", notes = "List of all upcoming vote")
    public @ResponseBody
    List<Vote> getHistoryTask(
            @ApiParam(value = "the id of event")
            @PathVariable UUID id,
            HttpServletRequest request, HttpServletResponse response
    ) {
        return checkResourceFound(voteService.getHistoryVote(id));
    }

    @PostMapping("/event/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "task object", notes = "create new task")
    public @ResponseBody
    Vote createVote(
            @ApiParam(value = "the id of event")
            @PathVariable("id") UUID id,
            @RequestBody Vote vote,
            HttpServletRequest request, HttpServletResponse response
    ) {
        return checkResourceFound(voteService.createVote(id, vote));
    }

    @PostMapping("/voter/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "task object", notes = "create new task")
    public @ResponseBody
    Vote createVoter(
            @ApiParam(value = "the id of event")
            @PathVariable("id") UUID id,
            @RequestBody Vote vote,
            HttpServletRequest request, HttpServletResponse response
    ) {
        return checkResourceFound(voteService.createVote(id, vote));
    }
}

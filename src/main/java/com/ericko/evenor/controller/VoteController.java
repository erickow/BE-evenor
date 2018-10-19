package com.ericko.evenor.controller;

import com.ericko.evenor.entity.Answer;
import com.ericko.evenor.entity.EventComittee;
import com.ericko.evenor.entity.Vote;
import com.ericko.evenor.repository.EventComitteeRepository;
import com.ericko.evenor.service.activity.ActivityService;
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

    @Autowired
    private ActivityService activityService;

    @Autowired
    private EventComitteeRepository eventComitteeRepository;

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

    @PostMapping("/event/{eventId}/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "task object", notes = "create new task")
    public @ResponseBody
    Vote createVote(
            @ApiParam(value = "the id of event")
            @PathVariable("eventId") UUID eventId,
            @ApiParam(value = "the id of user")
            @PathVariable("userId") UUID userId,
            @RequestBody Vote vote,
            HttpServletRequest request, HttpServletResponse response
    ) {
        Vote vote1 = voteService.createVote(eventId, userId, vote);
        if(vote != null){
            activityService.createActivity("Membuat Vote", userId, eventId);
        }
        return vote1;
    }

    @PostMapping("/voter/{voteId}/{answerId}/{comitteeId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "task object", notes = "create new task")
    public @ResponseBody
    List<Answer> createVoter(
            @ApiParam(value = "the id of option")
            @PathVariable("voteId") UUID voteId,
            @ApiParam(value = "the id of option")
            @PathVariable("answerId") UUID answerId,
            @ApiParam(value = "the id of option")
            @PathVariable("comitteeId") UUID comitteeId,
            HttpServletRequest request, HttpServletResponse response
    ) {
        List<Answer> result = voteService.createVoter(voteId, answerId, comitteeId);
        EventComittee comittee = eventComitteeRepository.findOne(comitteeId);

        activityService.createActivity("Melakukan Vote pada Event",
                                        comittee.getComittee().getId(),
                                        comittee.getEvent().getId());
        return result;
    }
}

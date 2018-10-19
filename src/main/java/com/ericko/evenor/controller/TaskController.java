package com.ericko.evenor.controller;

import com.ericko.evenor.entity.Activity;
import com.ericko.evenor.entity.Job;
import com.ericko.evenor.entity.JobComment;
import com.ericko.evenor.entity.Task;
import com.ericko.evenor.service.activity.ActivityService;
import com.ericko.evenor.service.task.TaskService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.ericko.evenor.util.response.ResponseHandler.checkResourceFound;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private ActivityService activityService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get all task", notes = "List of all task")
    public @ResponseBody
    List<Task> getTask(HttpServletRequest request, HttpServletResponse response) {
        List<Task> result = taskService.getTask();
        checkResourceFound(result);
        return result;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get task by id", notes = "the task is search by id")
    public @ResponseBody
    Task getTask(
            @ApiParam(value = "the id of task")
            @PathVariable UUID id,
            HttpServletRequest request, HttpServletResponse response
    ) {
        Task result = taskService.getTask(id);
        checkResourceFound(result);
        return result;
    }

    @GetMapping("/event/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get task by id", notes = "the task is search by id")
    public @ResponseBody
    List<Task> getTaskByEvent(
            @ApiParam(value = "the id of event")
            @PathVariable UUID id,
            HttpServletRequest request, HttpServletResponse response
    ) {
       List<Task> result = taskService.getTaskByEvent(id);
        checkResourceFound(result);
        return result;
    }

    @GetMapping("/job/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get job by id", notes = "the job is search by id")
    public @ResponseBody
    Job getJob(
            @ApiParam(value = "the id of job")
            @PathVariable UUID id,
            HttpServletRequest request, HttpServletResponse response
    ) {
        Job result = taskService.getJob(id);
        checkResourceFound(result);
        return result;
    }

    @GetMapping("/job/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get job by id", notes = "the job is search by id")
    public @ResponseBody
    List<Job> getJobByUserId(
            @ApiParam(value = "the id of user")
            @PathVariable UUID id,
            HttpServletRequest request, HttpServletResponse response
    ) {
        List<Job> result = taskService.getJobByUserId(id);
        checkResourceFound(result);
        return result;
    }

    @GetMapping("/job/comment/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get job by id", notes = "the job is search by id")
    public @ResponseBody
    List<JobComment> getJobComment(
            @ApiParam(value = "the id of job")
            @PathVariable UUID id,
            HttpServletRequest request, HttpServletResponse response
    ) {
        List<JobComment> result = taskService.getJobComment(id);
        checkResourceFound(result);
        return result;
    }

    @PostMapping("/event/{idEvent}/{idUser}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "task object", notes = "create new task")
    public @ResponseBody
    Task createTask(
            @ApiParam(value = "the id of user")
            @PathVariable("idUser") UUID idUser,
            @ApiParam(value = "the id of event")
            @PathVariable("idEvent") UUID idEvent,
            @RequestBody Task task,
            HttpServletRequest request, HttpServletResponse response
    ) {
        Task result = taskService.createTask(idEvent, task);
        if(result != null) {
            activityService.createActivity("Membuat Task "+ result.getName(), idUser, idEvent);
        }
        return checkResourceFound(result);
    }

    @PostMapping("/job/{idTask}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "job object", notes = "create new job")
    public @ResponseBody
    Task createJob(
            @ApiParam(value = "the id of task")
            @PathVariable("idTask") UUID idTask,
            @ApiParam(value = "the id of event")
            @RequestParam("idEvent") UUID idEvent,
            @ApiParam(value = "the id of user")
            @RequestParam("idUser") UUID idUser,
            @RequestBody Job job,
            HttpServletRequest request, HttpServletResponse response
    ) {
        Task result = taskService.createJob(idTask, job);
        if(result != null) {
            activityService.createActivity("Membuat Job "+result.getName(), idUser, idEvent);
        }
        return checkResourceFound(result);
    }

    @PutMapping("/job/update/{idTask}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "job object", notes = "create new job")
    public @ResponseBody
    Job updateJob(
            @ApiParam(value = "the id of task")
            @PathVariable("idTask") UUID idTask,
            @ApiParam(value = "the id of event")
            @RequestParam("idEvent") UUID idEvent,
            @ApiParam(value = "the id of user")
            @RequestParam("idUser") UUID idUser,
            @RequestBody Job job,
            HttpServletRequest request, HttpServletResponse response
    ) {
        Job result = taskService.updateJob(job);
        if(result != null) {
            activityService.createActivity("Update Job "+ result.getName(), idUser, idEvent);
        }
        return checkResourceFound(result);
    }

    @PostMapping("/job/{idTask}/rekap")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "job object", notes = "create new job")
    public @ResponseBody
    Job jobCompletion(
            @ApiParam(value = "the id of task") @PathVariable("id") UUID id,
            @ApiParam(value = "the id of event") @RequestParam("idEvent") UUID idEvent,
            @ApiParam(value = "the id of user") @RequestParam("idUser") UUID idUser,
            @ApiParam(value = "String description") @RequestParam("completion") String completion,
            @ApiParam(value = "String description") @RequestParam("dateCompletion") String dateCompletion,
            HttpServletRequest request, HttpServletResponse response
    ) throws ParseException {
        Job result = taskService.jobCompletion(id, completion, dateCompletion);
        if(result != null) {
            activityService.createActivity("Melakukan rekap job "+ result.getName(), idUser, idEvent);
        }
        return checkResourceFound(result);
    }

    @DeleteMapping("/job/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "task id" , notes = "delete task by id")
    public @ResponseBody
    void deleteJob(
            @ApiParam(value = "the job id")
            @PathVariable("id") UUID id,
            HttpServletRequest request, HttpServletResponse response
    ) {
        taskService.deleteJob(id);
    }

    @PostMapping("/comment/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "job object", notes = "create new job")
    public @ResponseBody
    JobComment createJobComment(
            @ApiParam(value = "the id of job") @PathVariable("id") UUID jobId,
            @ApiParam(value = "the id of event") @RequestParam("idEvent") UUID idEvent,
            @ApiParam(value = "the id of user") @RequestParam("idUser") UUID idUser,
            @ApiParam(value = "String comment") @RequestParam("comment") String comment,
            HttpServletRequest request, HttpServletResponse response
    ) {
        JobComment result = taskService.createJobComment(jobId, idUser, comment, new Date());
        if(result != null) {
            activityService.createActivity("Membuat komentar di " + result.getJob().getName(), idUser, idEvent);
        }
        return checkResourceFound(result);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "task object", notes = "updating task data by id")
    public @ResponseBody Task updateTask(
            @ApiParam(value = "the task id")
            @PathVariable("id") String id,
            @ApiParam(value = "the id of event")
            @RequestParam("idEvent") UUID idEvent,
            @ApiParam(value = "the id of user")
            @RequestParam("idUser") UUID idUser,
            @RequestBody Task task,
            HttpServletRequest request, HttpServletResponse response
    ) {
        Task result = taskService.updateTask(task);
        if(result != null) {
            activityService.createActivity("Melakukan update task "+ result.getName(), idUser, idEvent);
        }
        return result;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "task id" , notes = "delete task by id")
    public @ResponseBody
    void deleteTask(
            @ApiParam(value = "the task id")
            @PathVariable("id") UUID id,
            @ApiParam(value = "the id of event")
            @RequestParam("idEvent") UUID idEvent,
            @ApiParam(value = "the id of user") @RequestParam("idUser") UUID idUser,
            HttpServletRequest request, HttpServletResponse response
    ) {
        taskService.deleteTask(id);
        activityService.createActivity("Menghapus Task", idUser, idEvent);
    }


}

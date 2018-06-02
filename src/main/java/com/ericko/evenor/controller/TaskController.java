package com.ericko.evenor.controller;

import com.ericko.evenor.entity.Job;
import com.ericko.evenor.entity.Task;
import com.ericko.evenor.service.task.TaskService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import static com.ericko.evenor.util.response.ResponseHandler.checkResourceFound;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

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

    @PostMapping("/event/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "task object", notes = "create new task")
    public @ResponseBody
    Task createTask(
            @ApiParam(value = "the id of event")
            @PathVariable("id") UUID id,
            @RequestBody Task task,
            HttpServletRequest request, HttpServletResponse response
    ) {
        return checkResourceFound(taskService.createTask(id, task));
    }

    @PostMapping("/job/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "job object", notes = "create new job")
    public @ResponseBody
    Task createJob(
            @ApiParam(value = "the id of task")
            @PathVariable("id") UUID id,
            @RequestBody Job job,
            HttpServletRequest request, HttpServletResponse response
    ) {
        return checkResourceFound(taskService.createJob(id, job));
    }

    @PostMapping("/job/{id}/rekap")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "job object", notes = "create new job")
    public @ResponseBody
    Job jobCompletion(
            @ApiParam(value = "the id of task") @PathVariable("id") UUID id,
            @ApiParam(value = "String description") @RequestParam("completion") String completion,
            @ApiParam(value = "String description") @RequestParam("dateCompletion") String dateCompletion,
            HttpServletRequest request, HttpServletResponse response
    ) throws ParseException {
        return checkResourceFound(taskService.jobCompletion(id, completion, dateCompletion));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "task object", notes = "updating task data by id")
    public @ResponseBody Task updateTask(
            @ApiParam(value = "the task id")
            @PathVariable("id") String id,
            @RequestBody Task task,
            HttpServletRequest request, HttpServletResponse response
    ) {
        return taskService.updateTask(task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "task id" , notes = "delete task by id")
    public @ResponseBody
    void deleteTask(
            @ApiParam(value = "the task id")
            @PathVariable("id") UUID id,
            HttpServletRequest request, HttpServletResponse response
    ) {
        taskService.deleteTask(id);
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
}

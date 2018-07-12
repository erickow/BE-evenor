package com.ericko.evenor.service.task;

import com.ericko.evenor.entity.Job;
import com.ericko.evenor.entity.Task;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Validated
public interface TaskService {

    List<Task> getTask();

    Task getTask(UUID id);

    List<Task> getTaskByEvent(UUID id);

    Task createTask(UUID id, @Valid Task task);

    Task updateTask( @Valid Task task);

    Job getJob(UUID id);

    Task createJob(UUID id, Job job);

    Job updateJob(UUID id, Job job);

    void deleteJob(UUID id);

    void deleteTask(UUID id);

    Job jobCompletion(UUID id, String completion, String dateCompletion) throws ParseException;

    Job createJobComment(UUID jobId, UUID userId, String comment, Date date);
}

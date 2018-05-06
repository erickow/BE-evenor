package com.ericko.evenor.service.Task;

import com.ericko.evenor.entity.Job;
import com.ericko.evenor.entity.Task;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
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
}

package com.ericko.evenor.service.Task;

import com.ericko.evenor.entity.Task;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Validated
public interface TaskService {

    List<Task> getTask();

    Task getTask(UUID id);

    Task createTask( @Valid Task task);

    Task updateTask( @Valid Task task);

    void deleteTask(UUID id);
}

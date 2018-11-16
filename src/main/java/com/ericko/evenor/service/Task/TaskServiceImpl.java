package com.ericko.evenor.service.Task;

import com.ericko.evenor.entity.Task;
import com.ericko.evenor.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Override
    public List<Task> getTask() {
        return (List<Task>) taskRepository.findAll();
    }

    @Override
    public Task getTask(UUID id) {
        return taskRepository.findOne(id);
    }

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(UUID id) {
        taskRepository.delete(id);
    }
}

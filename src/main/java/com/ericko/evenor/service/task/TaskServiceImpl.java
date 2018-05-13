package com.ericko.evenor.service.task;

import com.ericko.evenor.entity.Event;
import com.ericko.evenor.entity.Job;
import com.ericko.evenor.entity.Task;
import com.ericko.evenor.repository.EventRepository;
import com.ericko.evenor.repository.JobRepository;
import com.ericko.evenor.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private JobRepository jobRepository;

    @Override
    public List<Task> getTask() {
        return (List<Task>) taskRepository.findAll();
    }

    @Override
    public Task getTask(UUID id) {
        return  taskRepository.findOne(id);
    }

    @Override
    public List<Task> getTaskByEvent(UUID id) {
        Event event = eventRepository.findOne(id);
        return (List<Task>) taskRepository.findAllByEventOrderByPosition(event);
    }

    @Override
    public Task createTask(UUID id, Task task) {
        task.setEvent(eventRepository.findOne(id));
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Job getJob(UUID id) {
        return jobRepository.findOne(id);
    }

    @Override
    public Task createJob(UUID id, Job job) {
        Task task = taskRepository.findOne(id);
        task.setJobs(Arrays.asList(job));
        return taskRepository.save(task);
    }11

    @Override
    public Job updateJob(UUID id, Job job) {
        return jobRepository.save(job);
    }

    @Override
    public void deleteJob(UUID id) {
        jobRepository.delete(id);
    }

    @Override
    public void deleteTask(UUID id) {
        taskRepository.delete(id);
    }
}

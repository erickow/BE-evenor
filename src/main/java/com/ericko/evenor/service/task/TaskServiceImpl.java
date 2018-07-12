package com.ericko.evenor.service.task;

import com.ericko.evenor.entity.*;
import com.ericko.evenor.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.net.www.MimeTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private EventComitteeRepository eventComitteeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobCommentRepository jobCommentRepository;

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
    }

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

    @Override
    public Job jobCompletion(UUID id, String completion, String dateCompletion) throws ParseException {
        Job job = jobRepository.findOne(id);
        job.setCompletion(Boolean.valueOf(completion));
        Quest quest;
        if (job.getEndDate().before(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(String.valueOf(dateCompletion)))){
            quest = questRepository.findByCode("#ADD_COMPLETION");
        }else{
            quest = questRepository.findByCode("#ADD_COMPLETION_LATE");
        }
        if (Boolean.valueOf(completion)) {
            job.getComittees().stream().forEach(comittee -> {
                comittee.setScore(comittee.getScore() + quest.getScore());
                comittee.getComittee().setExperience(comittee.getComittee().getExperience() + quest.getScore());
                eventComitteeRepository.save(comittee);
            });
        }else{
            job.getComittees().stream().forEach(comittee -> {
                comittee.setScore(comittee.getScore() - quest.getScore());
                comittee.getComittee().setExperience(comittee.getComittee().getExperience() - quest.getScore());
                eventComitteeRepository.save(comittee);
            });
        }
        return jobRepository.save(job);
    }

    @Override
    public Job createJobComment(UUID jobId, UUID userId, String comment, Date date) {
        Job job = jobRepository.findOne(jobId);
        User user = userRepository.findOne(userId);
        JobComment jobComment = JobComment.builder().comment(comment).date(date).build();
        job.setComments(Arrays.asList(jobCommentRepository.save(jobComment)));
        return jobRepository.save(job);
    }
}

package com.ericko.evenor.service.task;

import com.ericko.evenor.entity.*;
import com.ericko.evenor.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.net.www.MimeTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public List<Job> getJobByUserId(UUID id) {
        User user = userRepository.findOne(id);
        return jobRepository.findAllByComittees_Comittee(user);
    }

    @Override
    public List<JobComment> getJobComment(UUID id) {
        Job job = jobRepository.findOne(id);
        return jobCommentRepository.findAllByJob(job);
    }

    @Override
    public Task createJob(UUID id, Job job) {
        Task task = taskRepository.findOne(id);
        List<Job> listJob = new ArrayList<>();
        listJob.add(jobRepository.save(job));
        task.setJobs(listJob);
        return taskRepository.save(task);
    }

    @Override
    public Job updateJob(Job job) {
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
        String level = job.getLevel();
        if (job.getEndDate().before(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(String.valueOf(dateCompletion)))){
            if(level.equals("easy")){
                quest = questRepository.findByCode("#ADD_COMPLETION");
            }else if(level.equals("medium")){
                quest = questRepository.findByCode("#ADD_COMPLETION");
            }else{
                quest = questRepository.findByCode("#ADD_COMPLETION");
            }
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
    public JobComment createJobComment(UUID jobId, UUID userId, String comment, Date date) {
        Job job = jobRepository.findOne(jobId);
        User user = userRepository.findOne(userId);
        JobComment jobComment = JobComment.builder().comment(comment).user(user).job(job).date(date).build();
        return jobCommentRepository.save(jobComment);
    }
}

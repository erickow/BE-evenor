package com.ericko.evenor.service.activity;

import com.ericko.evenor.entity.Activity;
import com.ericko.evenor.entity.Event;

import java.util.List;
import java.util.UUID;

public interface ActivityService {

    List<Activity> getActivityByEvent(UUID id);
    List<Activity> getActivityByUser(UUID id);
    List<Activity> getActivityByAllEvent(UUID id);
    Activity createActivity(String name, UUID idUser, UUID idEvent);
    void deleteActivity(UUID id);
}

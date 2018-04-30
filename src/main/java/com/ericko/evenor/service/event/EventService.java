package com.ericko.evenor.service.event;

import com.ericko.evenor.entity.Event;
import com.ericko.evenor.entity.EventComittee;
import com.ericko.evenor.entity.EventParticipant;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Validated
public interface EventService {
    List<Event> getEvent();

    Event getEvent(UUID id);

    List<EventComittee> getUpcommingEvent(UUID id);

    List<EventComittee> getHistoryEvent(UUID id);

    List<EventComittee> getComittee(UUID id);

    List<EventParticipant> getUpcomminhParticipate(UUID id);

    List<EventParticipant> getHistoryParticipate(UUID id);

    List<EventParticipant> getParticipant(UUID id);

    Event createEvent(UUID id, @Valid Event event);

    Event updateEvent(@Valid Event event);

    void deleteEvent(UUID id);
}

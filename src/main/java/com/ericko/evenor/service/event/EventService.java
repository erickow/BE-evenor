package com.ericko.evenor.service.event;

import com.ericko.evenor.entity.Event;
import com.ericko.evenor.entity.EventComittee;
import com.ericko.evenor.entity.EventParticipant;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Date;
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

    Event createEvent(
            UUID id,
            String name,
            String description,
            String setParticipant,
            String setComittee,
            String startDate,
            String endDate,
            MultipartFile file) throws FileFormatException, ParseException;

    Event updateEvent(@Valid Event event);

    void deleteEvent(UUID id);
}

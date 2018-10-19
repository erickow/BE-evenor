package com.ericko.evenor.service.event;

import com.ericko.evenor.configuration.FilesLocationConfig;
import com.ericko.evenor.entity.*;
import com.ericko.evenor.repository.*;
import com.ericko.evenor.service.storage.StorageService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.io.FilenameUtils;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventComitteeRepository eventComitteeRepository;

    @Autowired
    private EventParticipantRepository eventParticipantRepository;

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    @Qualifier("ImageStorageService")
    private StorageService imageStorageService;

    @Override
    public List<Event> getEvent() {
        return (List<Event>) eventRepository.findAll();
    }

    @Override
    public Event getEvent(UUID id) {
        return eventRepository.findOne(id);
    }

    @Override
    public List<EventComittee> getUpcommingEvent(UUID id) {
        User user = userRepository.findOne(id);
        return eventComitteeRepository.findAllByComitteeAndEvent_EndDateAfter(user, new Date());
    }

    @Override
    public List<EventComittee> getHistoryEvent(UUID id) {
        User user = userRepository.findOne(id);
        return eventComitteeRepository.findAllByComitteeAndEvent_EndDateBefore(user, new Date());
    }

    @Override
    public List<EventComittee> getComittee(UUID id) {
        return eventComitteeRepository.findAllByEvent_Id(id);
    }

    @Override
    public List<EventParticipant> getUpcomminhParticipate(UUID id) {
        User user = userRepository.findOne(id);
        return eventParticipantRepository.findAllByParticipantAndEvent_EndDateAfter(user, new Date());
    }

    @Override
    public List<EventParticipant> getHistoryParticipate(UUID id) {
        User user = userRepository.findOne(id);
        return eventParticipantRepository.findAllByParticipantAndEvent_EndDateBefore(user, new Date());
    }

    @Override
    public List<EventParticipant> getParticipant(UUID id) {
        return eventParticipantRepository.findAllByEvent_Id(id);
    }

    @Override
    public Event createEvent(
            UUID id,
            String name,
            String description,
            String setParticipant,
            String setComittee,
            String startDate,
            String endDate,
            MultipartFile file) throws FileFormatException, ParseException {

        String filename = FilenameUtils.removeExtension(file.getOriginalFilename());
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String imageName;

        if (Arrays.asList(FilesLocationConfig.Image.FILE_EXTENSION_ALLOWED).contains(extension)) {
            imageName = imageStorageService.storeOne(file, filename);
        }  else {
            throw new FileFormatException("Format tidak didukung");
        }

        Event event = Event.builder()
                            .name(name)
                            .description(description)
                            .setParticipant(Boolean.valueOf(setParticipant))
                            .setComittee(Boolean.valueOf(setComittee))
                            .startDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(String.valueOf(startDate)))
                            .endDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(String.valueOf(endDate)))
                            .photo(imageName)
                            .build();
        User user = userRepository.findOne(id);
        event.setPhoto(imageName);

        EventComittee eventComittee = EventComittee.builder().comittee(user).score(0).event(event).build();
        eventComitteeRepository.save(eventComittee);

        Quest quest = questRepository.findByCode("#ADD_EVENT");
        user.setExperience(user.getExperience() + quest.getScore());
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public void deleteEvent(UUID id) {
        eventRepository.delete(id);
    }
}

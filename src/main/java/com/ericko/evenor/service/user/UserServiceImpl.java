package com.ericko.evenor.service.user;

import com.ericko.evenor.configuration.FilesLocationConfig;
import com.ericko.evenor.entity.Event;
import com.ericko.evenor.entity.EventComittee;
import com.ericko.evenor.entity.User;
import com.ericko.evenor.repository.EventComitteeRepository;
import com.ericko.evenor.repository.EventRepository;
import com.ericko.evenor.repository.UserRepository;
import com.ericko.evenor.service.storage.StorageService;
import org.apache.commons.io.FilenameUtils;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventComitteeRepository eventComitteeRepository;

    @Autowired
    @Qualifier("ImageStorageService")
    private StorageService imageStorageService;

    @Override
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(UUID id) {
        return userRepository.findOne(id);
    }

    @Override
    public EventComittee getComittee(UUID userId, UUID eventId) {
        User comittee = userRepository.findOne(userId);
        Event event = eventRepository.findOne(eventId);
        return eventComitteeRepository.findByComitteeAndEvent(comittee, event);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(User user) {
        user.setPassword(getPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.delete(id);
    }

    @Override
    public List<User> searchByName(String name) {
        return userRepository.findAllByNameContains(name);
    }

    @Override
    public User uploadPhoto(UUID id, MultipartFile file) throws FileFormatException {
        String filename = FilenameUtils.removeExtension(file.getOriginalFilename());
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String imageName;

        User user = userRepository.findOne(id);
        if (Arrays.asList(FilesLocationConfig.Image.FILE_EXTENSION_ALLOWED).contains(extension)) {
            imageName = imageStorageService.storeOne(file, filename);
        }  else {
            throw new FileFormatException("Format tidak didukung");
        }
        user.setPhoto(imageName);
        return userRepository.save(user);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

package com.ericko.evenor.service.user;

import com.ericko.evenor.configuration.FilesLocationConfig;
import com.ericko.evenor.entity.User;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

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
    public User uploadPhoto(UUID id, MultipartFile[] file) throws FileFormatException {
        String filename = FilenameUtils.removeExtension(file[0].getOriginalFilename());
        String extension = FilenameUtils.getExtension(file[0].getOriginalFilename());
        ArrayList imageName;

        User user = userRepository.findOne(id);
        if (Arrays.asList(FilesLocationConfig.Image.FILE_EXTENSION_ALLOWED).contains(extension)) {
            imageName = imageStorageService.store(file, filename);
        }  else {
            throw new FileFormatException("Format tidak didukung");
        }
        user.setPhoto(String.valueOf(imageName));
        return userRepository.save(user);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

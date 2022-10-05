package com.hoaxify.ws.services;

import com.hoaxify.ws.dto.UserVM;
import com.hoaxify.ws.entity.User;
import com.hoaxify.ws.error.NotFoundException;
import com.hoaxify.ws.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService{

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User user){
        user.setPassword( this.passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Page<User>  getAllUsers(Pageable pageable, User user) {
        if(user != null){
        return  userRepository.findByUsernameNot(user.getUsername(),pageable);
        }
        return userRepository.findAll(pageable);
    }

    public User getUser(String username) {
        User inDb = userRepository.findByUsername(username);
        if(inDb == null){
            throw new NotFoundException();
        }
        return inDb;
    }
}

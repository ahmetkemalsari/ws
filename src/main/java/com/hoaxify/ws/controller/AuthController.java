package com.hoaxify.ws.controller;

import com.hoaxify.ws.entity.User;
import com.hoaxify.ws.repository.UserRepository;
import com.hoaxify.ws.shared.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/1.0/")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("auth")
    public ResponseEntity<?> handleAuthentication(@CurrentUser User user){

        return ResponseEntity.ok(user);
    }

}

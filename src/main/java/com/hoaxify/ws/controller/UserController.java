package com.hoaxify.ws.controller;


import com.hoaxify.ws.dto.UserVM;
import com.hoaxify.ws.entity.User;
import com.hoaxify.ws.services.UserService;
import com.hoaxify.ws.shared.CurrentUser;
import com.hoaxify.ws.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.function.Function;

@RestController
@RequestMapping("/api/1.0/users")
public class UserController {


    @Autowired
    UserService userService;


    @PostMapping(value = {"","/"})
    public GenericResponse createUser(@Valid @RequestBody User user) {
        userService.save(user);
        return (new GenericResponse("user created"));
    }

    @GetMapping("")
    public Page<UserVM> getUsers(Pageable page, @CurrentUser User user) {
      return userService.getAllUsers(page,user).map(UserVM::new);
    }
    @GetMapping("/{username}")
    public UserVM getUser(@PathVariable String username){
        return new UserVM(userService.getUser(username));
    }



}

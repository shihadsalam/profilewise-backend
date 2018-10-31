package com.boot.angular.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.angular.model.User;
import com.boot.angular.service.UserService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping({"/users"})
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = {"/signup"})
    public User create(@RequestBody User user){
        return userService.addUser(user);
    }
    
    @PutMapping
    public User update(@RequestBody User user){
        return userService.update(user);
    }

    @GetMapping(path = {"/{username}"})
    public User findUser(@PathVariable("username") String username){
        return userService.findUserByUserName(username);
    }
    
    @GetMapping(path = {"/get-all-usernames"})
    public List<String> findUserNames(){
        return userService.findAllUserNames();
    }

    @DeleteMapping(path ={"/{username}"})
    public User delete(@PathVariable("username") String username) {
        User user = findUser(username);
    		userService.deleteUser(user);
        return user;
    }

    @GetMapping
    public List<User> findAllUsers(){
        return userService.findAllUsers();
    }
}


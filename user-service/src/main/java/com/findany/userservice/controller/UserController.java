package com.findany.userservice.controller;

import com.findany.userservice.entity.Preference;
import com.findany.userservice.entity.User;
import com.findany.userservice.model.UserDetails;
import com.findany.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User addUser(@RequestBody UserDetails userDetails) {
        return userService.saveUserDetails(userDetails);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUser(id);
    }

    @PutMapping("/{id}/preferences")
    public User addUserPreferences(@PathVariable int id, @RequestBody Preference preference) {
        User user = userService.getUser(id);
        user.setPreference(preference);
        return userService.saveUser(user);
    }

}

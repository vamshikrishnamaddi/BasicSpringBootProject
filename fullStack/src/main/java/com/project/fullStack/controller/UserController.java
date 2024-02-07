package com.project.fullStack.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.fullStack.exception.UserNotFoundExcepiton;
import com.project.fullStack.model.User;
import com.project.fullStack.repository.UserRepository;

@RestController
@CrossOrigin("http://localhost:3000")

public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    User newUser (@RequestBody User newUser)
    {
        return userRepository.save(newUser);
    }
    @GetMapping("/getAllUsers")
    List<User> getAllUsers()
    {
        return userRepository.findAll();
    }
    @GetMapping("/user/{id}")
    User getUserById(@PathVariable long id)
    {
        return userRepository.findById(id)
                             .orElseThrow(() -> new UserNotFoundExcepiton(id));
    }
    @DeleteMapping("/removeUser/{id}")
    String deleteUserById(@PathVariable long id)
    {
        
        if(userRepository.existsById(id))
        {
            userRepository.deleteById(id);
            return "User With id " + id +" is Successfully Deleted";
        }
        else{
            throw new UserNotFoundExcepiton(id);
        }
    }

    @PatchMapping("updateUser/{id}")
    User updateUser(@PathVariable long id, @RequestBody User updatedUser)
    {
        // Optional<User> optionalUser = userRepository.findById(id);
        // if (optionalUser.isPresent()) {
        //     User existingUser = optionalUser.get();
        //     existingUser.setEmail(updatedUser.getEmail());
        //     existingUser.setUserName(updatedUser.getUserName());
        //     existingUser.setName(updatedUser.getName());
        //     userRepository.save(existingUser);
        //     return existingUser;
        // }
        // return new UserNotFoundExcepiton(id);
        return userRepository.findById(id).
        map(user -> {
                user.setEmail(updatedUser.getEmail());
                user.setName(updatedUser.getName());
                user.setUserName(updatedUser.getUserName());
                return userRepository.save(user);
            }).orElseThrow(() -> new UserNotFoundExcepiton(id));
    }
}

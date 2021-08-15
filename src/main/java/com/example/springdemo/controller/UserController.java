package com.example.springdemo.controller;

import com.example.springdemo.repository.UserRepository;
import com.example.springdemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public String getUsers(ModelMap modelMap){
        List<User> users = userRepository.findAll();
        modelMap.addAttribute("users",users);
        return "users";
    }

    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return "redirect:/";
        }
        modelMap.addAttribute("user", user.get());
        return "singleUser";
    }



    @PostMapping("/addUser")
    public String addUser(ModelMap modelMap){
        return "redirect:/users";
    }


}

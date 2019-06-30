package com.NightClubsAndTheirVisitors.project.controllers;


import com.NightClubsAndTheirVisitors.project.entities.User;
import com.NightClubsAndTheirVisitors.project.entities.enums.RoleType;
import com.NightClubsAndTheirVisitors.project.jpadatarepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

//@RestController
@RequestMapping("user")
@Controller
@Transactional
public class UserController {

    @Autowired
    UserRepository userRepository;

    @ModelAttribute("user")
    public Model registration(Model model) {
        return model.addAttribute("user", new User());
    }

    @GetMapping
    public String showRegistrationForm() {
        return "newuser";
    }


    @PostMapping
    @Transactional
    public String addNewUser(@ModelAttribute("user") @Valid User user, BindingResult result) {

        if (result.hasErrors()){
            return "newuser";
        }


        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        if (userRepository.findByUsername(user.getUsername())!=null){
            return  "redirect:/user?double";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUsername(user.getUsername());
        user.setRoles(Collections.singleton(RoleType.USER));
        userRepository.save(user);

        return "redirect:/club/clubs";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public String getUserInfo() {
        return "userinfo";
    }

    @RequestMapping ("/filter")
    public String onVisit(@RequestParam String filter, Model model) {

        if (userRepository.findByUsername(filter) == null) {
            return  "redirect:/user/userinfo?notfound"; }

        if (userRepository.clubsUserVisited(filter).isEmpty()) {
            return  "redirect:/user/userinfo?nooneclub"; }

        model.addAttribute("usersWithClub", userRepository.clubsUserVisited(filter));
        return "userinfo";

    }

    @RequestMapping ("/filter2")
    public String onVisitNotYet(@RequestParam String filter, Model model) {

        List<String> listClubs = userRepository.clubsUserVisitedNotYet();
        List<String> listClubsVisitedClub =  userRepository.clubsUserVisited(filter);

        if (userRepository.findByUsername(filter)==null) {
           return  "redirect:/user/userinfo?notfound2"; }

        listClubs.removeAll(listClubsVisitedClub);
        model.addAttribute("notvisitedclubs", listClubs);
        return "userinfo";

    }
}


package com.NightClubsAndTheirVisitors.project.controllers;

import com.NightClubsAndTheirVisitors.project.entities.Club;
import com.NightClubsAndTheirVisitors.project.entities.User;
import com.NightClubsAndTheirVisitors.project.jpadatarepository.ClubRepository;
import com.NightClubsAndTheirVisitors.project.jpadatarepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


//@RestController
@Controller
@Transactional
@RequestMapping("club")
public class ClubController {

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    UserRepository userRepository;

    @ModelAttribute("club")
    public Model registration(Model model) {
        return model.addAttribute("club", new Club());
    }

    @GetMapping
    public String showRegistrationForm() {
        return "newclub";
    }


    @PostMapping
    @Transactional
    public String addNewClub(@ModelAttribute("club") @Valid Club club, BindingResult result) {

        if (result.hasErrors()){
            return "newclub";
        }


        if (!clubRepository.getClubByNightClubName (club.getNightClubName()).isEmpty()){
            return  "redirect:/club?double";
        }

        clubRepository.save(club);
        return "redirect:/club/clubs";
    }


    @RequestMapping(value = "/clubs", method = RequestMethod.GET)
    public String getAllClubs(Model model) {

        model.addAttribute("clubs", clubRepository.findAll());
        return "listofclubs";
    }

    @GetMapping(value = "/club/clubs/{id}")
    public String onVisit(@AuthenticationPrincipal User user, @PathVariable Integer id) {

        Club club = clubRepository.findById(id).get();

      if (clubRepository.getClubAndUser(user.getId(), club.getId()).size()==0) {
          clubRepository.saveIdUserAndIdClub(user.getId(), club.getId());
      }

        int newCon =  club.getQuantityOfVisits() + 1;
        club.setQuantityOfVisits(newCon);

        clubRepository.save(club);
        return "redirect:/club/clubs?visited";
    }

    @GetMapping(value = "/club/clubs/delete/{id}")
    public String delete(@PathVariable Integer id) {

        Club club = clubRepository.findById(id).get();
        clubRepository.delete(club.getId());

        clubRepository.delete(club);
        return "redirect:/club/clubs";
    }

    @GetMapping(value = "/club/info/{id}")
    public String clubInfo(@PathVariable Integer id, Model model) {

    model.addAttribute("info", clubRepository.getAllUsersNameOneClub(id));
    model.addAttribute("usersId", userRepository.getAllUserId());
        return "clubinfo";
    }


    @GetMapping(value = "/club/clubs/info/{name}")
    public String getAll(@PathVariable String name, Model model) {
        return "redirect:/club/clubs";
    }



}

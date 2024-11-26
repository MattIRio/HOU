package com.heroesOfUbersharik.controller;

import com.heroesOfUbersharik.model.*;
import com.heroesOfUbersharik.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@Controller
public class TeamController {
    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    private MyUserRepository userRepository;
    @Autowired
    private MyTeamRepository teamRepository;
    @Autowired
    private MyTeamRequestsRepository requestsRepository;
    @Autowired
    private MyTeamMembersRepository membersRepository;
    @Autowired
    MyNotificationRepository notificationRepository;
    @Autowired
    MyMessageRepository myMessageRepository;

//Form for changing team information
    @Transactional
    @PostMapping("/submitForm2")
    public ResponseEntity<Map<String, Object>> addDataToTeamProfile(@ModelAttribute MyUser userModel, @AuthenticationPrincipal UserDetails userDetails, @ModelAttribute TeamModel teamModel, Model model) {

        Optional<MyUser> user = userRepository.findByEmail(userDetails.getUsername());
        Map<String, Object> response = new HashMap<>();
        MyUser existingUser = user.get();
        TeamModel existingTeamModel = new TeamModel();
        TeamMembersModel membersModel = new TeamMembersModel();


        if (teamRepository.findByTeamsCreatorID(existingUser.getId()).isPresent()) {
            response.put("success", false);
            response.put("errorMessage", "You are already a member of the team");
            return ResponseEntity.badRequest().body(response);
        }
        if (teamModel.getTeamName().length() < 4 || teamModel.getTeamName().length() > 20) {
            response.put("success", false);
            response.put("errorMessage", "The name must be 4 to 20 characters long");
            return ResponseEntity.badRequest().body(response);
        }
        if (teamModel.getTeamName().equals("Choose your option") || teamModel.getTeamsCountry().equals("Choose your option") || teamModel.getTeamsDifficulty().equals("Choose your option") || teamModel.getTeamsPlayingDays().equals("Choose your option") || teamModel.getTeamsGameMode().equals("Choose your option") || teamModel.getTeamsPlayingTime().equals("Choose your option")) {
            response.put("success", false);
            response.put("errorMessage", "All fields must be filled in");
            return ResponseEntity.badRequest().body(response);
        }

        //TEAM MODEL
        existingTeamModel.setTeamsCreatorID(existingUser.getId());

        if (teamModel.getTeamName() != "" && teamModel.getTeamName() != null) {
            existingTeamModel.setTeamName(teamModel.getTeamName());
        }
        if (teamModel.getTeamsDifficulty() != "" && teamModel.getTeamsDifficulty() != null) {
            existingTeamModel.setTeamsDifficulty(teamModel.getTeamsDifficulty());
        }
        if (teamModel.getTeamsCountry() != "" && teamModel.getTeamsCountry() != null) {
            existingTeamModel.setTeamsCountry(teamModel.getTeamsCountry());
        }
        if (teamModel.getTeamsGameMode() != "" && teamModel.getTeamsGameMode() != null) {
            existingTeamModel.setTeamsGameMode(teamModel.getTeamsGameMode());
        }
        if (teamModel.getTeamsPlayingTime() != "" && teamModel.getTeamsPlayingTime() != null) {
            existingTeamModel.setTeamsPlayingTime(teamModel.getTeamsPlayingTime());
        }
        if (teamModel.getTeamsPlayingDays() != "" && teamModel.getTeamsPlayingDays() != null) {
            existingTeamModel.setTeamsPlayingDays(teamModel.getTeamsPlayingDays());
        }


        membersModel.setMemberId(user.get().getId());
        System.out.println(user.get().getId());
        membersModel.setTeamName(teamModel.getTeamName());
        System.out.println();
        membersModel.setTeamOwnerId(user.get().getId());

        MyNotification newNotification = new MyNotification();

        newNotification.setMessage("You've created a team <b>" + teamModel.getTeamName() +"</b>");
        newNotification.setUserId(user.get().getId());

        notificationRepository.save(newNotification);

        membersRepository.save(membersModel);
        existingTeamModel.setAmountOfMembers(1);
        teamRepository.save(existingTeamModel);
        response.put("success", true);

        user.get().setChatRoomId(existingTeamModel.getChatRoomId());

        return ResponseEntity.ok(response);
    }

//Returns all available teams
    @GetMapping("/mainpage/teams")
    public ResponseEntity<ArrayList<TeamModel>> teamList(@RequestParam(value = "sortBy", required = false) String sortBy, @AuthenticationPrincipal TeamModel teamModel, @AuthenticationPrincipal UserDetails localuser) {
        ArrayList<TeamModel> teamMap = new ArrayList<>();
        List<TeamModel> sortedTeams = new ArrayList<>();


        if (sortBy != null) {
            switch (sortBy.toLowerCase()) {
                case "name":
                    sortedTeams = teamRepository.findAll(Sort.by(Sort.Direction.ASC, "teamName"));
                    break;
                case "country":
                    sortedTeams = teamRepository.findAll(Sort.by(Sort.Direction.ASC, "teamsCountry"));
                    break;
                case "difficulty":
                    sortedTeams = teamRepository.findAll(Sort.by(Sort.Direction.ASC, "teamsDifficulty"));
                    break;
                case "gamemode":
                    sortedTeams = teamRepository.findAll(Sort.by(Sort.Direction.ASC, "teamsGameMode"));
                    break;
                case "playingdays":
                    sortedTeams = teamRepository.findAll(Sort.by(Sort.Direction.ASC, "teamsPlayingDays"));
                    break;
                case "playingtime":
                    sortedTeams = teamRepository.findAll(Sort.by(Sort.Direction.ASC, "teamsPlayingTime"));
                    break;
                default:
                    sortedTeams = teamRepository.findAll();
            }
        } else {
            sortedTeams = teamRepository.findAll();
        }


        for (TeamModel currentUser : sortedTeams) {
            Boolean isActive = true;
            if (currentUser.getAmountOfMembers() == 4){
                continue;
            }else if (userRepository.findByEmail(localuser.getUsername()).get().getId() == currentUser.getTeamsCreatorID()) {              //IF USER IS OWNER
                isActive = false;
            } else if (requestsRepository.findByTeamOwnerIdAndSenderId(currentUser.getTeamsCreatorID(), userRepository.findByEmail(localuser.getUsername()).get().getId()).isPresent()) {    //IF USER ALREADY SENT REQUEST
                isActive = false;
            }

            TeamModel teamModel1 = new TeamModel(
                    currentUser.getTeamName(),
                    currentUser.getTeamsCountry(),
                    currentUser.getTeamsDifficulty(),
                    currentUser.getTeamsGameMode(),
                    currentUser.getTeamsPlayingDays(),
                    currentUser.getTeamsPlayingTime(),
                    isActive);
            teamMap.add(teamModel1);
        }
        return ResponseEntity.ok(teamMap);
    }


//Processes a request to join the team
    @PostMapping("/joinrequest")
    public ResponseEntity<Map<String, Object>> sendingRequest(@RequestBody TeamModel teamModel, @AuthenticationPrincipal UserDetails currentUser) {
        Map<String, Object> response = new HashMap<>();
        Optional<TeamModel> currentTeamOpt = teamRepository.findByTeamName(teamModel.getTeamName());

        Optional<MyUser> localUser = userRepository.findByEmail(currentUser.getUsername());

        if (membersRepository.findByMemberId(localUser.get().getId()).isPresent()) {
            response.put("message", "User is already a member");
            response.put("success", false);
            return ResponseEntity.ok(response);
        }

        TeamModel currentTeam = currentTeamOpt.get();
        currentTeam.setRequests_amount(currentTeam.getRequests_amount() + 1);

        TeamRequestModel teamRequestModel = new TeamRequestModel(
                userRepository.findByEmail(currentUser.getUsername()).get().getId(),
                currentTeam.getTeamsCreatorID(),
                LocalDate.now()
        );
        MyNotification newNotification = new MyNotification();
        newNotification.setMessage("You've requested to join team <b>" + teamModel.getTeamName() + "</b>");
        newNotification.setUserId(userRepository.findByEmail(currentUser.getUsername()).get().getId());

        notificationRepository.save(newNotification);

        requestsRepository.save(teamRequestModel);
        response.put("success", true);
        return ResponseEntity.ok(response);
    }


//Returns all join requests to the current team
    @GetMapping("/mainpage/requestsList")
    public ResponseEntity<ArrayList<MyUser>> requestList(@AuthenticationPrincipal TeamModel teamModel, @AuthenticationPrincipal UserDetails localuser) {
        ArrayList<MyUser> requestMap = new ArrayList<>();
        List<TeamRequestModel> allTeams = requestsRepository.findAll();

        for (TeamRequestModel currentUser : allTeams) {
            Boolean isActive = true;
            if (currentUser.getTeamOwnerId() == userRepository.findByEmail(localuser.getUsername()).get().getId()) {
                MyUser localUser = userRepository.findByid(currentUser.getSenderId()).get();

                MyUser userModel = new MyUser(
                        localUser.getName(),
                        localUser.getCountry(),
                        localUser.getEnglishLvl(),
                        localUser.getMainCareer(),
                        localUser.getAllCareers(),
                        localUser.getPlayingHours(),
                        localUser.getPlayingDays(),
                        localUser.getDifficulty(),
                        localUser.getGameMode());
                requestMap.add(userModel);
            }
        }

        return ResponseEntity.ok(requestMap);
    }

//Processes a request to add member to the team
    @Transactional
    @PostMapping("/mainpage/addmember")
    public ResponseEntity<Map<String, Object>> addmember(@RequestParam String userName, @AuthenticationPrincipal UserDetails currentUser) {
        ArrayList<TeamRequestModel> requestModelArrayList = new ArrayList<>();
        ArrayList<MyUser> membersModelArrayList = new ArrayList<>();

        Optional<MyUser> localUser = userRepository.findByEmail(currentUser.getUsername());
        Optional<TeamModel> currentTeam = teamRepository.findByTeamsCreatorID(localUser.get().getId());


        currentTeam.get().setRequests_amount(currentTeam.get().getRequestsAmount() - 1);
        currentTeam.get().setAmountOfMembers(currentTeam.get().getAmountOfMembers() + 1);


        requestsRepository.deleteBySenderId(userRepository.findByName(userName).get().getId());

        TeamMembersModel member = new TeamMembersModel(userRepository.findByName(userName).get().getId(), localUser.get().getId(), teamRepository.findByTeamsCreatorID(localUser.get().getId()).get().getTeamName());
        membersRepository.save(member);

        userRepository.findByName(userName).get().setChatRoomId(teamRepository.findByTeamsCreatorID(localUser.get().getId()).get().getChatRoomId());

        for (TeamRequestModel requestModel : requestsRepository.findAll()) {
            requestModelArrayList.add(requestModel);
        }
        for (TeamMembersModel membersModel : membersRepository.findAll()) {
            if (membersModel.getTeamOwnerId() == localUser.get().getId()) {
                MyUser trueUser = userRepository.findByid(membersModel.getMemberId()).get();
                membersModelArrayList.add(trueUser);
            }
        }
        MyNotification newNotification = new MyNotification();
        MyNotification newNotification1 = new MyNotification();

        newNotification.setMessage("You've added a member <b>" + userName + "</b> to your team");
        newNotification.setUserId(localUser.get().getId());

        newNotification1.setMessage("You've been added to a team <b>" + currentTeam.get().getTeamName() + "</b>");
        newNotification1.setUserId(userRepository.findByName(userName).get().getId());

        membersRepository.findByMemberId(userRepository.findByName(userName).get().getId()).get().setNotified(true);

        notificationRepository.save(newNotification);
        notificationRepository.save(newNotification1);


        Map<String, Object> response = new HashMap<>();
        response.put("requests", requestModelArrayList);
        response.put("members", membersModelArrayList);

        return ResponseEntity.ok(response);
    }


//Processes a request to add delete the team
    @Transactional
    @DeleteMapping("/mainpage/deleteTeam")
    public String deleteTeam(Principal principal) {
        Optional<MyUser> localUser = userRepository.findByEmail(principal.getName());
        Optional<TeamModel> localTeam = teamRepository.findBychatRoomId(localUser.get().getChatRoomId());
        List<MyUser> usersArray = userRepository.findAllBychatRoomId(localTeam.get().getChatRoomId());
        List<TeamRequestModel> requestArray = requestsRepository.findAllByTeamOwnerId(localUser.get().getId());

        for (MyUser user : usersArray) {
            myMessageRepository.deleteAllByChatRoomId(userRepository.findByEmail(principal.getName()).get().getChatRoomId());
            user.setChatRoomId(null);
        }

        for (MyUser user : usersArray) {
            if (user.getId() == localTeam.get().getTeamsCreatorID()) {
                MyNotification newNotification = new MyNotification();
                newNotification.setMessage("You've deleted a team <b>" + localTeam.get().getTeamName()+ "</b>");
                newNotification.setUserId(localUser.get().getId());
                notificationRepository.save(newNotification);
            } else {
                MyNotification newNotification = new MyNotification();
                newNotification.setMessage("Your team <b>" + localTeam.get().getTeamName() + "</b> was deleted");
                newNotification.setUserId(user.getId());
                notificationRepository.save(newNotification);
            }


            membersRepository.deleteAll(membersRepository.findAllByTeamName(localTeam.get().getTeamName()));
            requestsRepository.deleteAll(requestArray);
            teamRepository.delete(localTeam.get());
        }
        return "redirect:/mainpage";
    }


// Returns a bullean if current user is a team owner
    @GetMapping("/isowner")
    public ResponseEntity isOwner(Principal principal) {
        MyUser localUser = userRepository.findByEmail(principal.getName()).get();
        boolean response = false;
        TeamModel localTeam = teamRepository.findBychatRoomId(localUser.getChatRoomId()).get();

        if (localTeam.getTeamsCreatorID() == localUser.getId()){
            response = true;
        }
        return ResponseEntity.ok(response);
    }


// Processes a request to delete team member
    @DeleteMapping("/deleteteammember/{id}")
    @Transactional
    public ResponseEntity deleteteammember(@PathVariable Integer id) {
        MyUser localUser = userRepository.findByid(id).get();

        membersRepository.deleteByMemberId(localUser.getId());
        teamRepository.findBychatRoomId(localUser.getChatRoomId()).get().setAmountOfMembers(teamRepository.findBychatRoomId(localUser.getChatRoomId()).get().getAmountOfMembers() -1);
        localUser.setChatRoomId(null);

        return ResponseEntity.ok().build();
    }

// Returns name of team member by his id
    @GetMapping("/getmembername/{id}")
    @Transactional
    public ResponseEntity<String> getmembername(@PathVariable Integer id) {
        MyUser localUser = userRepository.findByid(id).get();
        return ResponseEntity.ok(localUser.getName());
    }


// Processes a request to delete a request to join the team
    @Transactional
    @PostMapping("/mainpage/deleterequest")
    public ResponseEntity deleteRequest(@RequestParam String userName, Principal principal) {
        requestsRepository.deleteBySenderId(userRepository.findByName(userName).get().getId());

        MyNotification newNotification = new MyNotification();
        newNotification.setMessage("Your request to join the team<b> " + teamRepository.findByTeamsCreatorID(userRepository.findByEmail(principal.getName()).get().getId()).get().getTeamName() + "</b> has been denied");
        newNotification.setUserId(userRepository.findByName(userName).get().getId());
        notificationRepository.save(newNotification);

        return ResponseEntity.ok("deleted");
    }


// Returns names of all team members
    @GetMapping("/mainpage/getallteammembersid")
    public ResponseEntity<ArrayList> getAllTeamMembersId(Principal principal) {
        List<TeamMembersModel> localTeam = membersRepository.findAllByTeamName(teamRepository.findByTeamsCreatorID(userRepository.findByEmail(principal.getName()).get().getId()).get().getTeamName());
        ArrayList<Integer> idList = new ArrayList();
        for (TeamMembersModel member : localTeam) {
            if (member.getMemberId() != member.getTeamOwnerId()) {
                idList.add(member.getMemberId());
            }
        }
        return ResponseEntity.ok(idList);
    }


// Processes a request to leave a request to join the team
    @Transactional
    @DeleteMapping("/mainpage/leaveteam")
    public ResponseEntity<String> leaveteam(Principal principal) {
        MyUser localUser = userRepository.findByEmail(principal.getName()).get();
        teamRepository.findBychatRoomId(localUser.getChatRoomId()).get().setAmountOfMembers(teamRepository.findBychatRoomId(localUser.getChatRoomId()).get().getAmountOfMembers()-1);
        membersRepository.deleteByMemberId(localUser.getId());
        localUser.setChatRoomId(null);

        return ResponseEntity.ok("deleted");
    }
}

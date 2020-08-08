package com.unicorn.studio.controller;

import com.unicorn.studio.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.unicorn.studio.entity.Team;
import com.unicorn.studio.service.UnicornService;

import java.util.List;

@RestController
public class TeamController {
    @Autowired
    private UnicornService unicornService;

    @GetMapping("/teams")
    public List<Team> getTeams() {
        return unicornService.getTeams();
    }

    @GetMapping("/team/{teamId}")
    public Team getTeam(@PathVariable int teamId) {
        Team team = unicornService.getTeam(teamId);
        if (team == null) {
            throw new NotFoundException("Team not found with ID:" + teamId);
        }
        return team;
    }

    @PostMapping("/teams")
    public Team addTeam(@RequestBody Team team) {
        team.setId((long)0);
        unicornService.saveTeam(team);
        return team;
    }

    @PutMapping("/teams")
    public Team updateTeam(@RequestBody Team team) {
        unicornService.saveTeam(team);
        return team;
    }

    @DeleteMapping("/teams/{teamId}")
    public String deleteTeam(@PathVariable int teamId) {
        Team isTeam = unicornService.getTeam(teamId);
        if (isTeam == null) {
            throw new NotFoundException("Team not found with ID:" + teamId);
        }
        unicornService.deleteTeam(teamId);
        return "Team deleted successfully for Id:" + teamId;
    }
}

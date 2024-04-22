package com.amigoscorp.ipl.controller;

import com.amigoscorp.ipl.model.Match;
import com.amigoscorp.ipl.model.Team;
import com.amigoscorp.ipl.repository.MatchRepository;
import com.amigoscorp.ipl.repository.TeamRepository;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TeamController {

  private final TeamRepository teamRepository;
  private final MatchRepository matchRepository;

  public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
    this.teamRepository = teamRepository;
    this.matchRepository = matchRepository;
  }

  @GetMapping("/team/{teamName}")
  @CrossOrigin
  public Team getTeam(@PathVariable String teamName) {
    Team team = teamRepository.findByTeamName(teamName);
    List<Match> matches = matchRepository.findLatestMatchesByTeam(teamName, 5);
    team.setMatches(matches);
    return team;
  }
}

package soccerapp.soccergames.controller;

import soccerapp.soccergames.service.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LeagueController {

    @Autowired
    private LeagueService leagueService;

    @GetMapping("/league")
    public String getLeagueTable(Model model) {
        model.addAttribute("teams", leagueService.getSortedTeams());
        return "league";
    }
}

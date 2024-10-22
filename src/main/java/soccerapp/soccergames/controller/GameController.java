package soccerapp.soccergames.controller;

import soccerapp.soccergames.domain.Game;
import soccerapp.soccergames.domain.Team;
import soccerapp.soccergames.repository.GameRepository;
import soccerapp.soccergames.repository.TeamRepository;
import soccerapp.soccergames.service.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class GameController {

    @Autowired
    private LeagueService leagueService;
    
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private GameRepository gameRepository;

    @GetMapping("/")
    public String home() {
        return "redirect:/games";
    }

    @GetMapping("/games")
    public String getGames(Model model) {
        List<Game> games = leagueService.getAllGames();
        model.addAttribute("games", games);
        return "games";
    }

    @PostMapping("/games/delete")
    public String deleteGame(@RequestParam Long gameId) {
        leagueService.deleteGame(gameId);
        return "redirect:/games";
    }

    @GetMapping("/addgame")
public String showAddGameForm(Model model) {
    model.addAttribute("game", new Game());
    model.addAttribute("teams", teamRepository.findAll());  
    return "addgame";
}

    @PostMapping("/addgame")
    public String addGame(@ModelAttribute Game game, @RequestParam String date) {
        LocalDate gameDate = LocalDate.parse(date);
        game.setDate(gameDate);
        leagueService.saveGame(game);
        return "redirect:/games";
    }

    @GetMapping("/editgame/{id}")
public String showEditGameForm(@PathVariable Long id, Model model) {
    Game game = gameRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Game not found"));
    model.addAttribute("game", game);
    model.addAttribute("teams", teamRepository.findAll());  
    return "editgame";
}

@PostMapping("/editgame/{id}")
public String editGame(@PathVariable Long id, @ModelAttribute Game gameDetails, @RequestParam String date) {
    Game game = gameRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Game not found"));

            Team homeTeam = teamRepository.findById(gameDetails.getHomeTeam().getId())
                .orElseThrow(() -> new RuntimeException("Home team not found"));
        Team awayTeam = teamRepository.findById(gameDetails.getAwayTeam().getId())
                .orElseThrow(() -> new RuntimeException("Away team not found"));
                
    game.setHomeTeam(homeTeam);
    game.setAwayTeam(awayTeam);
    game.setHomeScore(gameDetails.getHomeScore());
    game.setAwayScore(gameDetails.getAwayScore());
    
    LocalDate gameDate = LocalDate.parse(date);
    game.setDate(gameDate);

    leagueService.saveGame(game);
    return "redirect:/games";
}
}
package soccerapp.soccergames.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import soccerapp.soccergames.service.LeagueService;
import soccerapp.soccergames.domain.Game;
import soccerapp.soccergames.repository.GameRepository;

@RestController
@RequestMapping("/api/games")
public class GameRestController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private LeagueService leagueService;

    private static final Logger log = LoggerFactory.getLogger(GameRestController.class);

    @GetMapping
    public List<Game> getAllGames() {
        log.info("Fetch all games");
        return leagueService.getAllGames();
    }

    @GetMapping("/{id}")
public Game getGameById(@PathVariable Long id) {
    log.info("Fetch game with ID: " + id);
    return gameRepository.findById(id).orElse(null);
}

     @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    Game newGame(@RequestBody Game newGame){
        log.info("save new game" + newGame);
        return leagueService.saveGame(newGame);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
     @PutMapping("/{id}")
    public Game updateGame(@PathVariable Long id, @RequestBody Game updatedGame) {
        log.info("Update game with ID: " + id);
        updatedGame.setId(id); 
        return leagueService.saveGame(updatedGame);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        log.info("Delete game with ID: " + id);
        leagueService.deleteGame(id);
    }
}


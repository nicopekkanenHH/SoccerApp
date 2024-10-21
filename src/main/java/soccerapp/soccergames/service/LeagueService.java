package soccerapp.soccergames.service;

import soccerapp.soccergames.domain.Game;
import soccerapp.soccergames.domain.Team;
import soccerapp.soccergames.repository.GameRepository;
import soccerapp.soccergames.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeagueService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private TeamRepository teamRepository;

    
    public List<Game> getAllGames() {
        return gameRepository.findAll().stream()
                .sorted(Comparator.comparing(Game::getDate))
                .collect(Collectors.toList());
    }

    
    public void calculateLeagueTable() {
        List<Team> teams = teamRepository.findAll();
        
        for (Team team : teams) {
            team.setPoints(0);
            team.setPlayedGames(0);
        }

        
        for (Game game : gameRepository.findAll()) {
            adjustTeamStats(game, true);
        }
    }

    
    public Game saveGame(Game game) {

        Team homeTeam = teamRepository.findById(game.getHomeTeam().getId())
                .orElseThrow(() -> new RuntimeException("Home team not found"));
        Team awayTeam = teamRepository.findById(game.getAwayTeam().getId())
                .orElseThrow(() -> new RuntimeException("Away team not found"));

        game.setHomeTeam(homeTeam);
        game.setAwayTeam(awayTeam);
       
        if (game.getId() != null) {
            Game existingGame = gameRepository.findById(game.getId())
                    .orElseThrow(() -> new RuntimeException("Game not found"));
            adjustTeamStats(existingGame, false);
        }

        Game savedGame = gameRepository.save(game);
        adjustTeamStats(savedGame, true);

        calculateLeagueTable();

        return savedGame;
    }

    
    public void deleteGame(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found"));
        adjustTeamStats(game, false);
        gameRepository.deleteById(id);
    }

    
    private void adjustTeamStats(Game game, boolean isAdding) {
        Team homeTeam = game.getHomeTeam();
        Team awayTeam = game.getAwayTeam();

        
        if (isAdding) {
            homeTeam.setPlayedGames(homeTeam.getPlayedGames() + 1);
            awayTeam.setPlayedGames(awayTeam.getPlayedGames() + 1);
        } else {
            homeTeam.setPlayedGames(homeTeam.getPlayedGames() - 1);
            awayTeam.setPlayedGames(awayTeam.getPlayedGames() - 1);
        }

        
        if (game.getHomeScore() > game.getAwayScore()) {
            if (isAdding) {
                homeTeam.setPoints(homeTeam.getPoints() + 3);
            } else {
                homeTeam.setPoints(homeTeam.getPoints() - 3);
            }
        } else if (game.getHomeScore() < game.getAwayScore()) {
            if (isAdding) {
                awayTeam.setPoints(awayTeam.getPoints() + 3);
            } else {
                awayTeam.setPoints(awayTeam.getPoints() - 3);
            }
        } else {
            if (isAdding) {
                homeTeam.setPoints(homeTeam.getPoints() + 1);
                awayTeam.setPoints(awayTeam.getPoints() + 1);
            } else {
                homeTeam.setPoints(homeTeam.getPoints() - 1);
                awayTeam.setPoints(awayTeam.getPoints() - 1);
            }
        }

        
        teamRepository.save(homeTeam);
        teamRepository.save(awayTeam);
    }
    
    
    
    public List<Team> getSortedTeams() {
        List<Team> teams = teamRepository.findAll();
        teams.sort(Comparator.comparing(Team::getPoints).reversed());
        return teams;
    }
}


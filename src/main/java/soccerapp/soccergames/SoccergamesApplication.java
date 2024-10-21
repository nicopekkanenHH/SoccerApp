package soccerapp.soccergames;

import soccerapp.soccergames.domain.Team;
import soccerapp.soccergames.domain.Game;
import soccerapp.soccergames.repository.TeamRepository;
import soccerapp.soccergames.repository.GameRepository;
import soccerapp.soccergames.service.LeagueService;
import soccerapp.soccergames.service.UserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;

@SpringBootApplication
public class SoccergamesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoccergamesApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(TeamRepository teamRepository, GameRepository gameRepository, UserService userService, LeagueService leagueService) {
        return (args) -> {
            Team arsenal = new Team("Arsenal");
            Team city = new Team("City");
            Team manu = new Team("Manu");
            Team chels = new Team("Chels");
            Team varpool = new Team("Varpool");
            Team totenham = new Team("Totenham");

            teamRepository.save(arsenal);
            teamRepository.save(city);
            teamRepository.save(manu);
            teamRepository.save(chels);
            teamRepository.save(varpool);
            teamRepository.save(totenham);

            
			gameRepository.save(new Game(arsenal, city, 2, 1, LocalDate.of(2024, 9, 1)));
			gameRepository.save(new Game(manu, chels, 3, 1, LocalDate.of(2024, 9, 2)));
			gameRepository.save(new Game(varpool, totenham, 0, 0, LocalDate.of(2024, 9, 3)));
			gameRepository.save(new Game(arsenal, manu, 1, 1, LocalDate.of(2024, 9, 4)));
			gameRepository.save(new Game(city, chels, 2, 2, LocalDate.of(2024, 9, 5)));
			gameRepository.save(new Game(varpool, arsenal, 0, 3, LocalDate.of(2024, 9, 6)));
			gameRepository.save(new Game(totenham, city, 1, 2, LocalDate.of(2024, 10, 7)));
			gameRepository.save(new Game(chels, varpool, 0, 1, LocalDate.of(2024, 10, 8)));
			gameRepository.save(new Game(manu, totenham, 2, 2, LocalDate.of(2024, 10, 9)));
			gameRepository.save(new Game(arsenal, chels, 1, 0, LocalDate.of(2024, 10, 10)));
			gameRepository.save(new Game(city, manu, 1, 3, LocalDate.of(2024, 10, 11)));
			gameRepository.save(new Game(varpool, city, 1, 1, LocalDate.of(2024, 10, 12)));

			userService.createUser("admin", "admin", "ADMIN");
            userService.createUser("user", "user", "USER");

			leagueService.calculateLeagueTable();
        };
    }
}

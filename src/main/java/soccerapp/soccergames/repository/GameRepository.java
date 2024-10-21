package soccerapp.soccergames.repository;

import soccerapp.soccergames.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
    
}

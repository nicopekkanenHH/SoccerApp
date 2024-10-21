package soccerapp.soccergames.repository;

import soccerapp.soccergames.domain.Team;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findAllByOrderByPointsDesc();
}

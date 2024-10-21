package soccerapp.soccergames.repository;

import soccerapp.soccergames.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}

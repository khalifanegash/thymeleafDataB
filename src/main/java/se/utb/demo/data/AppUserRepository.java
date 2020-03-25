package se.utb.demo.data;

import org.springframework.data.repository.CrudRepository;
import se.utb.demo.entity.AppUser;

import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
    Optional<AppUser> findByEmailIgnoreCase(String email);
}

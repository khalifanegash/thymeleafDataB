package se.utb.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import se.utb.demo.entity.AppUser;

import java.time.LocalDate;

public interface AppUserService extends UserDetailsService {
    AppUser registerAppUser(String firstName, String lastName, String email, String password, LocalDate regDate, boolean isAdmin);
}

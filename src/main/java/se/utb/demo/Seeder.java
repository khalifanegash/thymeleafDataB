package se.utb.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.utb.demo.data.AppUserRepository;
import se.utb.demo.entity.AppUser;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class Seeder implements CommandLineRunner {
    private AppUserRepository repository;
    @Autowired
    public Seeder(AppUserRepository repository){
        this.repository = repository;
    }
    @Override
    public void run(String... args) throws Exception {
        List<AppUser> appUserList = Arrays.asList(
                new AppUser("Erik", "Svensson", "abcdefg10", "erik@gmail.com", LocalDate.now()),
                new AppUser("Nisse", "Nilsson", "abcdefg10", "nisse@gmail.com", LocalDate.now()),
                new AppUser("Simon", "Elbrink", "abcdefg10", "simon@gmail.com", LocalDate.now()),
                new AppUser("Ulf", "Bengtsson", "abcdefg10", "uffe@gmail.com", LocalDate.now())
        );


        repository.saveAll(appUserList);
    }
}

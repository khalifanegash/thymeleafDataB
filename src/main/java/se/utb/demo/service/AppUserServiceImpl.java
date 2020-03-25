package se.utb.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.utb.demo.data.AppRoleRepository;
import se.utb.demo.data.AppUserRepository;
import se.utb.demo.entity.AppRole;
import se.utb.demo.entity.AppUser;
import se.utb.demo.security.AppUserPrincipal;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AppUserServiceImpl implements AppUserService{

    private AppUserRepository appUserRepository; //Används generellt
    private AppRoleRepository appRoleRepository; //För att hämta roll(er)
    private BCryptPasswordEncoder passwordEncoder; //För att kryptera lösenordet

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AppUser> userOptional = appUserRepository.findByEmailIgnoreCase(email);
        AppUser appUser = userOptional.orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " could not be found"));

        //I AppRole role = "APP_USER"
        //I SimpleGrantedAuthority role = "ROLE_APP_USER"
        Collection<GrantedAuthority> authorities = new HashSet<>();
        for(AppRole role : appUser.getRoleSet()){
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return new AppUserPrincipal(appUser,authorities);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AppUser registerAppUser(String firstName, String lastName, String email, String password, LocalDate regDate, boolean isAdmin){
        AppUser newUser = new AppUser(
                firstName,
                lastName,
                passwordEncoder.encode(password),
                email,
                regDate
        );

        Set<AppRole> roles = new HashSet<>();

        if(isAdmin){
            AppRole admin = appRoleRepository.findByRole("app_admin").orElseThrow(IllegalArgumentException::new);
            roles.add(admin);
        }

        AppRole userRole = appRoleRepository.findByRole("app_user").orElseThrow(IllegalArgumentException::new);
        roles.add(userRole);

        newUser.setRoleSet(roles);

        return appUserRepository.save(newUser);
    }


}

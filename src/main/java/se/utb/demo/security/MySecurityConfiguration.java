package se.utb.demo.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class MySecurityConfiguration extends WebSecurityConfigurerAdapter {

    //AppUser uppfylla krav. UserDetails (interface) Skapa Principal.

    // Skapa en bean som implementerar UserDetailsService - viktig del när man loggar in - klar

    // Skapa en bean av en password krypterare - klar

    //Definera funktionalitet i MySecurityConfiguration - grundläggande

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/users/**").permitAll()
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .usernameParameter("email")
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout");
    }
    }


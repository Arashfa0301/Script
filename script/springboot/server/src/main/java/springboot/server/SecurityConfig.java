package springboot.server;

import core.main.User;
import data.DataHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    DataHandler datahandler = new DataHandler();

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf(crsf -> crsf.disable())
                .authorizeRequests(auth -> {
                    auth.antMatchers("/auth/register/").permitAll();
                    auth.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {

        Collection<UserDetails> users = new ArrayList<UserDetails>();

        for (User user : datahandler.read()) {
            UserDetails userDetails = org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles("USER")
                    .build();
            users.add(userDetails);
        }

        UserDetails admin = org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();

        users.add(admin);

        return new InMemoryUserDetailsManager(users);
    }
}
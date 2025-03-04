package org.varunmatangi.springsecurity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Bean
    UserDetailsService userDetailsService(){
        UserDetails user = User.builder()
                .username("root")
                .password("{noop}secret")
                .roles("ADMIN","USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    };
}

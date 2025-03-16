package com.leverx.javacourse.seller.rating.app.config;

import com.leverx.javacourse.seller.rating.app.entity.model.UserRoles;
import com.leverx.javacourse.seller.rating.app.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService (UserService userService){
        return new CustomUserDetailsService(userService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/auth/register_anon").hasAuthority(UserRoles.VISITOR.getAuthority())
                .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasAnyAuthority(UserRoles.SELLER.getAuthority(), UserRoles.VISITOR.getAuthority(), UserRoles.ADMINISTRATOR.getAuthority())
                .requestMatchers(HttpMethod.DELETE, "/items/{id}").hasAnyAuthority(UserRoles.SELLER.getAuthority(), UserRoles.ADMINISTRATOR.getAuthority())
                .requestMatchers(HttpMethod.PUT, "/items/{id}").hasAnyAuthority(UserRoles.SELLER.getAuthority(), UserRoles.ADMINISTRATOR.getAuthority())
                .requestMatchers(HttpMethod.DELETE, "/comments/{id}").hasAnyAuthority(UserRoles.VISITOR.getAuthority(), UserRoles.ADMINISTRATOR.getAuthority())
                .requestMatchers(HttpMethod.PUT, "/comments/{id}").hasAnyAuthority(UserRoles.VISITOR.getAuthority(), UserRoles.ADMINISTRATOR.getAuthority())
                .requestMatchers(HttpMethod.POST, "/users/{id}/comments").authenticated()
                .requestMatchers("/administration/**").hasAuthority(UserRoles.ADMINISTRATOR.getAuthority())
                .anyRequest().permitAll()).build();
    }
}

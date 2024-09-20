package fr.eni.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    /*
     * Méthode indiquant à Spring Security où sont les informations de connexion à l'application.
     */
    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

        // Récupère la liste des utilisateurs actifs
        userDetailsManager.setUsersByUsernameQuery("select pseudo, password, 1 from users where pseudo=?");

        // Récupère les autorisations de chaque utilisateur
        userDetailsManager.setAuthoritiesByUsernameQuery("select pseudo, authority from users where pseudo=?");

        return userDetailsManager;
    }


    /*
     * Méthode décrivant les rôles nécessaires pour accéder aux ressources
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                auth -> {
                    auth
                            // Tout le monde a le droit d'accéder à l'URL racine
                            .requestMatchers("/").permitAll()

                            // Autorise l'accès en GET à bonhommes pour les ADMIN et les EMPLOYE
                            .requestMatchers(HttpMethod.GET, "/bonhommes/**").hasAnyRole("ADMIN", "EMPLOYE")

                            // Autorise la modification de la ressource bonhomme uniquement pour l'ADMIN
                            .requestMatchers(HttpMethod.POST, "/bonhommes").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/bonhommes/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PATCH, "/bonhommes/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/bonhommes/**").hasRole("ADMIN")

                            // Toutes les autres requêtes sont refusées
                            .anyRequest().denyAll();
                }
        );
        // Utilise l'authentification basique de HTTP
        http.httpBasic(Customizer.withDefaults());

        // Désactive le CSRF pour permettre à Postman de réaliser des requêtes autres que GET
        // A ne mettre que pour les tests !!! A enlever en production.
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}







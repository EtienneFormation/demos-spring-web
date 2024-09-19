package fr.eni.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("select pseudo, password, 1 from users where pseudo=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select pseudo, authority from users where pseudo=?");

        return jdbcUserDetailsManager;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(auth -> {
          auth
                  .requestMatchers("/").permitAll()

                  .requestMatchers(HttpMethod.GET, "/bonhommes/**").hasAnyRole("EMPLOYE", "ADMIN")

                  .requestMatchers(HttpMethod.POST, "/bonhommes/**").hasAnyRole("ADMIN")
                  .requestMatchers(HttpMethod.PUT, "/bonhommes/**").hasAnyRole("ADMIN")
                  .requestMatchers(HttpMethod.PATCH, "/bonhommes/**").hasAnyRole("ADMIN")
                  .requestMatchers(HttpMethod.DELETE, "/bonhommes/**").hasAnyRole("ADMIN")
            .anyRequest().denyAll();
        });

        http.httpBasic(Customizer.withDefaults());

        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}

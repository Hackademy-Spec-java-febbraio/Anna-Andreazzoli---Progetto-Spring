package it.aulab.progettofinale.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import it.aulab.progettofinale.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers("/register/**").permitAll()
                        .requestMatchers("/admin/dashboard").hasRole("ADMIN")
                        .requestMatchers("/register", "/", "/articles", "/images/**", "articles/detail/**","/categories/search/{id}", "/search/{id}" ).permitAll() //permette l'accesso a tutti gli utenti e non solo agli utenti autenticati e e i permessi di visualizzazione anche a qualsiasi risorsa statica presente all’interno delfolder “images“
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll())
                .exceptionHandling(exception -> exception.accessDeniedPage("/error/403"))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .expiredUrl("/login?session-expired=true"));
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) //configura lo userDetailService nel nostro progetto ed utilizza un passwordEncoder
    throws Exception {
        auth.userDetailsService(customUserDetailsService)
        .passwordEncoder(passwordEncoder);
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
    throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

    
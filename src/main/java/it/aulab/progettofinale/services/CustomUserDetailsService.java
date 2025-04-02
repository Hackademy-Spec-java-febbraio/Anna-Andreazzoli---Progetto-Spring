package it.aulab.progettofinale.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.aulab.progettofinale.models.User;
import it.aulab.progettofinale.repositories.UserRepository;

import java.util.Arrays;
import java.util.Collection;


@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid credentials");
        }
        return new CustomUserDetails(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getPassword(),
        getAuthorities() //viene restituita una collezione di autorizzazion GrantedAuthority, appresentano i permessi assegnati a un utente autenticato
        
        );
    }
    
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("user"));
    }
    
}

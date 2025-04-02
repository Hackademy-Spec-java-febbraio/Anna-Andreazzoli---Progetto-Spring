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
import java.util.List;
import java.util.stream.Collectors;

import javax.management.relation.Role;


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
        mapRolesToAuthorities(user.getRoles())//sostituita  la funzione “getAuthorities()” con “mapRolesToAuthorities(user.getRoles())”, che mapperà i ruoli collegati al nostro utente.
        );
    }
    
    public Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<it.aulab.progettofinale.models.Role> roles) {
        Collection<? extends GrantedAuthority> mapRoles = null;
        if(roles.size() != 0){
            mapRoles = roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
        }
        else{
            mapRoles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return mapRoles;
    }
}

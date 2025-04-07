package it.aulab.progettofinale.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.aulab.progettofinale.dtos.UserDto;
import it.aulab.progettofinale.models.Role;
import it.aulab.progettofinale.models.User;
import it.aulab.progettofinale.repositories.RoleRepository;
import it.aulab.progettofinale.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public User findUserByEmail(String email) { // metodo analogo del repository
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(UserDto userDto, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) { //questo metodo in input riceverà httpServletRequest e httpServletResponse che mi servono per gestire il redirect verso la home page dopo la registrazione
        User user = new User(); //creo un nuovo oggetto utente perchè UserDTO non è un'entità quindi non lo posso salvare direttamente
        user.setUsername(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); //codifico la password con BCryptPasswordEncoder in modo che non venga salvata in chiaro nel database

        Role role = roleRepository.findByName("ROLE_USER");//troviamo il ruolo per poi assegnarlo allo user.
        user.setRoles(List.of(role));//assegna un elenco di ruoli all'utente ma inizialmente un utente entra con ruolo User
        
        userRepository.save(user);
        authenticateUserAndSetSession(user, userDto, request); // effettua una login tramite token all’interno della sessione mantenendola attiva
    }

    private void authenticateUserAndSetSession(User user, UserDto userDto, HttpServletRequest request) {

        try {
            CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(userDto.getEmail());
            
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDto.getPassword());

            Authentication authentication = authenticationManager.authenticate(authToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        } catch (AuthenticationException e) {
            e.printStackTrace();
        }   
    }

    @Override
    public User find(Long id) { 
        return userRepository.findById(id).get();
    }
}

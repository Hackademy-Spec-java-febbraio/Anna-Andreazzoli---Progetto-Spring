package it.aulab.progettofinale.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.aulab.progettofinale.dtos.UserDto;
import it.aulab.progettofinale.models.User;
import it.aulab.progettofinale.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    


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

        userRepository.save(user);
    }
}

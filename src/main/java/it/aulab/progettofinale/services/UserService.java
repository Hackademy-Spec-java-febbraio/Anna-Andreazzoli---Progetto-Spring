package it.aulab.progettofinale.services;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.aulab.progettofinale.dtos.UserDto;
import it.aulab.progettofinale.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    void saveUser(UserDto userDto, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response); //salverò nuovo utente tramite DTO
    User findUserByEmail(String email); //riceve input email e restituisce utente
    User find(Long id); //restituisce utente in base all'id
}
//questo metodo mi serve per verificare se l'email è già presente nel database, in caso contrario posso procedere con la registrazione
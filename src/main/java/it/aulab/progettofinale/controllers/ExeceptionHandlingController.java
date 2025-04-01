package it.aulab.progettofinale.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ExeceptionHandlingController {
    
    //Rotta per la gestione e cattura di errori
    @GetMapping("/error/{number}")
    public String accessDenied(@PathVariable int number, Model model) { //metodo dinamico che riceve codice dell'errore e attiva delle logiche 
        if (number == 403) {
            return "redirect:/?notAuthorized";// reindirizza l’utente verso la pagina home con un messaggio flash che specifica di non essere autorizzati
        } 
        return "redirect:/";
    }
}

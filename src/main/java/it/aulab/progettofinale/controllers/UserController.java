package it.aulab.progettofinale.controllers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import it.aulab.progettofinale.dtos.ArticleDto;
import it.aulab.progettofinale.dtos.UserDto;
import it.aulab.progettofinale.models.User;
import it.aulab.progettofinale.services.ArticleService;
import it.aulab.progettofinale.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;
    

    // Rotta di home page
    @GetMapping("/")
    public String home(Model viewModel) {

        List<ArticleDto> articles = articleService.readAll();

        // Ordina gli articoli in base alla data di pubblicazione in ordine decrescente
        Collections.sort(articles, Comparator.comparing(ArticleDto::getPublishDate).reversed());

        List<ArticleDto> lastThreeArticles = articles.stream().limit(3).collect(Collectors.toList());
        viewModel.addAttribute("articles", lastThreeArticles); 
                
        return "home";
    }

    // Rotta per la registrazione
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserDto());
        return "auth/register";
    }

    // Rotta per la pagina di login
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    // Rotta per il salvataggio della registrazione
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto, //@Valid per la validazione dei dati in base a regole definite in classi UserDto
                                BindingResult result, // risultato della validazione dei dati, per raccogliere eventuali errori di validazione che si verificano durante il processo di binding dei dati
                                Model model,
                                RedirectAttributes redirectAttributes, 
                                HttpServletRequest request,//request e response che utilizzeremo per la redirect dopo la register che non possiamo ancora attivare
                                HttpServletResponse response) {

        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null, "There is already an account registered with that email address");
        }
    
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "auth/register";
        }

        userService.saveUser(userDto, redirectAttributes, request, response);
        
        redirectAttributes.addFlashAttribute("successMassage" , "Registrazione avvenuta con successo!");

        return "redirect:/"; //redirect della funzione di register verso la home
    }

    //Rotta per la ricerca degli articoli in base all'utente 
    @GetMapping("/search/{id}")
    public String userArticlesSearch(@PathVariable("id") Long id, Model viewModel) {
        User user = userService.find(id);
        viewModel.addAttribute("title", "Tutti gli articoli trovati per utente" + user.getUsername());

        List<ArticleDto> articles = articleService.searchByAuthor(user);
        viewModel.addAttribute("articles", articles);

        return "articles/articles";
    }
}    


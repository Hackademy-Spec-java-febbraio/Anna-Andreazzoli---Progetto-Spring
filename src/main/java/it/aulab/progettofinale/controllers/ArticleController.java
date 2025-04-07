package it.aulab.progettofinale.controllers;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import  org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.aulab.progettofinale.models.Article;
import it.aulab.progettofinale.models.Category;
import it.aulab.progettofinale.services.ArticleService;
import it.aulab.progettofinale.services.CrudService;
import it.aulab.progettofinale.dtos.ArticleDto;
import it.aulab.progettofinale.dtos.CategoryDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    @Qualifier("categoryService")
    private CrudService<CategoryDto,Category,Long> categoryService;

    @Autowired
    private ArticleService articleService;

    //Rotta index degli articoli 
    @GetMapping
    public String articlesIndex(Model viewModel){
        viewModel.addAttribute("title", "Tutti gli Articoli");
        
        List<ArticleDto> articles = articleService.readAll();
        
        Collections.sort(articles, Comparator.comparing(ArticleDto::getPublishDate).reversed()); //ordina i risultati in base all’articolo più recente che di conseguenza viene visualizzato per primo
        viewModel.addAttribute("articles", articles);
        return "article/articles";
    }
    

    //Rotta per la creazione di un articolo
    @GetMapping("create")
    public String articleCreate(Model viewModel){
        viewModel.addAttribute("title", "Crea un articolo");
        viewModel.addAttribute("article", new Article());
        viewModel.addAttribute("categories", categoryService.readAll());
        return "articles/create";
        
    }

    //Rotta per lo store di un articolo
    @PostMapping
    public String articleStore(@Valid @ModelAttribute("article") Article article, 
                                BindingResult result, 
                                RedirectAttributes redirectAttributes, 
                                Principal principal,
                                MultipartFile file,
                                Model viewModel) {

        //Controllo degli errori con validazioni 
        if (result.hasErrors()) {
            viewModel.addAttribute("title", "Crea un articolo");
            viewModel.addAttribute("article", article);
            viewModel.addAttribute("categories", categoryService.readAll());
            return "articles/create";
        }

        articleService.create(article, principal, file);
        redirectAttributes.addFlashAttribute("successMessage", "Articolo aggiunto con successo!");
        return "redirect:/";
    }

    //Rotta per dettaglio di un articolo
    @GetMapping("detail/{id}")
    public String articleDetail(@PathVariable("id") Long id, Model viewModel){
        viewModel.addAttribute("title", "Article Detail");
        viewModel.addAttribute("article", articleService.read(id));
        return "articles/detail";
    }
}


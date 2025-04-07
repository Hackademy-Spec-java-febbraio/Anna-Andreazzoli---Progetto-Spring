package it.aulab.progettofinale.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import it.aulab.progettofinale.dtos.ArticleDto;
import it.aulab.progettofinale.dtos.CategoryDto;
import it.aulab.progettofinale.services.ArticleService;
import it.aulab.progettofinale.services.CategoryService;
import it .aulab.progettofinale.models.Category;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ModelMapper modelMapper;

    //Rotta per la ricerca dell'articolo in base alla categoria
    @GetMapping
    public String categorySearch(@PathVariable("id") Long id, Model viewModel) {
        CategoryDto category = categoryService.read(id);

        viewModel.addAttribute("title","Tutti gli articoli trovati per categoria" + category.getName()); 

        List<ArticleDto> articles = articleService.searchByCategory(modelMapper.map(category, Category.class));
        viewModel.addAttribute("articles", articles);

        return "articles/articles";
    }
}

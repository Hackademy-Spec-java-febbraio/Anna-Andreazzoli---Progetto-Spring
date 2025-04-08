package it.aulab.progettofinale.services;

import java.security.Principal;
import java.util.List;
import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;

import it.aulab.progettofinale.dtos.CategoryDto;
import it.aulab.progettofinale.repositories.CategoryRespository;
import it.aulab.progettofinale.models.Article;
import it.aulab.progettofinale.models.Category;
import jakarta.transaction.Transactional;

@Service
public class CategoryService implements CrudService<CategoryDto, Category, Long>{

    @Autowired
    private CategoryRespository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public List<CategoryDto> readAll() {
        List<CategoryDto> dtos = new ArrayList<CategoryDto>();
        for(Category category: categoryRepository.findAll()){
            dtos.add(modelMapper.map(category, CategoryDto.class));
        }
        return dtos;
    }

    @Override
    public CategoryDto read(Long key) {
        return modelMapper.map(categoryRepository.findById(key), CategoryDto.class);
    }

    @Override
    public CategoryDto create(Category model, Principal principal, MultipartFile file) {
        return modelMapper.map(categoryRepository.save(model), CategoryDto.class);
    }

    @Override
    public CategoryDto update(Long key, Category model, MultipartFile file) {
        if(categoryRepository.existsById(key)){
            model.setId(key);
            return modelMapper.map(categoryRepository.save(model), CategoryDto.class);
            } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if(categoryRepository.existsById(id)){

            Category category = categoryRepository.findById(id).get();
            
            if (category.getArticles() != null) {
                Iterable<Article> articles = category.getArticles();
                for (Article article : articles) {
                    article.setCategory(null);
                }
            }
            
            categoryRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                
        }
    }
}

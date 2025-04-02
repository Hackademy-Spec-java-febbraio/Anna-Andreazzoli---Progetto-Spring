package it.aulab.progettofinale.services;

import java.security.Principal;
import java.util.List;
import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;

import it.aulab.progettofinale.dtos.CategoryDto;
import it.aulab.progettofinale.repositories.CategoryRespository;
import it.aulab.progettofinale.models.Category;

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
    public CategoryDto read(Long key) {return null;}

    @Override
    public CategoryDto create(Category model, Principal principal, MultipartFile file) {return null;}

    @Override
    public CategoryDto update(Long key, Category model, MultipartFile file) {return null;}

    @Override
    public void delete(Long key) {}
}

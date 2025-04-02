package it.aulab.progettofinale.repositories;

import org.springframework.data.repository.ListCrudRepository;

import it.aulab.progettofinale.models.Category;

public interface CategoryRespository extends ListCrudRepository<Category, Long>{
    
}

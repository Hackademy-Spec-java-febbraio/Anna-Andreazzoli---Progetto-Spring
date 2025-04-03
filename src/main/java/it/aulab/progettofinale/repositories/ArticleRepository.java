package it.aulab.progettofinale.repositories;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import it.aulab.progettofinale.models.Article;
import it.aulab.progettofinale.models.Category;
import it.aulab.progettofinale.models.User;

public interface ArticleRepository extends ListCrudRepository<Article, Long>{
    List<Article> findByUser(User user);
    List<Article> findByCategory(Category category);
}

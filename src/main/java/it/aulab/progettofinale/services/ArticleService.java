package it.aulab.progettofinale.services;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.CompletableFuture;
    
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import it.aulab.progettofinale.dtos.ArticleDto;
import it.aulab.progettofinale.models.Article;
import it.aulab.progettofinale.models.User;
import it.aulab.progettofinale.repositories.ArticleRepository;
import it.aulab.progettofinale.repositories.UserRepository;

@Service
public class ArticleService implements CrudService<ArticleDto, Article, Long>{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ImageService imageService;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ArticleDto> readAll() {
        List<ArticleDto> dtos = new ArrayList<ArticleDto>();
        for (Article article : articleRepository.findAll()) {
            dtos.add(modelMapper.map(article, ArticleDto.class));
        }
        return dtos;
    }

    @Override
    public ArticleDto read(Long key) {
        throw new UnsupportedOperationException("Unimplemented method 'read'");
    }

    @Override
    public ArticleDto create(Article article, Principal principal, MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userRepository.findById(userDetails.getId()).get();
            article.setUser(user);
        }
        
        if(!file.isEmpty()){
            try {
                String url = "";
                CompletableFuture<String> futureUrl = imageService.saveImageOnCloud(file);
                url = futureUrl.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String url = "";
        ArticleDto dto = modelMapper.map(articleRepository.save(article), ArticleDto.class);
        if(!file.isEmpty()){
            imageService.saveImageOnDB(url, article);
        }
        
        return dto;
    }

    @Override
    public ArticleDto update(Long key, Article model, MultipartFile file) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long key) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    } 
}

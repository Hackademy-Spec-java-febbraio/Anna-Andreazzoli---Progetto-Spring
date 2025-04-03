package it.aulab.progettofinale.services;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;

import it.aulab.progettofinale.models.Article;
import it.aulab.progettofinale.models.Image;

import org.springframework.http.*;

import jakarta.transaction.Transactional;

@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    private ImageRepository imageRepository;

    @Value("${supabase.url}")
    private String supabeseUrl;

    @Value("${supabase.key}}")
    private String supabaseKey;

    @Value("${supabase.bucket}}")
    private String supabaseBucket;
    
    @Value("${supabase.image}}")
    private String supabaseImage;

    private final RestTemplate restTemplate = new RestTemplate();

    public void saveImageOnDB(String url, Article article){
        url = url.replace(supabaseBucket, supabaseImage);
        imageRepository.save(Image.builder().path(url).article(article).build());
    }
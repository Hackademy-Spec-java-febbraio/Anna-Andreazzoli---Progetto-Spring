package it.aulab.progettofinale.services;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.multipart.MultipartFile;

import it.aulab.progettofinale.models.Article;

public interface ImageService {
    void saveImageOnDB(String url, Article article);
    CompletableFuture<String> saveImageOnCloud(MultipartFile file) throws Exception;
    void delateImage(String imagePath) throws IOException;
}

package it.aulab.progettofinale.dtos;

import java.time.LocalDate;

import it.aulab.progettofinale.models.Category;
import it.aulab.progettofinale.models.Image;
import it.aulab.progettofinale.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ArticleDto {
    private Long id;
    private String title;
    private String subtitle;
    private String body;
    private LocalDate publishDate;
    private User user;
    private Category category;
    private Image image;
}

package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.ArticleDto;
import com.alphacodes.librarymanagementsystem.DTO.ArticleViewDto;
import com.alphacodes.librarymanagementsystem.Model.Article;
import com.alphacodes.librarymanagementsystem.Model.User;
import com.alphacodes.librarymanagementsystem.repository.ArticleRepository;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import com.alphacodes.librarymanagementsystem.service.ArticleService;
import com.alphacodes.librarymanagementsystem.util.ImageUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ArticleDto addArticle(ArticleDto articleDto) {
        try {
            Article article = mapToArticle(articleDto);
            Article newArticle = articleRepository.save(article);
            return mapToArticleDto(newArticle);
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            throw new RuntimeException("Failed to add article. Please check your request.");
        }
    }


    @Override
    public String deleteArticle(int articleID) {
        articleRepository.deleteById(articleID);
        return "Article deleted Successfully";
    }

    @Override
    public ArticleDto getArticleById(int articleID) {
        Article article = articleRepository.findById(articleID).
                orElseThrow(() -> new RuntimeException("Article not found with id " + articleID));
        return mapToArticleDto(article);
    }

    @Override
    public List<ArticleDto> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(this::mapToArticleDto).collect(Collectors.toList());
    }

    private ArticleDto mapToArticleDto(Article article) {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setUserID(article.getAuthor().getUserID());
        articleDto.setTitle(article.getTitle());
        articleDto.setBody(article.getBody());

        // Compress the image bytes before storing
        byte[] articleImgBytes = article.getArticleImg();
        if (articleImgBytes != null) {
            byte[] compressedImage = ImageUtils.compressBytes(articleImgBytes);
            articleDto.setArticleImg(compressedImage);
        } else {
            // Handle null image scenario (e.g., provide a default image or throw an error)
            // For example:
            // throw new IllegalArgumentException("Article image cannot be null");
            // Or set a default image:
            // articleDto.setArticleImg(ImageUtils.getDefaultImageBytes());
            articleDto.setArticleImg(null); // Assuming null is acceptable for your use case
        }
        return articleDto;
    }

    private Article mapToArticle(ArticleDto articleDto) {
        Article article = new Article();
        // Set the author based on userID from ArticleDto
        User author = userRepository.findById(articleDto.getUserID())
                .orElseThrow(() -> new RuntimeException("User not found with id " + articleDto.getUserID()));
        article.setAuthor(author);
        article.setTitle(articleDto.getTitle());
        article.setBody(articleDto.getBody());

        // Check if articleImg is not null before decompressing
        if (articleDto.getArticleImg() != null) {
            // Decompress the stored image bytes before returning
            byte[] decompressedImage = ImageUtils.decompressBytes(articleDto.getArticleImg());
            article.setArticleImg(decompressedImage);
        }

        return article;
    }


    // For article view dto
    public List<ArticleViewDto> getAllArticleView() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public ArticleViewDto getArticleViewById(int articleId) {
        Optional<Article> article = articleRepository.findById(articleId);
        return article.map(this::convertToDto).orElse(null);
    }

    @Override
    public Article getArticleFullById(int articleId) {
        return articleRepository.findById(articleId).orElse(null);
    }

    private ArticleViewDto convertToDto(Article article) {
        ArticleViewDto dto = new ArticleViewDto();
        dto.setArticleID(article.getArticleId());
        dto.setUserID(article.getAuthor().getUserID());
        dto.setTitle(article.getTitle());
        dto.setBody(article.getBody());
        dto.setArticleImg(article.getArticleImg());
        return dto;
    }
}






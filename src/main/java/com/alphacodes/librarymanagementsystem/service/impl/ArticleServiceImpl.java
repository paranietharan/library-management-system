package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.ArticleDto;
import com.alphacodes.librarymanagementsystem.DTO.ArticleHomeDto;
import com.alphacodes.librarymanagementsystem.DTO.ArticleViewDto;
import com.alphacodes.librarymanagementsystem.EmailService.EmailService;
import com.alphacodes.librarymanagementsystem.Model.Article;
import com.alphacodes.librarymanagementsystem.Model.User;
import com.alphacodes.librarymanagementsystem.enums.Role;
import com.alphacodes.librarymanagementsystem.repository.ArticleCommentRepository;
import com.alphacodes.librarymanagementsystem.repository.ArticleRatingRepository;
import com.alphacodes.librarymanagementsystem.repository.ArticleRepository;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import com.alphacodes.librarymanagementsystem.service.ArticleService;
import com.alphacodes.librarymanagementsystem.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private EmailService emailService;
    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleRatingRepository articleRatingRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository, ArticleCommentRepository articleCommentRepository, ArticleRatingRepository articleRatingRepository, EmailService emailService) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.articleRatingRepository = articleRatingRepository;
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
            articleDto.setArticleImg(null); // Assuming null is acceptable for your use case
        }
        return articleDto;
    }

    private Article mapToArticle(ArticleDto articleDto) {
        Article article = new Article();
        // Set the author based on userID from ArticleDto
        User author = userRepository.findByUserID(articleDto.getUserID());

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

    @Override
    public List<ArticleViewDto> getArticleByUserId(String userId) {
        List<Article> articles = articleRepository.findByAuthor_UserID(userId);
        return articles.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public ArticleDto editArticle(ArticleDto articleDto, int articleId) {
        Optional<Article> existingArticle = articleRepository.findById(articleId);

        if (existingArticle.isPresent()) {
            Article updatedArticle = existingArticle.get();

            // Update article fields
            updatedArticle.setTitle(articleDto.getTitle());
            updatedArticle.setBody(articleDto.getBody());

            // Handle image update
            if (articleDto.getArticleImg() != null) {
                updatedArticle.setArticleImg(articleDto.getArticleImg()); // Ensure no additional compression here
            } else {
                updatedArticle.setArticleImg(null); // Assuming null is acceptable for your use case
            }

            // Save the updated article
            articleRepository.save(updatedArticle);

            // Send email to the author
            emailService.sendSimpleEmail(
                    updatedArticle.getAuthor().getEmailAddress(),
                    "Article Updated",
                    "Your article has been updated successfully\n" +
                            "Title: " + updatedArticle.getTitle() + "\n" +
                            "If you did not make this change, please contact us immediately." +
                            "\n\nRegards,\nLibrary Management System"
            );

            return mapToArticleDto(updatedArticle);
        } else {
            throw new RuntimeException("Article not found with id " + articleId);
        }
    }

    @Override
    public String DeleteArticle(int articleId, String userId) {
        Optional<Article> existingArticle = articleRepository.findById(articleId);

        if (existingArticle.isPresent()) {
            Article article = existingArticle.get();

            // Check if the user is the author of the article
            if (article.getAuthor().getUserID().equals(userId)) {
                //Delete comments and ratings associated with the article

                //01.Find and Deleted comments associated with the article
                articleCommentRepository
                        .findByArticle(article)
                        .forEach(articleCommentRepository::delete);
                System.out.println("Comments deleted successfully");

                //02. Find Article Rating and delete it
                articleRatingRepository.findByArticle(article)
                        .forEach(articleRatingRepository::delete);

                // Delete the article
                articleRepository.deleteById(articleId);

                // send email to the author
                emailService.sendSimpleEmail(
                        article.getAuthor().getEmailAddress(),
                        "Article Deleted",
                        "Your article has been deleted successfully\n" +
                                "Title: " + article.getTitle() + "\n" +
                                "If you did not make this change, please contact us immediately." +
                                "\n\nRegards,\nLibrary Management System"
                );

                return "Article deleted successfully";
            }  else {
                // check the user is LIBRARIAN
                User user = userRepository.findByUserID(userId);
                if (user.getRole() == Role.LIBRARIAN) {
                    //Delete comments and ratings associated with the article

                    //01.Find and Deleted comments associated with the article
                    articleCommentRepository
                            .findByArticle(article)
                            .forEach(articleCommentRepository::delete);
                    System.out.println("Comments deleted successfully");

                    //02. Find Article Rating and delete it
                    articleRatingRepository.findByArticle(article)
                            .forEach(articleRatingRepository::delete);

                    articleRepository.deleteById(articleId);

                    // Notify the author of the deletion by admin
                    emailService.sendSimpleEmail(
                            article.getAuthor().getEmailAddress(),
                            "Article Deleted By Admin",
                            "Your article has been deleted by the librarian\n" +
                                    "Title: " + article.getTitle() + "\n" +
                                    "If you have any additional queries, please contact us immediately." +
                                    "\n\nRegards,\nLibrary Management System"
                    );
                    return "Article deleted successfully by librarian";
                }

                throw new RuntimeException("You are not authorized to delete this article");
            }
        } else {
            throw new RuntimeException("Article not found with id " + articleId);
        }
    }

    @Override
    public List<ArticleHomeDto> searchArticleByHeading(String heading) {
        // return the articles that contains the words in the heading
        List<Article> articles = articleRepository.findByTitleContaining(heading);

        return articles.stream().map(this::convertToHomeDto).collect(Collectors.toList());
    }

    @Override
    public List<ArticleHomeDto> searchArticleByBody(String body) {
        // return the articles that contains the words in the body
        List<Article> articles = articleRepository.findByBodyContaining(body);

        return articles.stream().map(this::convertToHomeDto).collect(Collectors.toList());
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

    private ArticleHomeDto convertToHomeDto(Article article) {
        ArticleHomeDto dto = new ArticleHomeDto();

        dto.setArticleID(article.getArticleId());
        dto.setUserID(article.getAuthor().getUserID());
        dto.setTitle(article.getTitle());
        dto.setBody(article.getBody());
        dto.setArticleImg(article.getArticleImg());
        dto.setDateCreated(article.getDateCreated());

        return dto;
    }
}
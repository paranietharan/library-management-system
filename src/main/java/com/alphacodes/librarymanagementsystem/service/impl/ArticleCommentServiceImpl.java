package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.ArticleCommentDto;
import com.alphacodes.librarymanagementsystem.DTO.CommentDto;
import com.alphacodes.librarymanagementsystem.Model.ArticleComment;
import com.alphacodes.librarymanagementsystem.Model.User;
import com.alphacodes.librarymanagementsystem.repository.ArticleCommentRepository;
import com.alphacodes.librarymanagementsystem.repository.ArticleRepository;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import com.alphacodes.librarymanagementsystem.service.ArticleCommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {

    private final UserRepository userRepository;

    private final ArticleCommentRepository articleCommentRepository;

    private final ArticleRepository articleRepository;

    public ArticleCommentServiceImpl(UserRepository userRepository, ArticleCommentRepository articleCommentRepository, ArticleRepository articleRepository) {
        this.userRepository = userRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public ArticleComment addArticleComment(ArticleCommentDto articleCommentDto) {
        ArticleComment articleComment = new ArticleComment();

        // set article comment string
        articleComment.setComment(articleCommentDto.getComment());

        // set commenter
        User commenter = userRepository.findByUserID(articleCommentDto.getCommenterId());
        articleComment.setCommenter(commenter);

        // set article
        articleComment.setArticle(
                articleRepository.findById(articleCommentDto.getArticleId())
                        .orElseThrow(() -> new RuntimeException("Article not found with id " + articleCommentDto.getArticleId()))
        );

        // save article comment
        return articleCommentRepository.save(articleComment);
    }

    @Override
    public String deleteArticleComment(int articleCommentId) {
        ArticleComment articleComment = articleCommentRepository.findById(articleCommentId).orElseThrow(
            () -> new RuntimeException("Article Comment not found with id " + articleCommentId));
        articleCommentRepository.delete(articleComment);
        return "Article Comment deleted Successfully";
    }

    @Override
    public List<ArticleCommentDto> getAllArticleComments(int articleID) {
        List<ArticleComment> articleComments = articleCommentRepository.findByArticle(
                articleRepository.findById(articleID)
                        .orElseThrow(() -> new RuntimeException("Article not found with id " + articleID))
        );

        return articleComments.stream()
                .map(articleComment -> {
                    ArticleCommentDto articleCommentDto = new ArticleCommentDto();
                    articleCommentDto.setComment(articleComment.getComment());
                    articleCommentDto.setCommenterId(articleComment.getCommenter().getUserID());
                    articleCommentDto.setArticleId(articleComment.getArticle().getArticleId());
                    articleCommentDto.setArticleCommentId(articleComment.getArticleCommentId());
                    return articleCommentDto;
                })
                .collect(Collectors.toList());
    }

}

package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.CommentDto;
import com.alphacodes.librarymanagementsystem.Model.ArticleComment;
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
    public CommentDto addArticleComment(int articleID, CommentDto commentDto) {
        ArticleComment articleComment1 = mapToArticleComment(commentDto);
        articleComment1.setArticle(articleRepository.findById(articleID).orElseThrow(
            () -> new RuntimeException("Article not found with id " + articleID)));

        ArticleComment newArticleComment = articleCommentRepository.save(articleComment1);
        return mapToCommentDto(newArticleComment);
    }

    @Override
    public List<CommentDto> getAllArticleComments(int articleID) {
        List<ArticleComment> articleComments = articleCommentRepository.findByArticle(articleRepository.findById(articleID).orElseThrow(
            () -> new RuntimeException("Article not found with id " + articleID))
        );
        return articleComments.stream().map(this::mapToCommentDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto getArticleCommentById(int articleID, int acmID) {
        ArticleComment articleComment = articleCommentRepository.findById(acmID).orElseThrow(
            () -> new RuntimeException("Article Comment not found with id " + acmID));
        return mapToCommentDto(articleComment);
    }

    @Override
    public String deleteArticleComment(int articleID, int acmID) {
        ArticleComment articleComment = articleCommentRepository.findById(acmID).orElseThrow(
            () -> new RuntimeException("Article Comment not found with id " + acmID));
        articleCommentRepository.delete(articleComment);
        return "Article Comment deleted Successfully";
    }

    private ArticleComment mapToArticleComment(CommentDto commentDto) {
        ArticleComment articleComment = new ArticleComment();
        articleComment.setComment(commentDto.getComment());
        articleComment.setCommenter(userRepository.findByUserID(commentDto.getUserID()).orElse(null));
        return articleComment;
    }

    private CommentDto mapToCommentDto(ArticleComment articleComment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setComment(articleComment.getComment());
        commentDto.setUserID(articleComment.getCommenter().getUserID());
        return commentDto;
    }


}

package com.alphacodes.librarymanagementsystem.DTO;
import lombok.Data;
import java.util.Date;
@Data
public class ArticleHomeDto {
    private int articleID;
    private String userID;
    private String title;
    private String body;
    private byte[] articleImg;
    private Date dateCreated;
}

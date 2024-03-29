package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cmID;

    @ManyToOne
    @JoinColumn(name = "clID")
    private Chat chat;

    private String chatMsg;
    private Date time;
    private Date date;
}


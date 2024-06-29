package com.alphacodes.librarymanagementsystem.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Resource {
    @Id
    private String resourceId;// ISBN
    private String title;// Book Nme
    private String author;// Author name
    private Integer availability;// Avilable copies
    private String about;// Description
    private String category;// Book category

    @Lob
    @Column(name = "book_img", columnDefinition = "LONGBLOB")
    private byte[] bookImg;

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Issue issue;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Reservation> reservations = new HashSet<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ResourceComment> resourceComments = new HashSet<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ResourceRating> resourceRatings = new HashSet<>();

}

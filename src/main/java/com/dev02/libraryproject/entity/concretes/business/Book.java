package com.dev02.libraryproject.entity.concretes.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String isbn;

    private int pageCount;

    @ManyToOne
    @JsonIgnore
    private Long authorId; //5 idli yazar

    @ManyToOne
    private Long publisherId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
    private int publishDate;

    @ManyToOne
    private Long categoryId; //4 idli kategori - Bilim Kurgu

    @Column(nullable = true)
    private File image;

    private boolean loanable = true;

    @Column(nullable = false)
    private String shelfCode; //format controle

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private boolean featured; //default false

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH:mm", timezone = "US")
    private LocalDateTime createDate;

    @Column(nullable = false)
    private boolean builtIn; //default false

    @OneToMany(mappedBy = "bookId",cascade = CascadeType.REMOVE)
    private List<Loan> loanId;

}

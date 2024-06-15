package com.dev02.libraryproject.entity.concretes.business;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Category {

    //Abstract Base Entity (ortak fieldlar olduğu için düşünülemez mi?)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean builtIn;

    // Dökümantasyonda burası yok, doğrusu nasıl olmalı?
    @OneToMany(mappedBy = "categoryId")
    private List<Book> bookList;

    @Column(nullable = false)
    private int sequence; //TODO: Bu kısmı araştır

}

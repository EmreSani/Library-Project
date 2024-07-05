package com.dev02.libraryproject.payload.response.business;

import com.dev02.libraryproject.entity.concretes.business.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {



    private Long id;


    private String name;


    private Boolean builtIn;


//    private List<Book> bookList; kitap listesini döndürmek için ne düşünmemiz lazım


    private int sequence;
}

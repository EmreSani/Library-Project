package com.dev02.libraryproject.payload.request.business;

import com.dev02.libraryproject.entity.concretes.business.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CategoryRequest {

    @NotNull
    @Size(min = 2, max = 80)
    private String name;

    @NotNull
    private Boolean builtIn;


    private List<Book> bookList;


    @NotNull
    private int sequence; //TODO: Bu kısmı araştır

}

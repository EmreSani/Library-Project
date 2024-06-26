package com.dev02.libraryproject.entity.concretes.business;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name can not be null")
    @Size(min = 2, max = 30, message = "Publisher name (${validatedValue}) {min} and {max} lengths allowed. !")
    private String name;

    @NotNull
    private Boolean builtIn;

    @OneToMany(mappedBy = "publisher")
    private List<Book> bookList;
}

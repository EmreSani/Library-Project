package com.dev02.libraryproject.entity.concretes.business;

<<<<<<< HEAD
import javax.persistence.*;
=======
>>>>>>> main
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


<<<<<<< HEAD
import javax.persistence.GenerationType;
=======
import javax.persistence.*;
>>>>>>> main
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean builtIn;

    @OneToMany(mappedBy = "authorId", cascade = CascadeType.REMOVE)
    private List<Book> bookList;


}

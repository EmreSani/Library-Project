package com.dev02.libraryproject.entity.concretes.user;

import com.dev02.libraryproject.entity.enums.RoleType;
<<<<<<< HEAD
import javax.persistence.*;
import javax.validation.constraints.NotNull;
=======


import com.fasterxml.jackson.annotation.JsonIgnore;
>>>>>>> main
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "roles")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleType roleType;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    private String name;
}

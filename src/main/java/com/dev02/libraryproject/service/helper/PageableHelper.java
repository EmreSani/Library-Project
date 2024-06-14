package com.dev02.libraryproject.service.helper;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Component
@RequiredArgsConstructor
public class PageableHelper {

    public Pageable getPageableWithProperties(int page, int size, String sort, String type)
    {
        Pageable pageable = PageRequest.of(page,size, Sort.by(sort).ascending());
        if(Objects.equals(type,"desc")){
            pageable = PageRequest.of(page,size, Sort.by(sort).descending());
        }
        return pageable ;
    }

    public Pageable getPageableWithProperties(int page, int size)
    {
        Pageable pageable = PageRequest.of(page,size, Sort.by("id").descending());
        pageable = PageRequest.of(page,size);
        return pageable ;
    }

}
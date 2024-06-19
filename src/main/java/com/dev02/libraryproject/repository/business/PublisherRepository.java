package com.dev02.libraryproject.repository.business;

import java.util.Optional;

import com.dev02.libraryproject.entity.concretes.business.Publisher;
import net.bytebuddy.jar.asm.commons.Remapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {



}


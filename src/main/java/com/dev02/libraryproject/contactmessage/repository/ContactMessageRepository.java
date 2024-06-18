package com.dev02.libraryproject.contactmessage.repository;

import com.dev02.libraryproject.contactmessage.entity.ContactMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
    Page<ContactMessage> findByEmailEquals(String email, Pageable pageable);

    Page<ContactMessage> findBySubjectEquals(String subject, Pageable pageable);

    @Query("SELECT c FROM ContactMessage c WHERE FUNCTION('DATE', c.dateTime) BETWEEN ?1 AND ?2")
    List<ContactMessage> findMessagesBetweenDates(LocalDate beginDate, LocalDate endDate);

    @Query("SELECT c FROM ContactMessage c WHERE " +
            "(EXTRACT(HOUR FROM c.dateTime) BETWEEN :startHour AND :endHour) AND " +
            "(EXTRACT(HOUR FROM c.dateTime) != :startHour OR EXTRACT(MINUTE FROM c.dateTime) >= :startMinute) AND " + // baslama dakikasina gore kontrol
            "(EXTRACT(HOUR FROM c.dateTime) != :endHour OR EXTRACT(MINUTE FROM c.dateTime) <= :endMinute)")
        // bitis dakikasina gore kontrol
    List<ContactMessage> findMessagesBetweenTimes(@Param("startHour") int startHour,
                                                  @Param("startMinute") int startMinute,
                                                  @Param("endHour") int endHour,
                                                  @Param("endMinute") int endMinute);

    	/*
		Yukardaki Query icin ornek senaryo :
		Db deki ContactMessage  ssati 09:30
		Sorguda gelen parametreler --> 09:15 / 17:15
		Ilk AND --> TRUE
		ikinci AND --> False OR True
		ucuncu AND --> True OR False
	 */ // Query aciklama


}

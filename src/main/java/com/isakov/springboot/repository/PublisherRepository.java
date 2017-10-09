package com.isakov.springboot.repository;

import com.isakov.springboot.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Publisher findByName(String name);

    @Transactional
    @Modifying
    @Query("DELETE FROM Publisher p WHERE p.id=:id")
    int delete(@Param("id") long id);
}

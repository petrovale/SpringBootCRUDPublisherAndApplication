package com.isakov.springboot.repository;

import com.isakov.springboot.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Publisher findByName(String name);

}

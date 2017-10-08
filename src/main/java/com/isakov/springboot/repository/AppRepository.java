package com.isakov.springboot.repository;

import com.isakov.springboot.model.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppRepository extends JpaRepository<App, Long> {

    App findByName(String name);

    @Query("SELECT a FROM App a WHERE a.publisher.id=:publisherId")
    List<App> getAll(@Param("publisherId") long publisherId);
}

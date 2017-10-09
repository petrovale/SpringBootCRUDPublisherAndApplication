package com.isakov.springboot.repository;

import com.isakov.springboot.model.App;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AppRepository extends JpaRepository<App, Integer> {

    @Query("SELECT a FROM App a WHERE a.publisher.id=:publisherId")
    List<App> getAll(@Param("publisherId") int publisherId);

    @EntityGraph(attributePaths={"genres"})
    @Query("SELECT a FROM App a WHERE a.publisher.id=?1")
    List<App> getAllWithGenre(int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM App a WHERE a.id=:id AND a.publisher.id=:publisherId")
    int delete(@Param("id") int id, @Param("publisherId") int publisherId);
}

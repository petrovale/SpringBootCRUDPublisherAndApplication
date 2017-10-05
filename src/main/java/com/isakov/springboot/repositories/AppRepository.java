package com.isakov.springboot.repositories;

import com.isakov.springboot.model.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppRepository extends JpaRepository<App, Long> {

    App findByName(String name);

    List<App> findByPublisher_id(Long publisherId);
}

package com.isakov.springboot.repositories;

import com.isakov.springboot.model.AppVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppVersionRepository extends JpaRepository<AppVersion, Long> {

    AppVersion findByName(String name);

    AppVersion findByActiveTrue();

    List<AppVersion> findByApp_id(Long appId);
}

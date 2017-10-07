package com.isakov.springboot.repositories;

import com.isakov.springboot.model.AppVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppVersionRepository extends JpaRepository<AppVersion, Long> {

    AppVersion findByName(String name);
}

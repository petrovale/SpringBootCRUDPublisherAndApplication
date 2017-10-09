package com.isakov.springboot.repository;

import com.isakov.springboot.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Repository
public interface VersionRepository extends JpaRepository<Version, Integer> {

    Version findByActiveTrue();

    @Query("SELECT v FROM Version v WHERE v.app.id=:appId AND v.publisher.id=:publisherId")
    List<Version> getAll(@Param("appId") int appId, @Param("publisherId") int publisherId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Version v WHERE v.id=:versionId AND v.app.id=:appId AND v.publisher.id=:publisherId")
    int delete(@Param("versionId") int versionId, @Param("appId") int appId, @Param("publisherId") int publisherId);
}

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
public interface VersionRepository extends JpaRepository<Version, Long> {

    Version findByName(String name);

    Version findByActiveTrue();

    List<Version> findByApp_id(Long appId);

    Version findByIdAndApp_idAndPublisher_id(long versionId, long appId, long publisherId);

    List<Version> findByApp_idAndPublisher_id(long appId, long publisherId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Version v WHERE v.id=:versionId AND v.app.id=:appId AND v.publisher.id=:publisherId")
    int delete(@Param("versionId") long versionId, @Param("appId") long appId, @Param("publisherId") long publisherId);
}

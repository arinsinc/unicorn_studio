package com.unicorn.studio.dao;



import com.unicorn.studio.entity.MediaStorage;
import com.unicorn.studio.projection.MediaStorageProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MediaStorageRepository  extends JpaRepository<MediaStorage, Long> {
    @Query(value="SELECT m.uid, m.fileName, m.mediaType FROM MediaStorage m WHERE m.fileName = :fileName")
    Optional<MediaStorageProjection> findByFilename(@Param("fileName") String fileName);

    void deleteByFileName(String fileName);

    @Query(value="SELECT m.uid, m.fileName, m.mediaType FROM MediaStorage m WHERE m.uid = :uid")
    Optional<MediaStorageProjection> findByUid(String uid);

    void deleteByUid(String uid);
}

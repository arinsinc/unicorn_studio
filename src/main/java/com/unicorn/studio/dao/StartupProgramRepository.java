package com.unicorn.studio.dao;



import com.unicorn.studio.entity.StartupProgram;
import com.unicorn.studio.projection.StartupProgramProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface StartupProgramRepository extends JpaRepository<StartupProgram, Long>, JpaSpecificationExecutor<StartupProgram>, PagingAndSortingRepository<StartupProgram, Long> {
    @Query("SELECT s.uid, s.name, s.profile, s.url, s.foundedYear, s.programType FROM StartupProgram s WHERE s.uid=:uid")
    Optional<StartupProgramProjection> findByUid(String uid);

    void deleteByUid(String uid);

    @Query("SELECT s.uid, s.name, s.profile, s.url, s.foundedYear, s.programType FROM StartupProgram s")
    List<StartupProgramProjection> findAllStartupPrograms();
}

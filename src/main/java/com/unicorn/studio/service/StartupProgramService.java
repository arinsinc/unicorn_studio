package com.unicorn.studio.service;

import com.unicorn.studio.entity.StartupProgram;
import com.unicorn.studio.projection.StartupProgramProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StartupProgramService {
    List<StartupProgramProjection> getStartupPrograms(Pageable pageable);
    void saveStartupProgram(StartupProgram startupProgram);
    StartupProgramProjection getStartupProgram(String uid);
    void deleteStartupProgram(String uid);
}

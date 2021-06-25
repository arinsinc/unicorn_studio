package com.unicorn.studio.service;



import com.unicorn.studio.dao.StartupProgramRepository;
import com.unicorn.studio.entity.StartupProgram;
import com.unicorn.studio.projection.StartupProgramProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StartupProgramServiceImp implements StartupProgramService{
    @Autowired
    private StartupProgramRepository startupProgramRepository;

    public StartupProgramServiceImp(StartupProgramRepository startupProgramRepository) {
        this.startupProgramRepository = startupProgramRepository;
    }

    /**
     * Get all startup programs
     * @return List<StartupProgram>
     */
    @Override
    public List<StartupProgramProjection> getStartupPrograms(Pageable pageable) {
        return startupProgramRepository.findAllStartupPrograms();
    }

    /**
     * Save a startup program
     * @param startupProgram
     */
    @Override
    public void saveStartupProgram(StartupProgram startupProgram) {
        startupProgramRepository.save(startupProgram);
    }

    /**
     * Get startup program by uid
     * @param uid
     * @return
     */
    @Override
    public StartupProgramProjection getStartupProgram(String uid) {
        return startupProgramRepository.findByUid(uid).get();
    }

    /**
     * Delete startup program by uid
     * @param uid
     */
    @Override
    public void deleteStartupProgram(String uid) {
        startupProgramRepository.deleteByUid(uid);
    }
}

package com.unicorn.studio.dao;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.unicorn.studio.entity.AuditLog;

@Repository
public class AuditLogDAOImp implements AuditLogDAO {
	
	
	@Override
	@Transactional
	public void saveLog(AuditLog logEntry) {
		System.out.println("hi there");
	}

	@Override
	@Transactional
	public AuditLog getLog(int id) {
		return null;
	}

}

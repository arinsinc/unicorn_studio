package com.unicorn.studio.dao;

import com.unicorn.studio.entity.AuditLog;

public interface AuditLogDAO {
	public void saveLog(AuditLog logEntry);
	public AuditLog getLog(int id);
}

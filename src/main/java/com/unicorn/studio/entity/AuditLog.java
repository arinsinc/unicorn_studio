package com.unicorn.studio.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="audit_log")
public class AuditLog {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="method_name")
	@NotNull
	private String methodName;
	
	@Column(name="method_type")
	@NotNull
	private String methodType;
	
	@Column(name="call_type")
	@NotNull
	private String callType;
	
	@Column(name="success")
	@NotNull
	private Boolean success;

	public AuditLog(String methodName, String methodType, String callType, Boolean success) {
		this.methodName = methodName;
		this.methodType = methodType;
		this.callType = callType;
		this.success = success;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodType() {
		return methodType;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	
}

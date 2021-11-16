package uoc.ded.practica.model;

import java.util.Date;

import uoc.ded.practica.SafetyActivities4Covid19.Mode;
import uoc.ded.practica.SafetyActivities4Covid19.Status;

public class Record {
	
	private String recordId;
	private String actId;
	private String description;
	private Date date;
	private Mode mode;
	private int num;
	private int organizationId;
	private Status status;
	
	public Record(String recordId, String actId, String description, Date date, Mode mode, int num, int organizationId) {
		this.setRecordId(recordId);
		this.setActId(actId);
		this.setDescription(description);
		this.setDate(date);
		this.setMode(mode);
		this.setMaxAssistants(num);
		this.setOrganizationId(organizationId);
		this.setStatus(Status.PENDING);
	}
	
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getRecordId() {
		return recordId;
	}
	
	public void setActId(String actId) {
		this.actId = actId;
	}
	
	public String getActId() {
		return actId;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public void setMaxAssistants(int num) {
		this.num = num;
	}
	
	public int getMaxAssistants() {
		return num;
	}
	
	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}
	
	public int getOrganizationId() {
		return organizationId;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Status getStatus() {
		return status;
	}
	
}

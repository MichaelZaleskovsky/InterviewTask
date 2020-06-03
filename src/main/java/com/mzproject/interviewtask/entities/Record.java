package com.mzproject.interviewtask.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Record {

	@Id
	@Column(name="PRIMARY_KEY", nullable=false)
	long primaryKey;
	
	@Column(name="NAME", nullable=false)
	String name;
	
	@Column(name="DESCRIPTION", nullable=false)
	String description;
	
	@Column(name="UPDATED_TIMESTAMP", nullable=false)
	long timeStamp;

	public long getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(long primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Record(long primaryKey, String name, String description, long timeStamp) {
		this.primaryKey = primaryKey;
		this.name = name;
		this.description = description;
		this.timeStamp = timeStamp;
	}
	
	public Record() {
	}

	@Override
	public String toString() {
		return "Record [primaryKey=" + primaryKey + ", name=" + name + ", description=" + description + ", timeStamp="
				+ timeStamp + "]";
	}
	
}

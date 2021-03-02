package com.fairportkungfu.util.classmanager.model;

import java.sql.Timestamp;

/**
 * Simple wrapper to hold basic properties of a class. Not persisted.
 * 
 * @author Mike Finn <mike@fairportkungfu.com>
 *
 */
public class FkfClass {

	private String program;
	private Timestamp startTime;
	private Timestamp endTime;

	public FkfClass() {
	}

	public FkfClass(String program, Timestamp startTime, Timestamp endTime) {
		super();
		this.program = program;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "FkfClass [program=" + program + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}

package com.bethford.babysitterkata;

public class HourlyWage {

	private int payRate;
	private String startTime;
	private String endTime;
	
	public HourlyWage(int payRate, String startTime, String endTime) {
		this.payRate = payRate;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public int getPayRate() {
		return payRate;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}	
}

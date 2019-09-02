package com.bethford.babysitterkata;

import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MINUTES;

public class BabysitterPay {
	
	private int totalDailyPay;
	private LocalTime bedtime;
	
	
	// Hard-coded variables for pay and cut-of times
	private LocalTime earliestStart = LocalTime.of(17,0);
	private LocalTime latestEnd = LocalTime.of(4, 0);
	private int standardRate = 12;
	private int bedTimeToMidnightRate = 8;
	private int midnightToFourRate = 16;
	

	public BabysitterPay(String bedtime) {
		this.bedtime = convertStringTimeToLocalTime(bedtime);
	}
	
	
	public int calculateTotalDailyPay(String startTime, String endTime) {
		LocalTime start = convertStringTimeToLocalTime(startTime);
		LocalTime end = convertStringTimeToLocalTime(endTime);
		
		int standardPayForTheDay = 0;
		int bedtimeToMidnightPayForTheDay = 0;
		int midnightToFourPayForTheDay = 0;
		
		standardPayForTheDay = findStandardPayAmount(start, end);

		totalDailyPay = standardPayForTheDay + bedtimeToMidnightPayForTheDay + midnightToFourPayForTheDay;
		
		return totalDailyPay;
	}
	
	private int findStandardPayAmount(LocalTime startTime, LocalTime endTime) {
		int hoursWorked = 0;
		if((startTime.isBefore(earliestStart) && startTime.isAfter(latestEnd))
				|| (startTime.isBefore(earliestStart) && startTime.isAfter(LocalTime.of(0, 0)) && startTime.isAfter(endTime))) {
			startTime = earliestStart;
			if(endTime.isBefore(bedtime)) {
				hoursWorked = Math.round(MINUTES.between(startTime, endTime) / 60);
			}
			else {
				hoursWorked = Math.round(MINUTES.between(startTime, bedtime) / 60);
			}
		}
		return 12 * hoursWorked;
	}
	
	

	
	private LocalTime convertStringTimeToLocalTime(String time) {
		String[] timeArray = time.substring(0, time.length() - 2).split(":");
		int hour = getMilitaryHourFromString(time, timeArray);
		int minutes = Integer.parseInt(timeArray[1]);
		return LocalTime.of(hour, minutes);
	}
	
	private int getMilitaryHourFromString(String time, String[] timeArray) {
		if(time.substring(time.length() - 2).contentEquals("AM")) {
			if(timeArray[0].equals("12")){
				return 0;
			}
			return Integer.parseInt(timeArray[0]);
		}
		else {
			return Integer.parseInt(timeArray[0]) + 12;
		}
	}


}

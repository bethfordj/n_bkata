package com.bethford.babysitterkata;

import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MINUTES;

public class BabysitterPay {

	private int totalDailyPay;
	private LocalTime bedtime;

	// Hard-coded variables for pay and cut-of times
	private LocalTime earliestStart = LocalTime.of(17, 0);
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
		
		if (start.isBefore(bedtime) && start.isAfter(latestEnd)) {
			standardPayForTheDay = findStandardPayAmount(start, end);
		}
		if (end.isAfter(bedtime) || end.isBefore(earliestStart)) {
			bedtimeToMidnightPayForTheDay = findBedtimeToMidnightPayAmount(start, end);
		}
		if (end.isBefore(earliestStart)) {
			midnightToFourPayForTheDay = findMidnightToFourPayAmount(start, end);
		}
		totalDailyPay = standardPayForTheDay + bedtimeToMidnightPayForTheDay + midnightToFourPayForTheDay;

		return totalDailyPay;
	}

	private int findStandardPayAmount(LocalTime startTime, LocalTime endTime) {
		int hoursWorked = 0;
		if (startTime.isBefore(earliestStart) && (startTime.isAfter(latestEnd) || startTime.isAfter(endTime))) {
			startTime = earliestStart;
		}
		if (endTime.isBefore(bedtime) && endTime.isAfter(earliestStart)) {
			hoursWorked = roundTimeToNextHour(MINUTES.between(startTime, endTime) / 60.0);
		} else if (!startTime.equals(endTime)) {
			hoursWorked = roundTimeToNextHour(MINUTES.between(startTime, bedtime) / 60.0);
		}
		return standardRate * hoursWorked;
	}

	private int findBedtimeToMidnightPayAmount(LocalTime startTime, LocalTime endTime) {
		int hoursWorked = 0;
		if (startTime.isBefore(bedtime) && startTime.isAfter(latestEnd)) {
			if (endTime.isBefore(earliestStart) || endTime.equals(LocalTime.MIDNIGHT)) {
				hoursWorked = roundTimeToNextHour((MINUTES.between(bedtime, LocalTime.of(23, 59)) + 1) / 60.0);
			} else {
				hoursWorked = roundTimeToNextHour(MINUTES.between(bedtime, endTime) / 60.0);
			}
		} else if ((startTime.isAfter(bedtime) || startTime.equals(bedtime)) && startTime.isAfter(latestEnd)) {
			if (endTime.isBefore(earliestStart) || endTime.equals(LocalTime.MIDNIGHT)) {
				hoursWorked = roundTimeToNextHour((MINUTES.between(startTime, LocalTime.of(23, 59)) + 1) / 60.0);
			} else {
				hoursWorked = roundTimeToNextHour(MINUTES.between(startTime, endTime) / 60.0);
			}
		}
		return bedTimeToMidnightRate * hoursWorked;
	}

	private int findMidnightToFourPayAmount(LocalTime startTime, LocalTime endTime) {
		int hoursWorked = 0;
		if (startTime.isBefore(LocalTime.of(23, 59)) || startTime.equals(LocalTime.MIDNIGHT)) {
			if (endTime.isAfter(latestEnd) && endTime.isBefore(earliestStart) || endTime.equals(latestEnd)) {
				hoursWorked = roundTimeToNextHour((MINUTES.between(LocalTime.MIDNIGHT, latestEnd)) / 60.0);
			} else {
				hoursWorked = roundTimeToNextHour((MINUTES.between(LocalTime.MIDNIGHT, endTime)) / 60.0);
			}

		} else if (startTime.isBefore(latestEnd) && startTime.isAfter(LocalTime.MIDNIGHT)) {
			if (endTime.isAfter(latestEnd) && endTime.isBefore(earliestStart) || endTime.equals(latestEnd)) {
				hoursWorked = roundTimeToNextHour((MINUTES.between(startTime, latestEnd)) / 60.0);
			} else {
				hoursWorked = roundTimeToNextHour((MINUTES.between(startTime, endTime)) / 60.0);
			}
		}
		return midnightToFourRate * hoursWorked;
	}

	private LocalTime convertStringTimeToLocalTime(String time) {
		String[] timeArray = time.substring(0, time.length() - 2).split(":");
		int hour = getMilitaryHourFromString(time, timeArray);
		int minutes = Integer.parseInt(timeArray[1]);
		return LocalTime.of(hour, minutes);
	}

	private int getMilitaryHourFromString(String time, String[] timeArray) {
		if (time.substring(time.length() - 2).contentEquals("AM")) {
			if (timeArray[0].equals("12")) {
				return 0;
			}
			return Integer.parseInt(timeArray[0]);
		} else if (Integer.parseInt(timeArray[0]) == 12) {
			return 12;
		} else {
			return Integer.parseInt(timeArray[0]) + 12;
		}
	}

	private int roundTimeToNextHour(double decimalHour) {
		if (decimalHour - (int) decimalHour > 0) {
			return (int) decimalHour + 1;
		} else {
			return (int) decimalHour;
		}
	}

}

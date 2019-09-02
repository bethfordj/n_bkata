package com.bethford.babysitterkata;

import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MINUTES;
/* This class calculates the day's pay of a babysitter based off of 3 different payment rates: 1. the standardRate which is for the hours from the 
 * earliest possible start time to bedtime, 2. the bedtimeToMidnightRate which is a reduced rate for the hours from the child's bedtime up to midnight,
 * and 3. the midnightToFourRate which is an increased rate from midnight to the latest hour the babysitter can work. The bedtime is assigned when the
 * class object is created, and the hours worked are entered into the calculateTotalDailyPay method as start and end times. Any hours worked outside of the
 * range allowed will not be calculated, and hours worked will be counted in whole hours only. */

public class BabysitterPay {

	private int totalDailyPay;
	private LocalTime bedtime;

	/*
	 * Hard-coded variables for the hours of the day when the babysitter can work.
	 * earliestStart is the first time a babysitter can start working for pay, and
	 * latestEnd is the latest time the babysitter can clock out.
	 */
	private LocalTime earliestStart = LocalTime.of(17, 0);
	private LocalTime latestEnd = LocalTime.of(4, 0);
	/*
	 * Hard-coded variables for payment rates. The standardRate is for 5pm to
	 * bedtime, the bedTimeToMidnightRate is for bedtime to midnight, and the
	 * midnightToFourRate is for midnight to 4 AM.
	 */
	private int standardRate = 12;
	private int bedTimeToMidnightRate = 8;
	private int midnightToFourRate = 16;

	/**
	 * CONSTRUCTOR: The constructor takes an assigned bedtime as a String and converts it to a
	 * private LocatTime variable
	 **/
	public BabysitterPay(String bedtime) {
		this.bedtime = convertStringTimeToLocalTime(bedtime);
	}

	/**********************************************************************************************************************
	 * PUBLIC METHODS
	 ********************************************************************************************************************/

	
	/**
	 * Public method for finding what the babysitter in question is owed for a
	 * single day's work: it takes a start time and end time as input.
	 **/
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

	/** getter for totalDailyPay **/
	public int getTotalDailyPay() {
		return totalDailyPay;
	}

	/**********************************************************************************************************************
	 * PRIVATE METHODS
	 ********************************************************************************************************************/

	/**
	 * Private method to find the pay owed for time worked from 5PM to bedtime and
	 * discount hours outside of allowed working hours
	 **/
	private int findStandardPayAmount(LocalTime startTime, LocalTime endTime) {
		int hoursWorked = 0;

		// Resets the start time to the earliest that the babysitter is allowed to start
		// if the hour provided is outside of that time
		if (startTime.isBefore(earliestStart) && (startTime.isAfter(latestEnd) || startTime.isAfter(endTime))) {
			startTime = earliestStart;
		}
		// Calculates the hours if the babysitter finished before bedtime
		if (endTime.isBefore(bedtime) && endTime.isAfter(earliestStart)) {
			hoursWorked = roundTimeToNextHour(MINUTES.between(startTime, endTime) / 60.0);
		}
		// Calculates the hours if the babysitter worked to bedtime or after
		else if (!startTime.equals(endTime)) {
			hoursWorked = roundTimeToNextHour(MINUTES.between(startTime, bedtime) / 60.0);
		}
		return standardRate * hoursWorked;
	}

	/**
	 * Private method to find the pay owed for time worked from bedtime to midnight
	 **/
	private int findBedtimeToMidnightPayAmount(LocalTime startTime, LocalTime endTime) {
		int hoursWorked = 0;

		// Selects a time period that started in the 5 to bedtime period
		if (startTime.isBefore(bedtime) && startTime.isAfter(latestEnd)) {
			// Calculates the hours if the babysitter worked for the full bedtime to
			// midnight period
			if (endTime.isBefore(earliestStart) || endTime.equals(LocalTime.MIDNIGHT)) {
				hoursWorked = roundTimeToNextHour((MINUTES.between(bedtime, LocalTime.of(23, 59)) + 1) / 60.0);
			}
			// Calculates the hours if the babysitter worked from before bedtime but not
			// until midnight
			else {
				hoursWorked = roundTimeToNextHour(MINUTES.between(bedtime, endTime) / 60.0);
			}
		}
		// Selects a time period that started after bedtime but before midnight
		else if ((startTime.isAfter(bedtime) || startTime.equals(bedtime)) && startTime.isAfter(latestEnd)) {
			// Calculates the hours worked starting after bedtime all the way through to
			// midnight
			if (endTime.isBefore(earliestStart) || endTime.equals(LocalTime.MIDNIGHT)) {
				hoursWorked = roundTimeToNextHour((MINUTES.between(startTime, LocalTime.of(23, 59)) + 1) / 60.0);
			}
			// Calculates the hours worked starting after bedtime and ending before midnight
			else {
				hoursWorked = roundTimeToNextHour(MINUTES.between(startTime, endTime) / 60.0);
			}
		}
		return bedTimeToMidnightRate * hoursWorked;
	}

	/** Private method to find the pay owed for time worked from midnight to 4AM **/
	private int findMidnightToFourPayAmount(LocalTime startTime, LocalTime endTime) {
		int hoursWorked = 0;
		// Selects a time period that started before midnight
		if (startTime.isBefore(LocalTime.of(23, 59)) || startTime.equals(LocalTime.MIDNIGHT)) {
			// Calculates the hours if the babysitter worked from midnight to 4AM
			if (endTime.isAfter(latestEnd) && endTime.isBefore(earliestStart) || endTime.equals(latestEnd)) {
				hoursWorked = roundTimeToNextHour((MINUTES.between(LocalTime.MIDNIGHT, latestEnd)) / 60.0);
			}
			// Calculates the hours if the babysitter worked from midnight but stopped
			// before 4AM
			else {
				hoursWorked = roundTimeToNextHour((MINUTES.between(LocalTime.MIDNIGHT, endTime)) / 60.0);
			}

		}
		// Selects a time period that started after midnight
		else if (startTime.isBefore(latestEnd) && startTime.isAfter(LocalTime.MIDNIGHT)) {
			// Calculates the hours worked starting after midnight and going to or through
			// 4AM
			if (endTime.isAfter(latestEnd) && endTime.isBefore(earliestStart) || endTime.equals(latestEnd)) {
				hoursWorked = roundTimeToNextHour((MINUTES.between(startTime, latestEnd)) / 60.0);
			}
			// Calculates the hours worked starting after midnight and stopping before 4AM
			else {
				hoursWorked = roundTimeToNextHour((MINUTES.between(startTime, endTime)) / 60.0);
			}
		}
		return midnightToFourRate * hoursWorked;
	}

	/**
	 * Private method to take a time as a String ("HH:mmAM" or "HH:mmPM") and
	 * convert it to a LocalTime object.
	 **/
	private LocalTime convertStringTimeToLocalTime(String time) {
		String[] timeArray = time.substring(0, time.length() - 2).split(":");
		int hour = getMilitaryHourFromString(time, timeArray);
		int minutes = Integer.parseInt(timeArray[1]);
		return LocalTime.of(hour, minutes);
	}

	/**
	 * Private method to take a time as a String ("HH:mmAM" or "HH:mmPM") and return
	 * an integer for the hour between 0 and 23 (inclusive).
	 **/
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

	/**
	 * Private method to round up to the next hour if even 1 minute is worked over
	 * an hour
	 **/
	private int roundTimeToNextHour(double decimalHour) {
		if (decimalHour - (int) decimalHour > 0) {
			return (int) decimalHour + 1;
		} else {
			return (int) decimalHour;
		}
	}

}

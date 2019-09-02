package com.bethford.babysitterkata;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class BabysitterPayUnitTest {
	
	private BabysitterPay testPay;
	
	
	@Before
	public void setup() {
		this.testPay = new BabysitterPay("8:00PM");
	}
	
	
	// No work returns no pay. //
	@Test
	public void no_hours_worked_zero_pay () {
		int result = testPay.calculateTotalDailyPay("5:00PM", "5:00PM");
		Assert.assertEquals(0, result);
	}
	
	// Hours worked outside of the allowable time returns no pay. //
	@Test
	public void hours_input_not_allowed_no_pay() {
		int result = testPay.calculateTotalDailyPay("12:00PM", "5:00PM");
		Assert.assertEquals(0, result);
	}

	// Hours partially outside allowable hours returns pay only for allowable time. //
	@Test
	public void hours_input_partly_in_allowable_range_paid_for_allowed_time_only() {
		int result = testPay.calculateTotalDailyPay("12:00PM", "6:00PM");
		Assert.assertEquals(12, result);
	}
	
	// Hours before bedtime ONLY with 8PM bedtime. //
	@Test
	public void standard_hours_only_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("5:00PM", "8:00PM");
		Assert.assertEquals(36, result);
	}
	
	// Hours after bedtime and before midnight ONLY with 8PM bedtime. //
	@Test
	public void bedtime_to_midnight_only_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("8:00PM", "10:00PM");
		Assert.assertEquals(16, result);
	}
	
	// Hours between midnight and four ONLY. //
	@Test
	public void midnight_to_four_only_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("12:00AM", "2:00AM");
		Assert.assertEquals(32, result);
	}
	
	// Hours before 5PM to after 8PM (but before midnight). //
	@Test
	public void time_before_5PM_to_after_bedtime_but_not_to_midnight_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("12:00PM", "9:00PM");
		Assert.assertEquals(44, result);
	}
	
	// Hours after bedtime to after midnight. //
	@Test
	public void after_bedtime_to_after_midnight_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("8:00PM", "1:00AM");
		Assert.assertEquals(48, result);
	}
	
	// Hours before 5PM to after midnight. //
	@Test
	public void time_before_5PM_to_after_midnight_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("12:00PM", "1:00AM");
		Assert.assertEquals(84, result);
	}
	
	// Hours before 5PM to after midnight. //
	@Test
	public void five_pm_to_after_midnight_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("5:00PM", "1:00AM");
		Assert.assertEquals(84, result);
	}
	
	// 15 minutes rounds up in standard hours and rate. //
	@Test
	public void standard_period_quarter_hour_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("5:00PM", "5:15PM");
		Assert.assertEquals(12, result);
	}
	
	// 15 minutes rounds up in after bedtime hours and rate. //
	@Test
	public void bedtime_period_quarter_hour_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("8:00PM", "8:15PM");
		Assert.assertEquals(8, result);
	}

	// 15 minutes rounds up in after midnight hours and rate. //
	@Test
	public void after_midnight_quarter_hour_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("12:00AM", "12:15AM");
		Assert.assertEquals(16, result);
	}
	
	// Fraction hours in both standard and post bedtime hours round separately. //
	@Test
	public void partial_standard_hour_and_partial_post_bedtime_hour_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("5:30PM", "8:45PM");
		Assert.assertEquals(44, result);
	}
	
	// Fraction hours in both standard and post midnight hours round separately. //
	@Test
	public void partial_standard_hour_and_partial_post_midnight_hour_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("5:30PM", "12:45AM");
		Assert.assertEquals(84, result);
	}
	
	// Fraction hours in both post bedtime and post midnight hours round separately. //
	@Test
	public void partial_post_bedtime_hour_and_partial_post_midnight_hour_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("8:30PM", "12:45AM");
		Assert.assertEquals(48, result);
	}
}

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
	
	
	// Test that no work returns no pay. //
	@Test
	public void no_hours_worked_zero_pay () {
		int result = testPay.calculateTotalDailyPay("5:00PM", "5:00PM");
		Assert.assertEquals(0, result);
	}
	
	// Test that hours worked outside of the allowable time returns no pay. //
	@Test
	public void hours_input_not_allowed_no_pay() {
		int result = testPay.calculateTotalDailyPay("12:00PM", "5:00PM");
		Assert.assertEquals(0, result);
	}

	// Test that hours partially outside allowable hours returns pay only for allowable time. //
	@Test
	public void hours_input_only_partly_in_allowable_range() {
		int result = testPay.calculateTotalDailyPay("12:00PM", "6:00PM");
		Assert.assertEquals(12, result);
	}
	
	// Test for hours before bedtime ONLY with 8PM bedtime. //
	@Test
	public void twelve_per_hour_range_only_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("5:00PM", "8:00PM");
		Assert.assertEquals(36, result);
	}
	
	// Test for hours after bedtime and before midnight ONLY with 8PM bedtime. //
	@Test
	public void eight_per_hour_range_only_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("8:00PM", "10:00PM");
		Assert.assertEquals(16, result);
	}
	
	// Test for hours after midnight ONLY with 8PM bedtime. //
	@Test
	public void sixteen_per_hour_range_only_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("12:00AM", "2:00AM");
		Assert.assertEquals(32, result);
	}
	
	// Test for hours before 5PM to after 8PM. //
	@Test
	public void mixed_regular_low_and_out_of_bounds_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("12:00PM", "9:00PM");
		Assert.assertEquals(44, result);
	}
	
	// Test for hours after bedtime to after midnight. //
	@Test
	public void after_bedtime_to_after_midnight_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("8:00PM", "1:00AM");
		Assert.assertEquals(48, result);
	}
	
	// Test for hours before 5PM to after midnight. //
	@Test
	public void mixed_regular_low_late_and_out_of_bounds_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("12:00PM", "1:00AM");
		Assert.assertEquals(92, result);
	}
	
	// Test for hours before 5PM to after 8PM. //
	@Test
	public void mixed_regular_low_and_late_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("5:00PM", "1:00AM");
		Assert.assertEquals(92, result);
	}
	
	// Test for 15 minutes in standard hours and rate. //
	@Test
	public void standard_quarter_hour_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("5:00PM", "5:15PM");
		Assert.assertEquals(12, result);
	}
	
	// Test for 15 minutes in after bedtime hours and rate. //
	@Test
	public void bedtime_quarter_hour_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("8:00PM", "8:15PM");
		Assert.assertEquals(8, result);
	}

	// Test for 15 minutes in after midnight hours and rate. //
	@Test
	public void after_midnight_quarter_hour_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("12:00AM", "12:15AM");
		Assert.assertEquals(16, result);
	}
	
	// Test for fraction hours in standard and post bedtime hours and rate. //
	@Test
	public void partial_standard_and_partial_post_bedtime_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("5:30PM", "8:45PM");
		Assert.assertEquals(44, result);
	}
	
	// Test for fraction hours in standard and post midnight hours and rate. //
	@Test
	public void partial_standard_and_partial_post_midnight_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("5:30PM", "12:45AM");
		Assert.assertEquals(92, result);
	}
	
	// Test for fraction hours in post bedtime and post midnight hours and rate. //
	@Test
	public void partial_post_bedtime_and_partial_post_midnight_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("8:30PM", "12:45AM");
		Assert.assertEquals(48, result);
	}
}

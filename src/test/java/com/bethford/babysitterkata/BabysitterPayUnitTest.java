package com.bethford.babysitterkata;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class BabysitterPayUnitTest {
	
	private BabysitterPay testPay;
	
	
	@Before
	public void setup() {
		this.testPay = new BabysitterPay("8:00 PM");
	}
	
	
	// Test that no work returns no pay. //
	@Test
	public void no_hours_worked_zero_pay () {
		int result = testPay.calculateTotalDailyPay("5:00 PM", "5:00 PM");
		Assert.assertEquals(0, result);
	}
	
	// Test that hours worked outside of the allowable time returns no pay. //
	@Test
	public void hours_input_not_allowed_no_pay() {
		int result = testPay.calculateTotalDailyPay("12:00 PM", "5:00 PM");
		Assert.assertEquals(0, result);
	}

	// Test that hours partially outside allowable hours returns pay only for allowable time. //
	@Test
	public void hours_input_only_partly_in_allowable_range() {
		int result = testPay.calculateTotalDailyPay("12:00PM", "6:00 PM");
		Assert.assertEquals(12, result);
	}
	
	// Test for hours before bedtime ONLY with 8PM bedtime. //
	@Test
	public void twelve_per_hour_range_only_calculates_correctly() {
		int result = testPay.calculateTotalDailyPay("5:00 PM", "8:00 PM");
		Assert.assertEquals(36, result);
	}

}

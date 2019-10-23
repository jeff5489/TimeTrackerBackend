package com.jeffstrunk.TimeTrackerWServlets;

public class TimePeriodTest {
	private static final long serialVersionUID = 1L;
	
	private String startTime;
	private ActivityType activity = ActivityType.UNDEFINED;
	
	public TimePeriodTest() {
	}
	
	public TimePeriodTest(String startTime, com.jeffstrunk.TimeTrackerWServlets.ActivityType activity) {
		super();
		this.startTime = startTime;
		this.activity = activity;
	}
	
	public String getstartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public ActivityType getActivity() {
		return activity;
	}
	public void setActivity(ActivityType activity) {
		this.activity = activity;
	}
}

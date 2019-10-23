package com.jeffstrunk.TimeTrackerWServlets;

public class TimePeriod {

	private static final long serialVersionUID = 1L;
	
	private String startTime;
	private ActivityType activity = ActivityType.UNDEFINED;
	
	public TimePeriod() {
	}
	
	public TimePeriod(String startTime, com.jeffstrunk.TimeTrackerWServlets.ActivityType activity) {
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
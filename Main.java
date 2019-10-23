package com.jeffstrunk.TimeTrackerWServlets;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

	public static void main(String[] args) {
//		timerTest();
		
//		try {
//			make96TPs();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		}

	public static void timerTest() {
		TimerTask repeatedTask = new TimerTask() {
			@Override
			public void run() {
				for(int i = 0; i < 96; i++) {
					LocalDateTime dateTime = LocalDateTime.of(2019, Month.OCTOBER, 14, 0, 0);	
					LocalDateTime dateTimeInstance = dateTime.plusMinutes(i * 15);

					String dateTimeToString = dateTimeInstance.toString();
					TimePeriodTest timePeriodTest = new TimePeriodTest();
					timePeriodTest.setStartTime(dateTimeToString);
					timePeriodTest.setActivity(ActivityType.UNDEFINED);
					
					System.out.println(i + " timePeriodTest.getstartTime().toString(): " + timePeriodTest.getstartTime().toString());
					System.out.println(i + " timePeriodTest.getActivity().toString(): " + timePeriodTest.getActivity().toString());
				}	
			}    
	    };
		
		Timer timer = new Timer("Timer");
	    long delay  = 1000L;
	    long period = 10000L;
	    timer.scheduleAtFixedRate(repeatedTask, delay, period);
	}
	
	public static void make96TPs() throws SQLException {
		
		String url = "jdbc:mysql://localhost/jeffstrunkcom?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	    String userName = "root";
	    String pass = "pass";
		
		TimePeriodDAO timePeriodDAO = new TimePeriodDAO(url, userName, pass);
		
		for(int i = 0; i < 96; i++) {
			LocalDateTime dateTime = LocalDateTime.of(2019, Month.OCTOBER, 15, 0, 0);	
			LocalDateTime dateTimeInstance = dateTime.plusMinutes(i * 15);

			String dateTimeToString = dateTimeInstance.toString();
			TimePeriod timePeriod = new TimePeriod();
			timePeriod.setStartTime(dateTimeToString);
			timePeriod.setActivity(ActivityType.UNDEFINED);
			
			timePeriodDAO.insertTimePeriod(timePeriod);
			
			System.out.println(i + " timePeriodTest.getstartTime().toString(): " + timePeriod.getstartTime().toString());
			System.out.println(i + " timePeriodTest.getActivity().toString(): " + timePeriod.getActivity().toString());
		}	
	}    
}
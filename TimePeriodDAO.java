package com.jeffstrunk.TimeTrackerWServlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TimePeriodDAO {
	
	private String url;
    private String userName;
    private String pass;
    private Connection connection;
  
	public TimePeriodDAO(String url, String userName, String pass) {
		this.url = url;
		this.userName = userName;
		this.pass = pass;
	}
    
	protected void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connection = DriverManager.getConnection(url, userName, pass);
        }
    }
     
    protected void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
    
   public TimePeriod getTimePeriod(String timePeriodId) throws SQLException {
	   TimePeriod timePeriod = new TimePeriod();
	   String sql = "SELECT * FROM TimePeriods where timePeriodId = ?";
	   connect();
	   PreparedStatement statement = connection.prepareStatement(sql);
       statement.setString(1, timePeriodId);
       ResultSet resultSet = statement.executeQuery();
       resultSet.next();
       String id = resultSet.getString("timePeriodId");
       String activityType = resultSet.getString("activityType");
       ActivityType activityEnum = ActivityType.valueOf(activityType);
       timePeriod.setStartTime(id);
       timePeriod.setActivity(activityEnum);
       resultSet.close();
       statement.close();
       disconnect();
       return timePeriod;
   }
   
    public List<TimePeriod> getAllTimePeriods() throws SQLException {
        List<TimePeriod> listTimePeriods = new ArrayList<>();
        String sql = "SELECT * FROM TimePeriods";
        connect();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            String timePeriodId = resultSet.getString("timePeriodId");
            String activityType = resultSet.getString("activityType");
            ActivityType activityEnum = ActivityType.valueOf(activityType);
            TimePeriod timePeriod = new TimePeriod(timePeriodId, activityEnum);
            listTimePeriods.add(timePeriod);
        }
        resultSet.close();
        statement.close();
        disconnect();
        return listTimePeriods;
    }
    
    public boolean insertTimePeriod(TimePeriod timePeriod) throws SQLException {
        String sql = "INSERT INTO TimePeriods (timePeriodId, activityType) VALUES (?, ?)";
        connect();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, timePeriod.getstartTime());
        statement.setString(2, timePeriod.getActivity().toString());
        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
    }
    
    public boolean updateTimePeriod(TimePeriod timePeriod) throws SQLException {
    	String sql = "UPDATE TimePeriods SET activityType = ? WHERE timePeriodId = ?";
        connect();
    	
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(2, timePeriod.getstartTime());
        statement.setString(1, timePeriod.getActivity().toString());
        
    	boolean rowInserted = statement.executeUpdate() > 0;
    	statement.close();
        disconnect();
    	return rowInserted;
    }
    
    public boolean deleteTimePeriod(String timePeriodId) throws SQLException {
    	String sql = "DELETE FROM TimePeriods WHERE timePeriodId = ?";
        connect();
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, timePeriodId);
    	
    	boolean rowInserted = statement.executeUpdate() > 0;
    	statement.close();
        disconnect();
    	return rowInserted; 
    }
}
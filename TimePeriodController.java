package com.jeffstrunk.TimeTrackerWServlets;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/TimePeriodController/*")
public class TimePeriodController extends HttpServlet {
	private static final long serialVersionUID = 1L;  
	
	private String url = "jdbc:mysql://localhost/jeffstrunkcom?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String userName = "root";
    private String pass = "pass";
	
	private TimePeriodDAO timePeriodDAO = new TimePeriodDAO(url, userName, pass);

	Gson gson = new Gson();

    public TimePeriodController() {
        super();
    }
    
    private void sendAsJson(HttpServletResponse response, Object obj) throws IOException {
		response.setContentType("application/json");
		String res = gson.toJson(obj);     
		PrintWriter out = response.getWriter();
		out.print(res);
		out.flush();
    }
    
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST, DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet reached GET");

		setAccessControlHeaders(response);
		String pathInfo = request.getPathInfo();
		String uri = request.getRequestURI();
		List<TimePeriod> timePeriods = new ArrayList();
		
		try {
			timePeriods = timePeriodDAO.getAllTimePeriods();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(pathInfo == null || pathInfo.equals("/")){		
//			System.out.println("path info is null");
//			System.out.println("uri: " + uri);
//			System.out.println("pathInfo: " + pathInfo);
			
			try {
				timePeriodDAO.connect();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			sendAsJson(response, timePeriods);
		}
		
		String[] splits = pathInfo.split("/");
		
		if(splits.length == 1){	
//			System.out.println("splits.length == 1");
//			System.out.println("uri: " + uri);
//			System.out.println("pathInfo: " + pathInfo);
		}
		
		if(splits.length == 2){		
//			System.out.println("splits.length == 2");
//			System.out.println("uri: " + uri);
//			System.out.println("pathInfo: " + pathInfo);
			
			TimePeriod timePeriod = new TimePeriod();
			try {		
				String stringId = splits[1];
//				int id = Integer.parseInt(stringId);
//				System.out.println("Id = " + id);
				
				timePeriod = timePeriodDAO.getTimePeriod(stringId);
				
				sendAsJson(response, timePeriod);
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost reached POST");
		setAccessControlHeaders(response);
		
		PrintWriter out = response.getWriter();	
		
		StringBuffer parameters = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				parameters.append(line);
			    }
			    String [] parametersToString = parameters.toString().split(",");
			    
			    TimePeriod timePeriod = new TimePeriod();
			    
			    for (String s : parametersToString) 
			    { 
			    	String s2 = s.replace("{", "");
			    	String s3 = s2.replace("}", "");
			    	String s4 = s3.replace("\"", "");
			    	String [] s5 = s4.split(":");
			    	String sKey = s5[0];
			    	String sValue = s5[1];
			    	
			    	if(sKey.equals("timePeriodId")) {
			    		timePeriod.setStartTime(sValue);
			    	}else if (sKey.equals("activityType")){
			    		timePeriod.setActivity(Enum.valueOf(ActivityType.class, sValue));
			    	}else {
			    		System.out.println("Something wrong in if statement in doPost");
			    	}	
			    }
			    try {
					timePeriodDAO.insertTimePeriod(timePeriod);
				} catch (SQLException e) {
					e.printStackTrace();
				} 
		    
		  } catch (Exception e) { /*report an error*/ }
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPut reached UPDATE");
		setAccessControlHeaders(response);
		
		StringBuffer parameters = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				parameters.append(line);
			    }
			    String [] parametersToString = parameters.toString().split(",");
			    
			    TimePeriod timePeriod = new TimePeriod();
			    
			    for (String s : parametersToString) 
			    { 
			    	String s2 = s.replace("{", "");
			    	String s3 = s2.replace("}", "");
//			    	System.out.println("s3: " + s3);
			    	String s4 = s3.replace("\"", "");
			    	String [] s5 = s4.split(":", 2);
			    	String sKey = s5[0];
			    	String sValue = s5[1];
			    	
			    	if(sKey.equals("timePeriodId")) {
			    		timePeriod.setStartTime(sValue);
			    	}else if (sKey.equals("activityType")){
			    		timePeriod.setActivity(Enum.valueOf(ActivityType.class, sValue));
			    	}else {
			    		System.out.println("Something wrong in if statement in doPost");
			    	}	
			    }
			    try {
					timePeriodDAO.updateTimePeriod(timePeriod);
				} catch (SQLException e) {
					e.printStackTrace();
				} 
		    
		  } catch (Exception e) { /*report an error*/ }
		
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doDelete reached DELETE");
		setAccessControlHeaders(response);
		
		StringBuffer parameters = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				parameters.append(line);
				System.out.println("line: " + line);
				System.out.println("parameters: " + parameters);
			}
		    String parametersToString = parameters.toString();

	    	String s2 = parametersToString.replace("{", "");
	    	String s3 = s2.replace("}", "");
	    	String s4 = s3.replace("\"", "");
	    	String [] s5 = s4.split(":");
	    	String sValue = s5[1];
	    	
	    	System.out.println("s5[1]: " + s5[1]);
	    
		    try {
				timePeriodDAO.deleteTimePeriod(s5[1]);
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		    
		} catch (Exception e) { /*report an error*/ }
	}
}
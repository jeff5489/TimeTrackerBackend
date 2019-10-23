package com.jeffstrunk.TimeTrackerWServlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/HttpRequestTest/*")
public class HttpRequestTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public HttpRequestTest() {
        super();
    }

    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST, DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		setAccessControlHeaders(response);
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		PrintWriter out = response.getWriter();	
		out.println();
		out.println("do Get of HttpRequest Test reached. GET GET GET GET GET GET GET GET GET GET GET GET ");
		System.out.println("do Get of HttpRequest Test reached. GET GET GET GET GET GET GET GET GET GET GET GET ");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		setAccessControlHeaders(response);
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		PrintWriter out = response.getWriter();	
//		out.println("do Get of HttpRequest Test reached. POST POST POST POST POST POST POST POST ");
//		System.out.println("do Get of HttpRequest Test reached. POST POST POST POST POST POST POST POST ");
		
//		System.out.println(request.getParameter("timePeriodId"));
//		System.out.println(request.getParameter("activityType"));

		
		StringBuffer parameters = new StringBuffer();
		  String line = null;
		  try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null) {
		    	parameters.append(line);
		    }
//		    System.out.println("parameters: " + parameters);
//		    System.out.println("parameters.toString(): " + parameters.toString());
		    
		    String [] parametersToString = parameters.toString().split(",");
		    
		    for (String s : parametersToString) 
		    { 
		    	String s2 = s.replace("{", "");
		    	String s3 = s2.replace("}", "");
		    	String s4 = s3.replace("\"", "");
		    	String [] s5 = s4.split(":");
		    	String sKey = s5[0];
		    	String sValue = s5[1];
		    	
		    	System.out.println("sKey: " + sKey);
		    	System.out.println("sValue: " + sValue);
		    }
		  } catch (Exception e) { /*report an error*/ }

	}
}
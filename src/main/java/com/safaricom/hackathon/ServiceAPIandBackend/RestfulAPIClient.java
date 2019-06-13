package com.safaricom.hackathon.ServiceAPIandBackend;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RestfulAPIClient {
	
	private static String getURL = "http://dummy.restapiexample.com/api/v1/employees";
	private static String createURL = "http://dummy.restapiexample.com/api/v1/create";
	private static String updateURL = "http://dummy.restapiexample.com/api/v1/update";
	
	public static void main(String[] args) {
		
		//Get all employees
		JSONArray allEmployeesArray = getAllEmployees();
		
		//Save employee details to the database if employee salary is greater than 1000 and age is greater than 25
		saveEmployeestoDB();
		
		//Create employee
		createEmployee();
		
		//Update employee
		updateEmployee();
		
	}

	public static JSONArray getAllEmployees(){		
		URL url = null;
		try {
			url = new URL(getURL);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpURLConnection httpsConn = null;
		try {
			httpsConn = (HttpURLConnection) url.openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			httpsConn.setRequestMethod("GET");
			//httpsConn.setDoOutput(true);
		} catch (ProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		int responseCode = 0;
		try {
			responseCode = httpsConn.getResponseCode();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = null; 
		String inputLine = null;
		StringBuffer responseStringBuffer = new StringBuffer();
		
		if(200 == responseCode) {
			try {
				in = new BufferedReader(
				new InputStreamReader(httpsConn.getInputStream()));
				
				while ((inputLine = in.readLine()) != null) {
					responseStringBuffer.append(inputLine);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
							
			
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			JSONArray responseJSON = new JSONArray(responseStringBuffer.toString());
			
			try {
				//Write JSONArray to a JSON file
				Functions.writeToJSONFile("allEmployees.json", responseJSON.toString());
				
				return responseJSON;
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Response Code : " + responseCode);
		}
		return null;
		
	}
	
	public static void saveEmployeestoDB(){	
		
		URL url = null;
		try {
			url = new URL(getURL);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpURLConnection httpsConn = null;
		try {
			httpsConn = (HttpURLConnection) url.openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			httpsConn.setRequestMethod("GET");
			//httpsConn.setDoOutput(true);
		} catch (ProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		int responseCode = 0;
		try {
			responseCode = httpsConn.getResponseCode();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = null; 
		String inputLine = null;
		StringBuffer responseStringBuffer = new StringBuffer();
		
		if(200 == responseCode) {
			try {
				in = new BufferedReader(
				new InputStreamReader(httpsConn.getInputStream()));
				
				while ((inputLine = in.readLine()) != null) {
					responseStringBuffer.append(inputLine);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
							
			
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			JSONArray responseJSON = new JSONArray(responseStringBuffer.toString());
			
			for(int i = 0; i <= responseJSON.length(); i++){
				JSONObject jsonObject = new JSONObject();
				
				
				try{
					jsonObject = responseJSON.getJSONObject(i);
				}
				catch(JSONException je){
					je.printStackTrace();
					continue;
				}
				catch(Exception je){
					je.printStackTrace();
					continue;
				}
			
				String id = jsonObject.getString("id");
				String employee_name = jsonObject.getString("employee_name");
				Double employee_salary = jsonObject.getDouble("employee_salary");
				int employee_age = jsonObject.getInt("employee_age");
				String profile_image = jsonObject.getString("profile_image");
				
				System.out.println("id: "+id+", employee_name: "+employee_name+", employee_salary: "+employee_salary+", employee_age: "+employee_age+", profile_image: "+profile_image);
				
				DatabaseConnector databaseConnector = new DatabaseConnector();
				Connection conn = databaseConnector.getConn();
				
				
				if(employee_salary > 1000 && employee_age > 25){
					String insertSQL_employee  = "INSERT INTO employees(id,employee_name,employee_salary,employee_age,"
							+ "profile_image) "
							+ "values(?,?,?,?,?)";
					PreparedStatement preparedStatement_employee  ;
					try {
						preparedStatement_employee   = conn.prepareStatement(insertSQL_employee);
						preparedStatement_employee.setString(1, id);
						preparedStatement_employee.setString(2, employee_name);
						preparedStatement_employee.setDouble(3, employee_salary);
						preparedStatement_employee.setInt(4, employee_age);
						preparedStatement_employee.setString(5, profile_image );						
												
						preparedStatement_employee .executeUpdate();		
						
						try {
			        		Functions.writeToFile("access.log", "Executed. Employee added to the database");
				        }
				        catch(IOException ex) {
				        	ex.printStackTrace();
				        }
						catch(NullPointerException e){
							e.printStackTrace();
						}
						           
			            
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						try {
			        		Functions.writeToFile("access.log","SQL Exception: "+e.getMessage().toString());
				        }
						catch(IOException ex) {
				        	ex.printStackTrace();
				        }
						catch(NullPointerException ex){
							ex.printStackTrace();
						}
					}
				}
				
				if(conn != null){
					databaseConnector.closeConnect();
				}
				
			}
			
			
		}
		else {
			System.out.println("Response Code : " + responseCode);
		}
		
		
	}
	
	public static void createEmployee(){		
		URL url = null;
		try {
			url = new URL(createURL);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpURLConnection httpsConn = null;
		try {
			httpsConn = (HttpURLConnection) url.openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			httpsConn.setRequestMethod("POST");
			httpsConn.setDoOutput(true);
		} catch (ProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JSONObject employeeJSONObject = new JSONObject();
		
		
		employeeJSONObject.put("id", "123456");
		employeeJSONObject.put("employee_name", "SafaricomHackathoner");
		employeeJSONObject.put("employee_salary", 180000);
		employeeJSONObject.put("employee_age", 32);
		employeeJSONObject.put("profile_image", "");
		
		//System.out.println(employeeJSONObject.toString());
		
		try {
			//Write JSONArray to a JSON file
			Functions.writeToFile("access.log", "Create Employee Request: "+employeeJSONObject.toString());
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		       
		
		// Send request
		DataOutputStream wr;
		try {
			wr = new DataOutputStream(httpsConn.getOutputStream());
			wr.writeBytes(employeeJSONObject.toString());
			wr.flush();
			wr.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		int responseCode = 0;
		try {
			responseCode = httpsConn.getResponseCode();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = null; 
		String inputLine = null;
		StringBuffer responseStringBuffer = new StringBuffer();
		
		if(200 == responseCode) {
			try {
				in = new BufferedReader(
				new InputStreamReader(httpsConn.getInputStream()));
				
				while ((inputLine = in.readLine()) != null) {
					responseStringBuffer.append(inputLine);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
							
			
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//System.out.println(responseStringBuffer.toString()); 
			
			try {
				//Write JSONArray to a JSON file
				Functions.writeToFile("access.log", "Create Employee Server Response: "+responseStringBuffer.toString());
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Response Code : " + responseCode);
		}
		
	}
	
	public static void updateEmployee(){	
		int employee_id = 26245;
		URL url = null;
		try {
			url = new URL(updateURL+"/"+employee_id);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpURLConnection httpsConn = null;
		try {
			httpsConn = (HttpURLConnection) url.openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			httpsConn.setRequestMethod("PUT");
			//httpsConn.setDoOutput(true);
		} catch (ProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			Functions.writeToFile("access.log", "Update Employee. ID: "+employee_id);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		       
		
		
		
		
		int responseCode = 0;
		try {
			responseCode = httpsConn.getResponseCode();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = null; 
		String inputLine = null;
		StringBuffer responseStringBuffer = new StringBuffer();
		
		if(200 == responseCode) {
			try {
				in = new BufferedReader(
				new InputStreamReader(httpsConn.getInputStream()));
				
				while ((inputLine = in.readLine()) != null) {
					responseStringBuffer.append(inputLine);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
							
			
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//System.out.println(responseStringBuffer.toString()); 
			
			try {
				//Write JSONArray to a JSON file
				Functions.writeToFile("access.log", "Update Employee Server Response: "+responseStringBuffer.toString());
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Response Code : " + responseCode);
		}
		
	}
}

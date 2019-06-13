package com.safaricom.hackathon.ServiceAPIandBackend;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.rpc.ServiceException;

import eu.dataaccess.footballpool.InfoLocator;
import eu.dataaccess.footballpool.InfoSoapType;
import eu.dataaccess.footballpool.TPlayer;
import eu.dataaccess.footballpool.TPlayerName;

public class SOAPAPIClient {
	
	public static void main(String[] args) {
		InfoLocator infoLocator = new InfoLocator();
		InfoSoapType infoSoapType = null;
   		try {
			infoSoapType = infoLocator.getInfoSoap();
			
			try {
				TPlayerName[] tPlayerName = infoSoapType.allPlayerNames(true);			
				
				String sName = null;
				for(int i=0;i < tPlayerName.length;i++){					
					if(tPlayerName[i].getSName().trim().equalsIgnoreCase("Mohamed Salah")){
						sName = tPlayerName[i].getSName().trim();
						System.out.println(sName);
						break;
					}					
		        }
				
				String[] sNameParts = sName.split(" ");
				String lastName = sNameParts[1];
				System.out.println("LastName: "+lastName);
				
				TPlayer[] tPlayer = infoSoapType.players(lastName);
				
				String tpsName = null;
				int tpsId;
		        String tpsFullName = null;
		        String tpsFirstName = null;
		        String tpsLastName = null;
		        
				for(int i=0;i < tPlayer.length;i++){					
					if(tPlayer[i].getSName().trim().equalsIgnoreCase("Mohamed Salah")){
						tpsId = tPlayer[i].getIId();
						tpsName = tPlayer[i].getSName().trim();
						tpsFullName = tPlayer[i].getSFullName().trim();
						tpsFirstName = tPlayer[i].getSFirstName().trim();
						tpsLastName = tPlayer[i].getSLastName().trim();
						
						System.out.println("TPlayer tpsId: "+tpsId);
						System.out.println("TPlayer tpsName: "+tpsName);
						System.out.println("TPlayer tpsFullName: "+tpsFullName);
						System.out.println("TPlayer tpsFirstName: "+tpsFirstName);
						System.out.println("TPlayer tpsLastName: "+tpsLastName);
						
						DatabaseConnector databaseConnector = new DatabaseConnector();
						Connection conn = databaseConnector.getConn();
						
						String insertSQL_voucher  = "INSERT INTO players(id,s_name,fullname,first_name,last_name) "
								+ "values(?,?,?,?,?)";
						PreparedStatement preparedStatement_voucher  ;
						try {
							preparedStatement_voucher   = conn.prepareStatement(insertSQL_voucher);
							preparedStatement_voucher.setInt(1, tpsId);		
							preparedStatement_voucher.setString(2, tpsName);	
							preparedStatement_voucher.setString(3, tpsFullName);	
							preparedStatement_voucher.setString(4, tpsFirstName);	
							preparedStatement_voucher.setString(5, tpsLastName);	
													
							preparedStatement_voucher.executeUpdate();		
							
							try {
				        		Functions.writeToFile("access.log", "Executed. Player details added to the database");
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
						break;
					}					
		        }
				
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

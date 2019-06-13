package com.safaricom.hackathon.ServiceAPIandBackend;

import java.rmi.RemoteException;
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

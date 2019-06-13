package com.safaricom.hackathon.ServiceAPIandBackend;

import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
 
@Path("/VoucherRSAPI")
public class VoucherRSAPI {
 
	@GET
	@Produces("application/json")
	public Response createVoucher() throws JSONException {
		
		formatDigits(generateUniqueVoucherCode(16, 4), 4);
 
		JSONObject jsonObject = new JSONObject();
		Double fahrenheit = 98.24;
		Double celsius;
		celsius = (fahrenheit - 32) * 5 / 9;
		jsonObject.put("F Value", fahrenheit);
		jsonObject.put("C Value", celsius);
 
		String result = "@Produces(\"application/json\") Output: \n\nF to C Converter Output: \n\n" + jsonObject;
		return Response.status(200).entity(result).build();
	}
 
	@Path("{f}")
	@GET
	@Produces("application/json")
	public Response convertFtoCfromInput(@PathParam("f") float f) throws JSONException {
 
		JSONObject jsonObject = new JSONObject();
		float celsius;
		celsius = (f - 32) * 5 / 9;
		jsonObject.put("F Value", f);
		jsonObject.put("C Value", celsius);
 
		String result = "@Produces(\"application/json\") Output: \n\nF to C Converter Output: \n\n" + jsonObject;
		return Response.status(200).entity(result).build();
	}
	
	



private  String generateUniqueVoucherCode(int digit, int charCnt) {
    long nano = System.nanoTime();
    Random rand = new Random();
    StringBuilder util = new StringBuilder(String.valueOf(nano));
    util = util.reverse();
   
    System.out.println("Hapa: "+util.toString());
    return rand.nextInt(10)+util.toString()+rand.nextInt(10);
}

private  String formatDigits(String original, int num){
    System.out.println("Before formatting " + original);
    return original.substring(0, num) + "-" + original.substring(num, 2*num)
            + "-" + original.substring(2*num, 3*num) + "-" + original.substring(3*num, 4*num);
}
}

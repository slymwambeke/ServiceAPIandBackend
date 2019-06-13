package com.safaricom.hackathon.ServiceAPIandBackend;

import org.junit.Test;

import junit.framework.Assert;

public class LeftRotationTest{
	@Test
	public void leftRotate_intArray_Returns_LeftRotatedIntArray() {
	    // Act
	    final int[] a;	    
	 // allocating memory for 5 integers. 
	    a = new int[5]; 
	      
		// initialize the first elements of the array 
	    a[0] = 1; 
	    a[1] = 2; 
	    a[2] = 3; 
	    a[3] = 4; 
	    a[4] = 5; 
	   
	    final int[] expected; 
	    
		// allocating memory for 5 integers. 
	    expected = new int[5]; 
	      
		// initialize the first elements of the array 
	    expected[0] = 3; 
	    expected[1] = 4; 
	    expected[2] = 5; 
	    expected[3] = 1; 
	    expected[4] = 2; 
	    
	 // Actual
	    final int[] actual = LeftRotation.rotLeft(a, 2);
		
	 // Assert
	    Assert.assertEquals(actual, expected);
	}
}


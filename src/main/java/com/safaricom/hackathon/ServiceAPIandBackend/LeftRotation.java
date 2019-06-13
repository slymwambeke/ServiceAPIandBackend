package com.safaricom.hackathon.ServiceAPIandBackend;

import java.util.Scanner;

public class LeftRotation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		 
        int i,n,k,temp;
        int[] a_; 

        System.out.println("Enter the number of integers (d):") ;
        n = sc.nextInt();
        
        a_ = new int[n];
        
        System.out.println("Enter the number of left rotations:") ;
        k = sc.nextInt();

	    System.out.println("Enter the integers one by one") ;
	    for(i=0;i<n;i++)
	    { 
	        a_[i] = sc.nextInt();
	    }        
			
        rotLeft(a_, k);
           
	}
	
	public static int[] rotLeft(int[] a, int d){
		int sizeOfArray = a.length;
		 
		System.out.println("\nArray before rotating it by "+d+" left rotations");
	    for(int i = 0; i < sizeOfArray; i++)
	    {
	      System.out.print(a[i]+" ");
	    }
	    
        int temp;   
        //1,2,3,4,5 2
        
        for(int i = 0; i < d; i++){ 
        	temp=a[0];
            for(int j = 0; j < sizeOfArray-1; j++)
            {
              a[j]=a[j+1];
            }
            a[sizeOfArray-1]=temp;
        }
        
    
        System.out.println("\nArray after rotating it by "+d+" left rotations");
        for(int i=0; i<sizeOfArray; i++)
        {
          System.out.print(a[i]+" ");
        }
	    
	
		return a;	
	}

}

package com.safaricom.hackathon.ServiceAPIandBackend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Voucher {
	public static void main(String argv[]) throws Exception {
        int digit = 16;
        System.out.println(digit + " digits random code > " + formatDigits(generateUniqueVoucherCode(16, 4), 4));
    }
	
	
    private static String generateUniqueVoucherCode(int digit, int charCnt) {
        long nano = System.nanoTime();
        Random rand = new Random();
        StringBuilder util = new StringBuilder(String.valueOf(nano));
        util = util.reverse();
       
        System.out.println("Hapa: "+util.toString());
        return rand.nextInt(10)+util.toString()+rand.nextInt(10);
    }

    private static String formatDigits(String original, int num){
        System.out.println("Before formatting " + original);
        return original.substring(0, num) + "-" + original.substring(num, 2*num)
                + "-" + original.substring(2*num, 3*num) + "-" + original.substring(3*num, 4*num);
    }


    
	
	
}

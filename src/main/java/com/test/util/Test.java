package com.test.util;

public class Test {
	public static void main(String[] args) {
		
		String test = "《读者》2020年第19期.pdf";
		System.out.println("2020_" + test.substring(10, 12) + ".pdf");
	}
	
}

package com;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class IPValidationRegex {
	
	private static String PATTERN = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
	
	public static boolean validateIP(String strInIP) {
		if (strInIP == null || strInIP.isEmpty()) 
			return false;
		
		Pattern r = Pattern.compile(PATTERN);
		return r.matcher(strInIP).find();
	}

	
	public static void main(String args[]) {
		//contents are ONLY for unit testing
		Scanner s = null;
		String filePath = "resources/IP.txt";
		try {
			File f = new File(filePath);
			if (f.exists()) {
				s = new Scanner(f);
				while (s.hasNext()) {
					String tmp = s.nextLine();
					System.out.println(tmp + " : " + (IPValidationRegex.validateIP(tmp) ? "Valid IP" : "Invalid IP")); 
				}
			} else {
				System.out.println("File " + filePath + " not found!");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			s.close();
		}
	}
	
}

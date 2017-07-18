package com;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class StudentInfo {

	public class StudentDAO {
		public int iID;
		public String strName;
		public double dbGPA;
	}
	
	public static class StudentInfoComparator implements Comparator{

		public int compare(Object o1, Object o2) {
			int iReturn = 0;
			
			StudentDAO dao1 = (StudentDAO)o1;
			StudentDAO dao2 = (StudentDAO)o2;
			
			if (dao1.dbGPA < dao2.dbGPA) {
				iReturn = 1;
			} else if (dao1.dbGPA > dao2.dbGPA) {
				iReturn = -1;
			} else {
				iReturn = dao1.strName.compareTo(dao2.strName);
				if (iReturn == 0) {
					iReturn = (dao1.iID > dao2.iID) ? 1 : -1;
				}
			}

			return iReturn;
		}
	}

	
	private ArrayList<StudentDAO> parseData(String sourceFile) {
		
		ArrayList<StudentDAO> alResult = new ArrayList<StudentDAO>();
		
		boolean bSkippedFirstLine = false;
		Scanner s = null;
		try {
			File f = new File(sourceFile);
			if (f.exists()) {
				s = new Scanner(f);
				while (s.hasNext()) {
					
					if (!bSkippedFirstLine) {
						//Skip the header
						bSkippedFirstLine = true;
						s.nextLine();
						continue;
					}
					
					String tmp = s.nextLine();
					String arrTmp[] = tmp.split(" ");
					if (arrTmp.length == 3) {
						StudentDAO dao = new StudentDAO();
						dao.iID = Integer.parseInt(arrTmp[0]);
						dao.strName = arrTmp[1];
						dao.dbGPA = Double.parseDouble(arrTmp[2]);
						
						alResult.add(dao);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			s.close();
		}
		
		return alResult;
	}
	
	
	
	public static void main(String args[]) {
		//contents are ONLY for unit testing
		//
		//assumption 
		//1. source data is in text format with space delimited
		//2. no space in name
		//3. cgpa and id value is numeric only
		//
		StudentInfo si = new StudentInfo();
		ArrayList<StudentDAO> alData = si.parseData("resources/StudentInfo.txt");
		if (!alData.isEmpty()) {
			
			Collections.sort(alData, new StudentInfoComparator());
			
			for (int i=0; i<alData.size(); i++) {
				System.out.println(alData.get(i).strName);
			}
			
		} else {
			System.out.println("No valid data found!");
		}
	}
	
}

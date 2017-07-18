package com;

import java.util.HashMap;

public class TimeMeasure {
	
	private HashMap<String, TimeMeasureDao> hm;
	
	public class TimeMeasureDao {
		private long lStart;
		private long lEnd;
		
		public TimeMeasureDao(long lStart, long lEnd) {
			this.lStart = lStart;
			this.lEnd = lEnd;
		}
		
		public void setStartTime(long lStart) {
			this.lStart = lStart;
		}
		
		public void setEndTime(long lEnd) {
			this.lEnd = lEnd;
		}
		
		public long getStartTime() {
			return lStart;
		}
		
		public long getEndTime() {
			return lEnd;
		}	
	}

	public TimeMeasure() {
		hm = new HashMap<String, TimeMeasureDao>();
	}
	
	private void start(String strKey) {
		long lStart = System.nanoTime();
		TimeMeasureDao dao = new TimeMeasureDao(lStart, 0);
		hm.put(strKey, dao);
	}
	
	private void end(String strKey) {
		long lEnd = System.nanoTime();
		TimeMeasureDao dao =  hm.get(strKey);
		if (dao != null) {
			dao.setEndTime(lEnd);
			hm.put(strKey, dao);
		}
	}
	
	private long getDurationNano(String strKey) {
		TimeMeasureDao dao =  hm.get(strKey);
		if (dao == null) 
			return -1;
		return dao.getEndTime()-dao.getStartTime();
	}	
	
	private double getDurationSecond(String strKey) {
		TimeMeasureDao dao =  hm.get(strKey);
		if (dao == null) 
			return -1;
		double seconds = (dao.getEndTime()-dao.getStartTime()) / 1000000000.0;
		return rounding(seconds);
	}
	
	private double getDurationMinutes(String strKey) {
		TimeMeasureDao dao =  hm.get(strKey);
		if (dao == null) 
			return -1;
		double minutes = (dao.getEndTime()-dao.getStartTime()) / 60000000000.0;
		return rounding(minutes);
	}
	
	private double getAverageSeconds() {
		double dbResult = 0.0;
		double dbTmp = 0.0;
		if (!hm.isEmpty()) {
			for (String key : hm.keySet()) {
				TimeMeasureDao dao =  (TimeMeasureDao) hm.get(key);
				dbTmp += dao.getEndTime()-dao.getStartTime();
			}
			dbResult = dbTmp/hm.size();
		}
		return rounding(dbResult/ 1000000000.0);
	}
	
	private double rounding(double dbIn) {
		return (long) (dbIn * 1000 + 0.5) / 1000.0;
	}
	
	public static void main(String args[]) {
		//contents are ONLY for unit testing
		TimeMeasure tm = new TimeMeasure();
		
		try {
			System.out.println("---Test case1---");
			tm.start("case1");
			Thread.sleep(2000);
			tm.end("case1");
			
			System.out.println("Nanos   : " + tm.getDurationNano("case1"));
			System.out.println("Seconds : " + tm.getDurationSecond("case1"));
			System.out.println("Minutes : " + tm.getDurationMinutes("case1"));

			tm.start("case2");
			Thread.sleep(2000);
			tm.end("case2");
			
			System.out.println("\n---Test case2---");
			System.out.println("Nanos   : " + tm.getDurationNano("case2"));
			System.out.println("Seconds : " + tm.getDurationSecond("case2"));
			System.out.println("Minutes : " + tm.getDurationMinutes("case2"));
			
			System.out.println("\nAverage time taken : " + tm.getAverageSeconds());
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}
	
}

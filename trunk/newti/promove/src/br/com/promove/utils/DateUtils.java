package br.com.promove.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	private DateUtils() {}
	
	public static Date montarDataInicialParaHQLQuery(Date init) {
		if(init == null)
			return null;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(init);
		
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		
		cal.add(Calendar.DATE, -1);
		
		return cal.getTime();
	}
	
	public static Date montarDataFinalParaHQLQuery(Date fim) {
		if(fim == null)
			return null;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(fim);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		
		return cal.getTime();
	}

	public static Date montarDataInicialParaSQLQuery(Date init) {
		if(init == null)
			return null;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(init);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		return cal.getTime();
	}
	
	public static Date montarDataFinalParaSQLQuery(Date fim) {
		if(fim == null)
			return null;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(fim);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		
		return cal.getTime();
	}

}

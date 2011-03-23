package br.com.promove.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	private DateUtils() {}
	
	public static void montarDataInicialParaQuery(Date init) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(init);
		
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		
		cal.add(Calendar.DATE, -1);
		
		init.setTime(cal.getTimeInMillis());
	}
	
	public static void montarDataFinalParaQuery(Date fim) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fim);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		
		fim.setTime(cal.getTimeInMillis());
	}

}

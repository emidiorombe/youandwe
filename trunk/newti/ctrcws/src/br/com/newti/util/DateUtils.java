package br.com.newti.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static Date parseFromString(String data, String padrao) {
		try {
			return new SimpleDateFormat(padrao).parse(data);
		} catch (ParseException e) {
			throw new IllegalArgumentException();
		}
	}

}

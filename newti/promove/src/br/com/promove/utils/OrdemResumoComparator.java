package br.com.promove.utils;

import java.util.Comparator;

public class OrdemResumoComparator implements Comparator<Integer> {

	@Override
	public int compare(Integer o1, Integer o2) {
		return o2.compareTo(o1);
	}

}

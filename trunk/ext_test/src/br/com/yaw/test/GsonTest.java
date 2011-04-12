package br.com.yaw.test;

import com.google.gson.Gson;
import com.google.gson.JsonPrimitive;

public class GsonTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Gson g = new Gson();
		System.out.println(g.toJson(new JsonPrimitive("outro")));

	}

}

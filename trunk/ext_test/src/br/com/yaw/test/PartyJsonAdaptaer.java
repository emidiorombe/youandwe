package br.com.yaw.test;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.googlecode.objectify.Key;

public class PartyJsonAdaptaer implements JsonSerializer<Key<Party>> {

	@Override
	public JsonElement serialize(Key<Party> p, Type t, JsonSerializationContext arg2) { 
		return new JsonPrimitive(DAO.getPartyByKey(p).getId());
	}

}

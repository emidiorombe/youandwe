package br.com.yaw.utils;

import org.compass.core.converter.ConversionException;
import org.compass.core.converter.basic.AbstractBasicConverter;
import org.compass.core.mapping.ResourcePropertyMapping;
import org.compass.core.marshall.MarshallingContext;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class KeyConverter extends AbstractBasicConverter<Key> {

	@Override
	protected Key doFromString(String keyString, ResourcePropertyMapping arg1,
			MarshallingContext arg2) throws ConversionException {
		return KeyFactory.stringToKey(keyString);
	}

	@Override
	protected String doToString(Key key,
			ResourcePropertyMapping resourcePropertyMapping,
			MarshallingContext context) {
		return KeyFactory.keyToString(key);
	}
}

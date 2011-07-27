package br.com.newti.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentMapper implements ExceptionMapper<IllegalArgumentException>{

	@Override
	public Response toResponse(IllegalArgumentException iae) {
		return Response.status(400).build();
	}

}

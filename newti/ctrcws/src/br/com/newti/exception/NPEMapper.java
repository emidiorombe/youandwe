package br.com.newti.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NPEMapper implements ExceptionMapper<NullPointerException> {

	@Override
	public Response toResponse(NullPointerException arg0) {
		return Response.status(500).build();
	}

}

package br.com.yaw.servlet.action;

import javax.servlet.http.HttpServlet;

import com.google.gson.Gson;

/**
 * Métodos genéricos a todos ActionServlet
 * @author Rafael Nunes
 *
 */
public class BaseActionServlet extends HttpServlet{
	protected Gson gson = new Gson();
}

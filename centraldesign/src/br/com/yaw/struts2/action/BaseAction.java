package br.com.yaw.struts2.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Generic Action
 * @author Rafael Nunes
 *
 */
public abstract class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, SessionAware{
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map<String,Object> mapSession;
	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}
	/**
	 * @param request the request to set
	 */
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	/**
	 * @return the response
	 */
	public HttpServletResponse getServletResponse() {
		return response;
	}
	/**
	 * @param response the response to set
	 */
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	/**
	 * @return the session
	 */
	public Map<String,Object> getSession() {
		return mapSession;
	}
	/**
	 * @param session the session to set
	 */
	public void setSession(Map<String,Object> session) {
		this.mapSession = session;
	}
}

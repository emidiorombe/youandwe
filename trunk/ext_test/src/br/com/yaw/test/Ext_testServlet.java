package br.com.yaw.test;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class Ext_testServlet extends HttpServlet {


	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String action = req.getParameter("action");
		
		if("login".equals(action)) {
			String result = null;
			String loginUsername = req.getParameter("loginUsername");
			String pwd = req.getParameter("loginPassword");;
			if (null != loginUsername && loginUsername.length() > 0 && null != pwd && pwd.length() > 0) {
				if (loginUsername.equals("rafael") && pwd.equals("123"))
					result = "{success:true}";
				else
					result = "{success:false,errors:{reason:'Login failed.Try again'}}";
		 
			} else {
				result = "{success:false,errors:{reason:'Login failed.Try again'}}";
			}
			
			resp.getWriter().write(result);
			resp.getWriter().flush();
		}else if("GRID_LIST".equals(action)) {
			try {
				resp.getWriter().write(DAO.getPresidents());
				resp.getWriter().flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if("ADD_PRESIDENT".equals(action)) {
			try {
				String partyId = req.getParameter("partyId");
				String fname = req.getParameter("fname");
				String lname = req.getParameter("lname");
				String dtIni = req.getParameter("dtIni");
				String dtFim = req.getParameter("dtFim");
				String income = req.getParameter("inc");
				
				DAO.addPresident(new President(DAO.getParty(new Long(partyId)), fname, lname, createDate(dtIni), createDate(dtFim), new Double(income)));
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("ADD_PARTY".equals(action)) {
			try {
				DAO.addParty(req.getParameter("pname"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}
	
	public static Date createDate(String dt) {
		String tks[] = dt.split("-");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, new Integer(tks[0]));
		cal.set(Calendar.MONTH, new Integer(tks[1]));
		cal.set(Calendar.DATE, new Integer(tks[2]));
		
		return cal.getTime();
	}
}

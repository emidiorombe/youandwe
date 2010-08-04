<%@ tag body-content="empty" isELIgnored="false"%>
<%@ attribute name="idCompany" required="true" %>

<%@tag import="net.sf.jsr107cache.Cache"%>
<%@tag import="java.util.ArrayList"%>
<%@tag import="java.util.List"%>
<%@tag import="java.util.Map"%>
<%@tag import="br.com.yaw.entity.Company"%>
<%@tag import="java.util.Collection"%>
<%@tag import="br.com.yaw.service.CacheService"%>
<%@tag import="br.com.yaw.ioc.ServiceFactory"%>
<%@tag import="br.com.yaw.service.CompanyService"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="idC" value="${idCompany}" scope="request"/>

<%
CompanyService companyService = ServiceFactory.getService(CompanyService.class); 

Map companies = CacheService.getCompanies();
String idC = (String)request.getAttribute("idC");
if(companies == null || !companies.containsKey(idC)){
	Company c = companyService.getCompanyById(Long.parseLong(idC));
	CacheService.addCompany(c);
	companies = CacheService.getCompanies();
}
request.setAttribute("companies", companies);

%>
${companies[idC].name}

	<%@page import="be.lacerta.cq2.sim.hbn.User"  
%><%@page import="be.lacerta.cq2.utils.CQ2Functions"  
%><%@page session="true" 
%>
<jsp:useBean id="user"  scope="session" class="be.lacerta.cq2.sim.hbn.User" />
<jsp:useBean id="Constants" class="be.lacerta.cq2.utils.SimConstants"/>
<%
String context = request.getContextPath();
String cq2url = CQ2Functions.URL;
String imagepack = cq2url;
if (request.getSession() != null && request.getSession().getAttribute("user") != null) {
	user = (User)request.getSession().getAttribute("user");
	pageContext.setAttribute("user",user);
	if (user.getImagepac() != null && !user.getImagepac().equals("")) {
		imagepack = user.getImagepac();
		if (imagepack.length() > 2 &&
			imagepack.charAt(imagepack.length()-1) != '/')
			imagepack += "/";
	}
		   
}

response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.sim.hbn.User"  %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="be.lacerta.cq2.sim.hbn.Item"  %>
<%@page import="be.lacerta.cq2.sim.hbn.Race"  %>
<%@ include file="include/init.jsp" %>
<%@ include file="include/header.jsp" %>
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>
<%@ include file="include/menu.jsp" %>

<div class="porlet" id="p-content">

<zod:Title title="Item list"/>


<p>
<% if (("show").equals(request.getParameter("all"))) { %>
<a href="?page=critdb&action=itemlist">Hide power/challenge items</a>
<% } else { %>
<a href="?page=critdb&action=itemlist&all=show">Show power/challenge items</a>
<% } %>
</p>

<%
List<Item> critlist;
String[] races = {"Air","Death","Earth","Forest"};

for (int i=0;i<races.length; i++) {
%>

	<p><font class="head" size="-1"><%= races[i] %>:</font>
	<% 
	critlist = (List<Item>)request.getAttribute("critdb_"+races[i]+"Items");
	for (Iterator<Item> it=critlist.iterator(); it.hasNext();) {
		Item item = it.next();
	%>
	    <br>
	    <% if (item.getWorkshop() <= 0) { %>
	    <font class="alert">
	    WS<%= item.getWorkshop() %> - <%= item.getName() %> - <%= item.getDescr() %>
	    </font>
	    <% } else { %>
	    WS<%= item.getWorkshop() %> - <%= item.getName() %> - <%= item.getDescr() %>
	    <% } %>
	<%
	}
	%>
	</p>
	<%
}
%>

</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
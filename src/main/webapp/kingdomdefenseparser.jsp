<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.sim.hbn.User"  %>
<%@page import="be.lacerta.cq2.sim.hbn.RevealCrit"  %>
<%@page import="be.lacerta.cq2.utils.Quadruplet"  %>
<%@page import="java.util.ArrayList"  %>
<%@page import="java.util.Map"  %>
<%@ include file="include/init.jsp" %>
<%@ include file="include/header.jsp" %>
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>
<%@ include file="include/menu.jsp" %>

<div class="porlet" id="p-content">

<zod:Title title="Kingdom Defense Parser (Created and copyrighted by Imbalancing)"/>

<form action="?page=defenseparser" method="POST">
<font class="head" size="-1">Insert kingdom defense:</font><br/>
<textarea cols="70" rows="8" class="input" name="inputPage">
</textarea>
<br/>
<input type="submit" class="input" value="Submit">
</form>

<%if(request.getAttribute("defparser_result") != null) { %>
<p>Totaal: <%= request.getAttribute("totaal") %></p>
<ul>
<%
Map<Quadruplet<String,String,String,String>,Integer> creatures = (Map<Quadruplet<String,String,String,String>,Integer>)request.getAttribute("defparser_result");

for (Quadruplet<String,String,String,String> key : creatures.keySet()) {
	%><li><%= creatures.get(key) %> * <%= key %></li><%
}

%>
</ul>


<%}%>

</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.sim.hbn.User"  %>
<%@ include file="include/init.jsp" %>
<%@ include file="include/header.jsp" %>
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>
<%@ include file="include/menu.jsp" %>

<div class="porlet" id="p-content">

<zod:Title title="Resource Check"/>

<form action="?page=rescheck" method="POST">
<font class="head" size="-1">Player Information page:</font><br/>
<textarea cols="70" rows="8" class="input" name="inputPage">
</textarea>
<br/>
<input type="submit" class="input" value="Calculate">
</form>

<%
int[][] resources = (int[][]) request.getAttribute("rescheck_resources");
String[] resNames =  (String[]) request.getAttribute("rescheck_resNames");

if (resNames != null) { //something was posted and returned
	if(resources!=null){ //calculation was succesful
	    %><p><font class="head">Opponent's Resources:</font></p><%
	    for(int i=0;i<resources.length;i++) {
	        %><%= resNames[i] %>: <%= resources[i][0] %> - <%= resources[i][1] %><br/><%
	    }
	} else {
		%><br>Not all necessary information was found. Please copy/paste the <strong>complete</strong> page.<%
	}
}

%>
</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
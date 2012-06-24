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

<object style="width:100%;height:500px" data="http://spreadsheets.google.com/ccc?key=0Ar6htztsl02xdHR5MVV3TkhpcnAzeThaVzVPVnR2aHc&hl=en" type="text/html" standby="Google Docs"></object>

</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
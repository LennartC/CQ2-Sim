<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@ include file="include/init.jsp" %>
<%@ include file="include/header.jsp" %>
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>
<%@ include file="include/menu.jsp" %>

<div class="porlet" id="p-content">
<zod:Title title="Travel Encounter Calculator" href="http://cq2.lacerta.be/ZoDTraveler/ZoDTraveler.jnlp" reftext="Download!"/>


<form action="?page=travelcalc" method="POST">
<font class="head" size="-1">Your Creature List:</font><br/>
<textarea cols="70" rows="8" class="input" name="critlist">
<%= request.getAttribute("travel_eigen") %>
</textarea><br/>
<font class="head" size="-1">Encounter Creature List:</font><br/>
<textarea cols="70" rows="8" class="input" name="encounter">
</textarea>
<br/>
<input type="submit" class="input" value="Calculate">
</form>


<%
String solution = (String) request.getAttribute("travel_solution");

if (solution != null) {
    %><p><font class="head">Solution</font></p>
    <%= solution %><%
    
   
}

%>
</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
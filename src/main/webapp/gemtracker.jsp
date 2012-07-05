<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.sim.hbn.User"  %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Locale" %>
<%@page import="java.util.Date" %>
<%@page import="be.lacerta.cq2.sim.hbn.GemTracker" %>
<%@page import="be.lacerta.cq2.sim.GemExtension" %>
<%@ include file="include/init.jsp" %>
<%@ include file="include/header.jsp" %>
<%@page import="be.lacerta.cq2.utils.StringUtils"%> 
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>
<%@ include file="include/menu.jsp" %>

<div class="porlet" id="p-content">

<table width="100%">
    <tr>
        <td class="titleline" width="100%"><font class="head" size="-1">Gem tracker</font></td>
    </tr>
</table>

<% if (request.getAttribute("message") != null) { %>
<div class="message"><%= request.getAttribute("message") %></div><br/>
<% } %>

<p><a href="#addtracker" rel="facebox">Add new/update</a></p>
<div id="addtracker" style="display:none;">
<form action="?page=gemtracker" method="POST">
	Copy/paste reveal of arcane chamber:<br/>
	<textarea class="input" name="text" style="width: 100%; height: 100px"></textarea><br/>
	<input type="submit" class="input" value="Update">
</form>
</div>

<script src="scripts/clock2.js" type="text/javascript"></script>


<table width="100%">
    <tr>
        <td class="titleline" width="100%"><font class="head" size="-1">Gems tracked by you:</font></td>
    </tr>
</table>
<table cellpadding="5" class="sortable">
	<tr>
		<th>Date</th>
		<th>%</th>
		<th>Mage</th>
		<th>Gem</th>
		<th id="timeleft">Expected time for 100%</th>
		<th>% now</th>
		<th></th>
	</tr>
<%
SimpleDateFormat sdf = new SimpleDateFormat("MMMMM dd, yyyy HH:mm:ss",Locale.ENGLISH);
SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm",Locale.ENGLISH);

List<GemTracker> gtList = (List<GemTracker>)request.getAttribute("gemtracker_list");
for (GemTracker gt : gtList) {
	%>
	<tr>
		<td><%= StringUtils.formatDate(gt.getSubmitDate()) %></td>
		<td><%= gt.getPercentage() %></td>
		<td><% pageContext.setAttribute("mage",gt.getMage()); %><zod:ProfileLink mage="${mage}"/></td>
		<td><%= gt.getGem() %></td>
		<td>
		<span id="clockcount<%= gt.getId() %>"></span>
		<script type="text/javascript">
        var futuredate=new cdtime("clockcount<%= gt.getId() %>", "<%= sdf.format(new Date()) %>", "<%= sdf.format(gt.getExpectedEndDate())%>" );
        futuredate.displaycountdown("days", formatresults);
        </script> (<%= sdf2.format(gt.getExpectedEndDate()) %>)
		</td>
		<td>
		<%= ( (new Date().getTime()-gt.getSubmitDate().getTime())/(GemExtension.SECONDSPERRAISE*1000) )+gt.getPercentage() %>
		</td>
		<td><a href="?page=gemtracker&action=delete&id=<%= gt.getId() %>">X</a></td>
	</tr>
	<%
}
%>

</table>

</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
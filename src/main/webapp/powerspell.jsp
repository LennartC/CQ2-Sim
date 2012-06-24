<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.sim.hbn.User"  %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="be.lacerta.cq2.sim.hbn.PowerspellLog" %>
<%@ include file="include/init.jsp" %>
<%@ include file="include/header.jsp" %>
<%@page import="be.lacerta.cq2.utils.StringUtils"%> 
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>
<% 
	int userid = (Integer)request.getAttribute("powerspelllog_userid");
%>
<%@ include file="include/menu.jsp" %>

<div class="porlet" id="p-content">

<zod:Title title="Powerspells"/>

<% if (userid==user.getId()) { %>
	<form action="?page=powerspell" method="POST">
	<b>L<input type="text" class="input" lang="MUSTINT" value="<%= user.getLevel() %>" name="level" size="5"/>&nbsp;
	F<input type="text" class="input" lang="MUSTINT" value="<%= user.getForestSkill() %>" name="forest" size="5"
	/>/D<input type="text" class="input" lang="MUSTINT" value="<%= user.getDeathSkill() %>" name="death" size="5"
	/>/A<input type="text" class="input" lang="MUSTINT" value="<%= user.getAirSkill() %>" name="air" size="5"
	/>/E<input type="text" class="input" lang="MUSTINT" value="<%= user.getEarthSkill() %>" name="earth" size="5"/></b>
	(or update <a href="?page=options">user details</a>)
	<br/>
	<br/>
	Amount: <input type="text" class="input" lang="MUSTINT" name="amount"/>
	Result: <input type="text" class="input" name="result"/>
	<input type="submit" class="input" value="Add">
	</form>
<% } %>
<table width="100%">
    <tr>
        <td class="titleline" width="100%"><font class="head" size="-1">Total power: <%= request.getAttribute("powerspelllog_totalPower") %> - Total spells: <%= request.getAttribute("powerspelllog_totalSpells") %></font></td>
    </tr>
</table>
<table cellpadding="5">
	<tr>
		<th>Date</th>
		<th>Mage details</th>
		<th>Amount</th>
		<th>Result</th>
		<th></th>
	</tr>
<%
List<PowerspellLog> psps = (List<PowerspellLog>)request.getAttribute("powerspelllog_log");
for (PowerspellLog psp : psps) {
	%>
	<tr>
		<td><%= StringUtils.formatDate(psp.getTime()) %></td>
		<td>L<%= psp.getLevel() %> F<%= psp.getForestSkill() %>/D<%= psp.getDeathSkill() %>/A<%= psp.getAirSkill() %>/E<%= psp.getEarthSkill() %></td>
		<td><%= psp.getAmount() %></td>
		<td><%= psp.getResult() %></td>
		<td><% if (userid==user.getId()) { %><a href="?page=powerspell&action=delete&id=<%= psp.getId() %>">X</a><% } %></td>
	</tr>
	<%
}
%>

</table>
<a href="?page=powerspell&all=1<% if (userid!=user.getId()) { %>&user=<%=userid%><% } %>">view all</a>



</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
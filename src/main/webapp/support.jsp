<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.sim.hbn.User"  %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="be.lacerta.cq2.sim.hbn.Support" %>
<%@page import="be.lacerta.cq2.utils.StringUtils"%> 
<%@ include file="include/init.jsp" %>
<%@ include file="include/header.jsp" %>


<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>
<%@ include file="include/menu.jsp" %>

<div class="porlet" id="p-content">

<zod:Title title="Support"/>

<% if (request.getAttribute("message") != null) { %>
<div class="message"><%= request.getAttribute("message") %></div><br/>
<% } %>

<form action="?page=support" method="POST">
	<table width="100%">
<% if (request.getAttribute("support_from") != null) { %>
	<input type="hidden" name="user" value="<%= ((User)request.getAttribute("support_from")).getId() %>"/>
	<tr><td>Amount:</td><td><input type="text" name="amount" class="input"/></td></tr>
	<tr><td>To:</td><td><input type="text" name="to" class="input"/></td></tr>
<% } else { %>
	<tr><td colspan="2">
	Paste the complete donation here, including the timestamp! (you can just copy/paste the complete page)<br/>
	<textarea class="input" name="donation" style="width: 50%; height: 100px"></textarea>
	</td></tr>
<% } %>
	<tr><td style="width: 50px;">Reason:</td><td>
		<select class="input" name="reason">
			<option>Shop</option>
			<option>Hatch</option>
			<option>Curse</option>
			<option>Shard</option>
			<option>XP Raid</option>
			<option>Other</option>
		</select>
	</td></tr>
	</table>
<input type="submit" class="input" value="Add">
</form>

<table width="100%">
    <tr>
        <td class=titleline width=100%><font class=head size=-1> <% if (request.getAttribute("support_from") != null) { %><%= ((User)request.getAttribute("support_from")).getUsername() %>'s <% } else {%>Your<% } %> support: <%= request.getAttribute("support_totalamount") %></font></td>
    </tr>
</table>
<table cellpadding="5">
	<tr>
		<th>Date</th>
		<th>Amount</th>
		<th>To</th>
		<th>Reason</th>
	</tr>
<%
List<Support> support = (List<Support>)request.getAttribute("support_donations");
for (Support donation : support) {
	%>
	<tr>
		<td><%=StringUtils.formatDate(donation.getTime())%></td>
		<td><%=donation.getAmount()%></td>
		<td><%=donation.getToMage()%></td>
		<td><%=donation.getReason()%></td>
	</tr>
	<%
}
%>

</table>




</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
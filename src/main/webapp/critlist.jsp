<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.sim.hbn.User"  %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="be.lacerta.cq2.sim.hbn.Creature"  %>
<%@ include file="include/init.jsp" %>
<%@ include file="include/header.jsp" %>
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>
<%@ include file="include/menu.jsp" %>

<div class="porlet" id="p-content">

<table width="100%">
    <tr>
        <td class=titleline width=100%><font class=head size=-1>Creature list</font></td>
    </tr>
</table>
<p>

<p>
<% if (("show").equals(request.getParameter("all"))) { %>
<a href="?page=critdb&action=critlist">Hide power/challenge creatures</a>
<% } else { %>
<a href="?page=critdb&action=critlist&all=show">Show power/challenge creatures</a>
<% } %>
</p>

<%
List<Creature> critlist;
String[] races = {"Air","Death","Earth","Forest"};

for (int i=0;i<races.length; i++) {
%>

	<p><font class="head" size="-1"><%= races[i] %>:</font>
	<% 
	critlist = (List<Creature>)request.getAttribute("critdb_"+races[i]+"Crits");
	for (Iterator<Creature> it=critlist.iterator(); it.hasNext();) {
		Creature c = it.next();
	%>
	    <br>
	    <% if (c.getType() != 1) { %>
	    <font class="alert">
	    L<%= c.getL2u() %> (req skill <%= c.getSkillToUse() %>) - <%= c.getName() %> (@20 <%= be.lacerta.cq2.battlecalc.objects.Creature.convertStrength(c.getDamage(),1,20) %>/<%= be.lacerta.cq2.battlecalc.objects.Creature.convertStrength(c.getHealth(),1,20) %>)
	    </font>
	    <% } else { %>
	    L<%= c.getL2u() %> (req skill <%= c.getSkillToUse() %>) - <%= c.getName() %> (@20 <%= be.lacerta.cq2.battlecalc.objects.Creature.convertStrength(c.getDamage(),1,20) %>/<%= be.lacerta.cq2.battlecalc.objects.Creature.convertStrength(c.getHealth(),1,20) %>)
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
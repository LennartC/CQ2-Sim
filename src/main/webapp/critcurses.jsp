<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.sim.hbn.User"  %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="be.lacerta.cq2.sim.hbn.CritCurses" %>
<%@page import="be.lacerta.cq2.sim.hbn.Mage" %>
<%@page import="be.lacerta.cq2.sim.hbn.Skill" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Locale" %>
<%@page import="java.util.Date" %>
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
        <td class="titleline" width="100%"><font class="head" size="-1">Creature Curses</font></td>
    </tr>
</table>

<% if (request.getAttribute("message") != null) { %>
<div class="message"><%= request.getAttribute("message") %></div><br/>
<% } %>

<p><a href="#addcurse" rel="facebox">Add your curses</a></p>
<div id="addcurse" style="display:none;">
<form action="?page=curse&action=creature" method="POST">
Copy/paste spellbook:<br/>
	<textarea class="input" name="spellbook" style="width: 100%; height: 100px"></textarea><br/>
<input type="submit" class="input" value="Update">
</form>
</div>

<table width="100%">
    <tr>
        <td class="titleline" width="100%"><font class="head" size="-1">Find curses</font></td>
    </tr>
</table>

<zod:AutoCompleteJS type="creature"/>
<form action="?page=curse&action=creature" method="POST">
	<zod:AutoInput type="creature" name="creature" value="" size="30"/>
	<input type="submit" class="input" value="Find">
</form>

<table width="100%">
    <tr>
        <td class="titleline" width="100%"><font class="head" size="-1">Known curses</font></td>
    </tr>
</table>

<table style="padding-left: 10px" width="60%" class="sortable">
	<thead>
	<tr align="left">
		<th>Skill</th>
		<th>Level</th>
		<th>Creature</th>
		<th>Mage</th>
	</tr>
	</thead>
<%
List<CritCurses> curses = (List<CritCurses>)request.getAttribute("curse_critcurses");
for (CritCurses curse : curses) {
	%>
	<tr>
		<td><%=curse.getSkill().getText()%></td>
		<td><%=curse.getLevel() %></td>
		<td><%=curse.getCreature().getName()%></td>
		<td>
		<% pageContext.setAttribute("mage",curse.getMage().getName()); %><zod:ProfileLink mage="${mage}"/>
		</td>
	</tr>
	<%
}
%>
</table>
<br/><br/>

</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
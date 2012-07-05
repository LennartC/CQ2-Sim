<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.sim.hbn.User"  %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="be.lacerta.cq2.sim.hbn.CursedMage" %>
<%@page import="be.lacerta.cq2.sim.hbn.MageSkill" %>
<%@page import="be.lacerta.cq2.sim.hbn.Skill" %>
<%@page import="be.lacerta.cq2.sim.hbn.Mage" %>
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
        <td class="titleline" width="100%"><font class="head" size="-1">Curses (SA)</font></td>
    </tr>
</table>

<% if (request.getAttribute("message") != null) { %>
<div class="message"><%= request.getAttribute("message") %></div><br/>
<% } %>

<p><a href="#addcurse" rel="facebox">Add new</a></p>
<div id="addcurse" style="display:none;">
<form action="?page=curse" method="POST">
Copy/paste active curses:<br/>
	<textarea class="input" name="spellbook" style="width: 100%; height: 100px"></textarea><br/>
<input type="submit" class="input" value="Update">
</form>
</div>

<table width="100%">
    <tr>
        <td class="titleline" width="100%"><font class="head" size="-1">Active curses</font></td>
    </tr>
</table>

<script src="scripts/clock2.js" type="text/javascript"></script>

<table style="padding-left: 10px" width="60%" class="sortable">
	<thead>
	<tr align="left">
		<th>Mage</th>
		<th>By</th>
		<th>Shards</th>
		<th id="timeleft">Time left</th>
	</tr>
	</thead>
<%
SimpleDateFormat sdf = new SimpleDateFormat("MMMMM dd, yyyy HH:mm:ss",Locale.ENGLISH);

List<CursedMage> curses = (List<CursedMage>)request.getAttribute("curse_activecurses");
for (CursedMage curse : curses) {
	%>
	<tr>
		<td>
		<% pageContext.setAttribute("mage",curse.getMage()); %><zod:ProfileLink mage="${mage}" link="?page=reveal&mage=${mage}"/>
		</td>
		<td><%=curse.getUser().getUsername()%></td>
		<td><%=curse.getShards()%></td>
		<td>
		<span id="clockcount<%= curse.getId() %>"></span>
		<script type="text/javascript">
        var futuredate=new cdtime("clockcount<%= curse.getId() %>", "<%= sdf.format(new Date()) %>", "<%= sdf.format(curse.getEndTime())%>" );
        futuredate.displaycountdown("days", formatresults);
        </script>
		</td>
	</tr>
	<%
}
%>
</table>

<%
	List<MageSkill> msList = MageSkill.loadBySkill(Skill.loadByText("Surathli's Anger"));
	List<Mage> inAlliance = new ArrayList<Mage>();
	List<Mage> outAlliance = new ArrayList<Mage>();
	
	for (MageSkill ms : msList) {
		List<User> users = User.getUsersByMageName(ms.getMage().getName());
		if (users != null && users.size() > 0) {
	inAlliance.add(ms.getMage());
		} else {
	outAlliance.add(ms.getMage());
		}
	}
%>
<br/><br/>
<table >
    <tr>
        <td colspan="2" class="titleline"><font class="head" size="-1">SA Cursers:</font></td>
    </tr>
    <tr>
    	<td style="width: 50%; vertical-align: top;"><b>Alliance</b><br/>
    	<ul>
    	<%
    		for (Mage mage : inAlliance) {
    			%>
    			<li><% pageContext.setAttribute("mage",mage); %><zod:ProfileLink mage="${mage}"/>
    			(curse<% if (MageSkill.load(mage,Skill.loadByText("Surathli's Anger Dispell")) != null) { %>&nbsp;&amp;&nbsp;dispell<% } %>)
    			</li>
    			<%
    		}
    	%>
    	</ul>
    	</td>
    	<td style="vertical-align: top;"><b>Not&nbsp;Alliance</b><br/>
    	<ul>
    	<%
    		for (Mage mage : outAlliance) {
    			%>
    			<li><% pageContext.setAttribute("mage",mage); %><zod:ProfileLink mage="${mage}"/>
    			(curse<% if (MageSkill.load(mage,Skill.loadByText("Surathli's Anger Dispell")) != null) { %>&nbsp;&amp;&nbsp;dispell<% } %>)
    			</li><%
    		}
    	%>
    	</ul>
    	</td>
    </tr>
</table>
</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
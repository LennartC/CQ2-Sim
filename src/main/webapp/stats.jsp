<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.utils.StringUtils" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Comparator" %>
<%@page import="java.util.Collections" %>
<%@page import="java.util.LinkedHashMap" %>
<%@ include file="include/init.jsp" %>
<%@ include file="include/header.jsp" %>
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>
<%@ include file="include/menu.jsp" %>


<%@page import="be.lacerta.cq2.sim.SimstatsExtension"%><div class="porlet" id="p-content">

<zod:Title title="ZoD Sim Stats"/>


<%

LinkedHashMap<String,String> stats = new LinkedHashMap<String,String>();

stats.put("mostSupport","Most support");
stats.put("leastSupport","Least support");

stats.put("powerSpent","Most power spent on powerspells");
stats.put("powerSpells","Most powerspells");

stats.put("forumPosts","Most forum posts");
stats.put("loggedIn","Most active sim users");

stats.put("revealsInserted","Most reveals inserted");
stats.put("curses","Most curses added");

stats.put("traveled","Most traveling");
stats.put("travelrecord","Best travel encounter");

stats.put("raidrecord","Best raid");
stats.put("raidloss","Biggest loser");

stats.put("resWon","Most resources raided");
stats.put("resLost","Most resources lost in raid");

stats.put("gemsWon","Most gems raided");
stats.put("gemPowerWon","Most gem power raided");

stats.put("oldest","Oldest Zodian");
stats.put("youngest","Youngest Zodian");

stats.put("mostRecentXP","Most XP (last 3 days)");

int i=0;

%>




<table width="1024" border="0" cellspacing="0" cellpadding="0">
<% for (String stat : stats.keySet()) {%>
	<% i++; %>
	<% if (i%2==1) { %><tr><% } %>
	<td width="50%" valign="top">
	
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="15">
			<td colspan="2"></td>
			</tr>
			<tr>
			<td colspan="2" class="titleline">
			<font class="head">
			<%= stats.get(stat) %>
			</font>
			</td>
			</tr>
			<tr height="10">
			<td></td>
			</tr>
			<tr>
			<td width="3%">
			</td>
			<td width="97%">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
			    	<%
			    	List<Object[]> list = (List<Object[]>)request.getAttribute("stats_"+stat);
			    	int index=1;
			    	if (list!=null) for (Object[] o : list) {
				    	%>
						<tr>
						<td width="60%" align="left"><%=index%>. <%=o[1]%></td>
						<td width="20%" align="right"><%=o[0]%></td>
						<td width="20%"></td>
						</tr>
				    	<%
			    		index++;
			    	}
			    	%>
				</table>
			</td>
			</tr>
			</table>
	
	
	</td>
	<% if (i%2==0) { %></tr><% } %>
<% } %>

	<% i++; %>
	<% if (i%2==1) { %><tr><% } %>
	
	<td width="50%">
	
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr height="15">
			<td colspan="2"></td>
			</tr>
			<tr>
			<td colspan="2" class="titleline">
			<font class="head">Mage classes</font>
			</td>
			</tr>
			<tr height="10">
			<td></td>
			</tr>
			<tr>
			<td width="3%">
			</td>
			<td width="97%">
				<img src="http://cq2.lacerta.be<%= context %>/getChart.jsp?type=classes" alt="Class Chart"/>
			</td>
			</tr>
			</table>
	
	</td>
	<% if (i%2==0) { %></tr><% } %>

    
    <% if (i%2==1) { %>
    <td></td>
    </tr>
    <% } %>
    
    
    <tr height="10">
        <td colspan="2"></td>
    </tr>
	<tr>
 		<td class="titleline" width="10%" colspan="2"><font class="head">Global XP progress chart</font></td>
	</tr>
    <tr height="10">
        <td colspan="2"></td>
    </tr>
	<tr>
		<td colspan="2"><img src="http://cq2.lacerta.be<%= context %>/getChart.jsp?id=0" alt="XP Chart"/></td>
	</tr>
	

</table>


</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
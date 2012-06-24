<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="be.lacerta.cq2.utils.StringUtils" %>
<%@page import="be.lacerta.cq2.sim.hbn.*" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Locale" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Date" %>
<%@ include file="include/init.jsp" %>
<%@ include file="include/header.jsp" %>
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>
<%@ include file="include/menu.jsp" %>

<div class="porlet" id="p-content">

<% String mage = (String)request.getAttribute("mage"); %>
<zod:Title title="Profile: ${requestScope.mage}"/>


<c:if test="${requestScope.message !=null}">
	<div class="message">${requestScope.message}</div>
</c:if>

<% List<Reveal> reveals = null; %>
<!--  SHOW OLD REVEALS -->
	<% if (request.getAttribute("mage_reveals") != null) {
    	reveals = (List<Reveal>)request.getAttribute("mage_reveals");
    	
    	if (reveals.size() > 0) {
    		Reveal r = reveals.get(0); 
    		mage = r.getMage().getName();
    		%>
    		<div id="mage_general">
			<a href="<%= cq2url %>index.php?page=playerinfo&action=viewinfo&name=<%= mage %>" target='_blank'><%= mage %></a>
			lives in
			<% pageContext.setAttribute("kingdom",r.getKingdom());%><zod:ProfileLink kingdom="${kingdom}"/><br/>
			Class: <%= r.getMageClass() %> Mage<br/>
			Level: <%= r.getLevel() %><br/>
			<table border="0" cellspacing="5" cellpadding="0">
			<tr><td>Forest Magic: <%= r.getForestSkill() %>&nbsp;</td><td>Death Magic: <%= r.getDeathSkill() %>&nbsp;</td></tr>
			<tr><td>Air Magic:    <%= r.getAirSkill()    %>&nbsp;</td><td>Earth Magic: <%= r.getEarthSkill() %>&nbsp;</td></tr>
			</table>
			</div>
	<%
    	}
	}
	
	if (request.getAttribute("mage_curse") != null) {
		CursedMage cm = (CursedMage)request.getAttribute("mage_curse");
		SimpleDateFormat sdf = new SimpleDateFormat("MMMMM dd, yyyy HH:mm:ss",Locale.ENGLISH);
		%>
		<script src="scripts/clock2.js" type="text/javascript"></script>
		<div id="mage_cursed">
		<%= mage %> is cursed: <%= cm.getShards() %> active shards left, 
		<span id="clockcount<%= cm.getId() %>"></span>
        <script type="text/javascript">
        var futuredate=new cdtime("clockcount<%= cm.getId() %>", "<%= sdf.format(new Date()) %>", "<%= sdf.format(cm.getEndTime())%>" )
        futuredate.displaycountdown("days", formatresults)
        </script> left.
		</div>
		<%
	}
	%>
		
	<% if (reveals != null) { %>
	
	<% if (reveals.size() > 0) { 
		Reveal r = reveals.get(0);
		String showreveal = request.getAttribute("mage_showreveal")==null||(Integer)request.getAttribute("mage_showreveal")==0?"none":"block";
	
	%>
		<div id="mage_latestreveal">
		Latest reveal: added <%= StringUtils.getDaysOld(r.getTime()) %> days ago (<%= StringUtils.formatDate(r.getTime()) %>) by <%= StringUtils.getUserLink(r.getUser()) %><br/>
		<a href="#" onclick="showreveal(this,'reveal<%= r.getId() %>'); return false;"><% if (showreveal.equals("none")) { %>show<% } else { %>hide<% } %> reveal</a>
		</div>
		<div id="reveal<%= r.getId() %>" style="display: <%= showreveal %>; padding-left: 10px; padding-bottom: 10px">
		  	<% Set<RevealCrit> crits = reveals.get(0).getCreatures(); %>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			    <tr>
			        <td class="titleline" width="100%" colspan="6"><font class="head">Creature List (<%= crits.size() %>)</font></td>
			    </tr>
		
			<% for (RevealCrit crit : crits) {
		  				 %>
		
		
				<tr height="8"> <td colspan="6"></td></tr>
			    <% if (crit.getRace()==null) { %>
			    <tr> <td colspan="6"><%= crit.getUnparsed() %></td></tr>
			    <% } else { %>
				<tr>
					<td width="28%" align="left">
					   <font class="head"><%=crit.getName()%></font>
					</td>
					<td width="25%" align="center">
					   <%=crit.getRace()%>/<%=crit.getCreatureClass()%><% if (crit.getType() != null) { %>(<%=crit.getType()%>)<% } %>
					</td>
					<td width="5%" align="center">L<%=crit.getLevel()%></td>
					<% if (crit.getStandardCreature()!=null) { %>
						<td width="6%" align="center">
							<font <% if (crit.getStandardCreature().getDamage() != crit.getDamage()) { %>class="alert"<% } %>
							><%=crit.getDamage()%></font>/<font <% if (crit.getStandardCreature().getHealth() != crit.getHealth()) { %>class="alert"<% } %>
							><%=crit.getHealth()%></font>
						</td>
						<td width="20%" align="center">
						    F<font <% if (crit.getStandardCreature().getForestDef() != crit.getForestDef()) { %>class="alert"<% } %>
						    ><%=crit.getForestDef()%></font>/D<font <% if (crit.getStandardCreature().getDeathDef() != crit.getDeathDef()) { %>class="alert"<% } %>
							><%=crit.getDeathDef()%></font>/A<font <% if (crit.getStandardCreature().getAirDef() != crit.getAirDef()) { %>class="alert"<% } %>
							><%=crit.getAirDef()%></font>/E<font <% if (crit.getStandardCreature().getEarthDef() != crit.getEarthDef()) { %>class="alert"<% } %>
							><%=crit.getEarthDef()%></font>
						</td>
					<% } else { %>
						<td width="6%" align="center">
						<%=crit.getDamage()%>/<%=crit.getHealth()%></td>
						<td width="20%" align="center">
						    F<font><%=crit.getForestDef()%></font>/D<font
							><%=crit.getDeathDef()%></font>/A<font 
							><%=crit.getAirDef()%></font>/E<font
							><%=crit.getEarthDef()%></font>
						</td>
					<% } %>
						
					<td width="16%" align="center">
					<% if (crit.getItem() != null) { %>
						<%
						Item i = Item.getItem(crit.getItem());
						if (i == null) i = Item.getItemByShortName(crit.getItem(), crit.getRace());
					
						if (i != null) {
						%>
			            <a href="javascript:void(0);"
			                onmouseover="window.status = '<%= i.getDescr().replaceAll("'","\\\\'") %>'; return overlib('<%= i.getDescr().replaceAll("'","\\\\'") %>');"
			                onmouseout="return nd();"><%= i.getName() %></a><% 
			            } else { %><%= crit.getItem() %><% } 
						
						if (crit.getEnchant() != null) { %>* (<%=crit.getEnchant()%>)<% } %>
		            <% } %>
					</td>
				</tr>
				<tr>
					<td colspan="6">&nbsp;&nbsp;<% if (crit.getCurse() != null) { %> Cursed: <%=crit.getCurse()%><% } %></td>
				</tr>
			    <% } %>
			
			<% } %>
			</table>
			<!-- SHOW REVEAL NOTES -->
			<table width= "100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			     <td class="titleline" width="50%"><font class="head" size="-1">Notes for this reveal</font></td>
			  </tr>
			</table>
			<br/>
			<!--  Reveal Notes -->
			<% if (r.getNotes() != null) {
				  Set<RevealNote> rns = r.getNotes();
				  for (RevealNote rn : rns) {
					 %>
					 <div style="font-weight: bold">
					 <%= rn.getNote().getUser().getUsername() %> @ <%= rn.getNote().getDate() %>
					 </div>
					 <div style="padding: 5px 0px 20px 20px">
					 <%=rn.getNote().getNote()%>
					 </div>
					 <% 
				  }
				  %>
			<% } %>
			<a href="#addrevealnote" rel="facebox">Add note</a><br/>
			<div id="addrevealnote" style="display:none;">
			<form action="?page=reveal" method="POST">
			  <input type="hidden" name="action" value="addnote"/>
			  <input type="hidden" name="id" value="<%= r.getId() %>"/>
			  <textarea class="input" name="note" style="width: 100%"></textarea><br/>
			  <input class="input" type="submit" value="Add Note"/>(max 1024 chars)
			</form>
			</div>
		
		  	<!-- End Reveal Notes -->
		</div>
	<% } %>
	
	<table width="100%">
	    <tr>
	        <td class="titleline" width="100%" colspan="7"><font class="head">All reveals</font></td>
	    </tr>
	    <% if(reveals.size()==0) {%>
			<tr><td>No reveals found.</td></tr>
		<% }
	    for (Iterator<Reveal> it=reveals.iterator(); it.hasNext();) { 
	        Reveal r = it.next();
	    %>
	    <tr>
	        <td><a href="?page=reveal&amp;id=<%= r.getId() %>&amp;mage=<%= r.getName()%>"><%= r.getName() %></a></td>
			<td>L<%= r.getLevel() %></td>
			<td><%= r.getMageClass() %></td>
			<td>F<%= r.getForestSkill() %>/D<%= r.getDeathSkill() %>/A<%= r.getAirSkill() %>/E<%= r.getEarthSkill() %></td>
			<td><% pageContext.setAttribute("kingdom",r.getKingdom());%><zod:ProfileLink kingdom="${kingdom}"/></td>
	        <td><%= StringUtils.getDaysOld(r.getTime()) %> Days old.</td>
	 		<td><%= StringUtils.getUserLink(r.getUser())%></td>
	    </tr>
	    <% } %>
	</table>
    <% } %>

<!-- SKILLS -->
	<script type="text/javascript" src="scripts/dhtmlXCommon.js"></script>
	<script type="text/javascript" src="scripts/dhtmlXTree.js"></script>
	<script type="text/javascript">
	function getSkills() {
		var hiddenSkills = document.getElementById("hiddenSkills");
		hiddenSkills.value = tree.getAllChecked();
		return true;
	}
	</script>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	    <tr height="15">
	        <td colspan="2"></td>
	    </tr>
	    <tr>
	        <td colspan="2" class="titleline"><font class="head">Skills</font></td>
	    </tr>
	    <tr height="10">
	        <td></td>
	    </tr>
	    <tr>
	        <td width="3%"></td>
	        <td width="97%">
	        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	            	<tr>
	                	<td width="20%"></td>
	                	<td width="60%">
	                	<form method="POST" action="?page=mage&action=skills" onSubmit="return getSkills()">
		                	<%
		                	pageContext.setAttribute("cq2user",mage);
		                	%>
		                	<zod:SkillTree name="tree" cq2user="${cq2user}"/>
		                	<input type="hidden" name="skills" id="hiddenSkills" value=""/>
		                	<input type="hidden" name="mage" value="<%= mage %>"/>
		                	<input class="input" type="submit" name="submit" value="Change">
	                	</form>
	                	</td>
	                </tr>
	            </table>
	        </td>
	    </tr>
	</table>
	<% List<RaidResult> raids; %>
	
<!-- INCOMING RAIDS -->
	<% if (request.getAttribute("mage_raidsby") != null) {
		raids = (List<RaidResult>)request.getAttribute("mage_raidsby"); %>
	<table width="100%">
	    <tr>
	        <td class="titleline" width="100%" colspan="7"><font class="head">Raids by <%= mage %>:</font></td>
	    </tr>
	    <% if(raids.size()==0) {%>
			<tr><td>No raids found.</td></tr>
		<% }
		for (RaidResult raid : raids){ pageContext.setAttribute("raid",raid); %>
	    <tr><td>
	    	<zod:RaidReport raid="${raid.id}" user="${user.id}"/>
	 	</td></tr>
	    <% } %>
	</table>
    <% } %>
    
<!-- OUTGOING RAIDS -->
	<% if (request.getAttribute("mage_raidson") != null) {
		raids = (List<RaidResult>)request.getAttribute("mage_raidson"); %>
	<table width="100%">
	    <tr>
	        <td class="titleline" width="100%" colspan="7"><font class="head">Raids on <%= mage %>:</font></td>
	    </tr>
	    <% if(raids.size()==0) {%>
			<tr><td>No raids found.</td></tr>
		<% }
	    for (RaidResult raid : raids){ pageContext.setAttribute("raid",raid); %>
	    <tr><td>
	    	<zod:RaidReport raid="${raid.id}" user="${user.id}"/>
	 	</td></tr>
	    <% } %>
	</table>
    <% } %>
    
</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
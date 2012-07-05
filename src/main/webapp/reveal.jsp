<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.sim.hbn.User"  %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Set" %>
<%@page import="java.util.HashSet" %>
<%@page import="java.util.TreeSet" %>
<%@page import="be.lacerta.cq2.sim.hbn.Reveal"  %>
<%@page import="be.lacerta.cq2.sim.hbn.RevealCrit"  %>
<%@page import="be.lacerta.cq2.sim.hbn.RevealNote"  %>
<%@page import="be.lacerta.cq2.sim.hbn.Item"  %>
<%@page import="be.lacerta.cq2.sim.hbn.CursedMage"  %>
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
	        <td class="titleline" width="100%"><font class="head" size="-1">Reveal DB</font></td>
	    </tr>
	</table>

<!-- SEARCH FORM -->
	<table width="100%">
	    <tr>
	        <td class="titleline" width="100%" colspan="7"><font class="head">Search</font></td>
	    </tr>
	</table>
	<form action="" method="GET">
	<input type="hidden" name="page" value="mage"/>
	<input type="text" name="mage" class="input"/>
	<input type="submit" class="input" value="Search"/>
	</form>
	
	<script type="text/javascript">

	function addAdvSearchRowToTable(tblName) {
		var rsCondFields = new Hash();
			rsCondFields.set("mage", "mage name");
			rsCondFields.set("kingdom", "kingdom");
			rsCondFields.set("level", "level");
			rsCondFields.set("forestSkill", "forest skill");
			rsCondFields.set("deathSkill", "death skill");
			rsCondFields.set("airSkill", "air skill");
			rsCondFields.set("earthSkill", "earth skill");
			rsCondFields.set("mageClass", "class");

		var rsConditions = new Hash();
			rsConditions.set("like", "equals");
			rsConditions.set("notlike", "not equals");
			rsConditions.set("gt", "greater than");
			rsConditions.set("lt", "lower than");
			
	  var tbl = document.getElementById(tblName);
	  var lastRow = tbl.rows.length;
	  var iteration = lastRow-1;
	  
	  if (iteration < 6) {
	      var row = tbl.insertRow(iteration);
	    
	      var cellField = row.insertCell(0);
	      var el = document.createElement('select');
	      el.className= 'input';
	      el.name = 'condField' + iteration;
	      el.id = 'revealCondField' + iteration;

	      rsCondFields.each(function(pair){
	 	      var objOption = document.createElement("option");
		      objOption.text=pair.value;
		      objOption.value=pair.key;
		      el.options.add(objOption);
	      });
	      cellField.appendChild(el);
	      
	      var cellField2 = row.insertCell(1);
	      var el2 = document.createElement('select');
	      el2.className= 'input';
	      el2.name = 'condition' + iteration;
	      el2.id = 'revealCondition' + iteration;

	      rsConditions.each(function(pair){
	 	      var objOption = document.createElement("option");
		      objOption.text=pair.value;
		      objOption.value=pair.key;
		      el2.options.add(objOption);
	      });
	      cellField2.appendChild(el2);

	      var cellField3 = row.insertCell(2);
	      var el3 = document.createElement('input');
	      el3.type = 'text';
	      el3.size = 10;
	      el3.className= 'input';
	      el3.name = 'condValue' + iteration;
	      el3.id = 'revealCondValue' + iteration;
	      cellField3.appendChild(el3);
	  }
	}
	
	function removeAdvSearchRowFromTable(tblName)
	{
	  var tbl = document.getElementById(tblName);
	  var lastRow = tbl.rows.length;
	  if (lastRow > 1) tbl.deleteRow(lastRow - 2);
	}

	</script>
	<table width="100%">
	    <tr>
	        <td class="titleline" width="100%" colspan="7"><font class="head">Advanced search</font></td>
	    </tr>
	</table>
	<form method="post" action="" id="frmAdvSearch">
	<input type="hidden" name="page" value="reveal"/>
	<input type="hidden" name="action" value="search"/>
	<table border="0" cellpadding="3" cellspacing="0" id="tblAdvSearch">
	    <tr>
	        <td colspan="3">
	        <input type="button" value="Add condition"  class="input" onclick="addAdvSearchRowToTable('tblAdvSearch');" />&nbsp;
	        <input type="button" value="Remove" class="input" onclick="removeAdvSearchRowFromTable('tblAdvSearch');" />
	        </td>
	    </tr>
	</table>
	<input type="submit" id="sbmAdvSearch" value="Search" class="input"/>
	</form>
	
    <p><a href="?page=reveal&action=new">Add new</a></p>
    
	<br/>
	<br/>
<!-- MESSAGE -->
	<%  if (request.getAttribute("reveal_message") != null) { %>
	    <p><%= request.getAttribute("reveal_message") %></p>
	<%  } %>

<!-- ADD REVEAL FORM -->
	<% if (("add").equals(request.getAttribute("reveal_action"))) { %>
		<form action="?page=reveal" method="POST">
		<table width="100%">
	    <tr>
	        <td class="titleline" width="100%" colspan="2"><font class="head" size="-1">Insert/update Reveal</font></td>
	    </tr>
		<tr height="3">
			<td colspan="2"></td>
		</tr>
		</table>
	    Copy/paste from Player Information page (include skills!):<br/>
	    <textarea cols="130" rows="5" class="input" name="general"><%=request.getAttribute("reveal_general")%></textarea>
	    <br/>
	    Reveal: <br/>
		<textarea cols="130" rows="20" class="input" name="reveal"><%=request.getAttribute("reveal_unparsed")%></textarea>
		<br/>
		<input type="hidden" name="action" value="add"/>
		<input type="submit" class="input" value="Add"/>
		</form>
	<% } %>

	<br/>
<!--  SHOW REVEAL -->
	<%  if (request.getAttribute("reveal_reveal") != null) {
			Reveal r = (Reveal) request.getAttribute("reveal_reveal"); %>

	<table width= "100%" border="0" cellspacing="0" cellpadding="0">
	    <tr>
	        <td class="titleline" width="50%"><font class="head" size="-1">
		        Reveal of <% pageContext.setAttribute("mage",r.getName()); %><zod:ProfileLink mage="${mage}"/>
		          (<a href="?page=reveal&action=add&mage=<%= r.getName() %>">update</a>
		         - <a href="?page=reveal&action=viewold&mage=<%= r.getName() %>">see old reveals</a>
		         <c:if test="${zod:hasAccess(user,Constants.RIGHTS_SITEADMIN)}">
		         - <a href="?page=reveal&action=reparse&id=<%= r.getId() %>">reparse</a>
		         </c:if>
		         <c:if test="${zod:hasAccess(user,Constants.RIGHTS_SUPERADMIN)}">
		         - <a href="?page=reveal&action=propagate&id=<%= r.getId() %>">propagate</a>
		         </c:if>)
	        </font></td>
	    </tr>
		<tr height="8"><td colspan="6"></td></tr>
		<tr>
			<td>
			<%=r.getName()+" " + StringUtils.getProfileLinks(r.getName())%>
				lives in <a href="<%= cq2url %>index.php?page=kingdominfo&action=viewinfo&name=<%= r.getKingdom() %>" target="_blank"><%= r.getKingdom() %></a><br/>
			Class: <%= r.getMageClass() %> Mage<br/>
			Level: <%= r.getLevel() %><br/>
			<table border="0" cellspacing="5" cellpadding="0">
			<tr><td>Forest Magic: <%= r.getForestSkill() %>&nbsp;</td><td>Death Magic: <%= r.getDeathSkill() %>&nbsp;</td></tr>
			<tr><td>Air Magic:    <%= r.getAirSkill()    %>&nbsp;</td><td>Earth Magic: <%= r.getEarthSkill() %>&nbsp;</td></tr>
			</table>
			Added <%= StringUtils.getDaysOld(r.getTime()) %> days ago (<%= StringUtils.formatDate(r.getTime()) %>) by <%= StringUtils.getUserLink(r.getUser()) %>
			</td>
		</tr>
	</table>
	
	<br/>

  	<% Set<RevealCrit> crits = r.getCreatures(); %>
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
	<br/><br/>
  
<!-- SHOW REVEAL NOTES -->
	<table width= "100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	     <td class="titleline" width="50%"><font class="head" size="-1">Notes</font></td>
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
	<form action="?page=reveal" method="POST">
	  <input type="hidden" name="action" value="addnote"/>
	  <input type="hidden" name="id" value="<%= r.getId() %>"/>
	  <textarea class="input" name="note" style="width: 50%"></textarea><br/>
	  <input class="input" type="submit" value="Add Note"/>(max 1024 chars)
	</form>

  	<!-- End Reveal Notes -->

	<% } // if (request.getAttribute("reveal_reveal") != null) { %>

<% 
List<Reveal> reveals = null;
boolean old = false;
%>
<!--  SHOW OLD REVEALS -->
	<% if (request.getAttribute("reveal_allformage") != null) {
		old = true;
    	reveals = (List<Reveal>)request.getAttribute("reveal_allformage"); %>
	<table width="100%" class="sortable">
	    <tr>
	        <td class="titleline" width="100%" colspan="7"><font class="head">All reveals for <%= request.getAttribute("reveal_mage") %></font></td>
	    </tr>
	</table>
	<% } %>
<!-- SHOW LATEST REVEALS -->
    <% if (request.getAttribute("reveal_latest") != null) {
 
    	reveals = (List<Reveal>)request.getAttribute("reveal_latest"); %>
	<table width="100%">
	    <tr>
	        <td class="titleline" width="100%" colspan="7"><font class="head" size="-1">Reveals (last 14 days)</font></td>
	    </tr>
	</table>
	<% } %>
	
	<% if (reveals != null) { %>

	<table style="padding-left: 10px" width="100%"  class="sortable">
		<thead>
		<tr align="left">
			<th>Reveal</th>
			<th>Level</th>
			<th>Class</th>
			<th width="2px" id="number">F</th>
			<th width="2px" id="number">D</th>
			<th width="2px" id="number">A</th>
			<th id="number">E</th>
			<th>Kingdom</th>
			<th>Date</th>
			<th>Revealed By</th>
		</tr>
		</thead>
	    <% for (Iterator<Reveal> it=reveals.iterator(); it.hasNext();) { 
	        Reveal r = it.next();
	    %>
	    <tr>
	        <td>
	        <% if (!old) { %>
	        <a href="?page=mage&amp;showreveal=1&amp;mage=<%= r.getName()%>"><%= r.getName() %></a><% if (CursedMage.getByMageName(r.getName()) != null) { %> (cursed)<% } %>
	        <% } else { %>
	        <a href="?page=reveal&amp;id=<%= r.getId() %>&amp;mage=<%= r.getName()%>"><%= r.getName() %></a>
	        <% } %>
	        </td>
			<td>L<%= r.getLevel() %></td>
			<td><%= r.getMageClass() %></td>
			<td style="white-space: nowrap; text-align: left">F<%= r.getForestSkill() %></td>
			<td style="white-space: nowrap; text-align: left">D<%= r.getDeathSkill() %></td>
			<td style="white-space: nowrap; text-align: left">A<%= r.getAirSkill() %></td>
			<td style="white-space: nowrap; text-align: left">E<%= r.getEarthSkill() %></td>
			<td><% pageContext.setAttribute("kingdom",r.getKingdom());%><zod:ProfileLink kingdom="${kingdom}"/></td>
	        <td><%= StringUtils.getDaysOld(r.getTime()) %> Days old.</td>
	 		<td><%= StringUtils.getUserLink(r.getUser())%></td>
	    </tr>
	    <% } %>
	</table>
    <% } %>
</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
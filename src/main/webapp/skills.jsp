<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.sim.hbn.User"  %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="be.lacerta.cq2.sim.hbn.CursedMage" %>
<%@page import="be.lacerta.cq2.sim.hbn.MageSkill" %>
<%@page import="be.lacerta.cq2.sim.hbn.Skill" %>
<%@page import="be.lacerta.cq2.sim.hbn.Creature" %>
<%@page import="be.lacerta.cq2.sim.hbn.Mage" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Locale" %>
<%@page import="java.util.Date" %>
<%@ include file="include/init.jsp" %>
<%@ include file="include/header.jsp" %>
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>
<%@ include file="include/menu.jsp" %>

<div class="porlet" id="p-content">

<zod:Title title="Skills"/>

<% if (request.getAttribute("message") != null) { %>
<div class="message"><%= request.getAttribute("message") %></div><br/>
<% } %>

<zod:AutoCompleteJS type="creature"/>

<script type="text/javascript" src="scripts/dhtmlXCommon.js"></script>
<script type="text/javascript" src="scripts/dhtmlXTree.js"></script>

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	    <tr height="15">
	        <td colspan="2"></td>
	    </tr>
	    <tr>
	        <td colspan="2" class="titleline"><font class="head">
	        Find skill</font></td>
	    </tr>
	    <tr height="10">
	        <td colspan="2"></td>
	    </tr>
	    <tr>
	        <td width="3%"></td>
	        <td width="97%">
	        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	            	<tr>
	                	<td width="20%" style="text-align: right; padding-right: 5px; vertical-align: text-top;">By skill:</td>
	                	<td width="60%" style="text-align: left;">
						<script type="text/javascript">
							function getSkills(fieldid) {
								var hiddenSkills = document.getElementById(fieldid);
								hiddenSkills.value = tree2.getAllChecked();
								return true;
							}
						</script>
	                	<form method="POST" action="?page=skills&action=search">
		                	<select name="skill" class="input">
		                	<option></option>
				            <option>Doppelganger</option>
							<option>Doppelganger Dispell</option>
							<option></option>
							<option>Jinx</option>
							<option>Jinx Dispell</option>
							<option></option>
							<option>Metamorphosis</option>
							<option>Metamorphosis Dispell</option>
							<option></option>
							<option>Suffocation</option>
							<option>Suffocation Dispell</option>
							<option></option>
							<option>Surathli's Anger</option>
							<option>Surathli's Anger Dispell</option>
							<option></option>
							<option>Surathli's Blessing</option>
							<option>Surathli's Blessing Dispell</option>
							<option></option>
							<option>Major Berserk</option>
							<option></option>
							<option>Major Immunity</option>
		                	</select>
		                	<input type="hidden" name="skills" id="fndSkillsHidden" value=""/>
		                	<input class="input" type="submit" name="submit" value="Search">
	                	</form>
	                </td>
	                </tr>
	            	<tr>
	                	<td width="20%" style="text-align: right; padding-right: 5px; vertical-align: text-top;">By creature:</td>
	                	<td width="60%" style="text-align: left;">
	                	<form method="POST" action="?page=skills&action=search">
		                	<zod:AutoInput type="creature" name="creature"/>
		                	<input class="input" type="submit" name="submit" value="Search"> (doesn't work yet...)
	                	</form>
	                	</td>
	                </tr>
	            </table>
	        </td>
	    </tr>
	</table>

<% if (request.getAttribute("skills_foundMages") != null) { %>
	
	<%
			List<Mage> foundMages = (List<Mage>)request.getAttribute("skills_foundMages");
			List<Mage> inAlliance = new ArrayList<Mage>();
			List<Mage> outAlliance = new ArrayList<Mage>();
			
			for (Mage mage : foundMages) {
				List<User> users = User.getUsersByMage(mage);
				if (users != null && users.size() > 0) {
			inAlliance.add(mage);
				} else {
			outAlliance.add(mage);
				}
			}
		%>
	<br/><br/>
	<table width="450px">
	    <tr>
	        <td colspan="2" class="titleline"><font class="head" size="-1"><%= request.getAttribute("skills_resultTitle") %></font></td>
	    </tr>
	    <tr>
	    	<td style="width: 50%; vertical-align: top;"><b>Alliance</b><br/>
	    	<ul>
	    	<%
	    		for (Mage mage : inAlliance) {
	    			%>
	    			<li><a href="<%= cq2url %>index.php?page=playerinfo&action=viewinfo&name=<%= mage %>"><%= mage %></a></li>
	    			<%
	    		}
	    	%>
	    	</ul>
	    	</td>
	    	<% if (outAlliance.size() > 0) { %>
	    	<td style="vertical-align: top;"><b>Not&nbsp;Alliance</b><br/>
	    	<ul>
	    	<%
	    		for (Mage mage : outAlliance) {
	    			%>
	    			<li><a href="<%= cq2url %>index.php?page=playerinfo&action=viewinfo&name=<%= mage %>"><%= mage %></a></li><%
	    		}
	    	%>
	    	</ul>
	    	</td>
	    	<% } %>
	    </tr>
	</table>
	
<% } %>


	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	    <tr height="15">
	        <td colspan="2"></td>
	    </tr>
	    <tr>
	        <td colspan="2" class="titleline"><font class="head">
	        Add skill</font></td>
	    </tr>
	    <tr height="10">
	        <td colspan="2"></td>
	    </tr>
	    <tr>
	        <td colspan="2">This is only for mages outside our alliance. Please use the <a href="?page=options">user details</a> page to update your own skills.</td>
	    </tr>
	    <tr height="10">
	        <td colspan="2"></td>
	    </tr>
	    <tr>
	        <td width="3%"></td>
	        <td width="97%">
	        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	            	<tr>
	                	<td width="20%" style="text-align: right; padding-right: 5px; vertical-align: text-top;">Mage:</td>
	                	<td width="60%" style="text-align: left;">
						<script type="text/javascript">
							function getSkills() {
								var hiddenSkills = document.getElementById('skillsHidden');
								hiddenSkills.value = tree2.getAllChecked();
								return true;
							}
						</script>
	                	<form method="POST" action="?page=skills&action=add" onsubmit="getSkills()">
	                		<input type="text" name="mage" id="" value="" class="input"/><br/><br/>
							<div id="treeboxbox_tree2"></div><br/><br/>
							<script>
									tree2=new dhtmlXTreeObject("treeboxbox_tree2","100%","100%",0);
									tree2.setImagePath("<%= context %>/images/tree/");
									tree2.enableCheckBoxes(1);
									tree2.enableTreeImages(0);
									tree2.enableThreeStateCheckboxes(true);
									tree2.loadXMLString('<%= Skill.getXMLSkillTree(null,null).replaceAll("'","\\\\'").replaceAll("\n","")%>');
							</script>
		                	<input type="hidden" name="skills" id="skillsHidden" value=""/>
		                	<input class="input" type="submit" name="submit" value="Add">
	                	</form>
	                </td>
	                </tr>
	            </table>
	        </td>
	    </tr>
	</table>



</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
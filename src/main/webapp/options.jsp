<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.utils.StringUtils" %>
<%@page import="be.lacerta.cq2.sim.hbn.Skill" %>
<%@page import="be.lacerta.cq2.sim.hbn.MageSkill" %>
<%@ include file="include/init.jsp" %>
<%@ include file="include/header.jsp" %>
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>
<%@ include file="include/menu.jsp" %>

<div class="porlet" id="p-content">

<% User u = (User)request.getAttribute("options_user"); %>

<zod:Title title="Options: ${requestScope.options_user.username}"/>

<% if (request.getAttribute("message") != null) { %>
<div class="message"><%= request.getAttribute("message") %></div>
<% } %>

<!-- CHANGE PASSWORD -->
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	    <tr height="15">
	        <td colspan="2"></td>
	    </tr>
	    <tr>
	        <td colspan="2" class="titleline"><font class="head">
	        Change Password </font></td>
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
	                <form method="POST" action="?page=options&action=changepassword">
	                <c:if test="${zod:hasAccess(user,Constants.RIGHTS_USERADMIN)}">
	                <input type="hidden" name="id" value="<%= u.getId()%>"/>
	                </c:if>
	                <table width="100%" border="0" cellspacing="0" cellpadding="0">
	
	                    <tr>
	                        <td width="30%">New password:</td>
	                        <td width="70%" align="left"><input type="password"
	                            name="newpassword" class="input" size="20" tabindex="2">
	                        </td>
	                    </tr>
	
	                    <tr>
	                        <td width="30%">Repeat new password:</td>
	                        <td width="70%" align="left"><input type="password"
	                            name="newpassword2" class="input" size="20" tabindex="3">
	                        </td>
	                    </tr>
	
	                    <tr>
	                        <td></td>
	                        <td><input class="input" type="submit" name="submit"
	                            value="Change Password" tabindex="4">
	                        </td>
	                    </tr>
	                </table>
	                </form>
	                </td>
	                <td width="20%"></td>
	            </tr>
	        </table>
	
	        </td>
	    </tr>
	</table>


<!-- GENERAL INFO -->
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	    <tr height="15">
	        <td colspan="2"></td>
	    </tr>
	    <tr>
	        <td colspan="2" class="titleline"><font class="head">
	        General info </font></td>
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
	                <form method="POST" action="?page=options&action=changegeneral">
	                <c:if test="${zod:hasAccess(user,Constants.RIGHTS_USERADMIN)}">
	                <input type="hidden" name="id" value="<%= u.getId()%>"/>
	                </c:if>
	                <table width="100%" border="0" cellspacing="0" cellpadding="0">
	
	                    <tr>
	                        <td width="30%">CQ2 name:</td>
	                        <td width="70%" align="left"><input type="text"
	                            name="cq2name" class="input" size="20" 
	                            value="<%= u.getCq2name() %>"/>
	                        </td>
	                    </tr>
	                    
	                    <tr>
	                        <td width="30%">Class:</td>
	                        <td width="70%" align="left">
	                     <select name="class" class="input">
	                        <option <% 
	                          if (("Forest").equals(u.getCq2class())) { 
	                          %>selected<% } %>
	                        >Forest</option>
	                        <option <% 
	                          if (("Death").equals(u.getCq2class())) { 
	                          %>selected<% } %>
	                        >Death</option>
	                        <option <% 
	                          if (("Air").equals(u.getCq2class())) { 
	                          %>selected<% } %>
	                        >Air</option>
	                        <option <% 
	                          if (("Earth").equals(u.getCq2class())) { 
	                          %>selected<% } %>
	                        >Earth</option>
	                    </select>
	                        </td>
	                    </tr>
	
	                    <tr>
	                        <td width="30%">Kingdom:</td>
	                        <td width="70%" align="left"><input type="text"
	                            name="kingdom" class="input" size="20" 
	                            value="<%= u.getKingdom() %>"/>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td width="30%">Level:</td>
	                        <td width="70%" align="left">
	                    <select name="level" class="input">
	                    <% for (int i=1; i<=70; i++) { %>
	                        <option <% if (u.getLevel() == i) { %>selected<% } %>
	                        ><%= i %></option>
	                    <% } %>
	                    </select>
	
	                        </td>
	                    </tr>
	                    <tr>
	                        <td width="30%">Forest skill:</td>
	                        <td width="70%" align="left">
	                        <input type="text" name="forestSkill" class="input" size="20" value="<%= u.getForestSkill() %>"/>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td width="30%">Death skill:</td>
	                        <td width="70%" align="left">
	                        <input type="text" name="deathSkill" class="input" size="20" value="<%= u.getDeathSkill() %>"/>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td width="30%">Air skill:</td>
	                        <td width="70%" align="left">
	                        <input type="text" name="airSkill" class="input" size="20" value="<%= u.getAirSkill() %>"/>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td width="30%">Earth skill:</td>
	                        <td width="70%" align="left">
	                        <input type="text" name="earthSkill" class="input" size="20" value="<%= u.getEarthSkill() %>"/>
	                        </td>
	                    </tr>
	
	                    <tr>
	                        <td width="30%">Email:</td>
	                        <td width="70%" align="left"><input type="text"
	                            name="email" class="input" size="20" 
	                            value="<%= u.getEmail() %>"/>
	                            (only visible to admins)
	                        </td>
	                    </tr>
	                    
	                    <tr>
	                        <td width="30%">Phone:</td>
	                        <td width="70%" align="left"><input type="text"
	                            name="phone" class="input" size="20" 
	                            value="<%= u.getPhone() %>"/>
	                            (only visible to admins)
	                        </td>
	                    </tr>
	                    
	                    <tr>
	                        <td width="30%">Q username:</td>
	                        <td width="70%" align="left"><input type="text"
	                            name="qauth" class="input" size="20" 
	                            value="<%= u.getQauth() %>"/>
	                        </td>
	                    </tr>                           
	                    <tr>
	                        <td width="30%">Birthday</td>
	                        <td width="70%" align="left">
								<script type="text/JavaScript" src="scripts/scw.js"></script>
								<input name="birthday" class="input" size="20"
								 onclick="scwShow(this,event);" value="<%= u.getBirthdayString()%>" />
	
	                        </td>
	                    </tr>
	                    
	                    <tr>
	                        <td width="30%">Location:</td>
	                        <td width="70%" align="left"><input type="text"
	                            name="location" class="input" size="20" 
	                            value="<%= u.getLocation() %>"/>
	                        </td>
	                    </tr>
	                    
	                    <tr>
	                        <td width="30%">Imagepack:</td>
	                        <td width="70%" align="left"><input type="text"
	                            name="imagepack" class="input" size="20" 
	                            value="<%= u.getImagepac() %>"/>
	                        </td>
	                    </tr>
	                    
		<c:if test="${user.id==1 || ( zod:hasAccess(user,Constants.RIGHTS_SUPERADMIN) && ! zod:hasAccess(requestScope.options_user,Constants.RIGHTS_SUPERADMIN) )}">
	                    <tr>
	                        <td width="30%">Disabled:</td>
	                        <td width="70%" align="left"><input type="checkbox"
	                            name="disabled" class="input"
	                            <% if (u.isDisabled()) { %>
	                            checked
	                            <% } %>
	                            >
	                        </td>
	                    </tr>
	
	                    <tr>
	                        <td width="30%">ZoD:</td>
	                        <td width="70%" align="left"><input type="checkbox"
	                            name="zodonly" class="input"
	                            <c:if test="${zod:hasAccess(requestScope.options_user,Constants.RIGHTS_ZODONLY)}">
	                            checked
	                            </c:if>
	                            >
	                        </td>
	                    </tr>
	
	                    <tr>
	                        <td width="30%">Pub mod:</td>
	                        <td width="70%" align="left"><input type="checkbox"
	                            name="pubmod" class="input"
	                            <c:if test="${zod:hasAccess(requestScope.options_user,Constants.RIGHTS_PUBMOD)}">
	                            checked
	                            </c:if>
	                            >
	                        </td>
	                    </tr>
	
	                    <tr>
	                        <td width="30%">Next age pub:</td>
	                        <td width="70%" align="left"><input type="checkbox"
	                            name="nextage" class="input"
	                            <c:if test="${zod:hasAccess(requestScope.options_user,Constants.RIGHTS_NEXT_AGE)}">
	                            checked
	                            </c:if>
	                            >
	                        </td>
	                    </tr>
	                        
	                    <tr>
	                        <td width="30%">Support admin:</td>
	                        <td width="70%" align="left"><input type="checkbox"
	                            name="support" class="input"
	                            <c:if test="${zod:hasAccess(requestScope.options_user,Constants.RIGHTS_SUPPORTADMIN)}">
	                            checked
	                            </c:if>
	                            >
	                        </td>
	                    </tr>
	                    
	                    <tr>
	                        <td width="30%">User admin:</td>
	                        <td width="70%" align="left"><input type="checkbox"
	                            name="admin" class="input"
	                            <c:if test="${zod:hasAccess(requestScope.options_user,Constants.RIGHTS_USERADMIN)}">
	                            checked
	                            </c:if>
	                            >
	                        </td>
	                    </tr>
	                    
	                    <tr>
	                        <td width="30%">Council:</td>
	                        <td width="70%" align="left"><input type="checkbox"
	                            name="tek" class="input"
	                            <c:if test="${zod:hasAccess(requestScope.options_user,Constants.RIGHTS_TEK)}">
	                            checked
	                            </c:if>
	                            >
	                        </td>
	                    </tr>
	                    
	    		<% if (user.getId() == 1) { %>
	                    <tr>
	                        <td width="30%">Superadmin:</td>
	                        <td width="70%" align="left"><input type="checkbox"
	                            name="superadmin" class="input"
	                            <c:if test="${zod:hasAccess(requestScope.options_user,Constants.RIGHTS_SUPERADMIN)}">
	                            checked
	                            </c:if>
	                            >
	                        </td>
	                    </tr>
	    		<% } %>
		</c:if>
	
	                    <tr>
	                        <td></td>
	                        <td><input class="input" type="submit" name="submit" value="Change">
	                        </td>
	                    </tr>
	                </table>
	                </form>
	                </td>
	                <td width="20%"></td>
	            </tr>
	        </table>
	
	        </td>
	    </tr>
	</table>
        
        
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
	        <td colspan="2" class="titleline"><font class="head">
	        Skill tree</font></td>
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
	                	<form method="POST" action="?page=options&action=skills" onSubmit="return getSkills()">
		                	<%
		                	pageContext.setAttribute("cq2user",u.getCq2name());
		                	pageContext.setAttribute("cq2class",u.getCq2class());
		                	%>
		                	<zod:SkillTree name="tree" cq2class="${cq2class}" cq2user="${cq2user}"/>
		                	<c:if test="${zod:hasAccess(user,Constants.RIGHTS_USERADMIN)}">
			                <input type="hidden" name="id" value="<%= u.getId()%>"/>
		                	</c:if>
		                	<input type="hidden" name="skills" id="hiddenSkills" value=""/>
		                	<input class="input" type="submit" name="submit" value="Change">
	                	</form>
	                	</td>
	                </tr>
	            </table>
	        </td>
	    </tr>
	</table>
<!-- XP PROGRESS  -->
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	    <tr height="15">
	        <td colspan="2"></td>
	    </tr>
	    <tr>
	        <td colspan="2" class="titleline"><font class="head">
	        XP Progress</font></td>
	    </tr>
	    <tr height="10">
	        <td></td>
	    </tr>
	    <tr>
	        <td width="3%"></td>
	        <td width="97%">
	
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            
	            <tr>
	            <td colspan="3"><p><img src="<%= context %>/getChart.jsp?id=<%= u.getId() %>"/></p></td>
	            </tr>
	            
	            
	             <tr>
	                <td width="20%"></td>
	                <td width="60%">Update from CQ2 Character page:</td>
	                <td></td>
	            </tr>           
	            <tr>
	                <td width="20%"></td>
	                <td width="60%">
	                <form method="POST" action="?page=options&action=fromcharpage">
		            <c:if test="${zod:hasAccess(user,Constants.RIGHTS_USERADMIN)}">
			        <input type="hidden" name="id" value="<%= u.getId()%>"/>
			        </c:if>
	                <textarea class="input" name="charpage" rows="10" cols="65"></textarea><br/>
	                <input class="input" type="submit" name="submit" value="Update">
	                </form>
	                </td>
	                <td></td>
	            </tr>
	            
	        </table>
	
	        </td>
	    </tr>
	</table>

<!-- IMAGE GENERATOR -->
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	    <tr height="15">
	        <td colspan="2"></td>
	    </tr>
	    <tr>
	        <td colspan="2" class="titleline"><font class="head">Image generator</font></td>
	    </tr>
	    <tr height="10">
	        <td></td>
	    </tr>
	    <tr>
	        <td width="3%"></td>
	        <td width="97%">
	
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">       
	            <tr>
	                <td width="60%">
	                <!-- 
						Include the WYSIWYG javascript files
					-->
					<script type="text/javascript" src="scripts/wysiwyg.js"></script>
					<script type="text/javascript" src="scripts/wysiwyg-settings.js"></script>
					<!-- 
						Attach the editor on the textareas
					-->
					<script type="text/javascript">
						WYSIWYG.attach('imagetext');
					</script>
	                <form method="POST" action="?page=options&action=updateimage">
			        <c:if test="${zod:hasAccess(user,Constants.RIGHTS_USERADMIN)}">
			        <input type="hidden" name="id" value="<%= u.getId()%>"/>
			        </c:if>
	                <textarea name="imagetext" id="imagetext" style="width:80%;height:200px;"><%= u.getUserImage()==null?"":u.getUserImage().getText() %></textarea><br/>
	                <input class="input" type="submit" name="submit" value="Update">
	                </form>
	                <p>(you cannot include images in your html)</p>
	                </td>
	                <td></td>
	            </tr>            
	            <tr>
	                <td width="60%">The link to your image: <a href="http://cq2.lacerta.be<%= context %>/image.jsp?id=<%= u.getId() %>">http://cq2.lacerta.be<%= context %>/image.jsp?id=<%= u.getId() %></a></td>
	                <td></td>
	            </tr>    
	            
	        </table>
	
	        </td>
	    </tr>
	</table>


</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.sim.hbn.User"  %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="be.lacerta.cq2.objects.Gem" %>
<%@ include file="include/init.jsp" %>
<%@ include file="include/header.jsp" %>
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>
<%@ include file="include/menu.jsp" %>

<div class="porlet" id="p-content">

<table width="100%">
    <tr>
        <td class="titleline" width="100%"><font class="head" size="-1">Creature calculator</font></td>
    </tr>
</table>



        <form method="post" action="?page=critdb">
        <input type="hidden" name="action" value="critcalc"/>
        <table style="width: 200px">
        <% 
        if (request.getAttribute("critdb_DY") != null) {
            %>
            <tr>
            <td class="head">
            <b>Level <%= request.getAttribute("critdb_LY") %></b>:
            </td><td>
            <%= request.getAttribute("critdb_DY") %>/<%= request.getAttribute("critdb_HY") %>
            </td></tr>
            <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
            <%
        }
        %>


        <tr><td>Damage:</td>
        <td><input type="text" name="DX" value="<%= request.getAttribute("critdb_DX")==null?"":request.getAttribute("critdb_DX") %>" size="10" class="input"/></td></tr>
        <tr><td>Health:</td>
        <td><input type="text" name="HX" value="<%= request.getAttribute("critdb_HX")==null?"":request.getAttribute("critdb_HX") %>" size="10" class="input"/></td></tr>
        <tr><td>Current level:</td>
        <td><input type="text" name="LX" value="<%= request.getAttribute("critdb_LX")==null?"":request.getAttribute("critdb_LX") %>" size="10" class="input"/></td></tr>
        <tr><td>Desired level:</td>
        <td><input type="text" name="LY" value="<%= request.getAttribute("critdb_LY")==null?"":request.getAttribute("critdb_LY") %>" size="10" class="input"/></td></tr>
        <tr><td></td><td><input type="submit" value="Submit" class="input"/></td></tr>
        </table>
        </form>


<table width="100%">
    <tr>
        <td class="titleline" width="100%"><font class="head" size="-1">Summon chance calculator</font></td>
    </tr>
</table>



        <form method="post" action="?page=critdb">
        <input type="hidden" name="action" value="summoncalc"/>
        <table style="width: 200px">

        <% 
        if (request.getAttribute("critdb_chance") != null) {
            %>
	        <tr>
	            <td class="head" colspan="2">
            <b><%=request.getAttribute("critdb_chance")%>% chance of success</b>
	        	</td>
	        </tr>
            <%
        }
        %>
        <tr><td>Mage skill:</td>
        <td><input type="text" name="mage" value="<%= request.getAttribute("critdb_mageSkill")==null?"":request.getAttribute("critdb_mageSkill") %>" size="10" class="input"/></td></tr>
        <tr><td>Creature skill:</td>
        <td><input type="text" name="crit" value="<%= request.getAttribute("critdb_critSkill")==null?"":request.getAttribute("critdb_critSkill") %>" size="10" class="input"/></td></tr>
       	<tr><td></td><td><input type="submit" value="Submit" class="input"/></td></tr>
        </table>
        </form>


</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
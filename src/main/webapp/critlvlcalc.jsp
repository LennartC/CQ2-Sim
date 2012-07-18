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
        <td class="titleline" width="100%"><font class="head" size="-1">Creature level calculator</font></td>
    </tr>
</table>

<zod:AutoCompleteJS type="amulet" />

        <form method="post" action="?page=critlvlcalc&action=calc">
<!--         <input type="hidden" name="action" value="critcalc"/> -->
       <!--  <table style="width: 200px"> -->
        
        <td><zod:AutoInput type="amulet" name="ammy" value=""
                                size="40" /></td>

					<select name="class" class="input">
						<option>Main class</option>
						<option>Second class</option>
					</select>

<%--     Max lvl: <% request.getAttribute("maxLvl"); %> --%>
        </form>


</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
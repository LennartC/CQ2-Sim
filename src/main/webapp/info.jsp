<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.utils.StringUtils" %>
<%@ include file="include/init.jsp" %>
<%@ include file="include/header.jsp" %>
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>
<%@ include file="include/menu.jsp" %>

<div class="porlet" id="p-content">
<c:set var="info_user" value="${requestScope.info_user}"/>
<table width="100%">
    <tr>
        <td class="titleline" width="100%">
        <font class="head" size="-1">General Information</font>
        </td>
    </tr>
</table>

<table width="100%">
    <tr>
        <td width="3%">&nbsp;</td>
        <td width="97%">
        <table> 
        <tr><td colspan="2">
        </td></tr>
        <tr><td>CQ2 name:</td><td><zod:ProfileLink mage="${info_user.mage}"/></td></tr>
        <tr><td>Class:</td><td>${info_user.cq2class}</td></tr>
        <tr><td>Kingdom:</td><td><zod:ProfileLink kingdom="${info_user.kingdom}"/></td></tr>
        <tr><td>Q username:</td><td>${info_user.qauth}</td></tr>
        <tr><td>Level:</td><td>${info_user.level}</td></tr>
        <tr><td>Travel record:</td><td>${info_user.travelgain}</td></tr>
        <tr><td>Best raid:</td><td>${info_user.raidgain}</td></tr>
        <tr><td>Biggest loss:</td><td>${info_user.raidloss}</td></tr>
        <c:if test="${zod:hasAccess(user,Constants.RIGHTS_USERADMIN)}">
       		<tr><td>Email:</td><td>${info_user.email}</td></tr>
        	<tr><td>Phone:</td><td>${info_user.phone}</td></tr>
        </c:if>
        <tr><td>Support:</td><td><a href="?page=support&amp;user=${info_user.id}">see support</a></td></tr>
        <tr><td>Powerspells:</td><td><a href="?page=powerspell&amp;user=${info_user.id}">see powerspells</a></td></tr>
        <tr><td colspan="2">
        <c:choose>
        	<c:when test="${info_user.online}">${info_user.username} is online</c:when>
        	<c:otherwise>${info_user.username} is offline (${zod:convertTimeCount(info_user.idleSeconds)})</c:otherwise>
        </c:choose>
        
        <c:if test="${zod:hasAccess(user,Constants.RIGHTS_USERADMIN) && user.ulvl >= requestScope.info_user.ulvl}">
        <p><a href="?page=options&id=${info_user.id}">Change</a></p>
        </c:if>
        </td></tr>
        </table>
        </td>
    </tr>
</table>
<br/>
<img src="getChart.jsp?id=${info_user.id}" alt="XP Progress"/>

</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
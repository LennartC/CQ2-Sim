<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.List"  
%><%@page import="java.util.Iterator"  
%><%@page import="java.text.SimpleDateFormat" 
%><%@page import="be.lacerta.cq2.utils.StringUtils"  
%><%@page import="be.lacerta.cq2.sim.hbn.*"  
%><%@include file="include/init.jsp" 
%><%@include file="include/header.jsp"
%>
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>

<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm");
%>


<%@ include file="include/menu.jsp" %>

<div class="porlet" id="p-content">

<zod:Title title="Raid reports (last 14 days)" href="#addraid" rel="facebox" reftext="Add new"/>

<div id="addraid" style="display:none;">
        <form method="post" action="?page=raid&action=addraid">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr><td>
                Copy/paste complete battle report (including the General Information):<br/>
                <textarea name="text" class="input" rows="5" style="width: 100%"></textarea></td>
            </tr>
            <tr>
                <td><input class="input" value="Post" type="submit"></td>
            </tr>
        </table>
        </form>
</div>

	<table width="100%">
	    <tr>
	        <td class="titleline" width="100%" colspan="7">
				<font class="head">
					show:
					<a href="?page=raid&type=<%= RaidResult.TYPE_ALL %>">all</a> |
					<a href="?page=raid&type=<%= RaidResult.TYPE_INCOMING %>">incoming</a> |
					<a href="?page=raid&type=<%= RaidResult.TYPE_OUTGOING %>">outgoing</a>
				</font>
			</td>
	    </tr>
	</table>


<table style="padding-left: 10px" class="titleline" border="0" cellpadding="3" cellspacing="0" width="100%">

<%	
List<RaidResult> raids = (List<RaidResult>)request.getAttribute("raid_raids");
pageContext.setAttribute("raids",raids);
%>
	<c:forEach items="${raids}" var="raid" >
		<tr><td>
		<zod:RaidReport raid="${raid.id}" user="${user.id}"/>
		</td></tr>
	</c:forEach>
  </table>

</div>


</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
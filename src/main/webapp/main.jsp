<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.sim.hbn.User"  %>
<%@page import="java.util.Iterator" %>
<%@page import="be.lacerta.cq2.sim.hbn.Announcement" %>
<%@page import="be.lacerta.cq2.utils.StringUtils" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@ include file="include/init.jsp" %>
<%@ include file="include/header.jsp" %>
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>
<% 
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm");
%>
<%@ include file="include/menu.jsp" %>

<div class="porlet" id="p-content">

    <%
    	if (("new").equals(request.getAttribute("announcement_action"))
                || ("edit").equals(request.getAttribute("announcement_action"))) {
            
                Announcement ann = (Announcement)request.getAttribute("announcement_announcement");
                boolean edit = (ann!=null);
    %>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="20%"></td>
        <td width="60%">
        <form method="POST" action="?page=announcement&action=<%= request.getAttribute("announcement_action") %>">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">

            <tr>
                <td width="30%" valign="top">Title:</td>
                <td width="70%" align="left">
                <input type="text" class="input" name="title" size="60"
                <% if (edit) {%>value="<%= ann.getTitle() %>"<%}%>
                />
                </td>
            </tr>
            <tr>
                <td width="30%" valign="top">Text:</td>
                <td width="70%" align="left">
                <textarea name="text" class="input" rows="12" cols="60" tabindex="1"><%
                	if (edit) {
                %><%=ann.getContent()%><%
                	}
                %></textarea></td>
            </tr>

            <tr>
                <td></td>
                <td><input class="input" type="submit" name="submit"
                    value="<% if (edit) { %>Edit<% } else { %>Post<% } %>" tabindex="2"/></td>
            </tr>
        </table>
        <%
        	if (edit) {
        %>
        <input type="hidden" name="aid" value="<%= ann.getAnnID() %>"/>
        <%
        	}
        %>
        </form>
        </td>
        <td width="20%"></td>
    </tr>
</table>
<%
	}
%>
 
 <%
  	Iterator<Announcement> anns = Announcement.getAnouncements(10).iterator();
   
    while (anns.hasNext()) {
        Announcement ann = anns.next();
  %>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr height="15">
            <td colspan="2"></td>
            </tr>
            <tr>

            <td colspan="2" class="titleline">
            <font class="head">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
            <td class="head" width="50%">
            <c:if test="${zod:hasAccess(user,Constants.RIGHTS_SITEADMIN)}">
            <a href="?page=announcement&action=delete&aid=<%= ann.getAnnID()%>">X</a>
            <a href="?page=announcement&action=edit&aid=<%= ann.getAnnID()%>">E</a>
            </c:if>
            <%=ann.getTitle()%>
            </td>
            <td class="head" width="20%" align="center">
            by <%=ann.getUser()%>

            </td>
            <td class="head" width="30%" align="center">
            <%= sdf.format(ann.getTime()) %>
            </td>
            </tr>
            </table>
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
            <%=   StringUtils.convertBBCode(ann.getContent()) %>
        </td>
        </tr>
        </table>
<% } %>
</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
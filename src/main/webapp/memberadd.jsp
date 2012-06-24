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

<% if (request.getAttribute("memberadd_added") != null 
        && request.getAttribute("memberadd_added").equals("success")) { %>
User added
<% } else { %>

<% if (request.getAttribute("memberadd_added") != null 
        && request.getAttribute("memberadd_added").equals("failed")) { %>
User not added!
<% } %>
<zod:Title title="Add a Member"/>

<form action="?page=memberadd" method="POST">

<table width="100%">
    <tr>
        <td width="20%">UserName:</td>
        <td width="80%"><input type="text" name="username" class="input"
            value="<%= request.getAttribute("memberadd_username")%>"/></td>
    </tr>
    <tr>
        <td>Password:</td>
        <td><input type="password" name="password" class="input"/></td>
    </tr>
    <tr>
        <td>CQ2 name:</td>
        <td width="80%"><input type="text" name="cq2name" class="input"
            value="<%= request.getAttribute("memberadd_cq2name")%>"/></td>
    </tr>
    <tr>
        <td>Kingdom:</td>
        <td><input type="text" name="kingdom" class="input" value="<%= request.getAttribute("memberadd_kingdom")%>"/></td>
    </tr>

    <tr>
        <td>Class:</td>
        <td><select name="class" class="input">
            <option <% 
              if (request.getAttribute("memberadd_class").equals("Forest")) { 
              %>selected<% } %>
            >Forest</option>
            <option <% 
              if (request.getAttribute("memberadd_class").equals("Death")) { 
              %>selected<% } %>
            >Death</option>
            <option <% 
              if (request.getAttribute("memberadd_class").equals("Air")) { 
              %>selected<% } %>
            >Air</option>
            <option <% 
              if (request.getAttribute("memberadd_class").equals("Earth")) { 
              %>selected<% } %>
            >Earth</option>
        </select></td>
    </tr>

</table>
<input type="submit" class="input" value="Add"></form>
<% } %>
</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
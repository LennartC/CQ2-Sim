<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.utils.StringUtils" 
%><%@page import="java.util.*" 
%><%@ include file="include/init.jsp" 
%><%@ include file="include/header.jsp" %>
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>
<%@ include file="include/menu.jsp" %>

<div class="porlet" id="p-content">

<% User u = (User)request.getAttribute("info_user"); 
List<User> users = (List<User>)request.getAttribute("members_userlist");%>

<zod:Title title="Member Listing (${fn:length(requestScope.members_userlist)})"
		href="addforms.jsp?form=parsekd" rel="facebox" reftext="Update a kingdom"/>

<%

Collections.sort(users, new Comparator<User>() {
	public int compare(User u1, User u2) {
		String kd1 = u1.getKingdom(); if (kd1==null) kd1="";
		String kd2 = u2.getKingdom(); if (kd2==null) kd2="";
		if (kd1.equals(kd2)) {
			if (u1.getMage()==null && u2.getMage()==null) return 0;
			else if (u1.getMage()==null) return -1;
			else return u1.getMage().compareTo(u2.getMage());
		} else {
			return kd1.compareTo(kd2);
		}
	}  });

if (users.size() > 1) {
String kingdom = users.get(0).getKingdom();
int members = 1;
int i;

for (i=1; i<=users.size();i++) {
	String kingdomOutput=kingdom;
	if(kingdom == null || kingdom.length()==0) kingdomOutput="Not Playing";
	
	String newKingdom = i<users.size()?users.get(i).getKingdom():null;
    if (i+1<users.size() && (newKingdom==null || newKingdom.equals(kingdom))) {
        members++;
    } else {
        boolean newkd = true;
        if (i<users.size() && (newKingdom==null || newKingdom.equals(kingdom))) {
         members++;
         newkd = false;
        }
        
        /** do html **/
        %>
        
<table width="100%">
    <tr>
        <td class="titleline" width="100%"><font class="head"><%= kingdomOutput %>
        (<%= members %> users)</font></td>
    </tr>
</table>
<div class="threecols">
	<ul class="threecols">
        <%

        if (!newkd) i++;
        for (int j=i-(members);j<i;j++) {
          pageContext.setAttribute("member",users.get(j));
          %>
          <c:choose>
          	<c:when test="${member.mage != null}">
	          	<li class="threecols">
	          	<c:choose>
	          	<c:when test="${member.online}">
	          		<zod:ProfileLink mage="${member.mage.name}" link="?page=info&id=${member.id}" text="${member.username}" cssClass="top"/>
	          	</c:when>
	          	<c:otherwise>
	          		<zod:ProfileLink mage="${member.mage.name}" link="?page=info&id=${member.id}" text="${member.username}" cssClass="head"/>
	          	</c:otherwise>
	          	</c:choose>
	          	</li>
          	</c:when>
          	<c:otherwise>
          		<li class="threecols">${zod:getUserLink(member)}</li>
          	</c:otherwise>
          </c:choose>
          <%
        }
        %>
    </ul>
</div>
        <%
        members = 1;
        if (newkd && i<users.size()) {
         	kingdom = users.get(i).getKingdom();
        }
    }
}

} else {
    %><p>You're alone :(</p><%
}
%>
<!--<br/>-->
<!--<table width="100%">-->
<!--    <tr>-->
<!--        <td class="titleline" width="100%">-->
<!--        <font class="head" size="-1">Parse kingdom:</font>-->
<!--        </td>-->
<!--    </tr>-->
<!--</table>-->
<!--<form method="post" action="?page=members&action=parsekd">-->
<!--<textarea rows="10" cols="70" name="kdpage" class="input" ></textarea><br/>-->
<!--<input type="submit" class="input" value="Update!">-->
<!--</form>-->





</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
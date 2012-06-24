<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.sim.hbn.User"  %>
<%@page import="be.lacerta.cq2.sim.hbn.Mage"  %>
<%@page import="be.lacerta.cq2.sim.hbn.ShardDonation"  %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="java.util.TreeMap" %>
<%@ include file="include/init.jsp" %>
<%@ include file="include/header.jsp" %>
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>
<%@ include file="include/menu.jsp" %>

<div class="porlet" id="p-content">

<table width="100%">
    <tr>
        <td class="titleline" width="100%"><font class="head" size="-1">Shard donations</font></td>
    </tr>
</table>


<%  if (request.getAttribute("shard_message") != null) { %>
    <p><%= request.getAttribute("shard_message") %></p>
<%  } %>


<c:if test="${zod:hasAccess(user,Constants.RIGHTS_SITEADMIN)}">
    <form action="?page=shard" method="POST">
    Copy/paste donation:<br/>
    <textarea cols="70" rows="5" class="input" name="donation">
<%=request.getAttribute("shard_donation")%></textarea>
    <br/>
    <input type="hidden" name="action" value="add"/>
    <input type="submit" class="input" value="Add"/>
    </form>
</c:if>
    <p>
    <a href="?page=shard&action=">Overview</a>
    -
    <a href="?page=shard&action=showdonations">Latest donations</a>
    </p>
<%  
  if (request.getAttribute("shard_stock") != null) { 
       %><font class="head" size="-1">Shard stock:</font><%
       TreeMap<Integer,TreeMap<String,Integer>> hm;
       hm = (TreeMap<Integer,TreeMap<String,Integer>>)request.getAttribute("shard_stock");
       
       int cols=3;
       int i=0;
       %><table cellpadding="5"><%
       for (Integer mage : hm.keySet()) {
           i++;
           TreeMap<String,Integer> ih = hm.get(mage);
           if (i%cols==1) { %><tr><% }
           Long total = 0L;
           String s = "";
           for (String shard : ih.keySet()) { 
               s+="<tr>";
               s+="<td>"+ ih.get(shard) +"&nbsp;x&nbsp;</td>";
               s+="<td>"+ shard +"</td>";
               s+="</tr>";
               total += ih.get(shard);
           }
           %>
           <td style="vertical-align: top">
           <table>
           <tr><td colspan="2"><strong><%=((User)User.get(User.class,mage)).getCq2name()%> (<%=total%>):</strong></td></tr>
           <%=s%>
           </table>
           </td><%
           	if (i%cols==0) {
           %></tr><%
           	}
                  }
                  while (i%cols!=0) {
           %><td></td><%
           	i++;
                      if (i%cols==0) {
           %></tr><%
           	}
                  }
           %></table><%
           	}
           %>
<%
	if (request.getAttribute("shard_shardspermage") != null) {
%><font class="head" size="-1">Shards donated per mage:</font><%
	TreeMap<Mage,TreeMap<String,Integer>> hm;
       hm = (TreeMap<Mage,TreeMap<String,Integer>>)request.getAttribute("shard_shardspermage");
       
       int cols=3;
       int i=0;
%><table cellpadding="5"><%
	for (Mage mage : hm.keySet()) {
           i++;
           TreeMap<String,Integer> ih = hm.get(mage);
           if (i%cols==1) {
%><tr><%
	}
           Long total = 0L;
           String s = "";
           for (String shard : ih.keySet()) { 
               s+="<tr>";
               s+="<td>"+ ih.get(shard) +"&nbsp;x&nbsp;</td>";
               s+="<td>"+ shard +"</td>";
               s+="</tr>";
               total += ih.get(shard);
           }
%>
           <td style="vertical-align: top">
           <table>
           <tr><td colspan="2"><strong><%=mage%> (<%=total%>):</strong></td></tr>
           <%=s%>
           </table>
           </td><%
           	if (i%cols==0) {
           %></tr><%
           	}
                  }
                  while (i%cols!=0) {
           %><td></td><%
           	i++;
                      if (i%cols==0) {
           %></tr><%
           	}
                  }
           %></table><%
           	}
           %>
<%
	if (request.getAttribute("shard_donations") != null) {
%><font class="head" size="-1">Latest donations:</font><%
	List<ShardDonation> l;
       l = (List<ShardDonation>)request.getAttribute("shard_donations");
       
       TreeMap<Integer,String> ul = new TreeMap<Integer,String>();
%><table cellpadding="5"><%
	for (ShardDonation sd : l) {
           if (!ul.containsKey(sd.getToUserId())) {
               ul.put(sd.getToUserId(),((User)User.get(User.class,sd.getToUserId())).getCq2name());
           }
%>
    	   <tr>
    	   <td><%= sd.getTime() %></td>
    	   <td><%= sd.getMage() %></td>
    	   <td><%= sd.getShard() %></td>
    	   <td><%= ul.get(sd.getToUserId()) %></td>
    	   </tr>
    	   <%
       }
       %></table><%
       
   }
%>
</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
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
        <td class=titleline width=100%><font class=head size=-1>Gems</font></td>
    </tr>
</table>
<p><font class="head" size="-1">Gem:</font>

<form action="?page=gem" method="POST">
<textarea cols="70" rows="8" class="input" name="gems">
<%= request.getAttribute("gem_gems") %></textarea>
<br/>
<input type="checkbox" value="yes" name="sort" checked>Sort by power<br>
<input type="checkbox" value="yes" name="showall">Show All Information <br>
<input type="submit" class="input" value="Calculate Gems">
</form>

<%

if (request.getAttribute("gem_totalpower") != null) {
    %><p>
    <font class="head">Total Power: </font><%= request.getAttribute("gem_totalpower") %>
    (<%= request.getAttribute("gem_count") %> gems)</p><%
}

if (request.getAttribute("gem_gemlist") != null) {
    List<Gem> gems = (List<Gem>)request.getAttribute("gem_gemlist");
    for (Iterator<Gem> i = gems.iterator(); i.hasNext(); ) {
        Gem g = i.next();
        %><b><%=g.toCompactString()%></b> -> <%= g.getPower() %><br/><%
    }
}

int cnt = 40;

%>

<table width="100%">
    <tr>
        <td class=titleline width=100%><font class=head size=-1>Gem list:</font></td>
    </tr>
</table>
<table>
<tr>
	<td class="head">Regular:</td>
	<td>
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">nis</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">inu</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">eno</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">tur</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">aru</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">ruh</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">ner</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">sah</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">var</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">chi</a>
	</td>
</tr>
<tr>
	<td class="head">Common:</td>
	<td>
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">fih</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">efu</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">fen</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">fau</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">eda</a>
	</td>
</tr>
<tr>
	<td class="head">Uncommon:</td>
	<td>
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">dun</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">uka</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">kih</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">vae</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">len</a>
	</td>
</tr>
<tr>
	<td class="head">Scarce:</td>
	<td>
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">lua</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">jih</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">zey</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">uza</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">zar</a>
	</td>
</tr>
<tr>
	<td class="head">Rare:</td>
	<td>
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">ime</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">mul</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">mae</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">qer</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">qah</a>
	</td>
</tr>
<tr>
	<td class="head">Exceptional:</td>
	<td>
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">yar</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">yun</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">nuy</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">uyi</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">xil</a>
	</td>
</tr>
<tr>
	<td class="head">Singular:</td>
	<td>
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">xis</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">uxi</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">nax</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">xio</a>,
	<a href="<%= CQ2Functions.URL %>index.php?page=playersinfo&action=randomtarget&bsgem=<%= cnt-- %>" target="_blank">uax</a>
	</td>
</tr>

</table>




</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
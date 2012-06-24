<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.sim.hbn.User"  %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="java.text.NumberFormat" %>
<%@page import="be.lacerta.cq2.objects.Gem" %>
<%@page import="be.lacerta.cq2.objects.Enchant" %>
<%@page import="be.lacerta.cq2.objects.Orb" %>
<%@page import="be.lacerta.cq2.utils.CQ2Functions" %>
<%@ include file="include/init.jsp" %>
<%@ include file="include/header.jsp" %>
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>
<%@ include file="include/menu.jsp" %>

<div class="porlet" id="p-content">
<zod:Title title="Orb Comparer"/>
<zod:AutoCompleteJS type="creature"/>

Very experimental though, let me know how it works for you ;-)
<form action="?page=orb" method="POST">

<table>
<tr>
<td>
<font class="head" size="-1">Gems:</font><br/>
<textarea cols="40" rows="14" class="input" name="gems">
<%= user.getGems() %></textarea>
</td><td>
<font class="head" size="-1">Orbs:</font><br/>
<textarea cols="60" rows="14" class="input" name="orbs">
<%= user.getOrbs() %></textarea>
</td>
</tr>
</table>
<p><input class="input" type="checkbox" value="1" name="fullOnly" <% if ((Integer)request.getAttribute("orb_fullOnly")==1) { %>checked="checked"<% } %>/>Complete orbs only</p>
<p><input class="input" type="radio" value="highestpower" name="orderBy" <% if ((Boolean)request.getAttribute("orb_highestpower")) { %>checked="checked"<% } %>/>Order by power</p>
<p><input class="input" type="radio" value="highesteffect" name="orderBy" <% if ((Boolean)request.getAttribute("orb_highesteffect")) { %>checked="checked"<% } %>/>Order by nether effect</p>
<p><input class="input" type="radio" value="highestefficiency" name="orderBy" <% if ((Boolean)request.getAttribute("orb_highestefficiency")) { %>checked="checked"<% } %>/>Order by nether efficiency* and effect</p>
<p><input class="input" type="checkbox" value="1" name="effectpergem" <% if ((Integer)request.getAttribute("orb_effectpergem")==1) { %>checked="checked"<% } %>/>Show nether effect per gem</p>
<p><!-- input type="checkbox" value="yes" name="hideincomplete" />Order from High to Low power<br/-->
Calculate (up to)
<select class="input" name="uses">
<% for (int i=1; i<5; i++) { %>
    <option><%= i %></option>
<% } %>
    <option value="20">All</option>
</select>
Uses</p>
<p>Minimum signs: 
<select class="input" name="minSigns">
<% for (int i=1; i<=12; i++) { %>
    <option <% if ((""+i).equals(""+request.getAttribute("orb_minSigns"))) { %>selected="selected"<%}%>><%= i %></option>
<% } %>
</select>
</p>
<p>Minimum orb efficiency*: <input class="input" type="text" name="minEfficiency" size="5" value="<%= request.getAttribute("orb_minEfficiency") %>"/></p>
<p>
<%
	Integer baselevel = (Integer)request.getAttribute("orb_baselevel");
%>
Creature:
<zod:AutoInput type="creature" name="creature" id="creature" value="${requestScope.orb_creature}"/>
</p>
<p>
* Orb efficiency is calculated as (total emerald effect / ( power coming from bs gems + ( power coming from travel gems / 3)) where all bs gems in the orb are set to 
Ugly level &lt;base level&gt; gems, and all travel gems are set to Exquisite level &lt;base level&gt; gem.
</p>

<input type="submit" class="input" value="Parse">
</form>

<%

Boolean effectpergem = (Integer)request.getAttribute("orb_effectpergem")==1;
NumberFormat nf = NumberFormat.getInstance();
int grandTotalPower = 0;
int grandTotalPowerFull = 0;

if (request.getAttribute("orb_orblist") != null) {
    List<Orb> orbs = (List<Orb>)request.getAttribute("orb_orblist");

    for (Iterator<Orb> i = orbs.iterator(); i.hasNext(); ) {
        
        Orb orb = i.next();
        boolean full = true;
    %>
    <table style="padding-bottom: 10px">
    <tr><th colspan="18" style="text-align: left;"><%= orb  %></th></tr>
    <% if (effectpergem) { %>
	     <tr>
	     	<td></td>
	     	<td class="title">power</td>
	     	<td class="title" colspan="2">quartz</td>
	     	<td class="title" colspan="2">diamond</td>
	     	<td class="title" colspan="2">opal</td>
	     	<td class="title" colspan="2">sapphire</td>
	     	<td class="title" colspan="2">topaz</td>
	     	<td class="title" colspan="2">emerald</td>
	     	<td class="title" colspan="2">carnelian</td>
	     	<td class="title" colspan="2">ruby</td>
	     </tr>
	<% } %>
    <%
        int totalpower = 0;
    	int signs = orb.getGems().size();
        for (String sign : orb.getGemsigns()) {
            if (orb.getGem(sign)!=null) {
            	Gem gem = orb.getGem(sign);
	            int power=orb.getGem(sign).getPower();
	            totalpower += power; %>
	     <tr>
	     	<td width="200px">&nbsp;<%= gem.toCompactString() %></td>
	     	<td><%= power %></td>
	     	<td><% if (effectpergem) { %><%= nf.format(CQ2Functions.calcNetherEffect(baselevel,signs,gem,Enchant.QUARTZ))%><% } %></td>
	     	<td style="color: #555"><% if (effectpergem) { %><%= gem.getLevel()*Gem.COST_QUARTZ%><% } %></td>
	     	<td><% if (effectpergem) { %><%= nf.format(CQ2Functions.calcNetherEffect(baselevel,signs,gem,Enchant.DIAMOND))%><% } %></td>
	     	<td style="color: #555"><% if (effectpergem) { %><%= gem.getLevel()*Gem.COST_DIAMOND%><% } %></td>
	     	<td><% if (effectpergem) { %><%= nf.format(CQ2Functions.calcNetherEffect(baselevel,signs,gem,Enchant.OPAL))%><% } %></td>
	     	<td style="color: #555"><% if (effectpergem) { %><%= gem.getLevel()*Gem.COST_OPAL%><% } %></td>
	     	<td><% if (effectpergem) { %><%= nf.format(CQ2Functions.calcNetherEffect(baselevel,signs,gem,Enchant.SAPPHIRE))%><% } %></td>
	     	<td style="color: #555"><% if (effectpergem) { %><%= gem.getLevel()*Gem.COST_SAPPHIRE%><% } %></td>
	     	<td><% if (effectpergem) { %><%= nf.format(CQ2Functions.calcNetherEffect(baselevel,signs,gem,Enchant.TOPAZ))%><% } %></td>
	     	<td style="color: #555"><% if (effectpergem) { %><%= gem.getLevel()*Gem.COST_TOPAZ%><% } %></td>
	     	<td><% if (effectpergem) { %><%= nf.format(CQ2Functions.calcNetherEffect(baselevel,signs,gem,Enchant.EMERALD))%><% } %></td>
	     	<td style="color: #555"><% if (effectpergem) { %><%= gem.getLevel()*Gem.COST_EMERALD%><% } %></td>
	     	<td><% if (effectpergem) { %><%= nf.format(CQ2Functions.calcNetherEffect(baselevel,signs,gem,Enchant.CARNELIAN))%><% } %></td>
	     	<td style="color: #555"><% if (effectpergem) { %><%= gem.getLevel()*Gem.COST_CARNELIAN%><% } %></td>
	     	<td><% if (effectpergem) { %><%= nf.format(CQ2Functions.calcNetherEffect(baselevel,signs,gem,Enchant.RUBY))%><% } %></td>
	     	<td style="color: #555"><% if (effectpergem) { %><%= gem.getLevel()*Gem.COST_RUBY%><% } %></td>
	     </tr>
	     <% } else { %>
	     <tr>
	     	<td><%= sign %></td>
	     	<td></td>
	     	<td></td>
	     	<td></td>
	     	<td></td>
	     	<td></td>
	     	<td></td>
	     	<td></td>
	     	<td></td>
	     	<td></td>
	     	<td></td>
	     	<td></td>
	     	<td></td>
	     	<td></td>
	     	<td></td>
	     	<td></td>
	     	<td></td>
	     	<td></td>
	     </tr><%
	     full = false;
            }
        }
        
        if (full) grandTotalPowerFull += totalpower;
        grandTotalPower += totalpower;
    %>
        <tr><td colspan="18"><b>Power:</b> <%= totalpower %>.</td></tr>
        <tr><td colspan="18"><b>Res/Ruby:</b> <% if (totalpower > 0) { %><%= nf.format(orb.getCost(Gem.COST_RUBY)/(orb.getEffect(baselevel,Enchant.RUBY)*1000)) %><% } else { %>0 <% } %></td></tr>
        <tr><td colspan="18"><b>Efficiency:</b> <% if (totalpower > 0) { %><%= nf.format(CQ2Functions.calcNetherEfficiency(baselevel,orb)) %><% } else { %>0 <% } %></td></tr>
        <tr><td colspan="18"><b>Potential ith:</b> <% if (totalpower > 0) { %><%= nf.format(CQ2Functions.calcItherianPotential(baselevel,orb)) %><% } else { %>0 <% } %></td></tr>
        <tr><td colspan="18">
        <font class="alert">Topaz:</font> <%= nf.format(orb.getEffect(baselevel,Enchant.TOPAZ))%> (<%= orb.getCost(Gem.COST_TOPAZ) %>) | 
        <font class="alert">Quartz:</font> <%= nf.format(orb.getEffect(baselevel,Enchant.QUARTZ))%> (<%= orb.getCost(Gem.COST_QUARTZ) %>) | 
        <font class="alert">Opal:</font> <%= nf.format(orb.getEffect(baselevel,Enchant.OPAL))%> (<%= orb.getCost(Gem.COST_OPAL) %>) | 
        <font class="alert">Carnelian:</font> <%= nf.format(orb.getEffect(baselevel,Enchant.CARNELIAN))%> (<%= orb.getCost(Gem.COST_CARNELIAN) %>)
        </td></tr>
        <tr><td colspan="18">
        <font class="alert">Diamond:</font> <%= nf.format(orb.getEffect(baselevel,Enchant.DIAMOND))%> (<%= orb.getCost(Gem.COST_DIAMOND) %>) | 
        <font class="alert">Sapphire:</font> <%= nf.format(orb.getEffect(baselevel,Enchant.SAPPHIRE))%> (<%= orb.getCost(Gem.COST_SAPPHIRE) %>) | 
        <font class="alert">Emerald:</font> <%= nf.format(orb.getEffect(baselevel,Enchant.EMERALD))%> (<%= orb.getCost(Gem.COST_EMERALD) %>) | 
        <font class="alert">Ruby:</font> <%= nf.format(orb.getEffect(baselevel,Enchant.RUBY))%> (<%= orb.getCost(Gem.COST_RUBY) %>)
        </td></tr>
    </table>
        <%
    }
}

%><p><%
if (grandTotalPower>0) {
    %><font class="alert">Total Power: </font><%= grandTotalPower %><br/><%
}
if (grandTotalPowerFull>0) {
    %><font class="alert">Total Power from full orbs: </font><%= grandTotalPowerFull %><br/><%
}
%></p><%

if (request.getAttribute("orb_remaining") != null) {
	%><p><font class="alert">Remaining BS gems: </font></p>
	
	
	
	<table>
	<% if (effectpergem) { %>
		<tr>
			<td></td>
	     	<td class="title">power</td>
	     	<td class="title">quartz</td>
	     	<td class="title">diamond</td>
	     	<td class="title">opal</td>
	     	<td class="title">sapphire</td>
	     	<td class="title">topaz</td>
	     	<td class="title">emerald</td>
	     	<td class="title">carnelian</td>
	     	<td class="title">ruby</td>
		</tr>
	<% } %>
		<%
    List<Gem> gems = (List<Gem>)request.getAttribute("orb_remaining");
	int totalPower = 0;
    for (Iterator<Gem> i = gems.iterator(); i.hasNext(); ) {
        Gem gem = i.next();
        totalPower += gem.getPower();
        %>
        <tr>
        	<td><%= gem.toCompactString() %></td>
        	<td><%= gem.getPower() %></td>
	     	<td style="color: #555"><% if (effectpergem) { %><%= gem.getLevel()*Gem.COST_QUARTZ%><% } %></td>
	     	<td style="color: #555"><% if (effectpergem) { %><%= gem.getLevel()*Gem.COST_DIAMOND%><% } %></td>
	     	<td style="color: #555"><% if (effectpergem) { %><%= gem.getLevel()*Gem.COST_OPAL%><% } %></td>
	     	<td style="color: #555"><% if (effectpergem) { %><%= gem.getLevel()*Gem.COST_SAPPHIRE%><% } %></td>
	     	<td style="color: #555"><% if (effectpergem) { %><%= gem.getLevel()*Gem.COST_TOPAZ%><% } %></td>
	     	<td style="color: #555"><% if (effectpergem) { %><%= gem.getLevel()*Gem.COST_EMERALD%><% } %></td>
	     	<td style="color: #555"><% if (effectpergem) { %><%= gem.getLevel()*Gem.COST_CARNELIAN%><% } %></td>
	     	<td style="color: #555"><% if (effectpergem) { %><%= gem.getLevel()*Gem.COST_RUBY%><% } %></td>
	     </tr>  
	<% } %>
	</table>
	<p>Total power: <%= totalPower %></p>
	
<%
    
}

if (request.getAttribute("orb_needed") != null) {
	%><p><font class="alert">Gems needed: </font></p><%
    String[] gemtypes = {
	            "Regular",
	            "Common",
	            "Uncommon",
	            "Scarce",
	            "Rare",
	            "Exceptional",
	            "Singular",
	            "Travel"};
	int curmax = 0;
	
	for (String type : gemtypes) {
		if (type.equals("Travel")) {%><br/><% }
	%><b><%= type %>:</b> <%
	   if (request.getAttribute("orb_needed"+type)!= null) {
		   List<Gem> neededgems = (List<Gem>)request.getAttribute("orb_needed"+type);

		   int cnt = 0;
		   String prevsign = "";
		   String signs = "";
		   for (Gem g : neededgems) {
			   if (!g.getName().equals(prevsign)) {
				   if (cnt > 0)signs += cnt+" x "+prevsign+", ";
				   prevsign = g.getName();
				   cnt = 1;
			   } else {
				   cnt++;
			   }
		   }
		   if (cnt > 0)signs += cnt+" x "+prevsign+", ";
		   if (signs.length()>2) {
		      %><%= signs.substring(0,signs.length()-2) %><%
		   }
		   %><br/><%
	   }
    }
}
%>
</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
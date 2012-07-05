<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.sim.hbn.User"  %>
<%@page import="be.lacerta.cq2.sim.hbn.News" %>
<%@page import="be.lacerta.cq2.sim.hbn.Topic" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.Set" %>
<%@page import="java.util.List" %>
<%@page import="be.lacerta.cq2.utils.SessionListener" %>
<%@page import="java.text.SimpleDateFormat" %>
<% String display = "none"; %>
    <div class="portlet" id="p-main">

<table width="172" border="0" cellspacing="0" cellpadding="0">
<tr>
<td width="20"><img src="<%= imagepack %>images/blue/c_lefttopcorner.gif" width="20" height="15"></td>
<td width="132" background="<%= imagepack %>images/blue/c_top.gif">&nbsp;</td>
<td width="20"><img src="<%= imagepack %>images/blue/c_righttopcorner.gif" width="20" height="15"></td>
</tr>
<tr>
<td width="20" background="<%= imagepack %>images/blue/c_left.gif">&nbsp;</td>
<td width="132">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
        <td class="title">Creature DB</td>
        </tr>
        <tr>
        <td class="link" width="100%">
        <div id='critdb' name='critdb' style='display:block;align:center'>
        
        <a href="#" onclick="hidetoggle('Air'); return false;" onmouseover="window.status='Show Air Races!'; return true;" onmouseout="window.status=''; return true;" class="menu">Air</a><br/>
        <% if ((new String("Air")).equals(request.getParameter("class"))) display = "block"; else display = "none"; %>
        <span id='Air' style='display: <%= display %>'>
        &nbsp;&nbsp;<a href="?page=critdb&class=Air&race=1" class="menu">Air Remnant</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Air&race=2" class="menu">Angel</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Air&race=3" class="menu">Balrog</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Air&race=4" class="menu">Bat</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Air&race=5" class="menu">Dragon</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Air&race=6" class="menu">Gargoyle</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Air&race=7" class="menu">Pit Wraith</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Air&race=8" class="menu">Rift Dancer</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Air&race=9" class="menu">Spirit</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Air&race=10" class="menu">Storm</a><br/>
        </span>
        
        <a href="#" onclick="hidetoggle('Death'); return false;" onmouseover="window.status='Show Death Races!'; return true;" onmouseout="window.status=''; return true;" class="menu">Death</a><br/>
        <% if ((new String("Death")).equals(request.getParameter("class"))) display = "block"; else display = "none"; %>
        <span id='Death' style='display: <%= display %>'>
        &nbsp;&nbsp;<a href="?page=critdb&class=Death&race=11" class="menu">Apocalypse</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Death&race=12" class="menu">Banshee</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Death&race=13" class="menu">Carnage</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Death&race=14" class="menu">Death Remnant</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Death&race=15" class="menu">Devil</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Death&race=16" class="menu">Diabolic Horde</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Death&race=17" class="menu">Doomguard</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Death&race=18" class="menu">Lich</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Death&race=19" class="menu">Pit Worm</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Death&race=20" class="menu">Reaper</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Death&race=21" class="menu">Undead</a><br/>
        </span>
        
        <a href="#" onclick="hidetoggle('Earth'); return false;" onmouseover="window.status='Show Earth Races!'; return true;" onmouseout="window.status=''; return true;" class="menu">Earth</a><br/>
        <% if ((new String("Earth")).equals(request.getParameter("class"))) display = "block"; else display = "none"; %>
        <span id='Earth' style='display: <%= display %>'>
        &nbsp;&nbsp;<a href="?page=critdb&class=Earth&race=22" class="menu">Earth Remnant</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Earth&race=23" class="menu">Fiend</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Earth&race=24" class="menu">Giant</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Earth&race=25" class="menu">Golem</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Earth&race=26" class="menu">Imler</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Earth&race=27" class="menu">Imling</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Earth&race=28" class="menu">Rat</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Earth&race=29" class="menu">Smith</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Earth&race=30" class="menu">Tempest</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Earth&race=31" class="menu">Tremor</a><br/>
        </span>
        
        <a href="#" onclick="hidetoggle('Forest'); return false;" onmouseover="window.status='Show Forest Races!'; return true;" onmouseout="window.status=''; return true;" class="menu">Forest</a><br/>
        <% if ((new String("Forest")).equals(request.getParameter("class"))) display = "block"; else display = "none"; %>
        <span id='Forest' style='display: <%= display %>'>
        &nbsp;&nbsp;<a href="?page=critdb&class=Forest&race=32" class="menu">Cleric</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Forest&race=33" class="menu">Ent</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Forest&race=34" class="menu">Forest Remnant</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Forest&race=35" class="menu">Hound</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Forest&race=36" class="menu">Hunter</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Forest&race=37" class="menu">Imp</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Forest&race=38" class="menu">Monk</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Forest&race=39" class="menu">Priest</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Forest&race=40" class="menu">Seraph</a><br/>
        &nbsp;&nbsp;<a href="?page=critdb&class=Forest&race=41" class="menu">Wolf</a><br/>
        </span>
        
        <br/>
        <a href="?page=critdb&amp;action=critlist"  class="menu">Creature list</a><br/>
        <a href="?page=critdb&amp;action=itemlist"  class="menu">Item list</a><br/>
        <a href="?page=critdb&amp;action=search"  class="menu">Search</a><br/>
        <a href="?page=critdb&amp;action=critcalc"  class="menu">Creature Calcs</a><br/>
        <a href="?page=critdb&amp;action=costcalc"  class="menu">Cost Calc</a>
        </div>
        </td>
        </tr>
        
        <tr><td class="title"><br/>Communication</td></tr>
        
        <tr>
        <td class="link" width="100%">
        <% 
           HashMap<Integer,Integer> unseenHash = Topic.getUnseenTopics(user);
           Integer unseen;
        %>
        <% unseen =unseenHash.get(new Integer(1)); if (unseen == null) unseen = 0; %>
        <a href="?page=pub&location=1" title="Visit the pub" accesskey="p" class="<% if (unseen>0) {%>highlight<% } else { %>menu<% } %>">Pub (<%= unseen %>)</a><br/>
        <% unseen =unseenHash.get(new Integer(3)); if (unseen == null) unseen = 0; %>
        <a href="?page=pub&location=3" title="Knowledge" accesskey="k" class="<% if (unseen>0) {%>highlight<% } else { %>menu<% } %>">Knowledge (<%= unseen %>)</a><br/>
        <% unseen =unseenHash.get(new Integer(2)); if (unseen == null) unseen = 0; %>
        <a href="?page=pub&location=2" title="View/add suggestions" class="<% if (unseen>0) {%>highlight<% } else { %>menu<% } %>">Suggestions (<%= unseen %>)</a><br/>
        <% unseen =unseenHash.get(new Integer(4)); if (unseen == null) unseen = 0; %>
        <a href="?page=pub&location=4" title="Votes" class="<% if (unseen>0) {%>highlight<% } else { %>menu<% } %>">Votes (<%= unseen %>)</a><br/>
        <% unseen =unseenHash.get(new Integer(5)); if (unseen == null) unseen = 0; %>
        <a href="?page=pub&location=5" title="Marketplace" class="<% if (unseen>0) {%>highlight<% } else { %>menu<% } %>">Marketplace (<%= unseen %>)</a><br/>
        <% unseen =unseenHash.get(new Integer(6)); if (unseen == null) unseen = 0; %>
        <a href="?page=pub&location=6" title="War reports" class="<% if (unseen>0) {%>highlight<% } else { %>menu<% } %>">War reports (<%= unseen %>)</a><br/>
		<c:if test="${zod:hasAccess(user,Constants.RIGHTS_ZODONLY)}">
        <% unseen =unseenHash.get(new Integer(7)); if (unseen == null) unseen = 0; %>
        <a href="?page=pub&location=7" title="ZoD Only" class="<% if (unseen>0) {%>highlight<% } else { %>menu<% } %>">ZoD Only (<%= unseen %>)</a><br/>
		</c:if>
		<c:if test="${zod:hasAccess(user,Constants.RIGHTS_TEK)}">
        <% unseen =unseenHash.get(new Integer(10)); if (unseen == null) unseen = 0; %>
        <a href="?page=pub&location=10" title="Council pub" class="<% if (unseen>0) {%>highlight<% } else { %>menu<% } %>">Tek's pub (<%= unseen %>)</a><br/>
		</c:if>
		<c:if test="${zod:hasAccess(user,Constants.RIGHTS_SITEADMIN)}">
        <% unseen =unseenHash.get(new Integer(8)); if (unseen == null) unseen = 0; %>
        <a href="?page=pub&location=8" title="Amministratori" class="<% if (unseen>0) {%>highlight<% } else { %>menu<% } %>">Amministratori (<%= unseen %>)</a><br/>
		</c:if>
		<% unseen =unseenHash.get(new Integer(9)); if (unseen == null) unseen = 0; %>
        <a href="?page=pub&location=9" title="Ni" class="<% if (unseen>0) {%>highlight<% } else { %>menu<% } %>">Ni (<%= unseen %>)</a><br/>
        </td>
        </tr>

        <tr><td class="title"><br/>Rodia</td></tr>
        
        <tr>
        <td class="link" width="100%">
        <a href="?page=reveal" title="Reveal DB" accesskey="r" class="menu">Reveals</a><br/>
        <a href="?page=kingdom" title="Kingdom" class="menu">Kingdom</a><br/>
        <a href="?page=curse" title="Surathli's Anger" accesskey="s" class="menu">Surathli's Anger</a><br/>
        <a href="?page=curse&action=creature" title="Curses" accesskey="c" class="menu">Creature Curses</a><br/>
        <a href="?page=curse&action=poolshards" title="Curses" accesskey="p" class="menu">Pool shards</a><br/>
        <a href="?page=skills" title="Skills" accesskey="s" class="menu">Skills</a><br/>
        <a href="?page=raid" title="Raids" class="menu">Raids</a><br/>
        </td>
        </tr>
        
        <tr><td class="title"><br/>Tools</td></tr>
        
        <tr>
        <td class="link" width="100%">
        <a href="?page=gem" title="Gems" accesskey="g" class="menu">Gem</a><br/>
        <a href="?page=orb" title="Orbs" accesskey="o" class="menu">Orbs</a><br/>
        <a href="?page=gemtracker" title="Gem Tracker" class="menu">Gem tracker</a><br/>
        <a href="?page=rescheck" title="ResCheck" accesskey="r" class="menu">ResCheck</a><br/>
        <a href="?page=travelcalc" title="TravelCalc" accesskey="t" class="menu">TravelCalc</a><br/>
        <a href="?page=battlecalc" title="BattleCalc" accesskey="b" class="menu">BattleCalc</a><br/>
        <a href="?page=powerspell" title="Powerspell" accesskey="p" class="menu">Powerspells</a><br/>
        <a href="?page=support" title="Support" accesskey="s" class="menu"><b>Support</b></a><br/>
        <a href="?page=shard" title="Shard support" class="menu">Shard support</a><br/>
        <a href="?page=defenseparser" title="DefenseParser" class="menu">Defense Parser</a><br/>
        </td>
        </tr>
           
        </table>

</td>
<td width="21" background="<%= imagepack %>images/blue/c_right.gif">&nbsp;</td>
</tr>
<tr>
<td width="20"><img src="<%= imagepack %>images/blue/c_leftbottomcorner.gif" width="20" height="15"></td>
<td width="132" background="<%= imagepack %>images/blue/c_bottom.gif">&nbsp;</td>
<td width="20"><img src="<%= imagepack %>images/blue/c_rightbottomcorner.gif" width="20" height="15"></td>
</tr>
</table>


<table width="172" border="0" cellspacing="0" cellpadding="0">
<tr>
<td width="20"><img src="<%= imagepack %>images/blue/c_lefttopcorner.gif" width="20" height="15"></td>
<td width="132" background="<%= imagepack %>images/blue/c_top.gif">&nbsp;</td>
<td width="20"><img src="<%= imagepack %>images/blue/c_righttopcorner.gif" width="20" height="15"></td>
</tr>
<tr>
<td width="20" background="<%= imagepack %>images/blue/c_left.gif">&nbsp;</td>
<td width="132">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr><td class="title">Online users (<%= SessionListener.getOnlineUsers().size() %>)</td></tr>
        
        <tr>
        <td class="link" width="100%">
        <%      
        for (User u : SessionListener.getOnlineUsers()) {
            %><a href="?page=info&id=<%= u.getId() %>" class="menu"><%= u.getUsername() %></a><br/><%
        }
        %>
        </td>
        </tr>
        
        <tr><td class="title">Online travelers (<%= SessionListener.getOnlineTravelers().size() %>)</td></tr>
        
        <tr>
        <td class="link" width="100%">
        <%
        for (User u : SessionListener.getOnlineTravelers()) {
            %><a href="?page=info&id=<%= u.getId() %>" class="menu"><%= u.getUsername() %></a><br/><%
        }
        %>
        </td>
        </tr>
        
        </table>

</td>
<td width="21" background="<%= imagepack %>images/blue/c_right.gif">&nbsp;</td>
</tr>
<tr>
<td width="20"><img src="<%= imagepack %>images/blue/c_leftbottomcorner.gif" width="20" height="15"></td>
<td width="132" background="<%= imagepack %>images/blue/c_bottom.gif">&nbsp;</td>
<td width="20"><img src="<%= imagepack %>images/blue/c_rightbottomcorner.gif" width="20" height="15"></td>
</tr>
</table>

<table width="172" border="0" cellspacing="0" cellpadding="0">
<tr>
<td width="20"><img src="<%= imagepack %>images/blue/c_lefttopcorner.gif" width="20" height="15"></td>
<td width="132" background="<%= imagepack %>images/blue/c_top.gif">&nbsp;</td>
<td width="20"><img src="<%= imagepack %>images/blue/c_righttopcorner.gif" width="20" height="15"></td>
</tr>
<tr>
<td width="20" background="<%= imagepack %>images/blue/c_left.gif">&nbsp;</td>
<td width="132">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr>
        <td class="link" width="100%">
        <a href="?page=members" class="menu">Members</a><br/>
        <c:if test="${zod:hasAccess(user,Constants.RIGHTS_USERADMIN)}">
        <a href="?page=memberadd" class="menu">Add a Member</a><br/>
        </c:if>
        <c:if test="${zod:hasAccess(user,Constants.RIGHTS_SITEADMIN)}">
        <a href="?page=announcement&action=new" class="menu">Add Announcement</a><br/>
        </c:if>
        <a href="?page=options" class="menu">User Details</a><br/>
        <a href="?page=stats" class="menu">Global Stats</a><br/>
        <a href="?action=logout" class="menu">Log Out</a><br/>
        </td>
        </tr>
        
        </table>

</td>
<td width="21" background="<%= imagepack %>images/blue/c_right.gif">&nbsp;</td>
</tr>
<tr>
<td width="20"><img src="<%= imagepack %>images/blue/c_leftbottomcorner.gif" width="20" height="15"></td>
<td width="132" background="<%= imagepack %>images/blue/c_bottom.gif">&nbsp;</td>
<td width="20"><img src="<%= imagepack %>images/blue/c_rightbottomcorner.gif" width="20" height="15"></td>
</tr>
</table>
</div>


<div class="porlet" id="p-news">
<table width="100%">
<tr><td>
	<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" valign="top">
	    <tr>
	        <td width="20"><img
	            src="<%= imagepack %>images/blue/c_lefttopcorner.gif"
	            width="20" height="15"></td>
	        <td width="*%" height="15"
	            background="<%= imagepack %>images/blue/c_top.gif">&nbsp;</td>
	
	        <td width="21"><img
	            src="<%= imagepack %>images/blue/c_righttopcorner.gif"
	            width="20" height="15"></td>
	    </tr>
	    <tr>
	        <td width="20"
	            background="<%= imagepack %>images/blue/c_left.gif">&nbsp;</td>
	        <td valign="top">
	        <table width="100%" cellspacing="0" cellpadding="0">
	            <tr>
	                <td class="title" colspan="5">News</td>
	            </tr>
	            <%
	            SimpleDateFormat menu_sdf = new SimpleDateFormat("yyyy-MM-dd");
	            int news_max=5;
	            int news_offset=0;
	            
	            if (request.getParameter("page") == null) news_max=20;
	            
	            if (request.getParameter("news_offset") != null) {
	            	try {
	            		news_offset+=Integer.parseInt(request.getParameter("news_offset"));
	            	} catch (NumberFormatException nfe) {}
	            }
	            
	            for (News news : News.getNews(news_max,news_offset)) {
	            %>
	            <tr>
	            	<td width="5px">
	            	<c:if test="${zod:hasAccess(user,Constants.RIGHTS_SITEADMIN)}">
	                <a href="?page=news&action=delete&nid=<%= news.getNewsID() %>">X</a>&nbsp;
	                </c:if>
	                </td>
	                <td width="75px"><%= menu_sdf.format(news.getTime()) %></td>
	                <td>
	                <% if (news.getNewsfor() != null) { %><b><%= news.getNewsfor() %>:</b> <%}%>
	                <a href="<%= news.getDirectlink() %>">
	                <%= news.getTitle() %></a></td>
	                <td width="75px"><%=news.getUser().getUsername()%></td>
	            </tr>
	            <% 
	            }
	            
	            if (request.getParameter("page") == null) {
	            %>
		            <tr>
		            	<td colspan="3">
			            <% if (news_offset > 0) { %><a href="?news_offset=<%= news_offset-news_max %>">previous page</a><% } %>
			            </td>
			            <td style="text-align: right">
			            <a href="?news_offset=<%= news_offset+news_max %>">next page</a>
			            </td>
		            </tr>
	            <% } %>
	        </table>
	        </td>
	
	        <td width="21"
	            background="<%= imagepack %>images/blue/c_right.gif">&nbsp;</td>
	    </tr>
	    <tr>
	        <td width="20"><img
	            src="<%= imagepack %>images/blue/c_leftbottomcorner.gif"
	            width="20" height="15"></td>
	        <td width="*%" height="15"
	            background="<%= imagepack %>images/blue/c_bottom.gif">&nbsp;</td>
	
	        <td width="21"><img
	            src="<%= imagepack %>images/blue/c_rightbottomcorner.gif"
	            width="20" height="15"></td>
	    </tr>
	</table>
</td><td width="240px" style="vertical-align: top;">

	<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" valign="top">
	    <tr>
	        <td width="20"><img
	            src="<%= imagepack %>images/blue/c_lefttopcorner.gif"
	            width="20" height="15"></td>
	        <td width="*%" height="15"
	            background="<%= imagepack %>images/blue/c_top.gif">&nbsp;</td>
	
	        <td width="21"><img
	            src="<%= imagepack %>images/blue/c_righttopcorner.gif"
	            width="20" height="15"></td>
	    </tr>
	    <tr>
	        <td width="20"
	            background="<%= imagepack %>images/blue/c_left.gif">&nbsp;</td>
	        <td valign="top">
				<% 	
				boolean birthday=false;
				for(User u : User.getUserList()) {
					if(u.hasBirthdayToday()) {
						birthday=true;
						%><b><%= u.getUsername() %> turns <%= u.getAge() %> today!</b><br/><%
					}
				}
				if(birthday) {
					%><br/><%
				}
				%>
	        	Add:<br/>
	        	<div id="topaddlinks">
	        	<a href="addforms.jsp?form=addraid" rel="facebox">Raid</a> |
				<a href="addforms.jsp?form=addreveal" rel="facebox">Reveal</a> |
				<a href="addforms.jsp?form=addcurses" rel="facebox">Curses</a>
				</div>
	        	<div id="topaddlinks">
				<a href="addforms.jsp?form=updatexp" rel="facebox">Your XP</a> |
				<a href="addforms.jsp?form=parsekd" rel="facebox">Kingdom</a>
				</div>
	        </td>
	        <td width="21"
	            background="<%= imagepack %>images/blue/c_right.gif">&nbsp;</td>
	    </tr>
	    <tr>
	        <td width="20"><img
	            src="<%= imagepack %>images/blue/c_leftbottomcorner.gif"
	            width="20" height="15"></td>
	        <td width="*%" height="15"
	            background="<%= imagepack %>images/blue/c_bottom.gif">&nbsp;</td>
	
	        <td width="21"><img
	            src="<%= imagepack %>images/blue/c_rightbottomcorner.gif"
	            width="20" height="15"></td>
	    </tr>
	</table>
</td></tr>
</table>
</div>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.sim.hbn.Race" %>
<%@page import="be.lacerta.cq2.sim.hbn.Creature" %>
<%@page import="be.lacerta.cq2.sim.hbn.Item" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@ include file="include/init.jsp" %>
<%@ include file="include/header.jsp" %>
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>
<%@ include file="include/menu.jsp" %>

<%@page import="java.util.List;"%>
<div class="porlet" id="p-content">
<%
	Race race = null;
List<Creature> creatures = null;
if (request.getAttribute("critdb_creatures") != null) {
    creatures = (List<Creature>)request.getAttribute("critdb_creatures");
    if (creatures.size() > 0) {
        boolean  onerace = true;
        int raceid = creatures.get(0).getRace().getId();
        for (Iterator<Creature> it = creatures.iterator(); it.hasNext(); ) {
            if (it.next().getRace().getId() != raceid) {
                onerace = false;
                break;
            }
        }
        if (onerace) race = (Race)Race.get(Race.class,raceid);
    }
}
%>

<table width="100%">
    <tr>
        <td class="titleline" width="100%">
        <font class="head" size="-1">Creature and Item Database
        </font>
        </td>
    </tr>
</table>

<% if (race != null) { %>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr height="15">
            <td colspan="2"></td>
            </tr>
            <tr>
            <td colspan="2" class="titleline">
            <font class="head">
            Race: <%= race.getName()+" ("+race.getCreatureClass()+")" %>
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
            
                    <table width="100%" cellspacing="0" cellpadding="1">
                    <tr>
                    <td align="left" width="30%" valign="top">
                    Forest Defense: <%=race.getFD()%>%<br>
                    Death Defense: <%=race.getDD()%>%
                    </td>
                    <td align="left" width="40%" valign="top">
                    Air Defense: <%=race.getAD()%>%<br>
                    Earth Defense: <%=race.getED()%>%
                    </td>
                    </tr>
                    </table>
                    
        </td>
        </tr>
        </table>
<% } %>
<% if (creatures != null) { %>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <% for (Iterator<Creature> it = creatures.iterator(); it.hasNext(); ) {
              Creature crit = it.next();
    
    %>
              <tr><td colspan="4" class="titleline">&nbsp;</td></tr>
              <tr><td colspan="4">&nbsp;</td></tr>
              <tr>
                <td class="head" colspan="2"><%= crit.getName() %></td>
                <td colspan="2">
                <a href="javascript:void(0);"
                onmouseover="return overlib('<span class=\'button\'>&nbsp;Level 20:&nbsp;<%= be.lacerta.cq2.battlecalc.objects.Creature.convertStrength(crit.getDamage(),1,20) %>&nbsp;/&nbsp;<%= be.lacerta.cq2.battlecalc.objects.Creature.convertStrength(crit.getHealth(),1,20) %>&nbsp;</span>', FULLHTML, ABOVE, CENTER);"
                onmouseout="return nd();"
                onClick="return popitup('/critCalc.php?d=<%= crit.getDamage() %>&h=<%= crit.getHealth() %>&crit=<%= crit.getName() %>')"
                >Creature Calc</a>
                </td>
              </tr>
              <tr>
                <td>Damage: <%= crit.getDamage() %></td>
                <td>Creature Skill: <%= crit.getSkill() %></td>
                <td>Skill required to use: <%= (int)Math.floor(crit.getSkill()/2) %></td>
                <td>Brimstone: <%= crit.getBrim() %></td>
              </tr>
              <tr>
                <td>Health: <%= crit.getHealth() %></td>
                <td></td>
                <td>Level required to use: <%= crit.getL2u() %></td>
                <td>Crystal: <%= crit.getCrys() %></td>
              </tr>
    <% } %>
    </table>
<% } %>
<% if (race != null) { %>
    <br/><br/>
    <font class="head">Items for this race:</font>
    <table width="100%">
    <% 
    Iterator<Item> it = Item.getItems(race.getId()).iterator();
    while (it.hasNext()) {
        Item item = it.next();
    %>
              <tr>

                <td class="head"><%= item.getName() %></td>
                <td>
                <% if (item.getWorkshop() > 0) { %>
                    Workshop <%= item.getWorkshop() %>,
                <% } %>
                <%= item.getBrim() %> brimstone,
                <%= item.getBrim() %> crystal,
                <%= item.getEss() %>essence.</td>
              </tr>
              <tr>
                <td colspan="2"><%= item.getDescr() %><br/>&nbsp;</td>
              </tr>
    <%
    }
    %>
    </table>
<% } %>
<% if (("search").equals(request.getAttribute("action"))) { %>

    <br/><br/>
    <font class="head">Search:</font>
        <form action="?page=critdb" method="POST">
        <input type="hidden" name="action" value="search"/>
        <table class="contentpaneopen">

        <tr><td class="addinput">Class:</td>
        <td><select name="class" class="input">
        <option></option>
                <option>Air</option>

                <option>Death</option>
                <option>Earth</option>
                <option>Forest</option>
        </select></td></tr>
        <tr><td class="addinput">Race:</td>
        <td><select name="race" class="input">
        <option value=""></option>

        <option value="1">Air Remnant</option><option value="2">Angel</option><option value="3">Balrog</option><option value="4">Bat</option><option value="5">Dragon</option><option value="6">Gargoyle</option><option value="7">Pit Wraith</option><option value="8">Rift Dancer</option><option value="9">Spirit</option><option value="10">Storm</option><option value="11">Apocalypse</option><option value="12">Banshee</option><option value="13">Carnage</option><option value="14">Death Remnant</option><option value="15">Devil</option><option value="16">Diabolic Horde</option><option value="17">Doomguard</option><option value="18">Lich</option><option value="19">Pit Worm</option><option value="20">Reaper</option><option value="21">Undead</option><option value="22">Earth Remnant</option><option value="23">Fiend</option><option value="24">Giant</option><option value="25">Golem</option><option value="26">Imler</option><option value="27">Imling</option><option value="28">Rat</option><option value="29">Smith</option><option value="30">Tempest</option><option value="31">Tremor</option><option value="32">Cleric</option><option value="33">Ent</option><option value="34">Forest Remnant</option><option value="35">Hound</option><option value="36">Hunter</option><option value="37">Imp</option><option value="38">Monk</option><option value="39">Priest</option><option value="40">Seraph</option><option value="41">Wolf</option>        </select></td></tr>

        <tr><td>Name:</td><td><input type="text" name="name" value="" size="20" class="input"/></td></tr>
        <tr><td>Type:</td><td>
        <select name="type" class="input">
        <option value=""></option>
        <option value="1">Normal</option>
        <option value="2">Power</option>
        <option value="3">Challenge</option>

        <option value="4">Itherian</option>
        </select></td></tr>
        <tr><td>Damage:</td><td>
        (Min)<input type="text" name="mindmg" value="" size="10" class="input"/>&nbsp;
        (Max)<input type="text" name="maxdmg" value="" size="10" class="input"/></td></tr>
        <tr><td>Health:</td><td>

        (Min)<input type="text" name="minhlt" value="" size="10" class="input"/>&nbsp;
        (Max)<input type="text" name="maxhlt" value="" size="10" class="input"/></td></tr>
        <tr><td>Skill:</td><td>
        (Min)<input type="text" name="minskl" value="" size="10" class="input"/>&nbsp;
        (Max)<input type="text" name="maxskl" value="" size="10" class="input"/></td></tr>
        <tr><td>Level to use:</td><td>

        (Min)<input type="text" name="minlvl" value="" size="10" class="input"/>&nbsp;
        (Max)<input type="text" name="maxlvl" value="" size="10" class="input"/></td></tr>
        <tr><td></td><td><input type="submit" value="Search" class="input"/></td></tr>
        </table></form>

<% } %>
</div>


</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
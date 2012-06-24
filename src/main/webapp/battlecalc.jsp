<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.sim.hbn.User"  %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.List" %>
<%@page import="be.lacerta.cq2.sim.hbn.Creature" %>
<%@page import="be.lacerta.cq2.objects.Item" %>
<%@page import="be.lacerta.cq2.objects.Enchant" %>
<%@page import="be.lacerta.cq2.utils.StringUtils" %>
<%@ include file="include/init.jsp" %>
<%@ include file="include/header.jsp" %>
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>
<%@ include file="include/menu.jsp" %>

<div class="porlet" id="p-content">

<table width="100%">
    <tr>
        <td class="titleline" width="100%"><font class="head" size="-1">Battle calculator</font></td>
    </tr>
</table>
<p></p>

<script type="text/javascript" src="<%= context %>/dwr/interface/CreatureFinder.js"></script>
<script type="text/javascript" src="<%= context %>/dwr/engine.js"></script>
<script language="JavaScript">

function handleParseInput(crits) {
	var sides = new Array();
	sides[0] = "attacker";
	sides[1] = "defender";

	for (c=0;c<2;c++) {
	
		var creature = crits[c];
		var opponent = crits[0];
		if (c==0) opponent = crits[1];
		var side = sides[c];

		if ( creature != null && (
				creature.name != null ||
			 	document.getElementById("selectbutton"+side).disabled || 
			 	document.getElementById("parsebutton").disabled)) {

		    try { if (creature.name != null) document.getElementById(side+".name").value = creature.name; } catch(err) {}
		    try { document.getElementById(side+".level").value = creature.level; } catch(err) {}
			try { document.getElementById(side+".damage").value = creature.damage; } catch(err) {}
			try { document.getElementById(side+".ithdamage").value = creature.ith.damage; } catch(err) {}
			try { document.getElementById(side+".health").value = creature.health; } catch(err) {}
			try { document.getElementById(side+".ithhealth").value = creature.ith.health; } catch(err) {}
			
			try { 
				if (opponent.creatureClass.toLowerCase() == 'death') {
				 document.getElementById(side+".def").value = creature.deathDef;
				} else if (opponent.creatureClass.toLowerCase() == 'forest') {
				 document.getElementById(side+".def").value = creature.forestDef;
				} else if (opponent.creatureClass.toLowerCase() == 'air') {
				 document.getElementById(side+".def").value = creature.airDef;
				} else if (opponent.creatureClass.toLowerCase() == 'earth') {
				 document.getElementById(side+".def").value = creature.earthDef;
				} else {
				 document.getElementById(side+".def").value = "0";
				}
			} catch(err) {}
			 
			try { document.getElementById(side+".ithdef").value = creature.ith.defence; } catch(err) {}
			
			// item
			try {
				var oldRace = document.getElementById(side+".race");

				if (oldRace.value != creature.race.toLowerCase()) {
					var items = document.getElementById(side+".item");	
					items[0].selected = "1";

					var found = false;
					for (i=0; i <= items.length - 1; i++) {
					  if (items[i].value.toLowerCase() == "-----"+creature.race.toLowerCase()+"-----") {
					  	found = true;
					  } else if (found && items[i].value.toLowerCase() == "") {
						  	items[i].selected = "1";
							break;
					  }
					}
					
				}

				if (creature.item != null && creature.item.name != "") {
					for (i=0; i <= items.length - 1; i++) {
					  if (items[i].value == creature.item.name) {
					  	items[i].selected = "1";
					  	break;
					  }
					}
				}

				oldRace.value = creature.race.toLowerCase();
			} catch(err) { }
			
			try {
				var classes = document.getElementById(side+".class");
				classes[0].selected = "1";
				for (i=0; i <= classes.length - 1; i++) {
				  if (classes[i].value.toLowerCase() == creature.creatureClass.toLowerCase()) {
				  	classes[i].selected = "1";
				  	break;
				  }
				}
			} catch(err) {}	
			
			// enchant
			document.getElementById(side+".itemenchant")[0].selected = "1";
			try {document.getElementById(side+".itemenchant")[3].selected = creature.item.majorBerserk; } catch(err) {}
			
			// curse
			document.getElementById(side+".curse")[0].selected = "1";
			try {document.getElementById(side+".curse")[1].selected = creature.suffocated; } catch(err) {}
			try {document.getElementById(side+".curse")[2].selected = creature.metad; } catch(err) {}
	
		}
	}

	
	document.getElementById("parsebuttonenabled").style.display = "block"; 
	document.getElementById("parsebuttondisabled").style.display = "none"; 
	document.getElementById("parsebutton").disabled=false;
	
	document.getElementById("selectbuttonattackerenabled").style.display = "block"; 
	document.getElementById("selectbuttonattackerdisabled").style.display = "none"; 
	document.getElementById("selectbuttonattacker").disabled=false;	
	
	document.getElementById("selectbuttondefenderenabled").style.display = "block"; 
	document.getElementById("selectbuttondefenderdisabled").style.display = "none"; 
	document.getElementById("selectbuttondefender").disabled=false;
}

function parse()
{
    document.getElementById("parsebutton").disabled=true;
	document.getElementById("parsebuttonenabled").style.display = "none"; 
	document.getElementById("parsebuttondisabled").style.display = "block"; 
    var attackerField = document.getElementById("attackerField");
    var defenderField = document.getElementById("defenderField");
    CreatureFinder.parseInput(attackerField.value,defenderField.value,handleParseInput);
}


function doSelect(side)
{
    document.getElementById("selectbutton"+side).disabled=true;
	document.getElementById("selectbutton"+side+"enabled").style.display = "none"; 
	document.getElementById("selectbutton"+side+"disabled").style.display = "block"; 
    var attackerFieldName = document.getElementById("attacker.name");
    var attackerFieldLevel = document.getElementById("attacker.level");
    var defenderFieldName = document.getElementById("defender.name");
    var defenderFieldLevel = document.getElementById("defender.level");
    CreatureFinder.selectInput(attackerFieldName.value,attackerFieldLevel.value,defenderFieldName.value,defenderFieldLevel.value,handleParseInput);
}

function checkDependencies() {
	var sides = new Array();
	sides[0] = "attacker";
	sides[1] = "defender";
	
	var sameneeded = new Array();
	sameneeded[0] = "Rune";
	sameneeded[1] = "Diabolic";
	sameneeded[2] = "Book of Light";
	sameneeded[3] = "Servant";
	sameneeded[4] = "Director";
	sameneeded[5] = "Infusion";
	
	var diffneeded = new Array();
	diffneeded[0] = "Rune";

	var good = true;
	
	sides.each(function(side, i){
		var item = document.getElementById(side+".item").value;
		var same = document.getElementById(side+".same").value;
		var diff = document.getElementById(side+".diff").value;

		sameneeded.each(function(sameitem, c){
			if (item.match(sameitem)) {
				if (same == "") {
					alert("Please fill in the number of same creatures for the "+side+".");
					good = false;
				}
				throw $break;
			}
		})
		diffneeded.each(function(diffitem, c){
			if (item.match(diffitem)) {
				if (diff == "") {
					alert("Please fill in the number of different creatures for the "+side+".");
					good = false;
				}
				throw $break;
			}
		})
	})
	return good;
}

</script>

<zod:AutoCompleteJS type="creature"/>

<a href="?page=wavecalc">Wave calculator</a> | download <a href="http://cq2.lacerta.be/battlecalc.jnlp">offline calculator</a>

<form method="post" action="?page=costcalc" id="frmAmulets" onSubmit="return 1">
	<font class="head">Full Creature Parser</font>
	<p style="padding-left: 10px">
	<font class="head">Attacker</font><br/>
	<textarea class="input" id="attackerField" name="attackerField" style="height: 30px; overflow: hidden; white-space: nowrap; margin-bottom: 5px; padding: 1px"></textarea><br/>
	<font class="head">Defender</font><br/>
	<textarea class="input" id="defenderField" name="defenderField" style="height: 30px; overflow: hidden; white-space: nowrap; margin-bottom: 5px; padding: 1px"></textarea><br/>
	<span id="parsebuttonenabled"><input type="button" value="Parse" id="parsebutton" class="input" style="vertical-align: middle;" onclick="parse();" /></span>
	<span id="parsebuttondisabled" style="display: none"><img src="http://cq2.lacerta.be/images/loading.gif" alt="loading" height="17" width="52"/></span>
	</p>
</form>


<form action="?page=battlecalc" name="battleform" id="battleform" method="POST" onsubmit="return checkDependencies()">
	
<div id="sidesdiv" style="width: 100%;">	
<% 
	String[] sides = { "attacker", "defender" };
	for (String side : sides) {	
		pageContext.setAttribute("side",side);
%>
	<input type="hidden" value="" name="<%= side %>.race" id="<%= side %>.race"/>
	<div style="float: left; width: 50%;">
	    <div style="clear: left; padding-bottom: 4px;">
	    	<font class="head"><%= StringUtils.capitalize(side) %></font>
		</div>
	   
	    <div style="clear: left; padding-top: 4px;">
	    	<div style="float: left; width: 120px;"><b>Name &amp; level</b></div>
	    	<div style="float: left;">
	    		<zod:AutoInput type="creature" name="${side}.name" id="${side}.name"/>
				<input type="text" name="<%= side %>.level" id="<%= side %>.level" size="5" class="input" value="<%= request.getAttribute(side+"_level")%>" ><br/>
	            <span id="selectbutton<%= side %>enabled"><input type="button" value="Get" id="selectbutton<%= side %>" class="input" style="vertical-align: middle;" onclick="doSelect('<%= side %>');" /></span>
				<span id="selectbutton<%= side %>disabled" style="display: none"><img src="http://cq2.lacerta.be/images/loading.gif" alt="loading" height="17" width="52"/></span>
	        </div>
	    </div>

	    <div style="clear: left; padding-top: 8px;">
	    	<div style="float: left; width: 120px;"><b>Class</b></div>
	    	<div style="float: left;">
		        <select name="<%= side %>.class" id="<%= side %>.class" class="input">
		        	<option></option>
					<option <% if (("Air").equals(request.getAttribute(side+"_class"))) {   %> selected <% } %>>Air</option>
					<option <% if (("Death").equals(request.getAttribute(side+"_class"))) { %> selected <% } %>>Death</option>
					<option <% if (("Earth").equals(request.getAttribute(side+"_class"))) { %> selected <% } %>>Earth</option>
					<option <% if (("Forest").equals(request.getAttribute(side+"_class"))) {%> selected <% } %>>Forest</option>
		        </select>
	    	</div>
	    </div>
	    
	    <div style="clear: left; padding-top: 16px;">
	    	<div style="float: left; width: 120px;"><b>Damage</b></div>
	    	<div style="float: left;"><input type="text" name="<%= side %>.damage" id="<%= side %>.damage"
	            size="6" class="input" value="<%= request.getAttribute(side+"_damage")%>" > <b>+ ith:</b> <input type="text"
	            name="<%= side %>.ithdamage" id="<%= side %>.ithdamage" size="5" class="input" value="<%= request.getAttribute(side+"_ithdamage")%>" ></div>
	    </div>
	    <div style="clear: left; padding-top: 4px;">
	    	<div style="float: left; width: 120px;"><b>Health</b></div>
	    	<div style="float: left;"><input type="text" name="<%= side %>.health" id="<%= side %>.health"
	            size="6" class="input" value="<%= request.getAttribute(side+"_health")%>" > <b>+ ith:</b> <input type="text"
	            name="<%= side %>.ithhealth" id="<%= side %>.ithhealth" size="5" class="input" value="<%= request.getAttribute(side+"_ithhealth")%>" ></div>
	    </div>
	    <div style="clear: left; padding-top: 4px;">
	    	<div style="float: left; width: 120px;"><b>Defence</b></div>
	    	<div style="float: left;"><input type="text" name="<%= side %>.def" id="<%= side %>.def"
	            size="6" class="input" value="<%= request.getAttribute(side+"_def")%>" > <b>+ ith:</b> <input type="text"
	            name="<%= side %>.ithdef" id="<%= side %>.ithdef" size="5" class="input" value="<%= request.getAttribute(side+"_ithdef")%>" ></div>
	    </div>
	    <div style="clear: left; padding-top: 4px;">
	    	<div style="float: left; width: 120px;"><b>Curse</b></div>
	    	<div style="float: left;">
	    		<select name="<%= side %>.curse" id="<%= side %>.curse" class="input">
		        <option></option>
		        <option <% if (("Suffocation").equals(request.getAttribute(side+"_curse"))) { %> selected <% } %>>Suffocation</option>
		        <option <% if (("Metamorphosis").equals(request.getAttribute(side+"_curse"))) { %> selected <% } %>>Metamorphosis</option>
		        </select>
	    	</div>
	    </div>
	    <div style="clear: left; padding-top: 4px;">
	    	<div style="float: left; width: 120px;"><b>Item</b>*</div>
	    	<div style="float: left;">
		        <select name="<%= side %>.item" id="<%= side %>.item" class="input">
		            <%
		            for (int i=0; i<Item.ITEMS.length; i++) {
		                %>
		                <option <%
		                if (request.getAttribute(side+"_item").equals(Item.ITEMS[i])) {
		                %> selected <% } %>><%= Item.ITEMS[i] %></option>
		                <%
		            }
		            %>
		        </select>
		        <select name="<%= side %>.itemenchant" id="<%= side %>.itemenchant" class="input">
		            <%
		            for (int i=0; i<Item.ENCHANTS.length; i++) {
		                %>
		                <option <%
		                if (request.getAttribute(side+"_itemenchant").equals(Item.ENCHANTS[i])) {
		                %> selected <% } %>><%= Item.ENCHANTS[i] %></option>
		                <%
		            }
		            %>
		        </select>
	    	</div>
	    </div>
	    <div style="clear: left; padding-top: 4px;">
	    	<div style="float: left; width: 120px;"><b>#Same-Others</b></div>
	    	<div style="float: left;">
				<input type="text" name="<%= side %>.same" id="<%= side %>.same" size="3" class="input"
	            value="<%= request.getAttribute(side+"_same")%>" > - <input type="text" name="<%= side %>.diff" id="<%= side %>.diff" size="3"
	            class="input" value="<%= request.getAttribute(side+"_diff")%>">
	    	</div>
	    </div>
	    <div style="clear: left; padding-top: 8px;">
	    	<div style="float: left; width: 120px;"><b>Wave enchant</b></div>
	    	<div style="float: left;">
		        <select name="<%= side %>.waveenchanttype" id="<%= side %>.waveenchanttype" class="input">
		        	<option></option>
					<option value="<%=Enchant.SAPPHIRE %>" <% if (request.getAttribute(side+"_waveenchanttype").equals(""+Enchant.SAPPHIRE)) {   %> selected <% } %>>Sapphire</option>
					<option value="<%=Enchant.RUBY %>" <% if (request.getAttribute(side+"_waveenchanttype").equals(""+Enchant.RUBY)) { %> selected <% } %>>Ruby</option>
					<option value="<%=Enchant.OPAL %>" <% if (request.getAttribute(side+"_waveenchanttype").equals(""+Enchant.OPAL)) { %> selected <% } %>>Opal</option>
					<option value="<%=Enchant.CARNELIAN %>" <% if (request.getAttribute(side+"_waveenchanttype").equals(""+Enchant.CARNELIAN)) { %> selected <% } %>>Carnelian</option>
		        </select>
		        <select name="<%= side %>.waveenchantlevel" id="<%= side %>.waveenchantlevel" class="input">
		        	<% for (int i=0; i<=20; i++) { %>
		        	<option <% if (request.getAttribute(side+"_waveenchantlevel").equals(""+i)) { %> selected <% } %>><%= i %></option>
		        	<% } %>
		        </select>
	    	</div>
	    </div>
	</div>
<% } %>
</div>
<script language="JavaScript">
if (navigator.appName=="Microsoft Internet Explorer") {
	document.getElementById("sidesdiv").style.width = "700px";
}
</script>
<div style="clear: both;">
<p>
<input type="checkbox" name="calcpercentage" id="calcpercentage" value="yes" class="input"> Based on 1000 calculations
</p>

<input type="submit" value="Calculate" class="input">
</div>
</form>



<% if (request.getAttribute("battlecalc_result") != null) { %>
	<p>
	<%= request.getAttribute("battlecalc_result") %>
	</p>
	Share it: <a href="?page=battlecalc&battleid=<%= request.getAttribute("battlecalc_fight") %>">http://cq2.lacerta.be<%= context %>/sim?page=battlecalc&battleid=<%= request.getAttribute("battlecalc_fight") %></a> 
<% } %>

<div style="padding-top: 20px; color: #777">
<i>* If you select an item that simply adds damage or health, make sure it's not already included in the damage/health box.<br/>
&nbsp;&nbsp;Selecting an item that only adds defense has no effect at all.<br/>
&nbsp;&nbsp;If you select an item for which the effect depends on the opponent's class (smiths), make sure you also select the class.</i>
</div>
</div>
</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
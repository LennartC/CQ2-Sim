<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.sim.hbn.User"  %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.HashSet" %>
<%@page import="java.util.List" %>
<%@page import="be.lacerta.cq2.sim.hbn.Creature" %>
<%@page import="be.lacerta.cq2.sim.hbn.Race" %>
<%@page import="be.lacerta.cq2.battlecalc.objects.Item" %>
<%@page import="be.lacerta.cq2.battlecalc.objects.Enchant" %>
<%@page import="be.lacerta.cq2.utils.StringUtils" %>
<%@ include file="include/init.jsp" %>
<%@ include file="include/header.jsp" %>
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>
<%@ include file="include/menu.jsp" %>

<div class="porlet" id="p-content">

<zod:Title title="Wave calculator"/>

<script type="text/javascript" src="<%= context %>/dwr/interface/CreatureFinder.js"></script>
<script type="text/javascript" src="<%= context %>/dwr/engine.js"></script>
<script language="JavaScript">

function handleParseInput(sides) {
	var sideNames = new Array();
	sideNames[0] = "attacker";
	sideNames[1] = "defender";

	for (c=0;c<2;c++) {
		var creatures = sides[c];
		for (var i=1;i<=creatures.length;i++) {
			var creature = creatures[i-1];
			var opponent = sides[0][i-1];
			if (c==0) opponent = sides[1][i-1];
			var side = sideNames[c];
	
			if ( creature != null ) {
				fillInCreature(creature, side+"-"+i);
			} else {
				resetCreature(side+"-"+i);
			}

		}
	}

	document.getElementById("parsebuttonenabled").style.display = "block"; 
	document.getElementById("parsebuttondisabled").style.display = "none"; 
	document.getElementById("parsebutton").disabled=false;

}
function handleParseEnchantInput(enchants) {
	var sideNames = new Array();
	sideNames[0] = "attacker";
	sideNames[1] = "defender";

	for (c=0;c<2;c++) {
		var enchant = enchants[c];
		var prefix = sideNames[c];
		try { document.getElementById(prefix+".waveenchantvs").value = enchant.creature; } catch(err) {}
		try { document.getElementById(prefix+".waveenchantlevel")[enchant.level].selected = "1"; } catch(err) { alert(err); }
		try { document.getElementById(prefix+".waveenchanttype")[enchant.type].selected = "1"; } catch(err) {}
	}
}

function handleSelect(creature) {
	var sides = new Array();
	sides[0] = "attacker";
	sides[1] = "defender";

	for (c=0;c<2;c++) {

		var side = sides[c];

		for (var i=1;i<16;i++) {
			if (document.getElementById("selectbutton"+i+side).disabled) {
				fillInCreature(creature,side+"-"+i);
				document.getElementById("selectbutton"+i+side+"enabled").style.display = "block"; 
				document.getElementById("selectbutton"+i+side+"disabled").style.display = "none"; 
				document.getElementById("selectbutton"+i+side).disabled=false;
				break;	
			}
		}
	}
}

function fillInCreature(creature, prefix) {
	try { if (creature.name != null) document.getElementById(prefix+".name").value = creature.name; } catch(err) {}
	try { document.getElementById(prefix+".level").value = creature.level; } catch(err) {}
	try { document.getElementById(prefix+".damage").value = creature.damage; } catch(err) {}
	try { document.getElementById(prefix+".health").value = creature.health; } catch(err) {}
	
	try { document.getElementById(prefix+".Ddef").value = creature.deathDef;} catch(err) {}
	try { document.getElementById(prefix+".Fdef").value = creature.forestDef;} catch(err) {}
	try { document.getElementById(prefix+".Adef").value = creature.airDef;} catch(err) {}
	try { document.getElementById(prefix+".Edef").value = creature.earthDef;} catch(err) {}
	try { document.getElementById(prefix+".ithdamage").value = creature.ith.damage; } catch(err) {}
	try { document.getElementById(prefix+".ithhealth").value = creature.ith.health; } catch(err) {}
	try { document.getElementById(prefix+".ithdef").value = creature.ith.defence; } catch(err) {}
	
	// item
	try {
		var items = document.getElementById(prefix+".item");
		items[0].selected = "1";

		for (var i=0; i<= items.length - 1; i++) {
		  if (items[i].value.toLowerCase() == "-----"+creature.race.toLowerCase()+"-----") {
		  	items[i].selected = "1";
		  	break;
		  }
		}
		if (creature.item.name != "") {
			for (var i=0; i <= items.length - 1; i++) {
			  if (items[i].value == creature.item.name) {
			  	items[i].selected = "1";
			  	break;
			  }
			}
		}
	} catch(err) { }
	
	try {
		var classes = document.getElementById(prefix+".class");
		classes[0].selected = "1";
		for (var i=0; i <= classes.length - 1; i++) {
		  if (classes[i].value.toLowerCase() == creature.creatureClass.toLowerCase()) {
		  	classes[i].selected = "1";
		  	break;
		  }
		}
	} catch(err) {}	
	
	// enchant
	document.getElementById(prefix+".itemenchant")[0].selected = "1";
	try {document.getElementById(prefix+".itemenchant")[3].selected = creature.item.majorBerserk; } catch(err) {}
	try {document.getElementById(prefix+".itemenchant")[4].selected = creature.item.majorImmunity; } catch(err) {}
	try {document.getElementById(prefix+".enchantvs").value = creature.item.enchantVs; } catch(err) {}
	
	// curse
	document.getElementById(prefix+".curse")[0].selected = "1";
	try {document.getElementById(prefix+".curse")[1].selected = creature.suffocated; } catch(err) {}
	try {document.getElementById(prefix+".curse")[2].selected = creature.metad; } catch(err) {}
	try {document.getElementById(prefix+".curse")[3].selected = creature.doppeled; } catch(err) {}
}

function resetCreature(prefix) {
	try { document.getElementById(prefix+".name").value = ''; } catch(err) {}
	try { document.getElementById(prefix+".level").value = ''; } catch(err) {}
	try { document.getElementById(prefix+".damage").value = ''; } catch(err) {}
	try { document.getElementById(prefix+".health").value = ''; } catch(err) {}
	
	try { document.getElementById(prefix+".Ddef").value = '';} catch(err) {}
	try { document.getElementById(prefix+".Fdef").value = '';} catch(err) {}
	try { document.getElementById(prefix+".Adef").value = '';} catch(err) {}
	try { document.getElementById(prefix+".Edef").value = '';} catch(err) {}
	try { document.getElementById(prefix+".ithdamage").value = ''; } catch(err) {}
	try { document.getElementById(prefix+".ithhealth").value = ''; } catch(err) {}
	try { document.getElementById(prefix+".ithdef").value = ''; } catch(err) {}
	
	// item & enchant
	document.getElementById(prefix+".item")[0].selected = "1";
	document.getElementById(prefix+".class")[0].selected = "1";
	document.getElementById(prefix+".itemenchant")[0].selected = "1";
	try {document.getElementById(prefix+".enchantvs").value = ''; } catch(err) {}
	
	// curse
	document.getElementById(prefix+".curse")[0].selected = "1";
}

function parse()
{
    document.getElementById("parsebutton").disabled=true;
	document.getElementById("parsebuttonenabled").style.display = "none"; 
	document.getElementById("parsebuttondisabled").style.display = "block"; 
    var waveField = document.getElementById("waveField");
    CreatureFinder.parseWaveEnchant(waveField.value,handleParseEnchantInput);
    CreatureFinder.parseWave(waveField.value,handleParseInput);
}


function doSelect(side,i)
{
    document.getElementById("selectbutton"+i+side).disabled=true;
	document.getElementById("selectbutton"+i+side+"enabled").style.display = "none"; 
	document.getElementById("selectbutton"+i+side+"disabled").style.display = "block"; 
    var fieldName = document.getElementById(side+"-"+i+".name");
    var fieldLevel = document.getElementById(side+"-"+i+".level");
    CreatureFinder.selectCreature(fieldName.value,fieldLevel.value,handleSelect);
}

function autoCompleteCrit(event,field) {
	var creatures=new Array();
	<%
	try {
		List<Creature> cl = (List<Creature>)request.getAttribute("creatures");
		int i=0;
		for (Creature cr : cl) {
			%>creatures[<%= i++ %>] = "<%= cr.getName() %>";
			<%
		}
	} catch (NullPointerException npe) {
	} catch (ClassCastException cce) {}
	%>
	var found = false;
	for ( var i=0; i<creatures.length; i++){
		if (creatures[i].toUpperCase().indexOf(field.value.toUpperCase()) == 0) {
			found=true; break;
		}
	}

	var cursorKeys ="8;46;37;38;39;40;33;34;35;36;45;";
	if (cursorKeys.indexOf(event.keyCode+";") == -1) {
		var oldValue = field.value;
		var newValue = found ? creatures[i] : oldValue;
		if (newValue != field.value) {
			if (field.createTextRange) {
				field.value = newValue;
				var rNew = field.createTextRange();
				rNew.moveStart('character', oldValue.length) ;
				rNew.select();
			} else if (field.setSelectionRange) {
				field.value = newValue;
        		field.setSelectionRange(oldValue.length, newValue.length);
			}
		}
	}
}

function autoCompleteRace(event,field) {
	var races=new Array();
	<%
	try {
		HashSet<String> used = new HashSet<String>();
		List<Creature> cl = (List<Creature>)request.getAttribute("creatures");
		int i=0;
		for (Creature c : cl) {
			String race = c.getRace().getName();
			if (!used.contains(race)) {
				%>races[<%= i++ %>] = "<%= race %>";
				<%
				used.add(race);
			}
		}
	} catch (NullPointerException npe) {
	} catch (ClassCastException cce) {}
	%>
	var found = false;
	for ( var i=0; i<races.length; i++){
		if (races[i].toUpperCase().indexOf(field.value.toUpperCase()) == 0) {
			found=true; break;
		}
	}

	var cursorKeys ="8;46;37;38;39;40;33;34;35;36;45;";
	if (cursorKeys.indexOf(event.keyCode+";") == -1) {
		var oldValue = field.value;
		var newValue = found ? races[i] : oldValue;
		if (newValue != field.value) {
			if (field.createTextRange) {
				field.value = newValue;
				var rNew = field.createTextRange();
				rNew.moveStart('character', oldValue.length) ;
				rNew.select();
			} else if (field.setSelectionRange) {
				field.value = newValue;
        		field.setSelectionRange(oldValue.length, newValue.length);
			}
		}
	}
}

</script>

<a href="?page=battlecalc">Single crit calculator</a>

<form method="post" action="?page=wavecalc" id="frmAmulets" onSubmit="return 1">
	<font class="head">Full Creature Parser</font>
	<p style="padding-left: 10px">
	<font class="head">Wave:</font><br/>
	<textarea cols="130" rows="10" class="input" id="waveField" name="waveField" style="overflow: hidden; white-space: nowrap; margin-bottom: 5px; padding: 1px"></textarea><br/>
	<span id="parsebuttonenabled"><input type="button" value="Parse" id="parsebutton" class="input" style="vertical-align: middle;" onclick="parse();" /></span>
	<span id="parsebuttondisabled" style="display: none"><img src="http://cq2.lacerta.be/images/loading.gif" alt="loading" height="17" width="52"/></span>
	</p>
</form>


<form action="?page=wavecalc" name="battleform" id="battleform" method="POST">
	
<div id="sidesdiv" style="float: left; width: 100%;">

<%
String[] sides = { "attacker", "defender" };
for (String side : sides) {
%>
    <div style="float: left; width: 50%;">
    	<div style="clear: left; padding-top: 16px;">
	    	<div style="float: left; width: 120px;"><b>Wave enchant</b></div>
	    	<div style="float: left;">
	    		level
		        <select name="<%= side %>.waveenchantlevel" id="<%= side %>.waveenchantlevel" class="input">
		        	<% for (int i=0; i<=20; i++) { %>
		        	<option <% if (request.getAttribute(side+"_waveenchantlevel").equals(""+i)) { %> selected <% } %>><%= i %></option>
		        	<% } %>
		        </select>
		        <select name="<%= side %>.waveenchanttype" id="<%= side %>.waveenchanttype" class="input">
		        	<option></option>
					<option value="<%=Enchant.RUBY %>" <% if (request.getAttribute(side+"_waveenchanttype").equals(""+Enchant.RUBY)) {   %> selected <% } %>>Ruby</option>
					<option value="<%=Enchant.SAPPHIRE %>" <% if (request.getAttribute(side+"_waveenchanttype").equals(""+Enchant.SAPPHIRE)) { %> selected <% } %>>Sapphire</option>
					<option value="<%=Enchant.CARNELIAN %>" <% if (request.getAttribute(side+"_waveenchanttype").equals(""+Enchant.CARNELIAN)) { %> selected <% } %>>Carnelian</option>
					<option value="<%=Enchant.OPAL %>" <% if (request.getAttribute(side+"_waveenchanttype").equals(""+Enchant.OPAL)) { %> selected <% } %>>Opal</option>
		        </select>
		        on the
		        <input type="text" name="<%= side %>.waveenchantvs" id="<%= side %>.waveenchantvs"
	            size="18" class="input" value="<%= request.getAttribute(side+"_waveenchantvs")%>" onkeyup="autoCompleteCrit(event,this)">
	            creatures.
	    	</div>
    	</div>
    </div>
<% } %>

<div style="clear:both"></div>
<%
for (int i=1; i<=15; i++) {
	for (String side : sides) {	
%>
	<div style="float: left; width: 50%;">
	    <div style="clear: left; padding-top: 16px;">
	    	<font class="head"><%= StringUtils.capitalize(side)+" "+i %></font>
		</div>
	   
	    <div style="clear: left; padding-top: 4px;">
	    	<div style="float: left; width: 120px;"><b>Name &amp; level</b></div>
	    	<div style="float: left;"><input type="text" name="<%= side %>-<%= i %>.name" id="<%= side %>-<%= i %>.name"
	            size="18" class="input" value="<%= request.getAttribute(side+"-"+i+"_name")%>" onkeyup="autoCompleteCrit(event,this)"> <input type="text"
	            name="<%= side %>-<%= i %>.level" id="<%= side %>-<%= i %>.level" size="5" class="input" value="<%= request.getAttribute(side+"-"+i+"_level")%>" ><br/>
	            <span id="selectbutton<%= i %><%= side %>enabled"><input type="button" value="Get" id="selectbutton<%= i %><%= side %>" class="input" style="vertical-align: middle;" onclick="doSelect('<%= side %>',<%= i %>);" /></span>
				<span id="selectbutton<%= i %><%= side %>disabled" style="display: none"><img src="http://cq2.lacerta.be/images/loading.gif" alt="loading" height="17" width="52"/></span>
	        </div>
	        <div style="float: left; padding-left: 2px;">&amp;&nbsp;<b>Class</b></div>
	    	<div style="float: left;">
		        <select name="<%= side %>-<%= i %>.class" id="<%= side %>-<%= i %>.class" class="input">
		        	<option></option>
					<option <% if (("Air").equals(request.getAttribute(side+"-"+i+"_class"))) {   %> selected <% } %>>Air</option>
					<option <% if (("Death").equals(request.getAttribute(side+"-"+i+"_class"))) { %> selected <% } %>>Death</option>
					<option <% if (("Earth").equals(request.getAttribute(side+"-"+i+"_class"))) { %> selected <% } %>>Earth</option>
					<option <% if (("Forest").equals(request.getAttribute(side+"-"+i+"_class"))) {%> selected <% } %>>Forest</option>
		        </select>
	    	</div>
	    </div>
	    
	    <div style="clear: left; padding-top: 4px;">
	    	<div style="float: left; width: 120px;"><b>Creature</b></div>
	    	<div style="float: left;">
	    	<b>Dmg</b>
	    	<input type="text" name="<%= side %>-<%= i %>.damage" id="<%= side %>-<%= i %>.damage"
	            size="6" class="input" value="<%= request.getAttribute(side+"-"+i+"_damage")%>" >
	    	<b>Hlt</b>
	    	<input type="text" name="<%= side %>-<%= i %>.health" id="<%= side %>-<%= i %>_health"
	            size="6" class="input" value="<%= request.getAttribute(side+"-"+i+"_health")%>" >
	    	<b>Def</b>
	    	F<input type="text" name="<%= side %>-<%= i %>.Fdef" id="<%= side %>-<%= i %>.Fdef" size="4" class="input" value="<%= request.getAttribute(side+"-"+i+"_Fdef")%>" >&nbsp;
	    	D<input type="text" name="<%= side %>-<%= i %>.Ddef" id="<%= side %>-<%= i %>.Ddef" size="4" class="input" value="<%= request.getAttribute(side+"-"+i+"_Ddef")%>" >&nbsp;
	    	A<input type="text" name="<%= side %>-<%= i %>.Adef" id="<%= side %>-<%= i %>.Adef" size="4" class="input" value="<%= request.getAttribute(side+"-"+i+"_Adef")%>" >&nbsp;
	    	E<input type="text" name="<%= side %>-<%= i %>.Edef" id="<%= side %>-<%= i %>.Edef" size="4" class="input" value="<%= request.getAttribute(side+"-"+i+"_Edef")%>" >
	        </div>
	    </div>
	    <div style="clear: left; padding-top: 4px;">
	    	<div style="float: left; width: 120px;"><b>Curse</b></div>
	    	<div style="float: left;">
	    		<select name="<%= side %>-<%= i %>.curse" id="<%= side %>-<%= i %>.curse" class="input">
		        <option></option>
		        <option <% if (("Suffocation").equals(request.getAttribute(side+"-"+i+"_curse"))) { %> selected <% } %>>Suffocation</option>
		        <option <% if (("Metamorphosis").equals(request.getAttribute(side+"-"+i+"_curse"))) { %> selected <% } %>>Metamorphosis</option>
		        <option <% if (("Doppelganger").equals(request.getAttribute(side+"-"+i+"_curse"))) { %> selected <% } %>>Doppelganger</option>
		        </select>
	    	</div>
	    </div>
	    <div style="clear: left; padding-top: 4px;">
	    	<div style="float: left; width: 120px;"><b>Item</b>*</div>
	    	<div style="float: left;">
		        <select name="<%= side %>-<%= i %>.item" id="<%= side %>-<%= i %>.item" class="input">
		            <%
		            for (int j=0; j<Item.ITEMS.length; j++) {
		                %>
		                <option <%
		                if (Item.ITEMS[j].equals(request.getAttribute(side+"-"+i+"_item"))) {
		                %> selected <% } %>><%= Item.ITEMS[j] %></option>
		                <%
		            }
		            %>
		        </select>
		        <select name="<%= side %>-<%= i %>.itemenchant" id="<%= side %>-<%= i %>.itemenchant" class="input">
		            <%
		            for (int j=0; j<Item.ENCHANTS.length; j++) {
		                %>
		                <option <%
		                if (Item.ENCHANTS[j].equals(request.getAttribute(side+"-"+i+"_itemenchant"))) {
		                %> selected <% } %>><%= Item.ENCHANTS[j] %></option>
		                <%
		            }
		            %>
		        </select>
	    	</div>
	    	<div style="float: left;">vs<input type="text" name="<%= side %>-<%= i %>.enchantvs" id="<%= side %>-<%= i %>.enchantvs"
	            size="18" class="input" value="<%= request.getAttribute(side+"-"+i+"_enchantvs")%>" onkeyup="autoCompleteRace(event,this)">
	        </div>
	    </div>
	    
	    <div style="clear: left; padding-top: 4px;">
	    	<div style="float: left; width: 120px;"><b>Itherian</b></div>
	    	<div style="float: left;">
	    	<b>Dmg</b>
	    	<input type="text" name="<%= side %>-<%= i %>.ithdamage" id="<%= side %>-<%= i %>.ithdamage"
	            size="6" class="input" value="<%= request.getAttribute(side+"-"+i+"_ithdamage")%>" >
	    	<b>Hlt</b>
	    	<input type="text" name="<%= side %>-<%= i %>.ithhealth" id="<%= side %>-<%= i %>.ithhealth"
	            size="6" class="input" value="<%= request.getAttribute(side+"-"+i+"_ithhealth")%>" >
	    	<b>Def</b>
	    	<input type="text" name="<%= side %>-<%= i %>.ithdef" id="<%= side %>-<%= i %>.ithdef" 
	    		size="4" class="input" value="<%= request.getAttribute(side+"-"+i+"_ithdef")%>" >
	        </div>
	    </div>
	</div>
<% } 
}
%>

</div>
<script language="JavaScript">
if (navigator.appName=="Microsoft Internet Explorer") {
	document.getElementById("sidesdiv").style.width = "1100px";
}
</script>
<div style="clear: both;">
<p>
<input type="checkbox" name="calcpercentage" id="calcpercentage" value="yes" class="input"> Based on 1000 calculations
</p>

<input type="submit" value="Calculate" class="input">
</div>
</form>



<% if (request.getAttribute("wavecalc_results") != null) { 

List<String> results = (List<String>)request.getAttribute("wavecalc_results");
int i=1;
for (String result : results) {
%>
	<p>
	<font class="head">Fight <%= i++ %>:</font><br/>
	<%= result %>
	</p>
<% } 
}%>

<div style="padding-top: 20px; color: #777">
<i>* If you select an item that simply adds damage or health, make sure it's not already included in the damage/health box.<br/>
&nbsp;&nbsp;Selecting an item that only adds defense has no effect at all.<br/>
&nbsp;&nbsp;If you select an item for which the effect depends on the opponent's class (smiths), make sure you also select the class.</i>
</div>
</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
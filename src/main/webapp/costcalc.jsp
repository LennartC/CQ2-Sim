<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="be.lacerta.cq2.sim.hbn.User"  %>
<%@page import="java.util.*" %>
<%@page import="be.lacerta.cq2.sim.hbn.Amulet" %>
<%@page import="be.lacerta.cq2.sim.hbn.Item" %>
<%@page import="be.lacerta.cq2.sim.hbn.MageSkill" %>
<%@ include file="include/init.jsp" %>
<%@ include file="include/header.jsp" %>
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>
<%@ include file="include/menu.jsp" %>

<div class="porlet" id="p-content">

<table width="100%">
    <tr>
        <td class="titleline" width="100%"><font class="head" size="-1">Cost Calculator</font></td>
    </tr>
</table>

<%

int[] totalAmulet = {0,0,0};
int[] totalEnchant = {0,0,0,0};

if (request.getAttribute("costcalc_amuprices") != null) {

    HashMap<Amulet,Integer> amus = (HashMap<Amulet,Integer>)request.getAttribute("costcalc_amuprices");

    int[] ts = {0,0,0};
    int[] tu = {0,0,0};



    for (Iterator<Amulet> i = amus.keySet().iterator(); i.hasNext();) {
        Amulet a = i.next();
        int amount = amus.get(a);

        ts[0] += amount*a.getBrim();
        ts[1] += amount*a.getCrys();
        ts[2] += amount*a.getEss();

        tu[0] += amount*a.getBrim()/2;
        tu[1] += amount*a.getCrys()/2;
        tu[2] += amount*a.getEss()/2;

        totalAmulet[0] += amount*a.getBrim()+amount*a.getBrim()/2;
        totalAmulet[1] += amount*a.getCrys()+amount*a.getCrys()/2;
        totalAmulet[2] += amount*a.getEss()+amount*a.getEss()/2;

        %>
        <p>

        <font class="head" size="-1">Amulet costs:</font><br/>
        <b><%= amount %>&nbsp;x&nbsp;<%= a.getName() %>:</b>
        <%= a.getBrim() %> B /
        <%= a.getCrys() %> C <% if (a.getEss() > 0) { %>/
        <%= a.getEss() %> E<% } %>
        <br/>
        </p>

        <table>
        <tr><td><b>Summon:</b></td>
        <td>
        <%= amount*a.getBrim() %> B /
        <%= amount*a.getCrys() %> C <% if (a.getEss() > 0) { %>/
        <%= amount*a.getEss() %> E<% } %>
        </td>
        </tr>
        <tr><td><b>Use cost:</b></td>
        <td>
        <%= (amount*a.getBrim())/2 %> B /
        <%= (amount*a.getCrys())/2 %> C <% if (a.getEss() > 0) { %>/
        <%= (amount*a.getEss())/2 %> E<% } %>
        </td>
        </tr>
        <tr><td><b>Total:</b></td>
        <td>
        <%= (amount*a.getBrim())+(amount*a.getBrim())/2 %> B /
        <%= (amount*a.getCrys())+(amount*a.getCrys())/2 %> C <% if (a.getEss() > 0) { %>/
        <%= (amount*a.getEss())+(amount*a.getEss())/2 %> E<% } %>
        </td>
        </tr>
        </table>


        <%
    }

    %>
        <br/>
        <font class="head" size="-1">Total:</font>

        <table>
        <tr><td><b>Summon:</b></td>
        <td>
        <%= ts[0] %> B /
        <%= ts[1] %> C <% if (ts[2] > 0) { %>
        <%= ts[2] %> E<% } %>
        </td>
        </tr>
        <tr><td><b>Use cost:</b></td>
        <td>
        <%= tu[0] %> B /
        <%= tu[1] %> C <% if (tu[2] > 0) { %>/
        <%= tu[2] %> E<% } %>
        </td>
        </tr>
        <tr><td><b>Total:</b></td>
        <td>
        <%= totalAmulet[0] %> B /
        <%= totalAmulet[1] %> C <% if (totalAmulet[2] > 0) { %>/
        <%= totalAmulet[2] %> E<% } %>
        </td>
        </tr>
        </table>
        <br/>
    <%
}



if (request.getAttribute("costcalc_enchantprices") != null) {

    List<int[]> enchants = (List<int[]>)request.getAttribute("costcalc_enchantprices");


    %><font class="head" size="-1">Enchant costs:</font><br/><%

    for (int[] price : enchants) {
        int ti = price[0];
        String type = "";

        if (ti == 1) type="MI on ";
        else if (ti == 2) type="MB on ";

        int ws = price[1];
        int amount = price[2];

        totalEnchant[0] += price[3]*amount;
        totalEnchant[1] += price[4]*amount;
        totalEnchant[2] += price[5]*amount;
        totalEnchant[3] += price[6]*amount;

        %>
        <table>
        <tr><td><b><%= type %>Item level <%= ws %>:</b></td></tr>
        <tr>
        <td>&nbsp;for 1:
        <%= price[3] %> B /
        <%= price[4] %> C /
        <%= price[5] %> E /
        <%= price[6] %> G
        </td>
        </tr>
        <% if (amount > 1) { %>
        <tr>
        <td>&nbsp;for <%= amount %>:
        <%= price[3]*amount %> B /
        <%= price[4]*amount %> C /
        <%= price[5]*amount %> E /
        <%= price[6]*amount %> G
        </td>
        </tr>
        <% } %>
        </table>


        <%
    }

    %>
        <br/>
        <font class="head" size="-1">Total:</font>
            <table>
        <tr>
        <td>
        <%= totalEnchant[0] %> B /
        <%= totalEnchant[1] %> C /
        <%= totalEnchant[2] %> E /
        <%= totalEnchant[3] %> G
        </td>
        </tr>
        </table>


    <%
}


if (request.getAttribute("costcalc_enchantprices") != null && request.getAttribute("costcalc_amuprices") != null) {
    %>
    <br/>
    <font class="head" size="-1">Grand Total:</font>
        <br/>
    <%= totalEnchant[0]+totalAmulet[0] %> B /
    <%= totalEnchant[1]+totalAmulet[1] %> C /
    <%= totalEnchant[2]+totalAmulet[2] %> E /
    <%= totalEnchant[3] %> G
    <br/>
<%
}

%>
<br/>

<table width="100%">
    <tr>
        <td class="titleline" width="100%"><font class="head" size="-1">Amulets</font></td>
    </tr>
</table>
<script language="JavaScript">
var ammyRows=-1;
function addAmuletRowToTable(tblName)
{
    if (ammyRows < 5) {
    	ammyRows++;
        var row = document.getElementById('ammyTR'+ammyRows);
        row.style.display = 'block';
	}
}


function removeAmuletRowFromTable() {
        if (ammyRows >= 0) {
                var row = document.getElementById('ammyTR'+ammyRows);
                row.style.display = 'none';
				document.getElementById('ammy'+ammyRows).value="";
				document.getElementById('ammyamount'+ammyRows).value="";
                ammyRows--;
        }
}

var enchantRows=-1;
function addEnchantRowToTable() {
        if (enchantRows < 5) {
                enchantRows++;
                var row = document.getElementById('enchantTR'+enchantRows);
                row.style.display = 'block';
                var levelselect = document.getElementById('levelSelect'+enchantRows);
                setItems(levelselect);
        }
}

function removeEnchantRowFromTable() {
        if (enchantRows >= 0) {
                var row = document.getElementById('enchantTR'+enchantRows);
                row.style.display = 'none';
				document.getElementById('level'+enchantRows).value="";
				document.getElementById('enchamount'+enchantRows).value="";
                enchantRows--;
        }
}

function fixForm(tblName)
{
  var form = document.getElementById('frmAmulets');
  var button = document.getElementById('sbmAmulets');
  var tabel = document.getElementById(tblName);

  form.insertBefore(tabel, form.childNodes.item(0));

  var inputs = form.elements;
  buffer = "";
  for (i = 0; i < inputs.length; i++)
    buffer += inputs[i].name + "=" + inputs[i].value + "\n";

  return true;
}

function setFormula(id) {
  var formula = document.getElementById('formula'+id);

  var brim = document.getElementById('brim'+id);
  var crys = document.getElementById('crys'+id);
  var ess = document.getElementById('ess'+id);
  var gran = document.getElementById('gran'+id);
  var enchant = document.getElementById('enchant'+id);

  var option = formula.options[formula.selectedIndex].value;
  if (option == 'MI') {
        brim.value = '105'; crys.value = '420'; ess.value = '105'; gran.value = '420'; enchant.value = '1';
  } else if (option == 'MB') {
        brim.value = '420'; crys.value = '105'; ess.value = '105'; gran.value = '420'; enchant.value = '2';
  }
}

function setLevel(id) {
        var levelselect = document.getElementById('levelSelect'+id);
        var level = document.getElementById('level'+id);
        level.value = levelselect.options[levelselect.selectedIndex].value;
}

function setItems(levelselect) {
	levelselect.options.length = 0;

        var opt = document.createElement("option");
        levelselect.options.add(opt);
    opt.text = ''; opt.value = '';

    <%
    int i=0;
    List<Item> items = (List<Item>)request.getAttribute("costcalc_items");
    for (Item item : items) {
        %>
                var opt<%= i %> = document.createElement("option");
            levelselect.options.add(opt<%= i %>);
            opt<%= i %>.text = '<%= item.getName().replaceAll("\\'","\\\\'") %>'; opt<%= i %>.value = '<%= ""+(item.getLevel()) %>';
            <%
    }
    %>
}

</script>

<zod:AutoCompleteJS type="amulet"/>

<form method="post" action="?page=critdb&action=costcalc" id="frmAmulets" onSubmit="return fixForm('tblAmulets')">
<table border="0" cellpadding="1" cellspacing="0" id="tblAmulets">
<% for (i=0; i<6; i++) { pageContext.setAttribute("i",i); %>
	<tr id="ammyTR<%= i %>" style="display: none">
		<td><zod:AutoInput type="amulet" name="ammy${i}" value="" size="40"/></td>
		<td><input type="text" name="ammyamount<%= i %>" id="ammyamount<%= i %>" size="4" class="input"/></td>
	</tr>
<% } %>
    <tr>
        <td colspan="2"><input type="button" value="Add"  class="input"
            onclick="addAmuletRowToTable('tblAmulets');" />&nbsp; <input
            type="button" value="Remove"  class="input"
            onclick="removeAmuletRowFromTable('tblAmulets');" /></td>
    </tr>
</table>


<br/>
<br/>
<table width="100%">
    <tr>
        <td class="titleline" width="100%"><font class="head" size="-1">Enchants</font></td>
    </tr>
</table>

<table border="0" cellpadding="1" cellspacing="0" id="tblEnchants">
<% for (i=0; i<6; i++) { %>
    <tr id="enchantTR<%= i %>" style="display: none">
        <td>
        Formula:
        <select class="input" onChange="setFormula('<%= i %>')" id="formula<%= i %>">
                <option></option>
                <option value="MI">MI</option>
                <option value="MB">MB</option>
        </select>
        <input type="hidden" name="enchant<%= i %>" id="enchant<%= i %>" value=""/>
        <input type="text" class="input" name="brim<%= i %>" id="brim<%= i %>" size="5"/>
        <input type="text" class="input" name="crys<%= i %>" id="crys<%= i %>" size="5"/>
        <input type="text" class="input" name="ess<%= i %>" id="ess<%= i %>" size="5"/>
        <input type="text" class="input" name="gran<%= i %>" id="gran<%= i %>" size="5"/>
        Level:
        <select class="input" onChange="setLevel('<%= i %>')" id="levelSelect<%= i %>">
        </select>
        <input type="text" class="input" name="level<%= i %>" id="level<%= i %>" size="5"/>
        Amount:
        <input type="text" class="input" name="enchamount<%= i %>" id="enchamount<%= i %>" size="5"/>
        </td>
    </tr>
<% } %>

    <tr>
        <td><input type="button" value="Add"  class="input"
            onclick="addEnchantRowToTable();" />&nbsp; <input
            type="button" value="Remove"  class="input"
            onclick="removeEnchantRowFromTable();" /></td>
    </tr>
</table>

<br/>
<input type="submit" id="sbmEnchants" class="input"/>
</form>


<br/>
<br/>
<table width="350px">
    <tr>
        <td colspan="2" class="titleline"><font class="head" size="-1">Enchanters:</font></td>
    </tr>
    <tr>
    	<td style="vertical-align: top;"><b>Major&nbsp;Immunity</b><br/>
    	<ul>
    	<%
    	  if (request.getAttribute("costcalc_MIenchanters") != null) { 
    		List<MageSkill> msList = (List<MageSkill>)request.getAttribute("costcalc_MIenchanters");
    		for (MageSkill ms : msList) {
    			%><li><% pageContext.setAttribute("mage",ms.getMage()); %><zod:ProfileLink mage="${mage}"/></li><%
    		}
    	  }
    	%>
    	</ul>
    	</td>
    	<td style="vertical-align: top;"><b>Major&nbsp;Berserk</b><br/>
    	<ul>
    	<%
    	   if (request.getAttribute("costcalc_MBenchanters") != null) { 
    		List<MageSkill> msList = (List<MageSkill>)request.getAttribute("costcalc_MBenchanters");
    		for (MageSkill ms : msList) {
    			%><li><% pageContext.setAttribute("mage",ms.getMage()); %><zod:ProfileLink mage="${mage}"/></li><%
    		}
    	   }
    	%>
    	</ul>
    	</td>
    </tr>
</table>

</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>

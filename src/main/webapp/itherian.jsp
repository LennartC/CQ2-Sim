<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod"%>
<%@page import="be.lacerta.cq2.sim.hbn.User"%>
<%@page import="java.util.*"%>
<%@page import="be.lacerta.cq2.sim.hbn.Amulet"%>
<%@page import="be.lacerta.cq2.sim.hbn.Item"%>
<%@page import="be.lacerta.cq2.sim.hbn.Creature"%>
<%@page import="be.lacerta.cq2.sim.hbn.MageSkill"%>
<%@page import="be.lacerta.cq2.sim.hbn.Itherian"%>
<%@ include file="include/init.jsp"%>
<%@ include file="include/header.jsp"%>
<c:choose>
	<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
	<c:otherwise>
		<%@ include file="include/menu.jsp"%>

		<div class="porlet" id="p-content">

			<table width="100%">
				<tr>
					<td class="titleline" width="100%"><font class="head"
						size="-1">Itherian Calculator</font></td>
				</tr>
			</table>

			<br />

			<table width="100%">
				<tr>
					<td class="titleline" width="100%"><font class="head"
						size="-1">Add sacrifices</font></td>
				</tr>
			</table>
			<script language="JavaScript">
				var ammyRows = -1;
				function addAmuletRowToTable(tblName) {
					if (ammyRows < 20) {
						ammyRows++;
						var row = document.getElementById('ammyTR' + ammyRows);
						row.style.display = 'block';
					}
				}

				function removeAmuletRowFromTable() {
					if (ammyRows >= 0) {
						var row = document.getElementById('ammyTR' + ammyRows);
						row.style.display = 'none';
						document.getElementById('ammy' + ammyRows).value = "";
						ammyRows--;
					}
				}

				function fixForm(tblName) {
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
			</script>

			<zod:AutoCompleteJS type="amulet" />



			<form method="post" action="?page=itherian&action=addSacs"
				id="frmAmulets" onSubmit="return fixForm('tblAmulets')">

				My items:<br /> <select name="ownItem" class="input">
					<%
						List<Itherian> MyIthItem = (List<Itherian>) request.getAttribute("itherian_items_user");
						for (Itherian ith : MyIthItem) {
					%>
					<option value="<%=ith.getCq2ID()%>"><%=ith.getItemname() + " (id:" + ith.getCq2ID()	+ ")"%></option>
					<%
						}
					%>
				</select> <input type="submit" id="sbmSacs" class="input" />
			</form>


			<form method="post" action="?page=itherian&action=addIth"
				id="frmAmulets" onSubmit="return fixForm('tblAmulets')">


				<table border="0" cellpadding="1" cellspacing="0" id="tblAmulets">
					<%
						for (int i = 0; i <= 20; i++) {
									pageContext.setAttribute("i", i);
					%>
					<tr id="ammyTR<%=i%>" style="display: none">
						<td><zod:AutoInput type="amulet" name="ammy${i}" value=""
								size="40" /></td>
						<td>L<select name="level" class="input">
								<%
									for (int j = 1; j < 20; j++) {
								%>
								<option><%=j%></option>
								<%
									}
								%>
								<option selected="selected">20</option>
						</select>
						</td>
					</tr>
					<%
						}
					%>
					<tr>
						<td colspan="2"><input type="button" value="Add"
							class="input" onclick="addAmuletRowToTable('tblAmulets');" />&nbsp;
							<input type="button" value="Remove" class="input"
							onclick="removeAmuletRowFromTable('tblAmulets');" /></td>
					</tr>
				</table>


				<table width="100%">
					<tr>
						<td class="titleline" width="100%"><font class="head"
							size="-1">Orbs (not doing anything)</font></td>
					</tr>
				</table>
				<textarea cols="70" rows="8" class="input" name="gems">
    <%=request.getAttribute("itherian_gems")%></textarea>


				<table width="100%">
					<tr>
						<td class="titleline" width="100%"><font class="head"
							size="-1">Add itherians</font></td>
					</tr>
				</table>

				<table>
					<tr>
						<td>CQ id: <br /> <textarea class="input"
								name="cq2ID"></textarea>

						</td>
						<td>
							<%--     Creature:<br/>
    <select name="creature" class="input">
    <% List<Creature> ithCrits = (List<Creature>)request.getAttribute("itherian_creatures"); 
    for (Creature crit : ithCrits) {
        %>  <option value="<%= crit.getId() %>"><%= crit.getName() %></option><%
    }
    %>
    </select>
</td><td> --%> Item:<br /> <select name="item" class="input">
								<%
									HashMap<String, Integer> ithItems = (HashMap<String, Integer>) request
													.getAttribute("add_itherian_item");
											for (String item : ithItems.keySet()) {
								%>
								<option><%=item%></option>
								<%
									}
								%>
						</select>

						</td>
					</tr>
				</table>
				<br /> <br /> <input type="submit" id="sbmIth" class="input" />
			</form>
			
			
			
			
			
<br /> <br />
<br /> <br />
            <table width="100%">
                <tr>
                    <td class="titleline" width="100%"><font class="head"
                        size="-1">Delete itherian</font></td>
                </tr>
            </table>
            <br/>
            <br/>
<form method="post" action="?page=itherian&action=deleteIth"
                id="frmAmulets" onSubmit="return fixForm('tblAmulets')">
                                My items:<br /> <select name="ownItem2" class="input">
                    <%
                        List<Itherian> MyIthItem2 = (List<Itherian>) request.getAttribute("itherian_items_user");
                        for (Itherian ith : MyIthItem2) {
                    %>
                    <option value="<%=ith.getCq2ID()%>"><%=ith.getItemname() + " (id: " + ith.getCq2ID() + ")"%></option>
                    <%
                        }
                    %>
                
                <input type="submit" id="sbmIth" class="input" />
                </form>
		</div>

	</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp"%>

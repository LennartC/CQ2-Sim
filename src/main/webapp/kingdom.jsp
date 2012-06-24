<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="be.lacerta.cq2.utils.StringUtils" %>
<%@page import="be.lacerta.cq2.utils.CQ2Functions" %>
<%@page import="be.lacerta.cq2.sim.hbn.*" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.*" %>
<%@ include file="include/init.jsp" %>
<%@ include file="include/header.jsp" %>
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>
<%@ include file="include/menu.jsp" %>

<div class="porlet" id="p-content">

<c:if test="${requestScope.message !=null}">
	<div class="message">${requestScope.message}</div>
</c:if>
<zod:Title title="Kingdoms" href="addforms.jsp?form=parsekd" rel="facebox" reftext="Update a kingdom" subtitle="Search"/>

<form action="" method="GET">
	<input type="hidden" name="page" value="kingdom"/>
	<input type="text" name="kingdom" class="input"/>
	<input type="submit" class="input" value="Search"/>
</form>


<c:choose>
	<c:when test="${requestScope.kingdom == null}">
		<br/>
		<table width="100%">
		    <tr>
		        <td class="titleline" width="100%">
					<font class="head" size="-1">Kingdoms</font>
		        </td>
		    </tr>
		</table>
		<table width="500px" style="padding-left: 10px" class="sortable">
			<thead>
				<tr align="left">
					<th style="padding-right: 5px">Name</th>
					<th style="padding-right: 5px">Level</th>
					<th style="padding-right: 5px">Inhabitants</th>
					<th style="padding-right: 5px">Last update</th>
				</tr>
			</thead>
			<tbody>
			  <% pageContext.setAttribute("kingdoms",Kingdom.getKingdoms()); %>
			  <c:forEach items="${kingdoms}" var="kingdom">
				  <tr>
				  	<td><zod:ProfileLink kingdom="${kingdom.name}"/></td>
				  	<td><c:if test="${kingdom.level !=null}">${kingdom.level}</c:if></td>
				  	<td>${fn:length(kingdom.inhabitants)}</td>
				  	<td>
				  	<c:choose>
					  	<c:when test="${kingdom.modified !=null}"><c:out value="${zod:getDaysOld(kingdom.modified)}" /></c:when>
					  	<c:otherwise>lots of</c:otherwise>
				  	</c:choose>
				  	days ago
					</td>
				  </tr>
			  </c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<c:set var="kingdom" value="${requestScope.kingdom}"/>
		<br/>
		<table width="100%">
		    <tr>
		        <td class="titleline" width="100%">
		        <font class="head" size="-1">Kingdom: ${kingdom.name}
		        </font>
		        </td>
		    </tr>
		</table>
		<font style="padding-left: 10px"><zod:ProfileLink kingdom="${kingdom.name}"/> is a level ${kingdom.level} kingdom.<br/></font>
		<br/>
		
		<table width="100%">
		  <tr>
		    <td class="titleline" width="100%"><font class="head">Inhabitants (${fn:length(kingdom.inhabitants)})</font></td>
		  </tr>
		</table>
		
		<table  width="400px" style="padding-left: 10px" class="sortable">
			<thead>
				<tr width="400px" align="left">
					<th style="padding-right: 5px">Name</th>
					<th style="padding-right: 5px">Class</th>
					<th style="padding-right: 5px">Level</th>
					<th style="padding-right: 5px">Reveal</th>
				</tr>
			</thead>
			<tbody>
			
			<c:forEach items="${kingdom.inhabitants}" var="mage">
			  <tr>
			  	<td><zod:ProfileLink mage="${mage.name}"/></td>
			  	<td>${mage.cq2class}</td>
			  	<td>${mage.level}</td>
			  	<td>
					<c:set var="reveal" value="${zod:getReveal(mage.name)}"/>
					<c:choose>
						<c:when test="${reveal != null}"><c:out value="${zod:getDaysOld(reveal.time)}" /> days old</c:when>
						<c:otherwise>none</c:otherwise>
					</c:choose>
			  	</td>
			  </tr>
		    </c:forEach>
			</tbody>
		</table>
		<br/>
		
		
		
		
		<!-- ITHERIANS -->
		<table width="100%">
		  <tr>
		    <td class="titleline" width="100%"><font class="head">Itherians</font></td>
		  </tr>
		</table>	
		
		<c:set var="totalForestIth" value="${0}"/>
		<c:set var="totalDeathIth" value="${0}"/>
		<c:set var="totalAirIth" value="${0}"/>
		<c:set var="totalEarthIth" value="${0}"/>
		
		<table  style="padding-left: 10px">
			<thead>
				<tr align="left">
					<th style="padding-right: 5px">Forest</th>
					<th style="padding-right: 5px">Death</th>
					<th style="padding-right: 5px">Air</th>
					<th style="padding-right: 5px">Earth</th>
				</tr>
			</thead>
			<tbody>
			<tr>
			<td style="vertical-align: top;">
			    <c:forEach items="${requestScope.kingdom_forestitherians}" var="creature" >
					${creature.ith}&nbsp;(<zod:ProfileLink mage="${creature.reveal.mage}"/>)<br/>
					<c:set var="totalForestIth" value="${totalForestIth + creature.ith}"/>
			    </c:forEach>
			</td>
			<td style="vertical-align: top;">
			    <c:forEach items="${requestScope.kingdom_deathitherians}" var="creature" >
					${creature.ith}&nbsp;(<zod:ProfileLink mage="${creature.reveal.mage}"/>)<br/>
					<c:set var="totalDeathIth" value="${totalDeathIth + creature.ith}"/>
			    </c:forEach>
			</td>
			<td style="vertical-align: top;">
			    <c:forEach items="${requestScope.kingdom_airitherians}" var="creature" >
					${creature.ith}&nbsp;(<zod:ProfileLink mage="${creature.reveal.mage}"/>)<br/>
					<c:set var="totalAirIth" value="${totalAirIth + creature.ith}"/>
			    </c:forEach>
			</td>
			<td style="vertical-align: top;">
			    <c:forEach items="${requestScope.kingdom_earthitherians}" var="creature" >
					${creature.ith}&nbsp;(<zod:ProfileLink mage="${creature.reveal.mage}"/>)<br/>
					<c:set var="totalEarthIth" value="${totalEarthIth + creature.ith}"/>
			    </c:forEach>
			</td>
			</tr>
			
			<tr align="left">
				<th style="padding-right: 5px"><c:out value="${totalForestIth}"/></th>
				<th style="padding-right: 5px"><c:out value="${totalDeathIth}"/></th>
				<th style="padding-right: 5px"><c:out value="${totalAirIth}"/></th>
				<th style="padding-right: 5px"><c:out value="${totalEarthIth}"/></th>
			</tr>
			</tbody>
		</table>
		
		
		
		<!-- CREATURES -->
		<table width="100%">
		  <tr>
		    <td class="titleline" width="100%"><font class="head">Creatures</font></td>
		  </tr>
		</table>
		
		<table  width="400px" style="padding-left: 10px" class="sortable">
			<thead>
				<tr align="left">
					<th style="padding-right: 5px">Name</th>
					<th style="padding-right: 5px">Amount</th>
					<th style="padding-right: 5px">Minimal enchant</th>
				</tr>
			</thead>
			<tbody>
		<%
		final Hashtable<String,Integer> creatureList = (Hashtable<String,Integer>)request.getAttribute("kingdom_creatures");
		Object[] creatures = creatureList.keySet().toArray();
		Arrays.sort(creatures, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				return creatureList.get(arg1).compareTo(creatureList.get(arg0));
			}  });
		
		int totalCrits = 0;
		for (Object creature : creatures) {
			totalCrits += creatureList.get(creature);
		}
		pageContext.setAttribute("creatures",creatures);
		pageContext.setAttribute("creatureList",creatureList);
		pageContext.setAttribute("totalCrits",totalCrits);
		 %>
		 
		    <c:forEach items="${creatures}" var="creature" >
			<tr>
				<td>${creature}</td>
				<td><c:out value="${creatureList[creature]}"/></td>
				<td><c:out value="${zod:calcMinimumEnchant(creatureList[creature],totalCrits)}"/></td>
			</tr>
		    </c:forEach>
		 
			</tbody>
		</table>		
		
		<!-- NETHERS -->
		<table width="100%">
		  <tr>
		    <td class="titleline" width="100%"><font class="head">Nethers</font></td>
		  </tr>
		</table>
		
	<table border="0" cellspacing="0" cellpadding="0">
		<c:forEach items="${requestScope.kingdom_nethers}" var="creature" >
		<tr>
			<td width="20%" style="padding-left: 5px">
				<zod:ProfileLink mage="${creature.reveal.mage}"/>
			</td>
			<td width="26%" align="left" style="padding-right: 5px">
			   ${creature.name}
			</td>
			<td width="22%" align="center" style="padding-right: 5px">
			   ${creature.race}/${creature.creatureClass} (N)
			</td>
			<td width="7%" align="center" style="padding-right: 5px">
				L${creature.level}
			</td>
			<td width="9%" align="center" style="padding-right: 5px">		
				${creature.damage}/${creature.health}
			</td>
			<td width="16%" align="left" style="padding-right: 5px">
				F${creature.forestDef}/D${creature.deathDef}/A${creature.airDef}/E${creature.earthDef}
			</td>
		</tr>
		</c:forEach>
	</table>
	
	
	</c:otherwise>
</c:choose>

</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
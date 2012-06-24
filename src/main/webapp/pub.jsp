<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/zod.tld" prefix="zod" %>
<%@page import="java.util.List"  
%><%@page import="java.util.Iterator"  
%><%@page import="java.text.SimpleDateFormat" 
%><%@page import="be.lacerta.cq2.utils.StringUtils"  
%><%@page import="be.lacerta.cq2.sim.hbn.*"  
%><%@include file="include/init.jsp" 
%><%@include file="include/header.jsp"
%>
<c:choose>
<c:when test="${!zod:hasAccess(user,Constants.RIGHTS_ALL)}">What are you doing here?</c:when>
<c:otherwise>

<c:set var="action" value="${requestScope.pub_action}"/>

<%@ include file="include/menu.jsp" %>

<div class="porlet" id="p-content">
<%-- ----------- --%>
<%-- LIST TOPICS --%>
<%-- ----------- --%>
<c:choose>
	<c:when test="${action == null || action eq ''}">
		<c:set var="baseurl" value="?page=pub&location=${requestScope.pub_location}&action="/>
		<div id="addtopic" style="display:none;">
		        <form method="post" action="?page=pub&action=newtopic&location=${requestScope.pub_location}">
		        <table border="0" cellpadding="0" cellspacing="0" width="100%">
		            <tr>
		                <td width="20%">Topic:</td>
		                <td width="80%"><input name="topic" class="input" size="40" type="text"></td>
		            </tr>
		            <tr>
		                <td valign="top" width="20%">Text:</td>
		                <td width="80%"><textarea name="text" class="input" style="width: 100%; height: 100px"></textarea></td>
		            </tr>
		            <tr>
		                <td></td>
		                <td><input class="input" value="Post" type="submit"></td>
		            </tr>
		        </table>
		        </form>
		</div>
		
		<center><a href="#addtopic" rel="facebox">Post New Topic</a> | <a href="${baseurl}allread">Mark all as read</a></center>
		<table cellpadding="1" cellspacing="0" width="100%" class="pubtable">
		    <tr width="100%">
		        <td style="text-align: left" width="30%">Topic</td>
		        <td align="center" width="10%">Replies</td>
		        <td align="center" width="20%">Last Reply</td>
		        <td align="center" width="20%">Started by</td>
		        <td align="center" width="20%">Date</td>
		     </tr>
			<c:if test="${requestScope.pub_topics != null}">
				<c:forEach items="${requestScope.pub_topics}" var="topic">
					<tr>
			          <td style="text-align: left">
			            <a href="${baseurl}view&topic=${topic.topicID}">
			            <c:if test="${zod:needsHighlight(topic,user)}">!&nbsp;</c:if>
			            <c:if test="${topic.closed}">X </c:if>
			            <c:if test="${topic.sticky}">STICKY: </c:if>
						${topic.name}
						</a>
						<c:if test="${topic.replies > Constants.POSTSPERPAGE}">
							<c:set var="page" value="1"/> 
							(<c:forEach var="index" begin="0" end="${topic.replies-1}" step="${Constants.POSTSPERPAGE}">
								<a href="${baseurl}view&topic=${topic.topicID}&offset=${index}">${page}</a>,
								<c:set var="page" value="${page+1}"/> 
							</c:forEach>
							<a href="${baseurl}view&topic=${topic.topicID}&offset=${(page-2)*Constants.POSTSPERPAGE}">last</a>)
						</c:if>
			          </td>
			          <td>${topic.replies}</td>
					  <td>${zod:getUserLink(topic.lastPoster)}</td>
			          <td>${zod:getUserLink(topic.user)}</td>
			          <td>${zod:formatDate(topic.started)}</td> 
			        </tr>
				</c:forEach>
			</c:if>
		</table>
	</c:when>
<%-- ------------------- --%>
<%-- VIEW TOPIC OR REPLY --%>
<%-- ------------------- --%>
	<c:when test="${action == 'view' || action eq 'newreply' || action eq 'edit'}">
		<c:set var="topic" value="${requestScope.pub_topic}"/>
		<c:set var="baseurl" value="?page=pub&location=${topic.location}&topic=${topic.topicID}&action="/>
		
		<c:if test="${!topic.closed}">
			<div id="addreply" style="display:none;">
			    <form method="POST" action="?page=pub&action=newreply">
			    <table border="0" cellpadding="0" cellspacing="0" width="100%">
			        <tr>
			            <td valign="top" width="20%">Reply:</td>
			            <td width="80%">
					       <input type="hidden" name="location" value="${topic.location}"/>
					        <input type="hidden" name="topic" value="${topic.topicID}"/>
					        <input type="hidden" name="offset" value="${requestScope.pub_offset}"/>
				            <textarea name="text" class="input" style="width: 100%; height: 300px"></textarea>
			            </td>
			        </tr>
			        <tr>
			            <td></td>
			            <td><input class="input" value="Post" type="submit"></td>
			        </tr>
			    </table>
			    </form>
			</div>
		</c:if>
	
		<c:if test="${action eq 'newreply' || action eq 'edit'}">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			    <tr>
			        <td width="20%"></td>
			        <td width="60%">
			        <form method="POST" action="?page=pub&action=${requestScope.pub_action}">
			        <input type="hidden" name="location" value="${topic.location}"/>
			        <input type="hidden" name="topic" value="${topic.topicID}"/>
			        <input type="hidden" name="offset" value="${requestScope.pub_offset}"/>
			
			        <table width="100%" border="0" cellspacing="0" cellpadding="0">
			
			            <tr>
			                <td width="10%" valign="top" style="text-align: right">Text:&nbsp;</td>
			                <td width="90%"  style="text-align: left">
<textarea name="text" class="input" style="width: 600px; height: 300px" tabindex="1">
<c:if test="${requestScope.pub_post !=null}">
<c:if test="${action ne 'edit'}">[quote][b]Quote from ${requestScope.pub_post.user.username}:[/b]
</c:if>
${requestScope.pub_post.post}
<c:if test="${action ne 'edit'}">[/quote]</c:if>
</c:if>
</textarea>
							</td>
			            </tr>
			
			            <tr>
			                <td></td>
			                <td>
			                <c:choose>
				                <c:when test="${action eq 'edit'}">
				                	 <input type="hidden" name="post" value="${requestScope.pub_post.postID}"/>
				                	 <input class="input" type="submit" name="submit" value="Edit" tabindex="2"/>
				                </c:when>
				                <c:otherwise>
				                	<input class="input" type="submit" name="submit" value="Reply" tabindex="2"/>
				                </c:otherwise>
			                </c:choose>
							</td>
			            </tr>
			        </table>
			
			        </form>
			        </td>
			        <td width="20%"></td>
			    </tr>
			</table>
		</c:if>
		
		<table border="0" cellpadding="1" cellspacing="0" width="100%">
		    <tr>
		        <td class="titleline">
		        <table border="0" cellpadding="0" cellspacing="0" width="100%">
		            <tr>
		                <td width="80%">
		                <b>Topic: ${topic.name}</b>
		                <c:if test="${zod:hasAccess(user,Constants.RIGHTS_PUBMOD)}">
		                (<a href="${baseurl}delete">delete</a>, 
		                 <a href="${baseurl}switchsticky">
		                 <c:choose>
		                 	<c:when test="${topic.sticky}">remove sticky status</c:when>
		                 	<c:otherwise>sticky</c:otherwise>
		                 </c:choose>
		                 </a>,
		                 <a href="${baseurl}switchclose">
		                 <c:choose>
		                 	<c:when test="${topic.closed}">open</c:when>
		                 	<c:otherwise>close</c:otherwise>
		                 </c:choose>
		                 </a>)
		                </c:if>
		                </td>
		                <td align="right" width="20%">
		                 <c:choose>
		                 	<c:when test="${topic.closed}">closed</c:when>
		                 	<c:otherwise>
		                 	<a href="#addreply" rel="facebox">quick reply</a> | 
		                 	<a href="${baseurl}newreply&offset=${requestScope.pub_offset}">reply</a></c:otherwise>
		                 </c:choose>
						</td>
		            </tr>
		            <tr height="3">
		                <td></td>
		            </tr>
		        </table>
		        </td>
		    </tr>
		</table>
		
		<c:set var="firstPost" value="true"/>
		<c:forEach items="${requestScope.pub_posts}" var="post">
		  <table class="titleline" border="0" cellpadding="3" cellspacing="0" width="100%">
		    
		    <tr height="3"> <td></td> </tr>
		
		    <tr>
		        <td style="border-width: 0pt 1px 1px;" valign="top" width="20%">
		        <b>
		        <font size="2">${zod:getUserLink(post.user)}</font><br/>
		        ${post.user.cq2class} mage<br/>
		        Level ${post.user.level}<br/>
		        </b>
		        </td>
		        
		        <td style="border-width: 0pt 1px 1px;" height="100%" valign="top" width="80%">
		        
		        <table border="0" cellpadding="0" cellspacing="0" height="100%" width="100%">
		            <tr>
		                <td colspan="2">${post.convertedPost}</td>
		            </tr>
		            <tr height="5"> <td></td> </tr>
		            <tr>
		                <td valign="bottom" width="65%">
		                <i><font size="1">
		                <c:if test="${post.editflag == 1 && post.edit != null}">Last edited ${zod:formatDate(post.edit)}</c:if>
		                posted on ${zod:formatDate(post.date)}
		                </font></i>
		                </td>
		                
		                <td align="right" valign="bottom" width="35%">
		                <c:if test="${zod:hasAccess(user,Constants.RIGHTS_PUBMOD) && !firstPost}">
		                <a href="${baseurl}delete&post=${post.postID}&offset=${requestScope.pub_offset}">delete</a> -
		                </c:if>
		                <c:set var="firstPost" value="false"/>
		                <c:choose>
		                 	<c:when test="${topic.closed}">closed</c:when>
		                 	<c:otherwise><a href="${baseurl}newreply&post=${post.postID}&offset=${requestScope.pub_offset}">reply with quote</a></c:otherwise>
		                </c:choose>
		                <c:choose>
		                	<c:when test="${post.user.id == user.id}">
		                	- <a href="${baseurl}edit&post=${post.postID}&offset=${requestScope.pub_offset}">edit</a>
		                	</c:when>
		                	<c:otherwise>
		                	<c:if test="${zod:hasAccess(user,Constants.RIGHTS_SUPERADMIN)}">
			                - <a href="${baseurl}edit&post=${post.postID}&offset=${requestScope.pub_offset}">edit</a>
			                </c:if>
		                	</c:otherwise>
		                </c:choose>
		                </td>
		                
		            </tr>
		        </table>
		        </td>
		    </tr>
		    
		    
		  </table>
		</c:forEach>
		
		
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		    <tbody>
		        <tr>
		            <td width="50%"><a href="?page=pub&location=${topic.location}">back to pub</a></td>
		            <td align="right" width="50%"><c:if test="${!topic.closed}"><a href="#addreply" rel="facebox">quick reply</a>&nbsp;|&nbsp;</c:if></td>
		            <td align="right" width="50%"><c:if test="${!topic.closed}"><a href="${baseurl}newreply&offset=${requestScope.pub_offset}">reply</a></c:if></td>
		        </tr>
		        <c:if test="${topic.replies > Constants.POSTSPERPAGE}">
		         	<tr>
			            <td colspan="3" align="right">Page: 
						<c:set var="page" value="1"/> 
						(<c:forEach var="index" begin="0" end="${topic.replies-1}" step="${Constants.POSTSPERPAGE}">
							<c:choose>
							<c:when test="${index eq requestScope.pub_offset}">
								<b><a href="${baseurl}view&topic=${topic.topicID}&offset=${index}">${page}</a></b>,
							</c:when>
							<c:otherwise>
								<a href="${baseurl}view&topic=${topic.topicID}&offset=${index}">${page}</a>,
							</c:otherwise>
							</c:choose>
							<c:set var="page" value="${page+1}"/> 
						</c:forEach>
						<a href="${baseurl}view&topic=${topic.topicID}&offset=${(page-2)*Constants.POSTSPERPAGE}">last&nbsp;page</a>)
						</td>
					</tr>
		        </c:if>
		    </tbody>
		</table>
	</c:when>
<%-- --------- --%>
<%-- NEW TOPIC --%>
<%-- --------- --%>
	<c:when test="${action == 'newtopic'}">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		    <tr>
		        <td width="20%">&nbsp;</td>
		        <td width="60%">
		        <form method="post"
		            action="?page=pub&action=newtopic&location=${requestScope.pub_location}">
		        <table border="0" cellpadding="0" cellspacing="0" width="100%">
		            <tr>
		                <td width="30%">Topic:</td>
		                <td width="70%"><input name="topic" class="input" size="40"
		                    type="text"></td>
		            </tr>
		            <tr>
		                <td valign="top" width="30%">Text:</td>
		                <td width="70%"><textarea name="text" class="input" rows="18"
		                    cols="60"></textarea></td>
		            </tr>
		            <tr>
		                <td></td>
		                <td><input class="input" value="Post" type="submit"></td>
		            </tr>
		        </table>
		        </form>
		        </td>
		    </tr>
		</table>
	</c:when>
</c:choose>
</div>

</c:otherwise>
</c:choose>
<%@ include file="include/footer.jsp" %>
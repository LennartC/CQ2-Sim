<%
String form = request.getParameter("form");

if (("addraid").equals(form)) {
%>
        <form method="post" action="?page=raid&action=addraid">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr><td>
                Copy/paste complete battle report (including the General Information):<br/>
                <textarea name="text" class="input"></textarea></td>
            </tr>
            <tr>
                <td><input class="input" value="Post" type="submit"></td>
            </tr>
        </table>
        </form>
<% } else
if (("addreveal").equals(form)) {
%>
		<form action="?page=reveal" method="POST">
	    Copy/paste from Player Information page (include skills!):<br/>
	    <textarea rows="5" class="input" name="general"></textarea>
	    <br/>
	    Reveal: <br/>
		<textarea rows="20" class="input" name="reveal"></textarea>
		<br/>
		<input type="hidden" name="action" value="add"/>
		<input type="submit" class="input" value="Add"/>
		</form>
<% } else
if (("addcurses").equals(form)) {
%>
		<form action="?page=curse" method="POST">
		Copy/paste active curses:<br/>
			<textarea class="input" name="spellbook"></textarea><br/>
		<input type="submit" class="input" value="Update">
		</form>
<% } else
if (("updatexp").equals(form)) {
%>
	    <form method="POST" action="?page=options&action=fromcharpage">
	    Copy/paste your character page:<br/>
	      <textarea class="input" name="charpage"></textarea><br/>
	      <input class="input" type="submit" name="submit" value="Update">
	    </form>
<% } else
if (("parsekd").equals(form)) {
%>
		<form method="post" action="?page=kingdom&action=parse">
		Copy/paste Kingdom Information page:<br/>
		<textarea name="kdpage" class="input" ></textarea><br/>
		<input type="submit" class="input" value="Update!">
		</form>
<% } %>



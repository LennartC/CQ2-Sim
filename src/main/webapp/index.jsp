<%@ include file="include/init.jsp" %>
<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Castle Quest 2: ZoD Database</title>
<link rel="stylesheet" href="<%= cq2url %>images/blue/cq.css">
<link rel="stylesheet" href="http://cq2.lacerta.be<%= context %>/common.css">
</head>

<body>


<table class="header" width="100%" border="0" cellpadding="0" cellspacing="0" background="<%= cq2url %>images/blue/header_back.gif">
<tr> 
<td width="93%"><img src="<%= cq2url %>images/blue/header_left.gif" width="296" height="87"></td>
<td width="7%" align="right"><img src="<%= cq2url %>images/blue/header_right.gif" width="141" height="87"></td>
</tr>

</table>

<br>

<table class="master" width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td width="90%" valign="top" align="center">


<!-- Content -->

<table width="75%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td width="20"><img src="<%= cq2url %>images/blue/c_lefttopcorner.gif" width="20" height="15"></td>
<td width="*" background="<%= cq2url %>images/blue/c_top.gif">&nbsp;</td>
<td width="21"><img src="<%= cq2url %>images/blue/c_righttopcorner.gif" width="20" height="15"></td>
</tr>

<tr>
<td width="20" background="<%= cq2url %>images/blue/c_left.gif">&nbsp;</td>
<td width="806">


        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
        <td class="title">
        Login
        
        </td>
        </tr>
        <tr>

        <td width="100%">
    
If you don't have an account for the database yet, please ask your king to make you one.
<br>If you already have an account, log in using the form below.<br><br>

<% if (request.getAttribute("message") != null) { %>
<div class="message"><%= request.getAttribute("message") %></div>
<% } %>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
        <td width="20%">
        </td>
        <td width="60%">
        <form method="post" action="<%= context %>/sim?action=login">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        
        <tr>
        <td width="30%">

        Login Name:
        </td>
        <td width="70%" align="left">
        <input type="text" name="username" class="input" size="20" tabindex="1">
        </td>
        </tr>
        
        <tr>
        <td width="30%">
        Password:
        </td>

        <td width="70%" align="left">
        <input type="password" name="password" class="input" size="20" tabindex="2">
        </td>
        </tr>
        
        <tr>
        <td>
        </td>
        <td>
        <input class="input" type="submit" name="submit" value="Login" tabindex="3">

        </td>
        </tr>
        </table>
        </form>
        </td>
        <td width="20%">
        </td>
        </tr>
        </table>
        
</td>
</tr>
</table>

</td>
<td width="21" background="<%= cq2url %>images/blue/c_right.gif">&nbsp;</td>
</tr>
<tr>

<td width="20"><img src="<%= cq2url %>images/blue/c_leftbottomcorner.gif" width="20" height="15"></td>
<td width="*" background="<%= cq2url %>images/blue/c_bottom.gif">&nbsp;</td>
<td width="21"><img src="<%= cq2url %>images/blue/c_rightbottomcorner.gif" width="20" height="15"></td>
</tr>
</table>

<!-- /Content -->

</td>
</tr>
</table>



<table class="footer" width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td align="center">


    CQ2 ZoD db v0.1 © <a href="<%= cq2url %>index.php?page=playerinfo&action=viewinfo&name=mortician">mortician</a><br/>
    
</td>
</tr>
</table>


</body>
</html>
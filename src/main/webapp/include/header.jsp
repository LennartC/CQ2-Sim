<%@page import="be.lacerta.cq2.utils.CQ2Functions"  %>
<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link rel="shortcut icon" href="/tomcat.ico" />
	<title>ZoD</title>
	<script language="JavaScript" src="http://cq2.lacerta.be/js/scripts.js"></script>
	<link rel="stylesheet" type="text/css" href="<%= imagepack %>images/blue/cq.css"/>
	<link rel="stylesheet" type="text/css" href="<%= context %>/common.9.css"/>
	<link rel="stylesheet" type="text/css" href="<%= context %>/styles/facebox.css" media="screen"/>
	<link rel="stylesheet" type="text/css" href="<%= context %>/styles/cluetip.css" media="screen"/>

	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="<%= context %>/libs/jquery-1.7.1.min.js"><\/script>')</script>
	
	<script type="text/javascript" src="<%= context %>/scripts/jquery.hoverIntent.js"></script>
	<script type="text/javascript" src="<%= context %>/scripts/facebox.js"></script>
	<script type="text/javascript" src="<%= context %>/scripts/jquery.cluetip.js"></script>
	<script type="text/javascript" src="<%= context %>/scripts/jquery.tablekit.js"></script>
	<script type="text/javascript">
	  jQuery(document).ready(function($) {
	    $('a[rel*=facebox]').facebox({
	      loading_image : '<%= context %>/images/loading.gif',
	      close_image   : '<%= context %>/images/closelabel.gif'
	    });
	    $('a[rel*=cluetip]').cluetip({ 
	    	sticky: true,
	    	arrows: true, 
	    	mouseOutClose: true,
	    	showTitle: false,
	    	splitTitle: '#',
	    	closePosition: 'title',
	    	width: 'auto'
	    });
	  })
	</script>
	<script type="text/javascript" src="<%= context %>/dwr/interface/SimAjax.js?version=2"></script>
	<script type="text/javascript" src="<%= context %>/dwr/engine.js"></script>
	<script type="text/javascript" src="<%= CQ2Functions.URL %>images/blue/overlib/overlib.js"></script>
	<script type="text/javascript" src="<%= context %>/scripts/scripts.js"></script>
</head>
<body>
<div id="overDiv" style="position: absolute; visibility: hidden; z-index: 1000;"></div>
<img class="background" src="<%= imagepack %>images/blue/header_back.gif" />
<table width="100%" class="backgroundtop" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="50%">
        <a href="<%= context %>/sim" border="0" class="backgroundtop"><img src="<%= imagepack %>images/blue/header_left.gif" height="43" border="0"></a>
    </td>
    <td width="50%" align="right">
        <img src="<%= imagepack %>images/blue/header_right.gif" height="43">
    </td>
  </tr>
</table>
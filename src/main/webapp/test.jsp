<html>
<head>
<title>Test</title>
<style type="text/css">
 body {
 	width:100%;
 	height:100%;
 	padding: 0;
 	margin: 0;
 	background-color:#2B292A;
 	background-image:url("containerbg.jpg");
 	background-repeat:repeat-x;
 	text-align: center;
 	vertical-align: middle;
 }
 #container {
 	width: 900px;
 }
 #left {
 	float: left;
 	background-image:url("shadowleft.png");
 	background-repeat:repeat-y;
 	width: 12px;
 }
 #center {
 	float: left;
 	background-color:#2F2F2F;
 	width: 876px;
 }
 #right {
 	width: 12px;
 	background-image:url("shadowright.jpg");
 	background-repeat:repeat-y;
 	height:100%;
 }
</style>
</head>
<body>
<div id="container">
	<div id="top"></div>
	<div id="middle">
		<div id="left">a</div>
		<div id="center">
		
			tralalala	
			
		</div>
		<div id="right"></div>
	</div>
	<div id="bottom"></div>
</div>
</body>
</html>
<?php
session_start();
if(!isSet($_SESSION['s-user-id'])){
	header('Location: login.php');
}
?>


<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="icon" href="../res/img/my_icon.png" type="image/png"/>
		
		<title>CRIL-a</title>
		
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
		<script src="../libs/notify.min.js"></script>
		<script src="../script/sentences.js"></script>
		<script src="../script/util.js"></script>
	</head>

	<body onload= "beginningSet()" onclose="unexpectedClose()">
		<div class="container" style="display:block;max-width:320px;">
			
		</div>
	</body>
	
</html>
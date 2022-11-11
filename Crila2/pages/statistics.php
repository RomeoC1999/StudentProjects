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
		<script src="../script/statistics.js"></script>
		<script src="../script/util.js"></script>
		
		<!-- Datepicker -->
		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script> 
  
	</head>

	<body onload= "beginningSet()" onclose="unexpectedClose()">
	
		<div id= "s-loader" style="display:block">
			<span>Loading...</span>
		</div>
		
		<div id= 's-div-table' class="container" style="display:none;max-width:600px;margin-top:20px;padding:8px">
		
			<div class="table-responsive">
				<table id="s-table" class="table" align="center">
					<tbody id="s-table-body">
						<tr>
							<td colspan="4"><h3>Statistics</h3></td>
						</tr>
						<tr>
							<td>Start: </td>
							<td colspan="3"><input type="text" id="s-start-date" maxlength= "10"></td>
						</tr>
						<tr>
							<td>End: </td>
							<td colspan="3"><input type="text" id="s-end-date"></td>
						</tr>
						<tr>
							<td colspan="3">Game time: </td>
							<td id="s-game-time">0</td>
						</tr>
						<tr>
							<td colspan="3">Number of tests: </td>
							<td id="s-num-sessions">0</td>
						</tr>
						<tr>
							<td colspan="3">Number of questions: </td>
							<td id="s-num-questions">0</td>
						</tr>
						<tr>
							<td colspan="3">Average daily questions: </td>
							<td id="s-avg-questions-day">0</td>
						</tr>
						<tr>
							<td colspan="3">Average result: </td>
							<td id="s-avg-correct-day" >0</td>
						</tr>
						<tr>
							<td colspan="4">
							<a href="profile.php">
								<input type="button" id= "s-back-button" class="btn btn-secondary btn-block" value="Go back">
							</a>
							</td>
						</tr>
						<tr>
							<td colspan="4">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="4"><h3>Tests</h3></td>
						</tr>
						<tr>
							<th style="text-align:left">Date</th>
							<th style="text-align:left" colspan="2">Result</th>
							<th style="text-align:left">Time</th>
						</tr>
					</tbody>
					
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
				</table>
			</div>
		</div>
		
	</body>
	
</html>
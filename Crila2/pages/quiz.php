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
		<script src="../script/quiz.js"></script>
		<script src="../script/util.js"></script>
		
		<!-- Table -->
		<!-- https://stackoverflow.com/questions/51932614/reusable-bootstrap-modal-to-confirm-deletion -->
		
	</head>

	
	<body onload= "beginningSet()" onclose="unexpectedClose()">
	
		<div class="container" style="max-width:600px;margin-top:20px;padding:8px">
			
			<div id= "q-loader" style="display:block">
				<span>Loading...</span>
			</div>
			
			<form id="q-quiz" style="display:none;">
				<h3>Traslate</h3>
				<div class="form-group">
					<p id="l-n-question">Question 1</p>
				</div>
				<div class="form-group">
					<input type="text" id="q-ita" disabled  maxlength="30" class="form-control">
				</div>
				<div class="form-group">
					<input type="text" id="q-eng" autofocus placeholder="Traslate..."  maxlength="30" class="form-control">
				</div>
				<div id="q-last-el" class="form-group">
					<input type="button" id="q-next" onClick="nextQuestion()", value="Go on" class="btn btn-primary btn-block"><br>
					<div class="float-left">
						<input type="button" id="q-end" onclick="endSession()" value="Stop" class="btn btn-danger"/>
					</div>
					<div class="float-right">
						<input type="button" id="q-timer" value="00 : 00" class="btn btn-basic" />
					</div>
				</div>
			</form>	
			
			<div id="q-summary" style="display:none;">
				<h3>Summary</h3>
				<h5 id="q-result"></h5>
				<table class="table">
					<thead>
						<tr class="thead-dark">
							<th style="text-align:left" scope="col">#</th>
							<th style="text-align:left" scope="col">Requested</th>
							<th style="text-align:left" scope="col">Answer</th>
							<th style="text-align:left" scope="col">Correct</th>
						</tr>
					</thead>
					<tbody id="q-table">
						<!-- Javascript inserts here the results -->
					</tbody>
				</table>
				<input type="button" onClick="goBack()", value="Go back" class="btn btn-dark btn-block"><br>
			</div>
			
			
			<div id="q-modal" class="modal fade" role="dialog">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5>Attention!</h5>
							<button type="button" class="close" data-dismiss="modal">&times;</button>
						</div>
						<div class="modal-body">
							<p>You have to answer at least <span id="q-modal-min-sentences"></span> questions to end the test</p><br>
							<p>Otherwise the current work will be lost</p><br>
						</div>
						<div class="modal-footer">
							<div class="float-left">
								<button type="button" class="btn btn-default" data-dismiss="modal">Go back to the quiz</button>
							</div>
							<div class="float-right">
								<button type="button" onClick="goBack()" class="btn btn-danger" data-dismiss="modal">
									<span class="glyphicon glyphicon-remove"></span>End</button>
							</div>
						</div>
					</div>
				</div>
			</div>
	  
		</div>
		
	</body>
</html>
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
		
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
		<script src="../libs/notify.min.js"></script>
		<script src="../script/table.js"></script>
		<script src="../script/util.js"></script>
		
		<!-- Problems with Safari -->
		<style>
		.row:before, .row:after {width:0px; height:0px; display: inline-block;}
		.table-responsive, .table, .table-head, .table-body, .tr, .td, .th {white-space:normal}
		</style>
		
	</head>

	<body onload= "beginningSet()" onclose="unexpectedClose()">
	
		<div id= "t-loader" style="display:block">
			<span>Loading...</span>
		</div>
			
		<div id='t-container' class="container" style="display:none;max-width:600px;margin-top:20px;padding:8px">
			<h1 style="text-align:center">List</h1>
			<br>
			<div>
				<div id="normal-btn-group" style="display:block">
					<div class="row" style="margin:0px">
						<a id="t-go-back" href="profile.php" class="btn btn-secondary col-xs-6 btn-lg" style="border-radius:0px">
							<span class="glyphicon glyphicon-arrow-left"></span>&nbsp;Go back</a>
						<a id="t-add" href="#" onClick="openEditModal()" class="btn btn-secondary col-xs-6 btn-lg" style="border-radius:0px">
							<span class="glyphicon glyphicon-plus-sign"></span>&nbsp;Add</a>
					</div>
					<div class="row" style="margin:0px">
						<div class="dropdown col-xs-6" style="padding:0px">
							<button class="btn btn-secondary btn-lg btn-block" data-toggle="dropdown" style="border-radius:0px"> 
								<span class="glyphicon glyphicon-th-list"></span>&nbsp;Sort by
							</button>
							<div class="dropdown-menu">
								<a onClick="order(0)" class="dropdown-item btn-lg" href="#">Random</a>
								<a onClick="order(7)" class="dropdown-item btn-lg" href="#">Your Language</a>
								<a onClick="order(8)" class="dropdown-item btn-lg" href="#">English</a>
								<a onClick="order(1)" class="dropdown-item btn-lg" href="#">Priority</a>
								<a onClick="order(2)" class="dropdown-item btn-lg" href="#">Requested</a>
								<a onClick="order(3)" class="dropdown-item btn-lg" href="#">Correct</a>
								<a onClick="order(5)" class="dropdown-item btn-lg" href="#">Wrong</a>
								<a onClick="order(4)" class="dropdown-item btn-lg" href="#">Success</a>
								<a onClick="order(6)" class="dropdown-item btn-lg" href="#">Date</a>
							</div>
						</div>
						<a href="#" onClick="reverseOrder()" class="btn btn-secondary col-xs-6 btn-lg" style="border-radius:0px">
							<span class="glyphicon glyphicon-repeat"></span>&nbsp;Reverse</a>
					</div>
				</div>
				<div id="selection-btn-group" style="display:none">
					<a id="t-go-back" href="profile.php" class="btn btn-secondary col-sm-3 btn-lg">
						<span class="glyphicon glyphicon-arrow-left"></span>&nbsp;Go back</a>
					<a id="t-selection" href="#" onClick="changeSelectionMode()" class="btn btn-secondary col-sm-3 btn-lg">
						<span class="glyphicon glyphicon-unchecked"></span>&nbsp;Cancel</a>
					<a id="t-add" href="#" onClick="changePrioritySelected()" class="btn btn-secondary col-sm-3 btn-lg">
						<span class="glyphicon glyphicon-star"></span>&nbsp;Priority</a>
					<a id="t-add" href="#" onClick="openConfirmDeleteModal()" class="btn btn-secondary col-sm-3 btn-lg">
						<span class="glyphicon glyphicon-trash"></span>&nbsp;Delete</a>
				</div>
			</div>
			<div class="table-responsive">
				<table id="t-table" class="table" align="center">
					<br><br>
					<thead>
						<tr>
							<th style="text-align:left">Your Lang</th>
							<th style="text-align:left">English</th>
							<th style="text-align:left"><span class='glyphicon glyphicon-star'></span></th>
							<th style="text-align:left" colspan="2">Score</th>
						</tr>
					</thead>
					<tbody id="t-table-body">
						
					</tbody>
				</table>
			</div>
		</div>
		
		<div class="modal fade" id="t-form-modal" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h3 id="t-form-h">Edit</h3>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<div class="modal-body">
						<form>
							<div class="form-group">
								<label class="btn-lg" for="t-form-ita">Sentence: </label>
								<input type="text" id="t-form-ita" placeholder="Your language"  maxlength="30" class="form-control btn-lg">
							</div>
							<div class="form-group">
								<label class="btn-lg" for="t-form-eng1">First traslation: </label>
								<input type="text" id="t-form-eng1" placeholder="First traslation"  maxlength="30" class="form-control btn-lg">
							</div>
							<div class="form-group">
								<label class="btn-lg" for="t-form-eng2">Second traslation: </label>
								<input type="text" id="t-form-eng2" placeholder="Second traslation"  maxlength="30" class="form-control btn-lg">
							</div>
							<div class="form-group">
								<label class="btn-lg" for="t-form-eng3">Third traslation: </label>
								<input type="text" id="t-form-eng3" placeholder="Third traslation"  maxlength="30" class="form-control btn-lg">
							</div>
							<div class="form-group">
								<label class="btn-lg" for="t-form-priority">Priority:</label>
								<select id="t-form-priority" class="form-control">
									<option>1</option>
									<option>2</option>
									<option>3</option>
								</select>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						
						<button type="button" onClick="deleteSentence()" class="btn btn-danger btn-lg" id="t-form-del-btn" data-dismiss="modal">
								<span class="glyphicon glyphicon-trash"></span>Delete</button>
						<button type="button" class="btn btn-default btn-lg" data-dismiss="modal">
								<span class="glyphicon glyphicon-arrow-left"></span>Cancel</button>
						<button type="button"  onClick="saveOpenedSentence()" class="btn btn-primary btn-lg">
								<span class="glyphicon glyphicon-floppy-disk"></span>Save</button>
								
					</div>
				</div>
			</div>
		</div>
		
		<div id="t-del-modal" class="modal fade" role="dialog">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5>Attention!</h5>
							<button type="button" class="close" data-dismiss="modal">&times;</button>
						</div>
						<div class="modal-body">
							<p class="p-lg">Delete this sentence? <span id="t-n-selected"></span></p>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default " data-dismiss="modal" style="float:left">Cancel</button>
							<button type="button" onClick="confirmDelete()" class="btn btn-danger" data-dismiss="modal" style="float:left">
									<span class="glyphicon glyphicon-trash"></span>Delete</button>
						</div>
					</div>
				</div>
		
	</body>
	
</html>












<!DOCTYPE html>


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
		<script src="http://crypto-js.googlecode.com/svn/tags/3.1.2/build/rollups/sha512.js"></script>
		<script src="../libs/notify.min.js"></script>
		<script src="../script/login.js"></script>
		<script src="../script/util.js"></script>
	</head>
	
	<body onload= "beginningSet()">
		<div class="container">
			<form id="l-form" style="display:block;max-width:600px;margin-top:20px;padding:8px">
				<h3 id= "l-h">Login</h3>
				<div class="form-group">
					<label for="l-user">Username: </label>
					<input type="text" id="l-user" placeholder="Insert username..."  maxlength="30" class="form-control">
				</div>
				<div class="form-group">
					<label for="l-pwd"><span>Password</span>:</label>
					<input type="password" id="l-pwd" placeholder="Insert password..." maxlength= "30" class="form-control">
				</div>
				<div class="form-group" id="l-reg-possibility" style="display:block">
					<input type="button" id="l-loginButton" onclick="login()" value="Login" class="btn btn-primary btn-block"/>
					
					<br><label for="l-regButton" class="text-dark">Don't you have an account?</label>&nbsp;
					<input id="l-regButton" type="button" onclick="showSignin()" value="Register" class="btn btn-secondary" style="float:right"/><br>
					
					<br><label for="l-host-login" class="text-dark">Or</label>&nbsp;
					<input id="l-host-login" type="button" onclick="hostLogin()" value="Enter as host" class="btn btn-secondary" style="float:right">
					
				</div>
				<div class="form-group" id="l-log-possibility" style="display:none">
					<input type="button" id="l-registerButton" onclick="signin()" value="Register" class="btn btn-primary btn-block"/>
					<br/><label for="l-logButton" class="text-dark">Do you already have an account?</label>&nbsp;
					<input id="l-logButton" type="button" onclick="showLogin()" value="Login" class="btn btn-secondary" style="float:right"/>
				</div>
			</form>
		</div>
	</body>
	
</html>


function beginningSet(){
	makeKeyboardBridge('l-user', 'l-pwd');
	makeKeyboardAction('l-pwd', login);
	document.getElementById('l-user').focus();	
}



function checkFields(u, p){
	
	if(u=="" || p==""){
		if(u=="") setNotify("l-user", "Username field cannot be empty", "error");
		if(p=="") setNotify("l-pwd", "Password field cannot be empty", "error");
		return false;
	}
	if(u.includes(" ")){
		setNotify("l-user", "Username field cannot contain spaces", "error");
		return false;
	}
	if(u.length>USERNAME_MAX_LENGTH || p.length>PWD_MAX_LENGTH){
		if(u.length>USERNAME_MAX_LENGTH) setNotify("l-user", "Username cannot have more than "+USERNAME_MAX_LENGTH+" characters", "error");
		if(p.length>PWD_MAX_LENGTH)	     setNotify("l-pwd", "Your password cannot have more than "+PWD_MAX_LENGTH+" characters", "error");
		return false;
	}
	return true;
}


function login(){
	
	var u= document.getElementById("l-user").value;
	var p= document.getElementById("l-pwd").value;
	
	if(checkFields(u,p)){
		$.ajax({
			url: '../php/index.php',
			type: 'POST',
			data: {
				user: u,
				pwd: p,
				op: "userInfo"  
			} ,
		success: function(response){
			if(response.includes('__err__')){
				console.log(response);
				return;
			}
			var res=JSON.parse(response);
			if(res=="NoUser"){
				setNotify('l-loginButton','Username not found','error');
			}else if(res=="NoPwd"){
				setNotify('l-loginButton','Wrong password','error');
			}else{
				localStorage.setItem("c-user-id", res['user_id']);
				localStorage.setItem("c-username", res['username']);
				localStorage.setItem("c-time-signin", res['time_signin']);
				makeLogin(u,p);
			}
		} ,
		error: function(response){
			setNotify('l-loginButton','Impossible to connect to the database','error');
		}
		});
	}
	
}


function makeLogin(u,p){
	$.ajax({
		url: '../php/index.php',
		type: 'POST',
		data: {
			user: u,
			pwd: p,
			op: "makeLogin"  
	} ,
	success: function(response){
		if(response.includes("__err__")){
			printError('login', 'makeLogin', response);
		}else{
			document.location.href= "../pages/profile.php";
		}
	} ,
	error: function(response){
		setNotify('l-loginButton','Impossible to connect to the database','error');
		console.log(response);
	}
	});
}


function signin(){
	
	var u= document.getElementById("l-user").value;
	var p= document.getElementById("l-pwd").value;
	
	if(p.length<PWD_MIN_LENGTH){
		setNotify('l-registerButton','Password must contain at least ' + PWD_MIN_LENGTH + ' characters','error');
		return;
	}
		
	if(checkFields(u,p)){
		$.ajax({
			url: '../php/index.php',
			type: 'POST',
			data: {
				user: u,
				pwd: p,
				op: "createAccount"  
			} ,
		success: function(response){
			if(response=="InvalidUsername"){
				setNotify('l-registerButton','Username already used','error');
			}else{
				if(response.includes("__err__")){
					printError('login', 'signin', response);
					setNotify('l-registerButton','Error in the request to the database','error');
				}else{
					localStorage.setItem("c-user-id", parseInt(response));
					localStorage.setItem("c-username", u);
					localStorage.setItem("c-time-signin", new Date().getTime());
					makeLogin(u,p);
				}
			}
		} ,
		error: function(response){
			setNotify('l-registerButton','Impossible to connect to the database','error');
		}
		});
	}
	
}


function hostLogin(){
	document.getElementById("l-user").value= "host";
	document.getElementById("l-pwd").value= "host";
	login();
}


function showLogin(){
	document.getElementById("l-h").innerHTML= "Login";
	document.getElementById("l-log-possibility").style.display="none";
	document.getElementById("l-reg-possibility").style.display="block";
}


function showSignin(){
	document.getElementById("l-h").innerHTML="Register";
	document.getElementById("l-log-possibility").style.display="block";
	document.getElementById("l-reg-possibility").style.display="none";
}








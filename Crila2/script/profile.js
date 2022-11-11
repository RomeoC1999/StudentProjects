



function beginningSet(){
	if(localStorage.getItem('c-username') && isHost()==false){
		document.getElementById("p-h").innerHTML= "Welcome "+localStorage.getItem("c-username");
	}else{
		document.getElementById("p-h").innerHTML= "Welcome";
	}
}


function unexpectedClose(){
	
}


function exit(){
	deleteCookies();
	closeSession();
	document.location.href= "../pages/login.php";
}
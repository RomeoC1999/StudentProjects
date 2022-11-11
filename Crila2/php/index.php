<?php


	require "account.php";
	
	$conn = mysqli_connect("localhost","root","root","my_crila");
	if ($conn->connect_error) {
		$ret= array();
		$ret[0]= "Failed to connect to MySQL: " .$conn->connect_error;
		echo json_encode(mysqli_error($conn)."__err__ Errore di connesione al database");
	}
	
	session_start();
	$op=$_REQUEST['op'];
	$id= isSet($_SESSION['s-user-id']) ? intval($_SESSION['s-user-id']) : 0;
	$err= false;
	
	if(isSet($_SESSION['s-hash-login'])){
		if(getUserHash($conn, $id)!=$_SESSION['s-hash-login']){
			echo json_encode("__err__ __LoginTwice__ -> Session: ".$_SESSION['s-hash-login']." Dal db -> ".getUserHash($conn, $id));
			$err=true;
		}
	}
	
	if($err==false){
		
		switch ($op){
			case "userInfo" :
				echo json_encode(userInfo($conn,$_REQUEST['user'],$_REQUEST['pwd']));
				break;
			case "makeLogin" :
				echo json_encode(makeLogin($conn,$_REQUEST['user'],$_REQUEST['pwd']));
				break;
			case "createAccount" :
				echo json_encode(createAccount($conn,$_REQUEST['user'],$_REQUEST['pwd']));
				break;
			case "dataUser" :
				echo json_encode(dataUser($conn, $id));
				break;
			case "sentences" :
				echo json_encode(sentences($conn, $id,$_REQUEST['ord']));
				break;
			case "sessions" :
				echo json_encode(sessions($conn, $id));
				break;
			case "updateSentences" :
				echo json_encode(updateSentences($conn, $id, json_decode($_REQUEST['rows'], true)));
				break;
			case "addSession" :
				echo json_encode(addSession($conn, $id, $_REQUEST['questions'], $_REQUEST['rights'], $_REQUEST['start_time'], $_REQUEST['end_time']));
				break;
			case "deleteSession" :
				echo json_encode(deleteSession($conn, $id));
				break;
			case "editSentence" :
				echo json_encode(editSentence($conn,$id,$_REQUEST['id'],$_REQUEST['ita'],$_REQUEST['eng1'],$_REQUEST['eng2'],$_REQUEST['eng3'],$_REQUEST['pri']));
				break;
			case "userId" :
				echo json_encode($_SESSION['s-user-id']);
				break;
				
		}
	}
	
	
?>
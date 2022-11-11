<?php

	
	function editSentence($conn, $u_id, $s_id, $ita, $eng1, $eng2, $eng3, $pri){
		$ita= mysqli_real_escape_string($conn, $ita);
		$eng1= mysqli_real_escape_string($conn, $eng1);
		$eng2= mysqli_real_escape_string($conn, $eng2);
		$eng3= mysqli_real_escape_string($conn, $eng3);
		$t= round(microtime(true)*1000);
		
		if($s_id==(-1)){
			$q= "INSERT INTO sentence (user_id, time_insert, ita, eng1, eng2, eng3, priority) VALUES ($u_id, $t, '$ita', '$eng1', '$eng2', '$eng3', $pri)";
		}else{
			if($pri==(-1)){
				$q= "DELETE FROM sentence WHERE user_id=$u_id AND sentence_id=$s_id";
			}else{
				$q= "UPDATE sentence SET ita='$ita', eng1='$eng1', eng2='$eng2', eng3='$eng3', priority=$pri WHERE user_id=$u_id AND sentence_id=$s_id";
			}
		}
		
		$flag= mysqli_query($conn, $q);
		if($flag)	return "";
		else		return "__err__ ".mysqli_error($conn);
	}
	
	function updateSentences($conn, $id, $rows){
		
		if(count($rows)==0){
			return "__err__\n account.php/updateSentences Array rows vuoto";
		}
		
		for($i=0; $i<count($rows); $i=$i+1){
			
			$sid= $rows[$i]['sentence_id'];
			$p= $rows[$i]['priority'];
			$nt= $rows[$i]['num_test'];
			$nok= $rows[$i]['num_ok'];
			$ita= $rows[$i]['ita'];
			$eng1= $rows[$i]['eng1'];
			$eng2= is_null($rows[$i]['eng2']) ? "null" : $rows[$i]["eng2"];
			$eng3= is_null($rows[$i]['eng3']) ? "null" : $rows[$i]["eng3"];
			
			$ita= mysqli_real_escape_string($conn, $ita);
			$eng1= mysqli_real_escape_string($conn, $eng1);
			$eng2= mysqli_real_escape_string($conn, $eng2);
			$eng3= mysqli_real_escape_string($conn, $eng3);
			
			$q= "UPDATE sentence SET priority=$p , num_test=$nt , num_ok=$nok , ita='$ita' , eng1='$eng1' , eng2='$eng2' , eng3='$eng3' WHERE sentence_id = $sid AND user_id = $id";
			
			$flag= mysqli_query($conn, $q);
			if($flag == false){
				return "__err__ ".mysqli_error($conn);
			}
		}
		
	}
	
	function addSession($conn, $id, $questions, $rights, $start_time, $end_time){
		$numQ= count(explode('_', $rights));
		$numR= substr_count($rights, '1');
		$q= "INSERT INTO session VALUES ($id, NULL, '$questions', '$rights', $numQ, $numR, $start_time, $end_time)";
		$flag= mysqli_query($conn, $q);
		if($flag == false){
			return "__err__\n'$q'";
		}
	}
	
	function getUserHash($conn, $id){
		$q= mysqli_query($conn, "SELECT hash_login FROM user WHERE user_id=$id");
		if($q && mysqli_num_rows($q)==1){
			$arr= mysqli_fetch_assoc($q);
			return $arr['hash_login'];
		}else{
			return "-1";
		}
	}
	
	function dataUser($conn, $id){
    
    	$ret= array();
    	$res= mysqli_query($conn, "SELECT username, time_signin  FROM user WHERE user_id=$id");
		if(mysqli_num_rows($res)==0){
			$ret['user_id']= 0;
            
		}else{
        	$arr=mysqli_fetch_assoc($res);
            $ret['user_id']= $id;
            $ret['username']= $arr['username'];
            $ret['time_signin']= $arr['time_signin'];
            
            $res= mysqli_query($conn, "SELECT COUNT(*) AS num_sessions FROM session WHERE user_id=$id");
            $arr=mysqli_fetch_assoc($res);
            $ret['num_sessions']= $arr['num_sessions'];
            
            $res= mysqli_query($conn, "SELECT COUNT(*) AS num_sessions FROM sentence WHERE user_id=$id");
            $arr=mysqli_fetch_assoc($res);
            $ret['num_sentences']= $arr['num_sentences'];
            
            return $ret;
        }
    }
	
	function sentences($conn, $id, $ord){
		$q= "SELECT * FROM sentence WHERE user_id=$id ";
		switch(abs(intval($ord))){
			case 1: $q= $q."ORDER BY priority"; break;
			case 2: $q= $q."ORDER BY num_test"; break;
			case 3: $q= $q."ORDER BY num_ok"; break;
			case 4: $q= $q."ORDER BY num_ok/num_test"; break;
			case 5: $q= $q."ORDER BY num_test-num_ok"; break;
			case 6: $q= $q."ORDER BY time_insert"; break;
			case 7: $q= $q."ORDER BY ita"; break;
			case 8: $q= $q."ORDER BY eng1"; break;
		}
		if(intval($ord)<0) $q= $q." DESC";
		$flag= mysqli_query($conn, $q);
		if(!$flag){
			return "__err__  $q   ".mysqli_error($conn);
		}else{
			return rowsToArray($flag);
		}
    }
	
	function sessions($conn, $id){
		$q= "SELECT * FROM session WHERE user_id=$id ORDER BY start_time DESC";
		$flag= mysqli_query($conn, $q);
		if($flag){
			return rowsToArray($flag);
		}else{
			return "__err__ "."SELECT * FROM session WHERE user_id=$id ORDER BY start_time DESC ".mysqli_error($conn);
		}
    }
    
	function userInfo($conn, $user, $pwd){
		
        //Controllo esistenza username
		$res= mysqli_query($conn, "SELECT user_id FROM user WHERE username='$user'");
		
		if(!$res) return "__err__".mysqli_error($conn);
		
		if(mysqli_num_rows($res)==0){
			return "NoUser";
		}
		
        $pwd= crEncrypt($pwd);
        $res= mysqli_query($conn, "SELECT * FROM user WHERE username='$user' AND pwd='$pwd'");
		if(!$res) return "__err__".mysqli_error($conn);
		
        if(mysqli_num_rows($res)==0){
        	return "NoPwd";
        }else{
        
        	//Restituisco le informazioni dell'utente
			$arr=mysqli_fetch_assoc($res);
			return $arr;
		}
	}
	
	function makeLogin($conn, $user, $pwd){
		$res= userInfo($conn, $user, $pwd);
		if(is_array($res)){
			$id= intval($res['user_id']);
			session_start();
			$_SESSION['s-user-id']= $id;
			$hash= md5(uniqid(rand(), true));
			$_SESSION['s-hash-login']= $hash;
			$q= mysqli_query($conn, "UPDATE user SET hash_login='$hash' WHERE user_id=$id");
			if($q){
				return '';
			}else{
				return '__err__'.mysqli_error($conn);
			}
		}else{
			return 0;
		}
	}
	
	function deleteSession($conn, $id){
		session_start();
		session_destroy();
		return "";
	}
	
	function createAccount($conn, $user, $pwd){
		$pwd= crEncrypt($pwd);
		$qNum= mysqli_query($conn, "SELECT user_id FROM user WHERE username='$user'");
		if(mysqli_num_rows($qNum)!=0){
			return "InvalidUsername";
		}else{
			$user= mysqli_real_escape_string($conn, $user);
			$pwd= mysqli_real_escape_string($conn, $pwd);
			$qAdd= mysqli_query($conn, "INSERT INTO user VALUES (null, UNIX_TIMESTAMP()*1000, '$user', '$pwd', '0')");
			if(!$qAdd){
				return "__err__ "."INSERT INTO user VALUES (null, now(), '$user', '$pwd')  " . mysqli_error($conn);
			}else{
				$qId= mysqli_query($conn, "SELECT user_id FROM user WHERE username='$user'");
				return mysqli_fetch_assoc($qId)['user_id'];
			}
		}
	}
	
	function rowsToArray($queryRes){
		$res= array();
		while($row=mysqli_fetch_assoc($queryRes)){
			array_push($res, $row);
		}
		return $res;
	}
	
	function crEncrypt($str){
		$pwdLength= 30;
		for($i=strlen($str); $i<$pwdLength; $i++){
			switch($i%4){
				case 0: $str= $str . '@'; break;
				case 1: $str= $str . '{'; break;
				case 2: $str= $str . '£'; break;
				case 3: $str= $str . '='; break;
				default: $str= $str . 'ò'; break;
			}
		}
		return sha1($str);
	}
	

?>
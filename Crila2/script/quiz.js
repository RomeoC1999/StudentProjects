var g_map=[];
var g_questions= "";
var g_rights= "";
var g_answers= [];
var g_currentSentence;
var g_startTime;
var g_endTime;
var g_timer;
var g_response;


function computeK(i){
	var p= (g_map[i]['priority']-1)/4;													//Priority				0 - 0.5
	var rs= g_map[i]['num_test']!=0 ? 1-g_map[i]['num_ok']/g_map[i]['num_test'] : 0.5;	//Relative success		0 - 1.0
	var nt= g_map[i]['num_test']!=0 ? 1/g_map[i]['num_test']		: 1;				//Number of tests		0 - 1.0
	var r= Math.random()*3;																//Random				0 - 3
																						//TOTAL					0 - 5.5
	g_map[i]['k']= p+rs+nt+r;
}


function update(i, right){
	g_map[i]['num_test']++;
	if(right){
		g_map[i]['num_ok']++;
		g_map[i]['match_succ'] = -1;
	}else{
		g_map[i]['match_succ'] = 1;
	}
	computeK(i);
}




function beginningSet(){
	
	$.ajax({
		url: '../php/index.php',
        type: 'POST',
        data: {
			ord: 0,
            op: "sentences"  
        },
		success: function(response){
			
			if(response.includes("__err__")){
				console.log("quiz.js/beginningSet  "+response);
				if(response.includes("__LoginTwice__")){
					document.location.href= "usertwice.php";
				}
			}else{
				
				var res=JSON.parse(response);
				
				if(res.length==0){
					alert("To make a test you have to insert some words in the list");
					document.location.href= "../pages/profile.php";
				}else{
					for(var i=0; i<res.length; i++){
						g_map[i]= res[i];
						g_map[i]['user_id']= parseInt(g_map[i]['user_id']);
						g_map[i]['sentence_id']= parseInt(g_map[i]['sentence_id']);
						g_map[i]['priority']= parseInt(g_map[i]['priority']);
						g_map[i]['num_ok']= parseInt(g_map[i]['num_ok']);
						g_map[i]['num_test']= parseInt(g_map[i]['num_test']);
						g_map[i]['time_insert']= parseInt(g_map[i]['time_insert']);
						g_map[i]['match_ok'] = 0;		// -1 or +1 depends on the correctness of the last answer
						computeK(i);
					}
					
					makeKeyboardAction('q-quiz', nextQuestion);

					g_timer= window.setInterval(fTimer, 1000);
					g_startTime= (new Date()).getTime();
					g_currentSentence= changeQuestion();
					
					document.getElementById("q-loader").style.display= "none";
					document.getElementById("q-quiz").style.display= "block";
					document.getElementById('q-ita').value= g_map[g_currentSentence]['ita'];
					document.getElementById('q-eng').focus();
				}
			}
			
		} ,
		error: function(response){
			setNotify('q-loader','Impossible to connect to the database','error');
		}
    });
    
}


function endSession(){
	
	if(g_answers.length<MIN_QUESTIONS_PER_SESSION){
		$('#q-modal').modal();
		$('#q-modal').ready(function(){
			document.getElementById('q-modal-min-sentences').innerHTML= MIN_QUESTIONS_PER_SESSION;
		});
		
	}else{
		
		g_endTime= (new Date()).getTime();
		clearInterval(g_timer);
		g_rights= g_rights.substring(0, g_rights.length-1);
		g_questions= g_questions.substring(0, g_questions.length-1);
		
		document.getElementById("q-quiz").style.display= "none";
		document.getElementById("q-loader").style.display= "block";
		
		
		$.ajax({
			url: '../php/index.php',
			type: 'POST',
			data: { 
				rows: JSON.stringify(g_map),
				op: "updateSentences"			
			},
			success: function(response){
				if(response.includes("__err__")){
					console.log("quiz.js/endSession/updateSentences "+response);
					if(response.includes("__LoginTwice__")){
						document.location.href= "usertwice.php";
					}
				}
			} ,
			error: function(response){
				setNotify('q-loader','Impossible to connect to the database','error');
			}
		});
		
		
		$.ajax({
			url: '../php/index.php',
			type: 'POST',
			data: { 
				questions: g_questions,
				rights: g_rights,
				start_time: g_startTime,
				end_time: g_endTime,
				op: "addSession"			
			},
			success: function(response){
				if(response.includes("__err__")){
					console.log("quiz.js/endSession/addSession "+response);
					if(response.includes("__LoginTwice__")){
						document.location.href= "usertwice.php";
					}
				}
			} ,
			error: function(response){
				setNotify('q-loader','Impossible to connect to the database','error');
			}
		});
		
		fullSummaryTable();
	}
}




function registerQuestion(){
	var ans= cleanSentence(document.getElementById('q-eng').value);
	var right= false;
	g_response = ans;
	
	var eng1= g_map[g_currentSentence]['eng1'];
	var eng2= g_map[g_currentSentence]['eng2'];
	var eng3= g_map[g_currentSentence]['eng3'];
	
	if(ans.toLowerCase()==eng1.toLowerCase()){
		right= true;
	}
	if(eng2!="" && ans.toLowerCase()==eng2.toLowerCase()){
		right= true;
	}
	if(eng3!="" && ans.toLowerCase()==eng3.toLowerCase()){
		right= true;
	}
	
	if(right){
		g_rights+= "1_";
	}else{
		g_rights+= "0_";
	}
	
	g_questions+= g_map[g_currentSentence]['sentence_id']+"_";
	g_answers.push(ans);
	
	update(g_currentSentence, right);
	
	return right;
}



function nextQuestion(){
	var r= registerQuestion();
	var old= g_currentSentence;
	g_currentSentence= changeQuestion();
	
	if(g_answers.length>=MAX_QUESTIONS_PER_SESSION){
		endSession(false);
	}else{
		document.getElementById('q-ita').value= g_map[g_currentSentence]['ita'];
		document.getElementById('q-eng').value= "";
		document.getElementById('q-eng').placeholder= "Traslate...";
		document.getElementById('q-eng').focus();
		document.getElementById('l-n-question').innerHTML= "Question "+(g_answers.length+1);
		
		if(g_answers.length==MAX_QUESTIONS_PER_SESSION-1){
			setNotify('q-eng', 'Last question', 'warn', 'top right');
		}
		if(r){
			// If the previous question is right, show the possible alternatives
			var msg = ''
			for(var i=1; i<=3; i++){
				key = 'eng'+i;
				if(g_map[old][key] != '' && g_map[old][key].toLowerCase() != g_response.toLowerCase()){
					if(msg=='')	msg += g_map[old][key]
					else		msg += ', ' + g_map[old][key]
				}
			}
			if(msg=='') setNotify('q-eng', 'Ok!', 'success', 'top right');
			else		setNotify('q-eng', msg, 'success', 'top right');
			
		}else{
			setNotify('q-eng', g_map[old]['eng1'], 'error', 'top right', true, true, 4500);
		}
		
	}
}


function changeQuestion(){
	var maxInd= 0;
	for(var i=1; i<g_map.length; i++){
		if(g_map[i]['k']>g_map[maxInd]['k']){
			maxInd=i;
		}
	}
	return maxInd;
}


function fTimer(){
	var t= (new Date()).getTime() - g_startTime;
	document.getElementById('q-timer').setAttribute('value', msToStr(t, false, true));
}



function unexpectedClose(){
	clearInterval(g_timer);
}

function goBack(){
	document.location.href= "../pages/profile.php";
}




function fullSummaryTable(){
	
	var keys= g_questions.split("_");
	var rights= g_rights.split("_");
	var r= (g_rights.split("1")).length-1;
	var p= r/g_answers.length;
	
	p= Math.round(p*100) + "%";
	
	var t= g_endTime - g_startTime;
	var mm= String(Math.floor(t/60000));
	var ss= String(Math.floor(t/1000)%60);
	if(ss.length==1) ss= '0'+ss;
	if(mm.length==1) mm= '0'+mm;
	
	document.getElementById('q-result').innerHTML= "<br>Correct answers: <b>" + r +  "/" + g_answers.length + "</b> ( " + p +  ")<br>" +
													"Time taken: <b>" + mm + " : " + ss + "</b><br><br>";
	
	
	for(var i=0; i< g_answers.length; i++){
		
		var row = document.createElement("tr");
		
		var ind= -1;
		for(var j=0; j<g_map.length; j++){
			if(g_map[j]['sentence_id']==parseInt(keys[i])){
				ind= j;
				break;
			}
		}
		
		if(ind==(-1)){
			printError('quiz', 'fullSummaryTable', 'ind=-1');
			return;
		}
		
		if(rights[i]=='1'){
			row.setAttribute('class', 'table-success');
		}else{
			row.setAttribute('class', 'table-danger');
		}
		
		var tdn= document.createElement('td');	//Index
		var tdi= document.createElement('td');	//Requested
		var tda= document.createElement('td');	//Answer
		var tdc= document.createElement('td');	//Correct
		
		tdn.appendChild(document.createTextNode(i+1));
		tdi.appendChild(document.createTextNode(g_map[ind]['ita']));
		tda.appendChild(document.createTextNode(g_answers[i]));
		
		if(rights[i]=='1'){
			tdc.appendChild(document.createTextNode(g_answers[i]));
		}else{
			tdc.appendChild(document.createTextNode(g_map[ind]['eng1']));
		}
		
		row.appendChild(tdn);
		row.appendChild(tdi);
		row.appendChild(tda);
		row.appendChild(tdc);
		
		document.getElementById('q-table').appendChild(row);
	}
	
	document.getElementById("q-loader").style.display= "none";
	document.getElementById("q-summary").style.display= "block";
	
	
}









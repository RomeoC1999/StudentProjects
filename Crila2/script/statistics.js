var g_sessions;

function beginningSet(){
	
	$("#s-start-date" ).datepicker({
		onSelect: function(dateText) {
			fillValues();
		},
		dateFormat: 'dd/mm/yy'
	});
	
	$("#s-end-date" ).datepicker({
		onSelect: function(dateText) {
			fillValues();
		},
		dateFormat: 'dd/mm/yy'
	});
	
	
	$.ajax({
		url: '../php/index.php',
		type: 'POST',
		data: { 
			op: "sessions"			
		},
		success: function(response){
			if(response.includes("__err__")){
				console.log("statistics.js/beginningSet/sessions "+response);
				setNotify('s-loader','Impossible to connect to the database','error', 'bottom center');
				if(response.includes("__LoginTwice__")){
					document.location.href= "usertwice.php";
				}
			}else{
				g_sessions= JSON.parse(response);
				if(localStorage.getItem('c-time-signin')){
					var ds= msToItaDate(parseInt(localStorage.getItem('c-time-signin')));
				}else{
					var ds= msToItaDate((new Date()).getTime());	//Considers the current day as start
				}
				var de= msToItaDate((new Date()).getTime());
				document.getElementById('s-start-date').value= ds;
				document.getElementById('s-end-date').value= de;
				fillValues();
			}
		} ,
		error: function(response){
			setNotify('s-loader','Impossible to connect to the database','error', 'bottom center');
		}
	});
}

function fillValues(){
	
	document.getElementById('s-loader').style.display= 'block';
	document.getElementById('s-div-table').style.display= 'none';
	
	startDate= itaDateToMs(document.getElementById('s-start-date').value);
	endDate= itaDateToMs(document.getElementById('s-end-date').value)+ 1000*3600*24;
	
	if(endDate>startDate){
		
		var dd= Math.ceil((endDate-startDate)/(1000*3600*24));
		var ns=0, nr=0, nq=0, tt=0, n,r,t;
		
		$('.session-row').remove();
		
		for(var i=0; i<g_sessions.length; i++){
			if(parseInt(g_sessions[i]['start_time'])>=startDate && parseInt(g_sessions[i]['end_time'])<=endDate){
				
				r= countOccurencies(g_sessions[i]['rights'],'1');
				n= parseInt(g_sessions[i]['num_questions']);
				t= parseInt(g_sessions[i]['end_time'])-parseInt(g_sessions[i]['start_time']);
				ns++;
				
				nr+=r; nq+=n; tt+=t;
				
				appendRow(parseInt(g_sessions[i]['start_time']), r, n, t);
				
			}
		}
		
		document.getElementById('s-game-time').innerHTML= msToStr(tt,true,false);
		document.getElementById('s-num-sessions').innerHTML= ns;
		document.getElementById('s-num-questions').innerHTML= nq;
		document.getElementById('s-avg-questions-day').innerHTML= Math.round(nq/dd);
		if(nq!=0){
			document.getElementById('s-avg-correct-day').innerHTML= Math.round(nr*100/nq)+"%";
		}else{
			document.getElementById('s-avg-correct-day').innerHTML= "--";
		}
	}else{
		setNotify('s-back-button', 'Start date must preceed the end date', 'error');
	}
	
	document.getElementById('s-loader').style.display= 'none';
	document.getElementById('s-div-table').style.display= 'block';
				
}



function appendRow(d, r, n, t){
	var row= document.createElement('tr'); row.setAttribute('class', 'session-row');
	var td1= document.createElement('td'); row.appendChild(td1);
	var td2= document.createElement('td'); row.appendChild(td2);
	var td3= document.createElement('td'); row.appendChild(td3);
	var td4= document.createElement('td'); row.appendChild(td4);
	td1.innerHTML= msToShortItaDate(d);
	td2.innerHTML= r+"/"+n;
	td3.innerHTML= Math.round(r*100/n)+"%";
	td4.innerHTML= msToStr(t);
	document.getElementById('s-table-body').appendChild(row);
}

function unexpectedClose(){
	
}







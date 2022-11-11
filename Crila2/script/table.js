var g_sentences;
var g_openedSentence;
var g_selectionMode= false;
var g_selectedSet;
var g_modalOpened= false;




function beginningSet(){
	
	if(localStorage.getItem('c-order')){
		var ord= parseInt(localStorage.getItem('c-order'));
	}else{
		var ord= 7;	// Your language sorting
	}
	
	if(!localStorage.getItem('c-reverse')){
		localStorage.setItem('c-reverse', 1);
	}
	
	if(Math.abs(parseInt(localStorage.getItem('c-reverse')))==1){
		ord*= parseInt(localStorage.getItem('c-reverse'));
	}
	
	$.ajax({
		url: '../php/index.php',
        type: 'POST',
        data: {
			ord: ord,
            op: "sentences"  
        },
		success: function(response){
			g_sentences= [];
			g_modalOpened= false;
			if(response.includes("__err__")){
				setNotify('t-loader','Impossible to connect to the database','error','bottom-center');
				console.log("table.js/beginningSet "+response);
				if(response.includes("__LoginTwice__")){
					document.location.href= "usertwice.php";
				}
			}else{
				g_sentences=JSON.parse(response);
				
				var v = []
				for(var i=0; i<g_sentences.length; i++)
					v.push(i);
				if(ord==0)
					shuffleArray(v)
				
				for(var i=0; i<g_sentences.length; i++){
					appendRow(v[i]);
				}
				window.onkeypress = function(evt) {
					if(!g_modalOpened){
						evt = evt || window.event;
						var charCode = evt.keyCode || evt.which;
						if(charCode==65) openEditModal();
					}
				};
				$('#t-form-modal').on('hide.bs.modal', function (){
					g_modalOpened= false;
				});
				document.getElementById('t-loader').style.display= 'none';
				document.getElementById('t-container').style.display= 'block';
			}
		} ,
		error: function(response){
			setNotify('t-loader','Impossible to connect to the database','error','bottom-center');
		}
    });
	
}


function appendRow(i){
	
	var ita= g_sentences[i]['ita'];
	var eng= g_sentences[i]['eng1'];
	var nr= parseInt(g_sentences[i]['num_ok']);
	var nt= parseInt(g_sentences[i]['num_test']);
	var perc= (nt==0) ? "" : Math.round(nr*100/nt)+"%";
	var pri= parseInt(g_sentences[i]['priority']);
	var ids= g_sentences[i]['sentence_id'];
	
	var tdi= document.createElement('td');		//Your language
	var tde= document.createElement('td');		//English
	var tdp= document.createElement('td');		//Priority
	var tdnr= document.createElement('td');		//Corrects/Total
	var tdperc= document.createElement('td');	//Percentage
	var tds= document.createElement('td');		//Span (icon)
	var row= document.createElement('tr');
	
	tdi.innerHTML= ita;
	tde.innerHTML= eng;
	tdp.innerHTML= pri;
	tdnr.innerHTML= nr+"/"+nt;
	tdperc.innerHTML= perc;
	tds.id= 'sent_'+i;
	row.id= 's_row_'+i;
	tds.innerHTML= "<span class='glyphicon glyphicon-pencil'></span>";
	
	tds.addEventListener("click", function(){
		openEditModal(this.id);
	});
	
	row.appendChild(tdi);
	row.appendChild(tde);
	row.appendChild(tdp);
	row.appendChild(tdnr);
	row.appendChild(tdperc);
	row.appendChild(tds);
	
	row.style.backgroundColor= COLOR_NOT_SELECTED_ROW;
	
	document.getElementById('t-table-body').appendChild(row);
}



function openEditModal(index=-1){
	if(g_selectionMode){
		if(index!=-1){
			index= parseInt(index.substring(5, index.length));
			if(g_selectedSet.has(index)){
				g_selectedSet.delete(index);
				setRowStyle(index, 'unchecked', COLOR_NOT_SELECTED_ROW);
			}else{
				g_selectedSet.add(index);
				setRowStyle(index, 'checked', COLOR_SELECTED_ROW);
			}
		}else{
			printError("table", "openEditModal", "Selection modality, received index -1");
		}
	}else{
		$('#t-form-modal').modal();
		g_modalOpened= true;
		$('#t-form-modal').ready(function(){
			if(index!=(-1) && !index.includes('sent_')){
				console.log('openEditModal -> '+index+' does not start with sent_');
				return;
			}
			if(index!=(-1)){
				index= parseInt(index.substring(5, index.length));
				document.getElementById('t-form-ita').value= g_sentences[index]['ita'];
				document.getElementById('t-form-eng1').value= g_sentences[index]['eng1'];
				document.getElementById('t-form-eng2').value= g_sentences[index]['eng2'];
				document.getElementById('t-form-eng3').value= g_sentences[index]['eng3'];
				document.getElementById('t-form-priority').value= g_sentences[index]['priority'];
				document.getElementById('t-form-del-btn').style.display= "block";
				document.getElementById('t-form-h').innerHTML= 'Edit';
			}else{
				document.getElementById('t-form-ita').value= "";
				document.getElementById('t-form-eng1').value= "";
				document.getElementById('t-form-eng2').value= "";
				document.getElementById('t-form-eng3').value= "";
				document.getElementById('t-form-priority').value= 2;
				document.getElementById('t-form-del-btn').style.display= "none";
				document.getElementById('t-form-h').innerHTML= 'Add';
			}
			g_openedSentence= index;
			makeKeyboardBridge('t-form-ita', 't-form-eng1');
			makeKeyboardBridge('t-form-eng1', 't-form-eng2');
			makeKeyboardBridge('t-form-eng2', 't-form-eng3');
			makeKeyboardAction('t-form-eng3', saveOpenedSentence);
			$("#t-form-modal").on('shown.bs.modal', function () {
				$('#t-form-ita').focus();
			});
		});
	}
}


//Elimina la frase con l'id passato per parametro. Nel caso non vengano passati parametri viene usato il valore nella variabile globale
function deleteSentence(id=0){
	var toReload= !id;
	if(!id) id= g_sentences[g_openedSentence]['sentence_id'];
	$.ajax({
		url: '../php/index.php',
		type: 'POST',
		data: {
			id: id,
			ita: "",
			eng1: "",
			eng2: "",
			eng3: "",
			pri: -1,
			op: "editSentence"			
		},
		success: function(response){
			if(response.includes("__err__")){
				printError('table', 'deleteSentence', 'Errore SQL -> '+response);
				if(response.includes("__LoginTwice__")){
					document.location.href= "usertwice.php";
				}
			}else{
				if(toReload){
					location.reload();
				}
			}
		} ,
		error: function(response){
			setNotify('t-form-h','Impossible to connect to the database','error', 'bottom center');
		}
	});
}


// Usato per la modifica (parametro di default, ovvero salvato nella variabile globale) o per l'aggiunta (parametro id=0)
function saveOpenedSentence(){
	
	var index= g_openedSentence;
	var ita=  cleanSentence(document.getElementById('t-form-ita').value);
	var eng1= cleanSentence(document.getElementById('t-form-eng1').value);
	var eng2= cleanSentence(document.getElementById('t-form-eng2').value);
	var eng3= cleanSentence(document.getElementById('t-form-eng3').value);
	var pri= document.getElementById('t-form-priority').value;
	
	if(ita=='' || eng1=='' || (pri!=1 && pri!=2 && pri!=3)){
		if(ita=="")		setNotify('t-form-ita', 'This field cannot be empty', 'error');
		if(eng1=="")	setNotify('t-form-eng1', 'This field cannot be empty', 'error');
		if(pri!=1 && pri!=2 && pri!=3) setNotify('t-form-priority', 'Priority must be 1, 2 or 3', 'error');
	}else{
		
		var id;
		if(index==(-1)){
			index= g_sentences.length;
			g_sentences[index]= new Object();
			g_sentences['num_ok']= 0;
			g_sentences['num_test']= 0;
			id= -1;
		}else{
			id= g_sentences[index]['sentence_id'];
		}
		
		g_sentences[index]['ita']= ita;
		g_sentences[index]['eng1']= eng1;
		g_sentences[index]['eng2']= eng2;
		g_sentences[index]['eng3']= eng3;
		g_sentences[index]['priority']= parseInt(pri);
		
		$.ajax({
			url: '../php/index.php',
			type: 'POST',
			data: {
				id: id,
				ita: ita,
				eng1: eng1,
				eng2: eng2,
				eng3: eng3,
				pri: pri,
				op: "editSentence"			
			},
			success: function(response){
				/********************************************************* Problem, never enters the If*/
				if(response.includes("__err__")){
					printError('table', 'saveOpenedSentence', response);
				}else{
					location.reload();
				}
				$('#t-form-modal').modal('hide');
			} ,
			error: function(response){
				setNotify('t-form-h','Impossible to connect to the database','error', 'bottom center');
			}
		});
	}
}




function changeSelectionMode(){
	g_selectionMode= !g_selectionMode;
	
	if(g_selectionMode){
		document.getElementById('normal-btn-group').style.display='none';
		document.getElementById('selection-btn-group').style.display='block';
		for(var i=0; i<g_sentences.length; i++) setRowStyle(i, 'unchecked', COLOR_NOT_SELECTED_ROW);
		g_selectedSet= new Set();
	}else{
		location.reload();
	}
	
}


function setRowStyle(ind, icon='edit', bgColor){
	
	switch(icon){
		case 'edit': document.getElementById('sent_'+ind).innerHTML= "<span class='glyphicon glyphicon-pencil'></span>"; break;
		case 'checked': document.getElementById('sent_'+ind).innerHTML= "<span class='glyphicon glyphicon-check'></span>"; break;
		case 'unchecked': document.getElementById('sent_'+ind).innerHTML= "<span class='glyphicon glyphicon-unchecked'></span>"; break;
	}
	
	if(bgColor){
		document.getElementById('s_row_'+ind).style.backgroundColor= bgColor;
	}
}


function goBack(){
	document.location.href= "../pages/profile.php";
}

async function confirmDelete(){
	for(let item of g_selectedSet) await deleteSentence(g_sentences[item]['sentence_id']);
	location.reload();
}

function openConfirmDeleteModal(){
	if(g_selectedSet.size>0){
		$('#t-del-modal').modal();
		$('#t-del-modal').ready(function(){
			if(g_selectedSet.size==1){
				document.getElementById('t-n-selected').innerHTML= "questa frase?"
			}else{
				document.getElementById('t-n-selected').innerHTML= g_selectedSet.size + " frasi?";
			}
		});
	}
}

async function order(n=0){
	await localStorage.setItem('c-order', n);
	location.reload();
}

async function reverseOrder(){
	if(localStorage.getItem('c-reverse')==(-1)){
		localStorage.setItem('c-reverse', 1);
	}else{
		localStorage.setItem('c-reverse', -1);
	}
	location.reload();
}

function unexpectedClose(){
	
}

function shuffleArray(array) {
  let currentIndex = array.length,  randomIndex;
  while (currentIndex != 0) {
	randomIndex = Math.floor(Math.random() * currentIndex);
    currentIndex--;
	[array[currentIndex], array[randomIndex]] = [
      array[randomIndex], array[currentIndex]];
  }
  return array;
}




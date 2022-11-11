
const PWD_MIN_LENGTH= 4;
const PWD_MAX_LENGTH= 30;
const USERNAME_MAX_LENGTH= 30;
const SENTENCE_MAX_LENGTH= 100;
const QUESTIONS_SESSION_MAX_LENGTH= 1000;
const RIGHTS_SESSION_MAX_LENGTH= 200;
const MAX_QUESTIONS_PER_SESSION= 100;
const MIN_QUESTIONS_PER_SESSION= 5;

const HOST_ID= 29;

const COLOR_PRIORITY_1= "rgb(179, 209, 255)";
const COLOR_PRIORITY_2= "rgb(153, 194, 255)";
const COLOR_PRIORITY_3= "rgb(128, 179, 255)";
const COLOR_WHITE= "rgb(255, 255, 255)";
const COLOR_NOT_SELECTED_ROW= "rgb(179, 255, 255)";
const COLOR_SELECTED_ROW= "rgb(0, 255, 255)"





/*
 * id   => id dell'oggetto sul quale far apparire la notifica
 * txt  => testo della notifica
 * type => tipo di notifica (success, info, warn, error)
 * CAMPI OPZIONALI:
 * pos  => posizione di comparsa rispetto all'oggetto. altezza: [top, middle, bottom] larghezza: [left, center, right} (default: top center)
 * hide => chiudere la notifica se cliccata (default:true) 
 * auto => chiusura automatica dopo n millisecondi (default:true)
 * mSec => n millisecondi (default:3000)
 * aTmp => tempo di apertura animazione (default:400)
*/
function setNotify(id,txt,type,pos= "top center",hide=true,auto=true,mSec=3000,aTmp=400,cTmp=200)
{	
	$('#'+id).notify(txt,{ 
		clickToHide: hide,
		autoHide: auto,
		autoHideDelay: mSec,
		arrowShow: true,
		arrowSize: 5,
		position: pos,
		elementPosition: 'bottom left',
		globalPosition: 'top right',
		style: 'bootstrap',
		className: type,
		showAnimation: 'slideDown',
		showDuration: aTmp,
		hideAnimation: 'slideUp',
		hideDuration: cTmp,
		gap: 2
	});
}

function isHost(){
	id = localStorage.getItem("c-user-id");
	if(parseInt(id)==HOST_ID)	return true;
	else						return false;
}

function countOccurencies(str, oc){
	var n=0;
	for(var i=0; i<str.length; i++){
		if(str[i]==oc){
			n++;
		}
	}
	return n;
}


function msToShortItaDate(ms){
	var d= new Date(parseInt(ms));
	var m= ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']; 
	return d.getDate()+" "+m[d.getMonth()];
}


function msToItaDate(ms){
	var d= new Date(parseInt(ms));
	return d.getDate()+"/"+(d.getMonth()+1)+"/"+d.getFullYear();
}

function itaDateToMs(d){
	var arr= d.split("/");
	var tmp= new Date(parseInt(arr[2]), parseInt(arr[1])-1, parseInt(arr[0]));
	return tmp.getTime();
}



function msToStr(ms, withH=false, withSpace=false){
	
	if(withH){
		var hp= new String(Math.floor(ms/(1000*3600)));			hp= (hp.length==1 ? "0" : "")+hp;
		var mp= new String(Math.floor(ms/(1000*60))%60);		mp= (mp.length==1 ? "0" : "")+mp;
		var sp= new String(Math.floor(ms/(1000))%60);			sp= (sp.length==1 ? "0" : "")+sp;
		if(withSpace){
			return hp+" : "+mp+" : "+sp;
		}else{
			return hp+":"+mp+":"+sp;
		}
		
	}else{
		var mp= new String(Math.floor(ms/(1000*60)));			mp= (mp.length==1 ? "0" : "")+mp;
		var sp= new String(Math.floor(ms/(1000))%60);			sp= (sp.length==1 ? "0" : "")+sp;
		if(withSpace){
			return mp+" : "+sp;
		}else{
			return mp+":"+sp;
		}
	}
	
}


function makeKeyboardBridge(id1, id2){
	document.getElementById(id1).onkeypress = function(evt) {
		evt = evt || window.event;
		var charCode = evt.keyCode || evt.which;
		if(charCode==13) document.getElementById(id2).focus();
	};
}

function makeKeyboardAction(id, func){
	document.getElementById(id).onkeypress = function(evt) {
		evt = evt || window.event;
		var charCode = evt.keyCode || evt.which;
		if(charCode==13) func();
	};
}



var u_multiKeysPressed= [];
var u_cod1, u_cod2, u_func;

function detectingMultipleKeyPresses(cod1, cod2, func){
	window.addEventListener("keydown", keysPressed, false);
	window.addEventListener("keyup", keysReleased, false);
	u_cod1= cod1; u_cod2= cod2; u_func= func;
}

function keysPressed(e) {
	u_multiKeysPressed[e.keyCode] = true;
    if (u_multiKeysPressed[u_cod1] && u_multiKeysPressed[u_cod2]) {
		e.preventDefault();
        u_func();
    }
}
 
function keysReleased(e) {
    u_multiKeysPressed[e.keyCode] = false;
}



function printError(file, func, msg){
	console.log(file + " - " + func + " - " + msg);
}

function cleanSentence(str){
	str= str.replace(new RegExp('‘', 'g'), '\'');
	str= str.replace(new RegExp('’', 'g'), '\'');
	str= str.trim();
	return str;
}


function deleteCookies(){
	localStorage.removeItem("c-user-id");
	localStorage.removeItem("c-username");
	localStorage.removeItem("c-time-signin");
}

function closeSession(){
	$.ajax({
		async: false,
		url: '../php/index.php',
		type: 'POST',
		data: {
			op: "deleteSession"  
		} ,
		success: function(response){
			
		} ,
		error: function(response){
			printError('util', 'closeSession', response);
			
		}
	});
}

























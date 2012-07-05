
var oldReport = "";

function handleShowReport(rep) {
	var divId = rep[0];
	var report = rep[1];
	
	document.getElementById(divId).innerHTML = report;

	$("#"+divId).slideDown();
}

function showreport(id, report) {
	if (document.getElementById(report).style.display == 'none') {
		SimAjax.getRaidReport(id,report,handleShowReport);
	} else {
		$("#"+report).slideUp();
	}
}

function showlikes(like) {
	if (document.getElementById(like).style.display == 'none') {
		$("#"+like).slideDown();
	} else {
		$("#"+like).slideUp();
	}
}

var oldComment = "";

function showcommentform(commentfrm) {
	if (oldComment != "") {
		$("#"+oldComment).slideUp();
		if (oldComment != commentfrm) {
			oldComment=commentfrm;
			setTimeout(function() { $("#"+commentfrm).slideDown(); },320);
		} else {
			oldComment="";
		}
	} else {
		if (oldComment != commentfrm) {
			oldComment=commentfrm;
			$("#"+commentfrm).slideDown();
		} else {
			oldComment="";
		}
	}
}

function handleShowReveal(rep) {
	var divId = rep[0];
	var reveal = rep[1];
	
	document.getElementById(divId).innerHTML = reveal;

	$("#"+divId).slideDown();
}

function showreveal(link, reveal) {
	if (document.getElementById(reveal).style.display == 'none') {
		link.innerHTML = 'hide reveal';
		$("#"+reveal).slideDown();
	} else {
		link.innerHTML = 'show reveal';
		$("#"+reveal).slideUp();
	}
}

function invisible(nr)
{
    if (document.layers)
    {
        document.layers[nr].visibility = 'hide';
    }
    else if (document.all)
    {
        document.all[nr].style.visibility = 'hidden';
    }
    else if (document.getElementById)
    {
        document.getElementById(nr).style.visibility = 'hidden';
    }
}
function visible(nr)
{
    if (document.layers)
    {
        document.layers[nr].visibility = 'show';
    }
    else if (document.all)
    {
        document.all[nr].style.visibility = 'visible';
    }
    else if (document.getElementById)
    {
        document.getElementById(nr).style.visibility = 'visible';
    }
}


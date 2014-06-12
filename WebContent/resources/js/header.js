$(document).ready(function(){
	$('.links').click(function(){
		var url = $(this).attr("url");
		if(url == '#'){
			if(trialList.activeTrial == null) return false;
			url = 'controller?command=editTrialForward&trialId=' + trialList.activeTrial.id;
		}
		header.goToLink(url);
	});
});

var header = {
	goToLink : function(url){
		window.location.href = url;
	}
	
};


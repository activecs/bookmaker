$(document).ready(function() {
	// gets all trials
	ajaxTrials.query();
	
	// delete trial
	$('#delete').on('click', function(){
		url = 'controller?command=deleteTrial&trialId=' + trialList.trialId;
		header.goToLink(url);
	});
	
});

var trialList = {
	// trial's id for editing
	trialId : null,
	
	// trial for editing
	trial : null,
	
	// all trials
	allTrials : null,
	
	// looks for trial object
	look : function(){
		trialList.trialId = $('#trialId').attr('value');
		
		$.each(trialList.allTrials, function(){
			if(this.id == trialList.trialId){
				trialList.trial = this;
			}
		});
		
		trialList.set();
	},
	
	// sets html 'selects' according trial for editing
	set : function() {
		$("#trackId [value='"+ trialList.trial.trackId +"']").attr("selected", "selected");
		$("#distanceId :contains('"+ trialList.trial.distance +"')").attr("selected", "selected");
		$("#trialStatusId :contains('"+ trialList.trial.trialStatus +"')").attr("selected", "selected");
		$("#demo3").val(trialList.trial.startTime);
		
//		$('.trial_information_table tbody').hide(600);
//
//		setTimeout(function() {
//			$('#startTime').html(trialList.trial.startTime);
//			$('#name').html(trialList.trial.trackBean.name);
//			$('#distance').html(trialList.trial.distance);
//			$('#cover').html(trialList.trial.trackBean.cover);
//			$('#trackType').html(trialList.trial.trackBean.trackType);
//			$('#trialStatus').html(trialList.trial.trialStatus);
//			$('#country').html(trialList.trial.trackBean.country);
//			$('.trial_information_table tbody').show(600);
//		}, 600);
	}
};

var ajaxTrials = {

		url : '/controller',
		
		query : function() {

			jQuery.ajax({
				type : 'POST',
				cashe : false,
				url : ajaxTrials.url,
				data : "command=trialList",
				beforeSend : function() {

				},
				success : function(data) {
					// save all trials
					trialList.allTrials = data;
					
					trialList.look();
				 }
			});
		}
	};

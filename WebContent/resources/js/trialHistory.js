$(document).ready(function() {
	// static requests
	// update information about active trials and horses
	trialList.getTrials();
	horseList.getHorses();

	/**
	 * save current trial and show info about it
	 * 
	 */
	$('.trials_links_table').on('click', '.trials_link', function(event) {
		// gets attribute index from link
		var index = $(this).attr("index");

		// save active trial
		trialList.activeTrial = trialList.allTrials[index];
		console.log("Active trial ->" + trialList.activeTrial);

		// show information about trial
		trialList.showTrialInfo();

		// get horses with given trial
		ajaxHorses.query(trialList.activeTrial.id);
	});
		
});


var trialList = {
	
	showTrials : function(){
		// clear table
		$('.trials_links_table').empty();
		// add trials to page
		$.each(trialList.allTrials, function(){
			// get index current trial 
			var index = trialList.allTrials.indexOf(this);
			
			// add record to table
			$('.trials_links_table').append('<tr>'+
												'<td>'+
													'<div class="trials_link" index="'+ index +'">'+ 
														this.trackBean.name + 
													'</div>'+
												'</td>'+
											'</tr>');
		});
	},	
	
	getTrials : function() {
		// first query after loading page
		ajaxTrials.query();
		
		// repeat query each 25 second
		setInterval(function() {
			ajaxTrials.query();
		}, ajaxTrials.interval);
	},
	
	showTrialInfo : function(){
		$('#horses').slideUp("slow");
		//$('.trial_information_table td').hide(600);
		
		setTimeout(function() {
			$('#startTime').html(trialList.activeTrial.startTime);
			$('#name').html(trialList.activeTrial.trackBean.name);
			$('#distance').html(trialList.activeTrial.distance);
			$('#cover').html(trialList.activeTrial.trackBean.cover);
			$('#trackType').html(trialList.activeTrial.trackBean.trackType);
			$('#trialStatus').html(trialList.activeTrial.trialStatus);
			$('#country').html(trialList.activeTrial.trackBean.country);
			
			//$('.trial_information_table td').show(600);
			$('#horses').slideDown("slow");
		}, 1000);
	},
	
	allTrials : null,
	
	activeTrial : null
};

var ajaxTrials = {

		url : '/controller',
		
		interval: 50000,
		
		query : function() {

			jQuery.ajax({
				type : 'POST',
				cashe : false,
				url : ajaxTrials.url,
				data : "command=trialHistoryList",
				beforeSend : function() {

				},
				success : function(data) {
					// save all trials
					trialList.allTrials = data;
					// show all active Trials
					trialList.showTrials();
				 }
			});
		}
	};




var horseList = {
		
		interval : 50000,
		
		getHorses : function(){
			// repeat request
			setInterval(function(){
				if(trialList.activeTrial != null){
					ajaxHorses.query(trialList.activeTrial.id);
				}
			}, horseList.interval);
		},
		
		showHorses : function(){
			$('#horses.horses_table').empty();
			
			if(horseList.allHorses == null || horseList.allHorses.length == 0){
				$('#horses.horses_table').append('<tr><td><h2>No horses</h2><td></tr>');
				return;
			}
			
			$.each(horseList.allHorses, function(){
				$('#horses.horses_table').append(
					"<tr id='"+ this.id +"'>"+
						'<td class="td1">'+
								this.name +
						'</td>'+
						'<td class="td2">'+
								this.age +
						'</td>'+
						'<td class="td3">'+
								this.color +
						'</td>'+
						'<td class="td4">'+
								this.weight +
						'</td>'+
						'<td class="td5">'+
								this.ownerName +
						'</td>'+
						'<td class="td6">'+
								this.status +
						'</td>'+
						'<td class="td7">'+
								this.place +
						'</td>'+
						'<td class="td8">'+
								this.winCoefficient +
						'</td>'+
					'</tr>'
				);
			});
			
			// show addHorseToTrial button if it is possible(if you are admin)
			try{				
				addDeleteHorses.showAddButton();
			}catch(err){}
		},
		
		allHorses : null,
		
		activeHorseId : null,
		
		previousActiveTr : null
};

var ajaxHorses = {

		url : '/controller',
			
		query : function(trialId){
			jQuery.ajax({
				type : 'POST',
				cashe : false,
				url : ajaxHorses.url,
				data : "command=horseList&trialId=" + trialId,
				beforeSend : function() {
				},
				success : function(data) {
					// save all horses
					horseList.allHorses = data;
					// show horses
					setTimeout(function(){
						horseList.showHorses();
				}, 600);
				}
			});
		}
};
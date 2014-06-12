$(document).ready(function() {
	// starts service that check buttons add, delete, edit
	addDeleteHorses.check();
	
	
	/**
	 * adds div with links for editing and removing horse
	 * 
	 */
	$('.horses_table').on('click', 'tr' ,function(event){
		if(typeof($(this).attr("id")) != "undefined"){
			if(horseList.previousActiveTr != null){
				// resets the the selection the previous row
				$(horseList.previousActiveTr).css({'background-color' : ''});
			}
			$('#addDeleteHorse').remove();
			// highlight current row
			horseList.activeHorseId = $(this).attr("id");
			$(this).css({'background-color' : '#BFAAAA'});
			horseList.previousActiveTr = this;
			// adds div with links for editing and removing horse
			$('body').append(
					"<div  id='addDeleteHorse'>" +
					"<a href='controller?command=editTrialHorse&trialId="+ trialList.activeTrial.id + '&horseId=' + horseList.activeHorseId +"'>" +
					"<img src='/resources/img/edit.png' width='30' height='30' border='0' title='Edit horse'>" +
					"</a>" +
					
					"<a id='deleteHorseTrial' href='#'>" +
					"<img src='/resources/img/delete.png' width='30' height='30' border='0' title='Delete horse'>" +
					"</a>" +
			"</div>");
			$('#addDeleteHorse').css({'position' : 'absolute', 'left' : event.pageX+10+'px', 'top' : event.pageY+10+'px'});
			
			addDeleteHorses.createdTime = new Date();
		}
	});
	
	$('body').on('mouseover','#addDeleteHorse',function(){
		addDeleteHorses.lastTimeMoveOver = new Date();
	});
	

	$('body').on('mouseout','#addDeleteHorse',function(){
		addDeleteHorses.lastTimeMoveOut = new Date();	
	});
	
	/**
	 * if you click on link(image) will be done query
	 * 
	 */
	$('body').on('click','#deleteHorseTrial',function(){
		addDeleteHorses.deleteHorseTrial();
	});
		
});

var addDeleteHorses = {
		
		showAddButton : function(){
			$('#content a').remove();
			// add button "add horse to trial"
			if(horseList.allHorses == null || horseList.allHorses.length == 0 || horseList.allHorses.length < 8){
				$('#content').append(
						"<a href='controller?command=addHorseTrial&trialId="+ trialList.activeTrial.id +"'>" +
						"<img src='/resources/img/add.png' width='30' height='30' border='0' title='Add horse'>" +
				"</a>");
			}
			
			if(horseList.allHorses == null || horseList.allHorses.length == 0){
				$('#horses.horses_table').append('<tr><td><h2>No horses</h2><td></tr>');
				return;
			}
		},
		
		check : function(){
			setInterval(function(){
				var currentTime = new Date;
				
				if(addDeleteHorses.createdTime != null && addDeleteHorses.lastTimeMoveOver == null){
					var timeout = currentTime - addDeleteHorses.createdTime;
					if(timeout > 2000){
						$('#addDeleteHorse').remove();
						addDeleteHorses.lastTimeMoveOver = null;
						addDeleteHorses.lastTimeMoveOut = null;
						addDeleteHorses.createdTime = null;
						$(horseList.previousActiveTr).css({'background-color' : ''});
					}
				}
				
				if(addDeleteHorses.lastTimeMoveOver != null && addDeleteHorses.lastTimeMoveOut != null){
					var timeout = addDeleteHorses.lastTimeMoveOver - addDeleteHorses.lastTimeMoveOut;
					if(timeout > 0){
						return;
					}else 
						if(timeout < 0){
							var interval = currentTime - addDeleteHorses.lastTimeMoveOut;
							if(interval > 2000){								
								$('#addDeleteHorse').remove();
								addDeleteHorses.lastTimeMoveOver = null;
								addDeleteHorses.lastTimeMoveOut = null;
								addDeleteHorses.createdTime = null;
								$(horseList.previousActiveTr).css({'background-color' : ''});
							}
					}
				}
			}, 500);
		},
		
		createdTime : null,
		
		lastTimeMoveOver : null,
		
		lastTimeMoveOut : null,
		
		deleteHorseTrial : function(){
			ajaxDeleteHorseTrial.query();
			setTimeout(function(){
				$('#addDeleteHorse').remove();
			}, 800);
		}
};

var ajaxDeleteHorseTrial = {

		url : '/controller',
			
		query : function(){
			jQuery.ajax({
				type : 'POST',
				cashe : false,
				url : ajaxHorses.url,
				data : "command=deleteTrialHorse&horseId="+ horseList.activeHorseId + "&trialId=" + trialList.activeTrial.id,
				beforeSend : function() {
					$('#addDeleteHorse').append("<img src='/resources/img/loading31.gif' id='loading' border='0' title='please wait'>");
					$('#loading').css({'position' : 'absolute', 'left' : '50%', 'top' : '50%'});
				},
				complete : function(){
					setTimeout(function(){
						$('#loading').remove();
					}, 3600);
					
				},
				success : function(data) {
					// update information about horses after deleting
					if(trialList.activeTrial != null){
						ajaxHorses.query(trialList.activeTrial.id);
					}
				}
			});
		}
};
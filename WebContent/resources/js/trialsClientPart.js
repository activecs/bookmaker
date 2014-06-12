$(document).ready(function() {

	/**
	 * Show modal window with bid
	 */
	$('.horses_table').on('click', 'tr', function(event) {
		if (typeof ($(this).attr("id")) != "undefined") {
			if (horseList.previousActiveTr != null) {
				// resets the the selection the previous row
				$(horseList.previousActiveTr).css({'background-color' : ''});
			}
			// highlight current row
			horseList.activeHorseId = $(this).attr("id");
			$(this).css({'background-color' : '#BFAAAA'});
			horseList.previousActiveTr = this;
			
			bet.showForm();
		}
	});
	
	/**
	 * Hides modal window
	 */
	$("#background_bet_form").on("click", function(){
		setTimeout(function(){
			bet.hideForm();
			}, 400);
	});
	
	/**
	 * Does query 
	 */
	$("#invisible_bet_form .submit").on("click",function(){
		ajaxMakeBet.query();
	});
	
	$('#invisible_bet_form .in').keyup(function(){
		bet.checkForm();
	});
	
});

var bet = {
		showForm : function(){
			$("#invisible_bet_form").fadeIn("fast");
			$("#background_bet_form").fadeIn("fast");
		},
		
		hideForm : function(){
			$("#background_bet_form").fadeOut(600);
			$("#invisible_bet_form").fadeOut(600);
			$('#invisible_bet_form .in').val('');
		},
		
		//checks whether data is inputed correct. 
		// If no, locks button for sending
		checkForm : function(){
			var sum = $('#invisible_bet_form .in').val();
			var correctSum = sum.replace(",", ".");
			$('#invisible_bet_form .in').val(correctSum);
			if(bet.isNumeric(correctSum) && bet.isAllowed(correctSum)){
				$('.submit').prop('disabled', false);
			}else{
				$('.submit').prop('disabled', true);
			}
		},
		
		// checks is a string -> number
		isNumeric : function(n){
			return !isNaN(parseFloat(n)) && isFinite(n);
		},
		
		isAllowed : function(n){
			if(n>=0 &&n<=1000)return true;
			return false;
		}
};

var ajaxMakeBet = {

		url : '/controller',
			
		query : function(){
			jQuery.ajax({
				type : 'POST',
				cashe : false,
				url : ajaxHorses.url,
				data : $("#invisible_bet_form").serialize() +"&horseId="+ horseList.activeHorseId + "&trialId=" + trialList.activeTrial.id,
				beforeSend : function() {
					$('#wrapper').append("<img src='/resources/img/loading31.gif' id='loading' border='0' title='please wait'>");
					$('#loading').css({'position':'absolute','left':'50%','top':'50%','z-index':'1002','margin-left':'-60px'});
				},
				complete : function(){					
				},
				success : function(data) {
					setTimeout(function(){
						$('#loading').remove();
						alert(data);
						bet.hideForm();
					}, 2600);
				}
			});
		}
};
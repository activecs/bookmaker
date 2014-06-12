$(document).ready(function() {
	$('#winCoefficient').keyup(function(){
		checkField.check();
	});
});

var checkField = {
		// checks is a string -> number
		isNumeric : function(n){
			return !isNaN(parseFloat(n)) && isFinite(n);
		},
		
		isAllowed : function(n){
			if(n>=0)return true;
			return false;
		},
		
		//checks whether coefficient is inputed correct. 
		// If no, locks button for sending
		check : function(){
			var coefficient = $('#winCoefficient').val();
			var correctCoefficient = coefficient.replace(",", ".");
			$('#winCoefficient').val(correctCoefficient);
			if(checkField.isNumeric(correctCoefficient) && checkField.isAllowed(correctCoefficient)){
				$('.submit').prop('disabled', false);
			}else{
				$('.submit').prop('disabled', true);
			}
		}
};


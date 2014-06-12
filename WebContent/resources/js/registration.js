var modalWindow = {
		// show modal window
		show : function() {
			var HeightDocument = $(document).height();
			var WidthDocument = $(document).width();
			var HeightScreen = $(window).height();
			var HeightModal = $(".modal_window").height();
			var TopPosition = Math.abs(Math.ceil((HeightScreen - HeightModal) / 2));
		
			// Imposes a gray background
			$(".modal_bg").css({
				"width" : WidthDocument,
				"height" : HeightDocument
			});
			$(".modal_bg").fadeTo("fast", 0.9);
			
			$(".modal_window").css({
				"top" : TopPosition + "px"
			}).delay(400).fadeIn('fast');
			
			// Prohibits to scroll page
			$("body").css({
				"overflow" : "hidden"
			});
			return false;
		},
		
		// close modal window
		close : function(){
			this.resetError();
			$(".modal_bg, .modal_window").hide(600);
		},
		
		// check email
		validateEmail : function(email){ 
			var re = /\S+@\S+\.\S+/;
			return re.test(email);
		},
		
		// check all fields on form
		checkForm : function(){
			if($('div#registration_form form #name').val() == ''){modalWindow.showError('div#registration_form form #name',"check name"); return false;};
			if($('div#registration_form form #surname').val() == ''){modalWindow.showError('div#registration_form form #surname',"check surname"); return false;};
			if($('div#registration_form form #login').val() == ''){modalWindow.showError('div#registration_form form #login',"check login"); return false;};
			if(!this.validateEmail($('div#registration_form form #email').val())){modalWindow.showError('div#registration_form form #email',"check email"); return false;};
			if($('div#registration_form form #password').val() == ''){modalWindow.showError('div#registration_form form #password',"check password"); return false;};
			return true;
		},
		
		showError : function(element, message){
			this.resetError();
			$('#'+element).css({'border-color':'#E91515','border-width':'2px','background-color':'DF9090'});
			$('#registration_error_message').html(message);
		},
		
		resetError : function(){
			$('.modal_window #registration_form form').css({'border-color':'','border-width':'','background-color':''});
			$('#registration_error_message').html('');
		}
};

var AjaxJson = {

		URL : '/controller',
		
		Query : function(){
			
			jQuery.ajax({
				type : 'POST',
				cashe : false,
				url : AjaxJson.URL,
				data : $('div#registration_form form').serialize(),
				beforeSend : function() {
					$('#registration_loading').css({
						'display':'inline'
					});
				},
				success : function(data) {
					setTimeout(function(){
						$('#registration_loading').css({
							'display':'none'
						});
						if(data.errorCode!=0){
							modalWindow.showError(data.errorElement, data.message);
							return false;
						}else{
							alert(data.message);
							$('#registration_form form')[0].reset();
							modalWindow.close();
						}
					}, 1000);
					//$('#1').html(data.name);
				}
			});
		}
	};

$(document).ready(
		function() {

			// show registration modal window
			$('#registration_link').click(function(){
				modalWindow.show();
			});
			
			// Closes modal window
			$("#close_modal").click(function(){
				modalWindow.close();
			});
			
			// Closes modal window
			$(".modal_bg").click(function(){
				modalWindow.close();
			});
			
			// send query
			$('#register').click(function() {
				if(modalWindow.checkForm()){
					AjaxJson.Query();
					return false;
				}
				return false;
			});
});
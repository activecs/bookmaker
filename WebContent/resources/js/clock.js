$(document).ready(function(){
	clock.go();	
});

var clock = {
	go : function() {
		setInterval(function() {
			var date = new Date();
			var hh = clock.appendZero(date.getHours());
			var mm = clock.appendZero(date.getMinutes());
			var ss = clock.appendZero(date.getSeconds());
			$('#clock').html(hh + ':' + mm + ':' + ss);
		}, 100);
	},

	appendZero : function(a) {
		a = (a < 10) ? '0' + a : a;
		return a;
	}
};

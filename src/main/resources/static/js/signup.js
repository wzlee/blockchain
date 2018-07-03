$(function(){
	
	$('#sign-form').on('submit', function(e) {
		e.preventDefault();
		$(this).ajaxSubmit({
			target: '.message'
		})
	});
	
});
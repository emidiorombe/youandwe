jQuery(function ($) {
	$('#uphoto .basic').click(function (e) {
		$('#basic-modal-content').modal();
		
		  return false;
	});
});

jQuery(function ($) {
	$('.preview').click(function (e) {
		$.modal("<img src='" + this.href + "'/>");
		return false;
	});
});
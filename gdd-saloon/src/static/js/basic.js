jQuery(function ($) {
	$('.basic').click(function (e) {
		$('#modal_c_'+this.name).modal();
		
		  return false;
	});
});

jQuery(function ($) {
	$('.preview').click(function (e) {
		$.modal("<img src='" + this.href + "'/>");
		return false;
	});
});
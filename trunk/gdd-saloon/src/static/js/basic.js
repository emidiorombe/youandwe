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

jQuery(function($){
  $('.form_ajax').submit(function(e){
    $.ajax({
     type: 'POST',
     url: '/add_comment/' ,
     data: {txt_c: 'value1', id_tw: 'value2'},
     beforeSend:function(e){
       alert(e);
     },
     success:function(data){
       alert(data);
     },
     success:function(data){
       alert(data);
     },
    });
    return false;
  });
});

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
  $('.form_ajax').submit(function(e1){
    $.ajax({
     type: 'POST',
     url: '/add_comment/' ,
     data: {txt_c: 'value1', id_tw: 'value2'},
     beforeSend:function(e2){
       alert(e1.name);
     },
     success:function(data){
       //alert(data);
     },
     error:function(data){
       //alert(data);
     },
    });
    return false;
  });
});



function send_ajax(ajax_form, tw_id){
  var form_childs = ajax_form.elements;
  for(var i = 0; i < form_childs.length; i++){
    //alert(form_childs[i].type);
    if(form_childs[i].type == 'textarea')
      var txt_comment = form_childs[i].value; 
      if(txt_comment.length == 0){
        alert('Comentário vazio!');
        return false;
      }
    else if(form_childs[i].type == 'hidden')
      var id_tw = form_childs[i].value;
  }
  
  jQuery.ajax({
     type: 'POST',
     url: '/add_comment/' ,
     data: {txt_c: txt_comment, id_tw: id_tw},
     beforeSend:function(e2){
       $(".dv_form").hide();
       $(".dv_load").html("<div class='loading'><img src='/img/preload.gif' /></div>");
     },
     success:function(data){
       $(".dv_load").html("<div class='loading'>Comentario salvo com sucesso!</div>");
       $("#dv_comments_"+tw_id).append('<div><p>' + txt_comment + '</p></div>')
     },
     error:function(data){
       $(".dv_load").html("<div class='loading'><string>Oops!</string> Não foi possível salvar o comentario</div>");
     },
  });
  return false;
}


var news_mail_label = "insira seu e-mail";

$(document).ready(function(){
	$('#news_mail').val(news_mail_label)
	
	$('#news_mail').focus(function(){
		if($('#news_mail').val() === news_mail_label)
			$('#news_mail').val('')	
	})
	
	$('#news_mail').blur(function(){
		if($('#news_mail').val() === '')
			$('#news_mail').val(news_mail_label)	
	})
	
	$('#form_news_mail').submit(function(){
		$.post('/news/add/', {'mail': $('#news_mail').val(), 'csrfmiddlewaretoken': $('[name=csrfmiddlewaretoken]').val()}).error(function(data) { alert("xii " + data.responseText); })
		return false;
	})
	
})

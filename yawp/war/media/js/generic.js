function cleanSearchBox(box){
	if(box.value.indexOf('ex:') != -1){
		box.value = '';
	}
}

function loadLatestComments(){
	alert('Carregar com JQuery ou XMPP');
}

function defineRating(divId, rate){
	alert('ok');
}

//Operação para adicionar input de novas fotos
jQuery(function ($) {
	$('#add_com_p').click(function (e) {
		$('#com_p').append("<br/><input type='file' name='photo_com_" + new Date().getTime() + "'>");
		return false;
	});
});


//operação de classificar uma empresa com estrelas 
//jQuery(function ($){
//	$('#star').raty({
//		path: '/media/img/',
//		hintList:     ['Nao recomendo', 'Regular', 'Bom', 'Muito Bom', 'Recomendadissimo'],
//		width: '120',
//		onClick:    function(score) { $('#rating').val(score)  }	
//	})
//});

//Inserir notificação de navegadores no index
jQuery(function($){
	if($.browser.msie){
		$("body").bar({
			color 			 : '#1E90FF',
			background_color : '#FFFFFF',
			removebutton     : true,
			time			 : 12000
		});
	}
})
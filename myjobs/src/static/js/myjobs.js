/*
mee
*/

$(document).ready(function(){
						   
$('.loginW').hide();
$('.cadastroW').hide();

$('.login').click(function() {
  $('.loginW').fadeToggle();
  $('.cadastroW').fadeOut();
  return false;
});

$('.cadastre').click(function() {
  $('.cadastroW').fadeToggle();
  $('.loginW').fadeOut();
  return false;
});

$(document).click(function() {
  $('.loginW').hide();
  $('.cadastroW').hide();
});

$('.cadastroslide').click(function() {
	$(this).animate({marginLeft: "-=500px"});
});

$('a.fancybox').fancybox({
	'overlayOpacity' : .5,
	'overlayColor' : '#000000',
	'titlePosition' : 'over'
});

});
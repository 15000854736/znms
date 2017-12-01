
$(document).ready(function() {
	var leftMenu = $(".menu_znms").find("li");
	
	leftMenu.each(function() {
		var subMenu = $(this).children("ul");
		$(this).hover(function() {
			$(this).addClass("menu_hov");
			subMenu.stop(true, true).show();
		});
		$(this).mouseleave(function() {
			$(this).removeClass("menu_hov");
			subMenu.stop(true, true).hide();
		});
	});
});
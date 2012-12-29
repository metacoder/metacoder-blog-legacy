function enableMCEAsMixin(element){
	tinyMCE.init({ 
		mode : "exact",
		remove_linebreaks : false,
		theme_advanced_resizing : true,
		elements: element
	});
}
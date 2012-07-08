function enableMCEAsMixin(element){
	tinyMCE.init({ 
		mode : "exact",
		theme_advanced_resizing : true,
		elements: element
	});
}
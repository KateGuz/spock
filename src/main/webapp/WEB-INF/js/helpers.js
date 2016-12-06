Handlebars.registerHelper('each', function(context, options) {
	var ret = "";
	for(var i=0, j=context.length; i<j; i++) {
		ret = ret + options.fn(context[i]);
	}
	return ret;
});
Handlebars.registerHelper('if',function(conditional,options){
	if(conditional){
		return options.fn(this);
	}else{
		return options.inverse(this);
	}
});
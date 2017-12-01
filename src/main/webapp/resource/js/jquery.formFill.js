/**
 * jQuery Form Fill - http://makoto.blog.br/formFill
 * --------------------------------------------------------------------------
 *
 * Licensed under The MIT License
 * 
 * @version     0.1
 * @since       16.06.2010
 * @author      Makoto Hashimoto
 * @link        http://makoto.blog.br/formFill
 * @twitter     http://twitter.com/makoto_vix
 * @license     http://www.opensource.org/licenses/mit-license.php MIT 
 * @package     jQuery Plugins
 * 
 * Usage:
 * --------------------------------------------------------------------------
 * 
 *	$('form#formUser').fill({"name" : "Makoto Hashimoto", "email" : "makoto@makoto.blog.br"});
 *  
 *  <form id="formUser">
 *  	<label>Name:</label>
 *  	<input type="text" name="user.name"/>
 *  	<label>E-mail:</label>
 *  	<input type="text" name="user.email"/>
 *  </form>
 */
(function($){
	
	function Fill() {
		this.defaults = {
			styleElementName: 'object',	// object | none
			dateFormat: 'yyyy-MM-dd HH:mm:ss',
			debug: false,
			elementsExecuteEvents: ['checkbox', 'radio', 'select-one']
		};
	};
	
	$.extend(Fill.prototype, {
		setDefaults : function (settings) {
			this.defaults = $.extend({}, this.defaults, settings);
			return this;
		},
		
		fill : function (obj, _element, settings) {
			options = $.extend({}, this.defaults, settings);

			_element.find("*").each(function(i, item){
				if ($(item).is("input") || $(item).is("select") || $(item).is("textarea")) {
					try {
						try {
							var value = eval('obj.'+$(item).attr("name") );

						} catch(e) {
							if (options.debug) {
								debug(e.message);
							}
						}					

						if (value != null) {
							switch ($(item).attr("type")) {
								case "hidden":
//									if ($(item).attr("role")=='date') {
//										var re = /^[-+]*[0-9]*$/;
//										var dateValue = null;
//										var dateValueStr = '';
//										if (re.test(value)) {
//											var dateValue = new Date(value.time);
//											dateValueStr = dateValue.format("yyyy-MM-dd hh:mm:ss");
//										} else if (value) {			
//											var dateValue = new Date(value.time);
//											dateValueStr = dateValue.format("yyyy-MM-dd hh:mm:ss");
//										}
//										$(item).val(dateValueStr);							
//									} else 
									if ($(item).attr("alt") == "double") {
										$(item).val(value.toFixed(2));
									} else {
										$(item).val(value);
									}
								break;
								case "password":
								case "textarea":
									$(item).val(value);
								break;

								case "text":
//									if ($(item).attr("role")=='date') {
//										var re = /^[-+]*[0-9]*$/;
//										var dateValue = null;
//										var dateValueStr = '';
//										if (re.test(value)) {
//											var dateValue = new Date(value.time);
//											dateValueStr = dateValue.format("yyyy-MM-dd hh:mm:ss");
//										} else if (value) {			
//											var dateValue = new Date(value.time);
//											dateValueStr = dateValue.format("yyyy-MM-dd hh:mm:ss");
//										}
//										$(item).val(dateValueStr);							
//									}else 
									if($(item).attr("role")=='combobox') {
										$(item).jqxDropDownList('val', value)
										alert($(item).text());
									}else if ($(item).attr("alt") == "double") {
										$(item).val(value.toFixed(2));
									} else {
										$(item).val(value);
									}
								break;

								case "select-one":
									if (value) {
										$(item).val(value);
									}
								break;
								case "radio":
									$(item).each(function (i, radio) {
										if ($(radio).val() == value) {
											$(radio).attr("checked", "checked");
										}
									});
								break;
								case "checkbox":
									if ($.isArray(value)) {
										$.each(value, function(i, arrayItem) {
											if (typeof(arrayItem) == 'object') {											
												arrayItemValue = eval("arrayItem." + arrayAtribute);
											} else {
												arrayItemValue = arrayItem;
											}
											if ($(item).val() == arrayItemValue) {
												$(item).attr("checked", "checked");
											}
										}); 
									} else {
										if ($(item).val() == value) {
											$(item).attr("checked", "checked");
										}
									}						
								break;
							}
							executeEvents(item);
						}
					} catch(e) {
						if (options.debug) {
							debug(e.message);
						}
					}

				}

			});
		}
	});
	
	Date.prototype.format = function(fmt)   
	{ //author: meizz   
	  var o = {   
	    "M+" : this.getMonth()+1,                 //月份   
	    "d+" : this.getDate(),                    //日   
	    "h+" : this.getHours(),                   //小时   
	    "m+" : this.getMinutes(),                 //分   
	    "s+" : this.getSeconds(),                 //秒   
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
	    "S"  : this.getMilliseconds()             //毫秒   
	  };   
	  if(/(y+)/.test(fmt))   
	    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
	  for(var k in o)   
	    if(new RegExp("("+ k +")").test(fmt))   
	  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
	  return fmt;   
	}  
	
	$.fn.fill = function (obj, settings) {
		$.fill.fill(obj, $(this), settings);
		return this;
	};
	
	$.fill = new Fill();
	
	function executeEvents(element) {
		if (jQuery.inArray($(element).attr('type'), $.fill.defaults.elementsExecuteEvents)) {
			if ($(element).attr('onchange')) {
				$(element).change();
			}

			if ($(element).attr('onclick')) {
				$(element).click();
			}
		}	
	};
	
	function debug(message) {                                                                                            // Throws error messages in the browser console.
        if (window.console && window.console.log) {
            window.console.log(message);
        }
    };
})(jQuery);
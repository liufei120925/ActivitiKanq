(function() {

	var cdnVendor = null,  _cdnVendor= null;
	cdnVendor = _cdnVendor = function(baseName, prefix, url) {
		var platformCdnPath = '//192.168.0.28:8080/platform-cdn/static/';
		if ( arguments.length == 0 ) {
			return;
		} else if ( arguments.length == 1 ) {
			url = arguments[0];
		} else if ( arguments.length == 2 ) {
			if(_cdnVendor['moreAddress'][baseName]) {
				url = arguments[1];
				platformCdnPath =  cdnVendor.moreAddress[baseName];
			} else {
				url = arguments[1];
				prefix = arguments[0];
				platformCdnPath = prefix + "!" + platformCdnPath;
			}
		} else if ( arguments.length == 3 ) {
			platformCdnPath = prefix + "!" + cdnVendor.moreAddress[baseName];
		}
		return platformCdnPath + url;
	}
	
	if(typeof _cdnVendor['moreAddress'] != 'object') {
		_cdnVendor.moreAddress = {
		
		}
	}
	
	var contextPath = function() {
		var pathNameEndIndex = window.location.pathname.indexOf("/", 1);
		return window.location.pathname.substring(0, pathNameEndIndex);
	}
	
	_cdnVendor.contextPath = contextPath();

	window.cdnVendor = cdnVendor;
})()
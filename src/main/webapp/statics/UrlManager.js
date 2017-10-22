(function() {

	var isEmpty = function(obj) {
		if ( obj == undefined || obj == null || obj == '' ) { return true; }
		return false;
	}

	var globalPlatformUrlManager = window.$$globalPlatformUrlManager;

	var globalPrefix = null;

	/**
	 * 设置全局的前缀:使用前缀可以设置服务器的地址
	 * 设置前缀在url最后不需要添加"/"
	 * 例如：prefix = http://localhost:8080
	 */
	var setGlobalPrefix = function(prefix) {
		globalPrefix = prefix;
	}

	var getQueryString = function(name) {
		var reg = new RegExp( "(^|&)" + name + "=([^&]*)(&|$)", "i" );
		var r = window.location.search.substr( 1 ).match( reg );
		if ( r != null ) return (r[2]);
		return null;
	}

	var contextPath = function() {
		var pathNameEndIndex = window.location.pathname.indexOf( "/", 1 );
		return window.location.pathname.substring( 0, pathNameEndIndex );
	}

	var UrlFactory = function(suffix) {
		var self = this;
		self.suffix = suffix;
		self.prefix = null;
		self.urlMap = {};

		/**
		 * setPrefix，针对UrlFactory实例对象有效。
		 * 当使用此方法设置以后，在使用$getUrl时就不会使用全局的prefix而是使用setPrefix中设置的prefix。
		 */
		UrlFactory.prototype.setPrefix = function(prefix) {
			self.prefix = prefix;
		}

		/**
		 * 获得url
		 */
		UrlFactory.prototype.$getUrl = function(name) {
			var self = this;
			var notFoundUrl = self.urlMap[name];
			if ( isEmpty( notFoundUrl ) ) {
				var realPrefix = self.prefix;
				if ( isEmpty( realPrefix ) ) {
					if ( isEmpty( globalPrefix ) ) {
						realPrefix = contextPath();
					} else {
						realPrefix = globalPrefix;
					}
				}

				return realPrefix + "/" + name + self.suffix;
			}
			return notFoundUrl;
		}

		/**
		 * 动态添加
		 */
		UrlFactory.prototype.$url = function(name) {
			var self = this;
			return function(url) {
				if ( arguments.length == 0 ) {
					return self.$getUrl( name );
				} else if ( arguments.length == 1 ) {
					self.$addUrl( name )( url );
				}
			}
		}

		/**
		 * 做静态缓存
		 */
		UrlFactory.prototype.$addUrl = function(name) {
			var self = this;
			return function(url) {
				self.urlMap[name] = contextPath() + '/' + url + self.suffix;
			};
		}
	}

	/**
	 * 创建一个UrlFactory
	 */
	var createUrlFactory = function(prefix, name, suffix) {

		if ( arguments.length == 2 ) {
			suffix = name;
			name = prefix;
		}

		var urlFactory = new UrlFactory( suffix );
		if ( arguments.length == 3 ) {
			urlFactory.setPrefix( prefix );
		}

		globalPlatformUrlManager[name] = urlFactory;
		return createUrlFactory;
	}

	if ( !globalPlatformUrlManager ) {
		globalPlatformUrlManager = window.$$globalPlatformUrlManager = {
			contextPath : contextPath()
		};

		urlManager = globalPlatformUrlManager;

	}
	createUrlFactory( '$viewUrls', '.shtml' )//
	( '$modelUrls', '.do' )//
	( '$staticViewUrls', '.html' );

	globalPlatformUrlManager['getQueryString'] = getQueryString;

	if ( window.define != undefined && typeof window.define == 'function' ) {
		define( [], function() {

			return {
				$get : [ //
				function() {
					return globalPlatformUrlManager;
				}],

				urlManager : globalPlatformUrlManager,
				createUrlFactory : createUrlFactory,
				setGlobalPrefix : setGlobalPrefix,

			};

		} );
	}

})();

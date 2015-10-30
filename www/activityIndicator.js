var exec = require('cordova/exec');
var ActivityIndicator = {
    show: function (text, cancelCallback) {
      if (cancelCallback == true && typeof cancelCallback !== "function") {
          cancelCallback = function () {};
      }
    	text = text || "Please wait...";
        cordova.exec(cancelCallback, null, "ActivityIndicator", "show", [text, !!cancelCallback ]);
    },
    hide: function () {
        cordova.exec(null, null, "ActivityIndicator", "hide", []);
    }
};

module.exports = ActivityIndicator;

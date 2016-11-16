/*global cordova, module*/
var exec = cordova.require('cordova/exec');
var iiibeacon = iiibeacon || {};
module.exports = iiibeacon;

iiibeacon.greet = function (name, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "iiibeacon", "greet", [name]);
    return true;
};

iiibeacon.init = function (successCallback, errorCallback) {
	exec(successCallback, errorCallback, "iiibeacon", "init", []);
	return true;
};


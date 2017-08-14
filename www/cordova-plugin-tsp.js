//var exec = require('cordova/exec');
var TSP = function() {
    var _appID = "";
};
TSP.prototype.startInit = function(appId) {
	TSP._appID = appId;
    return this;
};
TSP.prototype.tsp_a30000 = function(arg0, success, error) {
	console.log("Cordova-plugin-tsp.js");
	
	cordova.exec(success, error, "tsp", "tsp_a30000", [arg0]);

};
TSP.prototype.tsp_a40000 = function(arg0, success, error) {
	console.log("Cordova-plugin-tsp.js");
	
	cordova.exec(success, error, "tsp", "tsp_a40000", [arg0]);

};
TSP.prototype.tsp_330000 = function(arg0, success, error) {
	console.log("Cordova-plugin-tsp.js");
	
	cordova.exec(success, error, "tsp", "tsp_330000", [arg0]);

};
TSP.prototype.tsp_M10000 = function(arg0, success, error) {
	console.log("Cordova-plugin-tsp.js");
	
	cordova.exec(success, error, "tsp", "tsp_M10000", [arg0]);

};
TSP.prototype.tsp_M20000 = function(arg0, success, error) {
	console.log("Cordova-plugin-tsp.js");
	
	cordova.exec(success, error, "tsp", "tsp_M20000", [arg0]);

};
//-------------------------------------------------------------------

if(!window.plugins)
    window.plugins = {};

if (!window.plugins.TSP)
    window.plugins.TSP = new TSP();

if (typeof module != 'undefined' && module.exports)
    module.exports = TSP;
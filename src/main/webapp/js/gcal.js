/*!
 * FullCalendar v2.3.1 Google Calendar Plugin
 * Docs & License: http://fullcalendar.io/
 * (c) 2015 Adam Shaw
 */

(function(factory) {
	if (typeof define === 'function' && define.amd) {
		define([ 'jquery' ], factory);
	}
	else if (typeof exports === 'object') { // Node/CommonJS
		module.exports = factory(require('jquery'));
	}
	else {
		factory(jQuery);
	}
})(function($) {


	var API_BASE = '/event';
	var fc = $.fullCalendar;
	var applyAll = fc.applyAll;

	fc.sourceFetchers.push(function(sourceOptions, start, end, timezone) {
		if(sourceOptions.fetched === true) return;
		sourceOptions.url = API_BASE;
		var data = {};
		data.timeMin = start.unix();
		data.timeMax = end.unix();
		return $.extend({}, sourceOptions, {'data': data, startParam: false, endParam: false, timezoneParam: false,
			fetched: true,
			url: API_BASE, 
			success: function(events){
				successArgs = [ events ].concat(Array.prototype.slice.call(arguments, 1)); // forward other jq args
				successRes = applyAll(sourceOptions.success, this, successArgs);
				if ($.isArray(successRes)) {
					return successRes;
				}
			}});
	});

});

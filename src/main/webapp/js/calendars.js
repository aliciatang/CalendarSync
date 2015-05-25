$(document).ready(function() {
	console.log("calendar.js");
	$.get('/calendar', null, function(data){
		console.log(data);
		var map = {};
		var list = [];
		$.each(data, function(index, cal){
			map[cal.id] = cal;
			list.push("<li id='"+cal.id+"' style='background-color:"+ cal.color+"'>" + cal.name + "</li>");
		});
		$("#gcalendar").append(list);
		$("#gcalendar li").click(function(){
			$(this).toggleClass("disabled");
			var id = $(this).attr('id');
			var bg = map[id]["color"];
			$(this).css('background-color', $(this).hasClass("disabled") ? "" : bg);
		});
		
		$('#calendar').fullCalendar({eventSources: data});
	}, 'json');
});

$(document).ready(function() {
	$.get('/calendar', null, function(data){
		var map = {};
		var list = [];
		$.each(data, function(index, cal){
			map[cal.id] = cal;
			list.push("<li id='"+cal.id+"' style='background-color:"+ cal.backgroundColor+"'>" + cal.name + "</li>");
		});
		$("#gcalendar").append(list);
		$("#gcalendar li").click(function(){
			$(this).toggleClass("disabled");
			var id = $(this).attr('id');
			var bg = map[id]["backgroundColor"];
			$(this).css('background-color', $(this).hasClass("disabled") ? "" : bg);
		});
	}, 'json');
});

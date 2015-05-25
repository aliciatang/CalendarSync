
$(document).ready(function() {
	$.get('/calendar', null, function(data){
		var map = {};
		var list = [];
		$.each(data, function(index, cal){
			map[cal.id] = cal;
			list.push("<li id='"+cal.id+"' style='background-color:"+ cal.color+"'>" + cal.name + "</li>");
			$('#calendar').fullCalendar( 'addEventSource', cal);
		});
		$("#gcalendar").append(list);
		$("#gcalendar li").click(function(){
			$(this).toggleClass("disabled");
			var id = $(this).attr('id');
			var source = map[id];
			if ($(this).hasClass("disabled")) {				
				$(this).css('background-color',  "");
				$('#calendar').fullCalendar('removeEventSource', source);
			} else {
				$(this).css('background-color',  source["color"]);
				$('#calendar').fullCalendar('addEventSource', source);
			}
		});
	}, 'json');
	$('#calendar').fullCalendar();
});


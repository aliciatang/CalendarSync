
$(document).ready(function() {
	var form = $('#dialog-form').find( "form" ).on( "submit", function( event ) {
	      event.preventDefault();
	      createEvent();
	    });
	var createEvent = function() {
		var data = form.serializeObject();
		console.log(data);
		data["start"] = Date.parse(data.start);
		data["end"] = Date.parse(data.end);
		console.log(JSON.stringify(data));
		$.ajax({
			url: '/event?cal='+ encodeURI(data["calendar"]),
			type: 'POST',
			contentType:"application/json; charset=utf-8",
			dataType:"json",
			data: JSON.stringify(data),
			success: function(response){
				console.log(response);
			},
			
		});
	}
	var dialog = $('#dialog-form').dialog({
		autoOpen: false,
		buttons: {
			"create": createEvent,
		},
	});
	$('#add-event').button().click(function(e){
		dialog.dialog("open")
	});
});


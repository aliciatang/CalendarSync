
$(document).ready(function() {
	var dialog, form, createEvent;
	createEvent = function() {
		var data = form.serializeObject();
		console.log(data);
		$.post('/event?cal='+ encodeURIComponent(data["calendar"]), data, function(response){
			console.log(response);
		}, 'json' );
	}
	
	var dialog = $('#dialog-form').dialog({
		autoOpen: false,
		buttons: {
			"create": createEvent,
		},
	});
	form = dialog.find( "form" ).on( "submit", function( event ) {
	      event.preventDefault();
	      createEvent();
	    });
	
	$('#add-event').button().click(function(e){
		dialog.dialog("open")
	});
});


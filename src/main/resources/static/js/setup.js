$("#createGame").submit(function(event) {
    event.preventDefault();
    var postParameters = {
        gameMode: $("#gameMode").val()
    };
	$.post("/create", postParameters, function(responseJSON) {
	   window.location.href = "/room";
	});
});
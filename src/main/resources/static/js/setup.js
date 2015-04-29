$("#createGame").submit(function(event) {
    event.preventDefault();
    var postParameters = {
        gameMode: $("input[name='gameMode']:checked").val()
    };
    console.log(postParameters)
	$.post("/create", postParameters, function(responseJSON) {
	   window.location.href = "/play";
	});
});
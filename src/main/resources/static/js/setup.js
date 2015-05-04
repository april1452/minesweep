// send game specs
$("#create").click(function(event) {
	event.preventDefault();
	$.getScript("../js/js.cookie.js", function() {
		$.cookie("minesweepName", $("#hostName").val());
	});
	var postParameters = {
		roomName: $("#gameName").val(),
		gameMode: $("input[name='gameMode']:checked").val(),
		difficulty: $("#difficulty").val(),
		boardType: $("input[name='boardType']:checked").val(),
		boardWidth: $("#width").val(),
		boardHeight: $("#height").val(),
		numTeams: $("input[name='teams']:checked").val(),
		numPlayers: $("input[name='players']:checked").val(),
		numLives: $("#lives").val()
	};
	console.log(postParameters);
	$.post("/create", postParameters, function(responseJSON) {
		window.location.href = "/play";
	});
});

// display game mode info
$("[name='gameMode']").click(function() {
	hideInfo();
	var mode = this.getAttribute("value");
	switch(mode) {
		case "CLASSIC":
		$("#classic").show();
		break;
		case "LAYERS":
		$("#layers").show();
		break;
		case "TERRITORY":
		$("#territory").show();
		break;
		case "PATHS":
		$("#paths").show();
		break;
		case "FSU":
		$("#fsu").show();
		break;
	}
});

function hideInfo() {
	$("#classic").hide();
	$("#layers").hide();
	$("#territory").hide();
	$("#paths").hide();
	$("#fsu").hide();
};




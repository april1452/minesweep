// send game specs
$("#create").click(function(event) {
	event.preventDefault();

	var roomNameVal = $("#gameName").val();
	var hostNameVal = $("#hostName").val();

	// fill out roomname and host name
	if (roomNameVal != "" && hostNameVal != "") {
		var hostCookie;
		$.getScript("../js/js.cookie.js", function() {
			$.cookie("minesweepName", hostNameVal);
			hostCookie = $.cookie("minesweepId");

			var postParameters = {
				roomName: roomNameVal,
				hostId: hostCookie,
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
	} else if (roomNameVal == "" && hostNameVal == "") {
		alert("Hey Anon, you need a room name and a player name!");
	} else if (roomNameVal == "") {
		alert("Better name your room, " + hostNameVal + ".");
	} else {
		alert("NO ANONS. Enter your name to host a game.");
	}

	
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
		case "TIMER":
		$("#timer").show();
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




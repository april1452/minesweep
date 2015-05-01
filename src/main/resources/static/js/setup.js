$("#create").click(function(event) {
    event.preventDefault();
    var postParameters = {
    		roomName: $("#gameName").val(),
        gameMode: $("input[name='gameMode']:checked").val(),
        boardType: $("input[name='boardType']:checked").val()
    };
    console.log(postParameters);
	$.post("/create", postParameters, function(responseJSON) {
	   window.location.href = "/play";
	});
});

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




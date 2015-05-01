$("#create").click(function(event) {
    event.preventDefault();
    var postParameters = {
        gameMode: $("input[name='gameMode']:checked").val()
        //boardType: $("input[name='boardShape']:checked").val();
    };
    console.log(postParameters)
	$.post("/create", postParameters, function(responseJSON) {
	   window.location.href = "/play";
	});
});

$("[name='gameMode']").click(function() {
	hideInfo();
	var mode = this.getAttribute("value");
	switch(mode) {
		case "CLASSIC":
			$("#CLASSIC").show();
			break;
		case "layers":
			$("#layers").show();
			break;
		case "territory":
			$("#territory").show();
			break;
		case "paths":
			$("#paths").show();
			break;
		case "fsu":
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
}


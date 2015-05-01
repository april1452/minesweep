	$("#createGame").submit(function(event) {
	    event.preventDefault();
	    var postParameters = {
	        gameMode: $("input[name='gameMode']:checked").val(),
	        boardType: $("input[name='boardShape']:checked").val()
	    }
	    console.log(postParameters);
		$.post("/create", postParameters, function(responseJSON) {
				   window.location.href = "/play"
		})
	});

	// $("[name='gameMode']").click(function() {
	// 	hideInfo();
	// 	var mode = this.getAttribute("value");
	// 	switch(mode) {
	// 		case "CLASSIC":
	// 			$("#CLASSIC").show();
	// 			break;
	// 		case "layers":
	// 			$("#layers").show();
	// 			break;
	// 		case "territory":
	// 			$("#territory").show();
	// 			break;
	// 		case "paths":
	// 			$("#paths").show();
	// 			break;
	// 		case "fsu":
	// 			$("#fsu").show();
	// 			break;
	// 	}
	// });

	// $("[name='boardShape']").click(function() {
	//     hideInfo();
	//     var mode = this.getAttribute("value");
	//     switch(mode) {
	//         case "RECTANGLE":
	//             $("#Rectangle").show();
	//             break;
	//         case "":
	//             $("Triangular").show();
	//             break;
	//     }
	// });

	// function hideInfo() {
	// 	$("#classic").hide();
	// 	$("#layers").hide();
	// 	$("#territory").hide();
	// 	$("#paths").hide();
	// 	$("#fsu").hide();
	// }

	// $("[name='boardShape']").click(function() {
	// 	hideInfo();
	// 	var board = this.getAttribute("value");
	// 	switch(board) {
	// 		case "classic":
	// 			$("#classic").show();
	// 			break;
	// 		case "layers":
	// 			$("#layers").show();
	// 			break;
	// 		case "territory":
	// 			$("#territory").show();
	// 			break;
	// 		case "paths":
	// 			$("#paths").show();
	// 			break;
	// 		case "fsu":
	// 			$("#fsu").show();
	// 			break;
	// 	}
	// });


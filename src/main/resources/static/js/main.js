function getGames() {

    $.get("/games", postParameters, function(responseJSON) {
        var response = JSON.parse(responseJSON);
        document.getElementById("responseHolder").innerHTML = response;
    });
}



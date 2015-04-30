var socket = new WebSocket("ws://localhost:7777");

socket.onopen = function(event) {
    var sendData = {
        type: "joinRoom",
        minesweepId: $.cookie("minesweepId"),
        minesweepRoomId: $.cookie("minesweepRoomId"),
        name: "test name"
    };
    socket.send(JSON.stringify(sendData));
};

socket.onmessage = function (event) {
    var responseJson = JSON.parse(event.data);
    if(responseJson.type === "joinResponse") {
        $.cookie("minesweepTeamId", responseJson.teamId)
}


function getUpdate() {
    $.get("/roomUpdate", function(responseJSON) {
        if(responseJSON === "gameStarted") {
            window.location.href = "/game";
            return;
        }
        var response = JSON.parse(responseJSON);
        var teams = response.teams;
        var innerBox = "";
        
        $.each(teams, function(i, team) {
            innerBox += "<p>" + team.name + ": </p>";
            $.each(team.players, function(j, player) {
                innerBox += "<p>" + player.name + "</p>";
            });
        });
        
        console.log(innerBox);
        
        $("#usernameBox").html(innerBox);
    });
}

$("#start").click(function() {
    $.post("/start");
});
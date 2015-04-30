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
        $.cookie("minesweepTeamId", responseJson.data);
    } else if (responseJson.type === "update") {
        var innerBox = "";
        
        var roomInfo = responseJson.data;

        var teams = roomInfo.teams;

        $.each(teams, function(i, team) {
            innerBox += "<p>" + team.name + ": </p>";
            $.each(team.players, function(j, player) {
                innerBox += "<p>" + player.name + "</p>";
            });
        });
        
        $("#usernameBox").html(innerBox);
    }
}

$("#start").click(function() {
    var sendData = {
        type: "startGame",
        minesweepId: $.cookie("minesweepId"),
        minesweepRoomId: $.cookie("minesweepRoomId"),
    };
    socket.send(JSON.stringify(sendData));
});
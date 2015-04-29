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
    console.log(event.data);
}


//addRoom();
//startLoop();

function addRoom() {
    $.post("/roomAdd");
}

function startLoop() {
    window.setInterval("getUpdate()", 500);
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
updateLoop();

function updateLoop() {
    window.setInterval("getGames()", 500);
}

function getGames() {
    $.get("/games", function(responseJSON) {
        var response = JSON.parse(responseJSON);
        var gameOptions = "";
        $.each(response, function(index, roomInfo) {
            console.log(roomInfo);
            if(roomInfo.sessionType === "setup") {
                gameOptions += "<option value=\"" + roomInfo.roomId + "\">A Game</option>";
            }
        });
        
        $("#gameId").html(gameOptions);
    });
}

$("#joinGame").submit(function(event) {
    event.preventDefault();
    document.cookie = "minesweepRoomId=" + $("#gameId").val();
    window.location.href = "/room";
});

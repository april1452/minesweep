document.onload = getGames();

$("#refresh").click(function() {
    getGames();
});

var clicked;
var currentForm;
var currentButton;

function getGames() {
    $.get("/games", function(responseJSON) {
        var response = JSON.parse(responseJSON);
        $("#gamesList").empty();
        gameIds = new Array();
        console.log("ROOMS");
        $.each(response, function(roomId, roomInfo) {
            console.log(roomInfo);

            // Get number of players currently in room
            var numPlayer = 0;
            $.each(roomInfo.teams, function(index, team) {
                $.each(team.players, function(index, player) {
                    numPlayer++;
                })
            })

            var specs = roomInfo.gameSpecs;
            if(roomInfo.sessionType === "SETUP") { 
                // Create button for each game on server
                var li = document.createElement("LI");
                var node = document.createElement("a");
                node.setAttribute("class", "button line-purple gameButton");
                var textnode = document.createTextNode(specs.mode + ": " + roomInfo.roomName + " (" + numPlayer + "/" + (specs.numTeamPlayers * specs.numTeams) +  " players)");
                node.appendChild(textnode);
                li.appendChild(node);
                $("#gamesList").append(li);

                node.onclick = function() {
                    console.log("Hello");
                    document.cookie = "minesweepRoomId=" + roomId;
                    console.log(document.cookie);
                    window.location.href = "/play";
                }
            }
        });

    });
}

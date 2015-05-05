document.onload = getGames();

$("#refresh").click(function() {
    getGames();
});

var clicked;
var currentForm;
var currentButton;

// load and display list of currently open game rooms
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
            var totalPlayers = specs.numTeamPlayers * specs.numTeams;

            if(roomInfo.sessionType === "SETUP") { 
                // Create button for each game on server
                var room = '<li><a id="' + roomId + '" class="button line-purple modal-trigger" data-modal-open="modal-name-'+roomId+'">'+specs.mode + ": " + roomInfo.roomName + " (" + numPlayer + "/" + totalPlayers +  " players)</a>";
                room += '<div class="modalplate" data-modal-id="modal-name-'+roomId+'"><div class="modalplate-content"><div class="formplate"><label for="player-name-'+roomId+'+">Enter your player name:</label><input type="text" id="player-name-'+roomId+'" name="player-name-'+roomId+'"></input><a id="join-'+roomId+'" class="button pink close">Let me play!</a><a class="close button line-pink" style="margin-left: 5px">Nvm</a></div></div></div></li>';
                $("#gamesList").html($("#gamesList").html() + room);
                
                if (numPlayer == totalPlayers) {
                    $("#" + roomId).attr('class', 'button line-purple');
                    $("#" + roomId).click(function() {
                        alert('Go away, the room is full.');
                    });
                } else {
                    $("#join-" + roomId).click(function() {
                        $.getScript("../js/js.cookie.js", function() {
                            $.cookie("minesweepRoomId", roomId);
                            $.cookie("minesweepName", $("#player-name-"+roomId).val());
                        });
                        window.location.href = "/play";
                    });
                }
            }
        });

    });
}

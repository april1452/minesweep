document.onload = getGames();

$("#refresh").click(function() {
    getGames();
});

var clicked;
var currentForm;
var currentButton;
var fullRooms;
var notFull;

// load and display list of currently open game rooms
function getGames() {
    $.get("/games", function(responseJSON) {
        var response = JSON.parse(responseJSON);
        $("#gamesList").empty();
        gameIds = new Array();
        
        fullRooms = new Array();
        notFull = new Array();

        $.each(response, function(roomId, roomInfo) {
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
                var button;
                if (numPlayer == totalPlayers) {
                    fullRooms.push(roomId);
                    button = '<li><a id="' + roomId + '" class="button line-purple">'+specs.mode + ": " + roomInfo.roomName + " (" + numPlayer + "/" + totalPlayers +  " players)</a>";   
                } else {
                    notFull.push(roomId);
                    button = '<li><a id="' + roomId + '" class="button line-purple modal-trigger" data-modal-open="modal-name-'+roomId+'">'+specs.mode + ": " + roomInfo.roomName + " (" + numPlayer + "/" + totalPlayers +  " players)</a>";
                    button += '<div class="modalplate" data-modal-id="modal-name-'+roomId+'"><div class="modalplate-content"><div class="formplate"><label for="player-name-'+roomId+'+">Enter your player name:</label><input type="text" id="player-name-'+roomId+'" name="player-name-'+roomId+'"></input><a id="join-'+roomId+'" class="button pink close">Let me play!</a><a class="close button line-pink" style="margin-left: 5px">Nvm</a></div></div></div></li>';
                }
                $("#gamesList").append(button);
            }
        });

        // bind alert to full room buttons
        for (var room in fullRooms) {
            console.log(room + ': ' + fullRooms[room]);
            $("#" + fullRooms[room]).click(function() {
                alert('Go away, the room is full.');
            });
        }

        for (var room in notFull) {
            console.log(room + ': ' + notFull[room]);
            var roomId = notFull[room];
            $("#join-" + roomId).click(function() {
                $.getScript("../js/js.cookie.js", function() {
                    $.cookie("minesweepRoomId", roomId);
                    $.cookie("minesweepName", $("#player-name-"+roomId).val());
                });
                window.location.href = "/play";
            });
        }
    });
}

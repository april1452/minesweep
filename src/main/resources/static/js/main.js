updateLoop();

getGames();

var clicked;
var currentForm;// = document.createElement("form");

function updateLoop() {
    window.setInterval("getGames()", 500);
}

function getGames() {
    $.get("/games", function(responseJSON) {
        var response = JSON.parse(responseJSON);
        var gameOptions = "";
        $("ul").empty();
        gameIds = new Array();
        $.each(response, function(index, roomInfo) {
            console.log(roomInfo);
            //document.write("<div id='room'><p>" + roomInfo.gameMode + "</p></div>");
            /*if(roomInfo.sessionType === "setup") {
                gameOptions += "<option value=\"" + roomInfo.roomId + "\">A Game</option>";
            }*/         
            var numPlayer = 0;
            $.each(roomInfo.teams, function(index, team) {
                $.each(team.players, function(index, player) {
                    numPlayer++;
                })
            })
            if(roomInfo.sessionType === "setup") { 
                var node = document.createElement("LI");
                var textnode = document.createTextNode(roomInfo.gameMode + ": " + numPlayer + " players");
                node.appendChild(textnode);
                document.getElementById("gamesList").appendChild(node);

                var box = document.createElement("DIV");
                box.id = roomInfo.roomId;

                var moreInfo = document.createTextNode("Text to come...");
                box.appendChild(moreInfo);

                var form = document.createElement("form");
                form.id = "joinForm";

                var button = document.createElement("INPUT");
                button.setAttribute("type", "submit");
                button.setAttribute("value", "Join Game");
                button.setAttribute("id", roomInfo.roomId);
                form.appendChild(button);
                box.appendChild(form);

                if(clicked != roomInfo.roomId) {
                  box.style.visibility = "hidden";
                }
                node.appendChild(box);
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

document.getElementById("gamesList").addEventListener("click",function(e) {
    if(e.target && e.target.nodeName == "LI") {
         //console.log(e.target.id + " was clicked");
         clicked = e.toElement.childNodes[1].id;
         //console.log(clicked);
         //console.log(e.toElement);
         currentForm = e.toElement.childNodes[1].childNodes[1];
         console.log(currentForm);
         console.log(document.getElementById("joinForm"));
         e.toElement.childNodes[1].style.visibility = "visible";
    }
});

document.getElementById("joinForm").submit(function(event) {
    console.log("Here we go!");
    event.preventDefault();
    document.cookie = "minesweepRoomId=" + currentForm.childNodes[0].id;
    window.location.href = "/room";
});
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
        $("ul").empty();
        gameIds = new Array();
        console.log(response);
        $.each(response, function(roomId, roomInfo) {
            console.log(roomInfo);
            var numPlayer = 0;
            $.each(roomInfo.teams, function(index, team) {
                $.each(team.players, function(index, player) {
                    numPlayer++;
                })
            })
            console.log(roomInfo.sessionType);
            if(roomInfo.sessionType === "SETUP") { 
                var node = document.createElement("LI");
                var textnode = document.createTextNode(roomInfo.gameSpecs.mode + ": " + numPlayer + " players");
                node.appendChild(textnode);
                node.setAttribute("id", "gameTitle");
                var  list = $("#gamesList");
                console.log(list);
                list.append(node);
                console.log("NODE APPENDED");

                var box = document.createElement("DIV");
                box.id = roomId;

                var name = document.createTextNode(roomInfo.roomName);
                box.appendChild(name);

                box.appendChild(document.createElement("BR"));

                var button = document.createElement("input");
                button.setAttribute("type", "submit");
                button.setAttribute("value", "Join Game");
                button.setAttribute("id", roomId);
                box.appendChild(button);
                button.onclick = function() {
                    console.log("Hello");
                    //event.preventDefault();
                    document.cookie = "minesweepRoomId=" + roomId;
                    console.log(document.cookie);
                    window.location.href = "/play";
                }
                //class gameButton = button;

                if(clicked != roomInfo.roomId) {
                  box.style.display = "none";
                }
                node.appendChild(box);
            }
        });

    });
}

$("#joinGame").submit(function(event) {
    event.preventDefault();
    document.cookie = "minesweepRoomId=" + $("#gameId").val();
    window.location.href = "/play";
});

$("#gamesList").click(function(e) {
    if(e.target && e.target.nodeName == "LI") {
         //console.log(e.target.id + " was clicked");
         clicked = e.toElement.childNodes[1].id;
         //console.log(clicked);
         //console.log(e.toElement);
         currentButton = e.toElement.childNodes[1].childNodes[1];
         console.log(currentButton);
         //console.log(document.getElementById("joinForm"));
         e.toElement.childNodes[1].style.display = "block";
    }
});

document.getElementById("gamesList").addEventListener("click",function(e) {
    if(e.target && e.target.nodeName == "LI") {
         //console.log(e.target.id + " was clicked");
         clicked = e.toElement.childNodes[1].id;
         //console.log(clicked);
         //console.log(e.toElement);
         currentButton = e.toElement.childNodes[1].childNodes[1];
         console.log(currentButton);
         //console.log(document.getElementById("joinForm"));
         e.toElement.childNodes[1].style.display = "block";
    }
});


$("[value='Join Game']").click(function() {
    console.log("Hello");
    //event.preventDefault();
    document.cookie = "minesweepRoomId=" + this.getId();
    window.location.href = "/play";
});

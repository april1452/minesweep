getGames();

function getGames() {
    $.get("/games", function(responseJSON) {
        var roomList = new Array();
    
        var response = JSON.parse(responseJSON);
        $.each(response, function(index, roomInfo) {
            roomList[roomList.length] = roomInfo.roomId;
        });
        
        var listHtml = document.getElementById("responseHolder").innerHTML;
        listHtml = "";
        $.each(roomList, function(index, roomInfo) {
            listHtml += ("<p>" + roomInfo + "<p>");
        });
        
        document.getElementById("responseHolder").innerHTML = listHtml;
    });
}


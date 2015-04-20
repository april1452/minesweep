addRoom();
startLoop();

function addRoom() {
    $.post("/roomAdd");
}

function startLoop() {
    window.setInterval("getUpdate()", 500);
}

function getUpdate() {
    $.get("/roomUpdate", function(responseJSON) {
        console.log(responseJSON);
        var response = JSON.parse(responseJSON);
        var names = response.playerNames;
        var innerBox = "";
        
        $.each(names, function(index, value) {
            innerBox += "<p>" + value + "</p>";
        });
        
        console.log(innerBox);
        
        $("#usernameBox").html(innerBox);
    });
}
UNEXPLORED = "#C0C0C0";
EXPLORED = "#FFFFFF";
BOMB = "#000000";
TEXT_COLOR = "#000000";

var CANVAS_X = 800;
var CANVAS_Y = 800;

var tileWidth;
var tileHeight;

var _ctx;

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
    } else if (responseJson.type === "gameData") {
        init();
        drawBoard(responseJson.data);
    }
}

function init() {
        var canvasBoard = $("#board")[0];
        canvasBoard.height = CANVAS_Y;
        canvasBoard.width = CANVAS_X;
        _ctx = canvasBoard.getContext("2d");
}

function drawBoard(responseJSON) {
    var board = JSON.parse(responseJSON);
    
    var width = board.width;
    var height = board.height;
    
    tileWidth = CANVAS_X / width;
    tileHeight = CANVAS_Y / height;
    
    var tiles = board.tiles;
    
    _ctx.clearRect(0, 0, CANVAS_X, CANVAS_Y);
    
    $.each(tiles, function(index, tile) {
        var tileX = tile.column * tileWidth;
        var tileY = tile.row * tileHeight;
        if (tile.visited) {
            if(tile.isBomb) {
                _ctx.fillStyle = BOMB;
                _ctx.fillRect(tileX, tileY, tileWidth, tileHeight);
            } else {
                _ctx.fillStyle = EXPLORED;
                _ctx.fillRect(tileX, tileY, tileWidth, tileHeight);
                _ctx.fillStyle = TEXT_COLOR;
                _ctx.fillText(tile.adjacentBombs, tileX + 8, tileY + 12);                    
            }
        } else {
            _ctx.fillStyle = UNEXPLORED;
            _ctx.fillRect(tileX, tileY, tileWidth, tileHeight);
        }

    });
    
    for (var i = 0; i <= CANVAS_X; i+= tileWidth) {
        for(var j = tileHeight; j <= CANVAS_Y; j += tileHeight) {
            _ctx.moveTo(0, i);       
            _ctx.lineTo(CANVAS_Y, i);
            _ctx.moveTo(j, 0);
            _ctx.lineTo(j, CANVAS_X);
        }
    }   
    _ctx.stroke();
}

$("#start").click(function() {
    var sendData = {
        type: "startGame",
        minesweepId: $.cookie("minesweepId"),
        minesweepRoomId: $.cookie("minesweepRoomId")
    };
    socket.send(JSON.stringify(sendData));
});

$("#board").bind('click', function(event){
    var board = $("#board")[0];

    var x = event.pageX - board.offsetLeft;
    var y = event.pageY - board.offsetTop;

    var row = Math.floor(y / tileHeight);
    var column = Math.floor(x / tileWidth);
    
    var sendData = {
        type: "makeMove",
        minesweepId: $.cookie("minesweepId"),
        minesweepRoomId: $.cookie("minesweepRoomId"),
        minesweepTeamId: $.cookie("minesweepTeamId"),
        row: row,
        col: column
    };
    socket.send(JSON.stringify(sendData));
});

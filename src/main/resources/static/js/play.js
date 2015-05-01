UNEXPLORED = "#C0C0C0";
EXPLORED = "#FFFFFF";
NORMAL_BORDER = "#000000";
BOMB = "#000000";
BOMB_BORDER = "#FF0000";
TEXT_COLOR = "#000000";


var CANVAS_X = 800;
var CANVAS_Y = 800;

var tileWidth;
var tileHeight;

var _ctx;

var server_ip = "" + location.host;
server_ip = server_ip.substring(0, server_ip.length - 5);

var socket = new WebSocket("ws://" + server_ip + ":8080");

socket.onopen = function(event) {
    $.getScript("../js/js.cookie.js", function(){
        var sendData = {
            type: "init",
            minesweepId: $.cookie("minesweepId"),
            minesweepRoomId: $.cookie("minesweepRoomId")
        };  
        socket.send(JSON.stringify(sendData));
    });
}

$("#start").click(function() {
    $.getScript("../js/js.cookie.js", function(){
        var sendData = {
            type: "startGame",
            minesweepId: $.cookie("minesweepId"),
            minesweepRoomId: $.cookie("minesweepRoomId")
        };  
        socket.send(JSON.stringify(sendData));
    });
});


socket.onmessage = function (event) {
    var responseJson = JSON.parse(event.data);
        
    if (responseJson.type === "update") {
        var innerBox = "";
        
        var roomInfo = responseJson.data;

        var teams = roomInfo.teams;

        $.each(teams, function(i, team) {
            innerBox += "<p>" + team.name + ": </p>";
            $.each(team.players, function(j, player) {
                innerBox += "<p>" + player.name + "</p>";
            });
            innerBox += "<button type=button id=buttonId" + i + ">" + "Join Team: </button>";
        });
        
         $("#usernameBox").html(innerBox);
        
         $.each(teams, function(i, team) {
            $('#buttonId' + i).click(function(){
                    $.getScript("../js/js.cookie.js", function(){
                        $.cookie("minesweepTeamId", i);
                        var sendData = {
                            type: "joinRoom",
                            minesweepId: $.cookie("minesweepId"),
                            minesweepTeamId: i,
                            minesweepRoomId: $.cookie("minesweepRoomId"),
                            name: "test name"
                    };
                    console.log(sendData);
                    socket.send(JSON.stringify(sendData));
                  });
            });
        });
    } else if (responseJson.type === "gameData") {
        init();
        drawBoard(responseJson.data);
        $("#start").hide();
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
    
    console.log(board.type);

    if (board.type == "DefaultBoard"){
        _ctx.clearRect(0, 0, CANVAS_X, CANVAS_Y);

        $.each(tiles, function(index, tile) {
            var tileX = tile.column * tileWidth;
            var tileY = tile.row * tileHeight;
            if (tile.visited) {
                if(tile.isBomb) {
                    _ctx.fillStyle = BOMB;
                    _ctx.fillRect(tileX, tileY, tileWidth, tileHeight);
                    _ctx.strokeStyle = BOMB_BORDER;
                    _ctx.strokeRect(tileX, tileY, tileWidth, tileHeight);
                } else {
                    _ctx.fillStyle = EXPLORED;
                    _ctx.fillRect(tileX, tileY, tileWidth, tileHeight);
                    _ctx.strokeStyle = NORMAL_BORDER;
                    _ctx.strokeRect(tileX, tileY, tileWidth, tileHeight);
                    _ctx.fillStyle = TEXT_COLOR;
                    _ctx.fillText(tile.adjacentBombs, tileX + 8, tileY + 12);                    
                }
            } else {
                _ctx.fillStyle = UNEXPLORED;
                _ctx.fillRect(tileX, tileY, tileWidth, tileHeight);
                _ctx.strokeStyle = NORMAL_BORDER;
                _ctx.strokeRect(tileX, tileY, tileWidth, tileHeight);
            }

        });
    } else if (board.type == "TriangularBoard"){

         _ctx.clearRect(0, 0, CANVAS_X, CANVAS_Y);
         tileWidth = CANVAS_X / (width + width / 2) * 2;

        $.each(tiles, function(index, tile) {
            var offset = tile.row * tileWidth / 2;
            if (tile.column % 2 === 0) {
                var x1 = tile.column / 2 * tileWidth + offset;
                var x2 = (tile.column / 2 + 1) * tileWidth + offset;
                var x3 = (tile.column / 2 + 0.5) * tileWidth + offset;
                var y1 = tile.row * tileHeight;
                var y2 = tile.row * tileHeight;
                var y3 = (tile.row + 1) * tileHeight;
            } else {
                var x1 = (tile.column / 2 + 0.5) * tileWidth + offset;
                var x2 = tile.column / 2 * tileWidth + offset;
                var x3 = (tile.column / 2 + 1) * tileWidth + offset;
                var y1 = tile.row * tileHeight;
                var y2 = (tile.row + 1) * tileHeight;
                var y3 = (tile.row + 1) * tileHeight;
            }
            triangleDraw(x1, x2, x3, y1, y2, y3, tile);
        });
   
        _ctx.stroke();
    } else {
        console.log("I had a stroke. Undefined board");
    }
}

function triangleDraw(x1, x2, x3, y1, y2, y3, tile) {
    _ctx.beginPath();
    _ctx.moveTo(x1, y1);
    _ctx.lineTo(x2, y2);
    _ctx.moveTo(x2, y2);
    _ctx.lineTo(x3, y3);
    _ctx.moveTo(x3, y3);
    _ctx.lineTo(x1, y1);
    _ctx.closePath();
    _ctx.fillStyle = BOMB;
    _ctx.fill();
    _ctx.strokeStyle = NORMAL_BORDER;
    _ctx.stroke();

    if (tile.visited) {
        if(tile.isBomb) {
            _ctx.fillStyle = BOMB;
            _ctx.fill();
            //_ctx.strokeStyle = BOMB_BORDER;
        } else {
            _ctx.fillStyle = EXPLORED;
            _ctx.fill();
            //_ctx.strokeStyle = NORMAL_BORDER;
            _ctx.fillStyle = TEXT_COLOR;
            _ctx.fillText(tile.adjacentBombs, (x1 + x2 + x3) / 3, (y1 + y2 + y3) / 3);                    
        }
    } else {
        _ctx.fillStyle = UNEXPLORED;
        _ctx.fill();
        //_ctx.strokeStyle = NORMAL_BORDER;
    }


    /*if (tile.isBomb) {
        fillColor = BOMB;
        _ctx.strokeStyle = BOMB_BORDER;
    } else if (tile.visited) {
        fillColor = EXPLORED;
        _ctx.strokeStyle = NORMAL_BORDER;
    } else {
        fillColor = UNEXPLORED;
        _ctx.strokeStyle = NORMAL_BORDER;
    }
    _ctx.fillStyle = fillColor;
    _ctx.beginPath();
    _ctx.moveTo(x1, y1);
    _ctx.lineTo(x2, y2);
    _ctx.moveTo(x2, y2);
    _ctx.lineTo(x3, y3);
    _ctx.moveTo(x3, y3);
    _ctx.lineTo(x1, y1);
    _ctx.fill();
    _ctx.fillStyle = TEXT_COLOR;
    _ctx.fillText(tile.adjacentBombs, (x1 + x2 + x3) / 3, (y1 + y2 + y3) / 3);  */
}


$("#board").bind('click', function(event){
    var board = $("#board")[0];

    var x = event.pageX - board.offsetLeft;
    var y = event.pageY - board.offsetTop;

    var row = Math.floor(y / tileHeight);
    var column = Math.floor(x / tileWidth);
    
    $.getScript("../js/js.cookie.js", function(){
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
});

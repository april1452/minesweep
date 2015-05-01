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
            innerBox += "<button type=button id=aiButtonId" + i + ">" + "Add AI. </button>";
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
            $('#aiButtonId' + i).click(function(){
                    $.getScript("../js/js.cookie.js", function(){
                        $.cookie("minesweepTeamId", i);
                        var sendData = {
                            type: "addAIPlayer",
                            minesweepId: $.cookie("minesweepId"),
                            minesweepTeamId: i,
                            minesweepRoomId: $.cookie("minesweepRoomId"),
                            difficulty: "EASY"
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

        tileHeight*=2

        var offset = tileWidth/2 * CANVAS_Y/tileHeight;



        _ctx.fillStyle = UNEXPLORED;
        _ctx.beginPath();
        _ctx.moveTo(0, CANVAS_Y);
        _ctx.lineTo(offset, 0);
        _ctx.lineTo(CANVAS_X, 0);
        _ctx.lineTo(CANVAS_X - offset, CANVAS_Y);
        _ctx.closePath();
        _ctx.fill();
        _ctx.stroke();
        _ctx.beginPath();
        _ctx.strokeStyle = TEXT_COLOR;
        for(var x = 0, y = CANVAS_Y; x <= offset && y >= 0; x+=tileWidth/2, y-=tileHeight){
            _ctx.moveTo(x, y);
            _ctx.lineTo(tileWidth * (CANVAS_Y - y)/tileHeight, Math.min(CANVAS_Y, y + CANVAS_Y/1.75));
            _ctx.moveTo(x, y);
            _ctx.lineTo(x + CANVAS_X/2, y);
        }
        for(var x = 0; x + offset < CANVAS_X; x+=tileWidth){
                _ctx.moveTo(x, CANVAS_Y);
                _ctx.lineTo(x + offset, 0);
            
        }
   
        _ctx.stroke();
    } else {
        console.log("I had a stroke. Undefined board");
    }
}

function triangleDraw(x1, x2, x3, y1, y2, y3, isMine, isVisited, bombsAround) {
    var fillColor;
    if (isMine) {
        fillColor = BOMB;
    } else if (isVisited) {
        fillColor = EXPLORED;
    } else {
        fillColor = UNEXPLORED;
    }
    _ctx.fillStyle = fillColor;
    _ctx.beginPath();
    _ctx.moveTo(x1, y1);
    _ctx.lineTo(x2, y2);
    _ctx.moveTo(x2, y2);
    _ctx.lineTo(x3, y3);
    _ctx.moveTo(x3, y3);
    _ctx.lineTo(x1, y1);
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

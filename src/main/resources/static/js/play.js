UNEXPLORED = "#C0C0C0";
EXPLORED = "#FFFFFF";
NORMAL_BORDER = "#000000";
BOMB = "#000000";
BOMB_BORDER = "#FF0000";
TEXT_COLOR = "#000000";
ONE_MINE = "#0000FF";
TWO_MINE = "#00FF00";
THREE_MINE = "#FF0000";
FOUR_MINE = "#000066";
FIVE_MINE = "#006600";
SIX_MINE = "#660000";
SEVEN_MINE = "#666666";
EIGHT_MINE = "#000000";

var SIZE = Math.min(0.9 * $(window).height(), 0.8 *$(window).width());

var CANVAS_X = SIZE;
var CANVAS_Y = SIZE;

$(window).resize(function () {
    SIZE = Math.min(0.9 * $(window).height(), 0.8 *$(window).width());
    
    CANVAS_X = SIZE;
    CANVAS_Y = SIZE;
    
    init();
    drawBoard();
});

var tileWidth;
var tileHeight;

var globalData;

var _ctx;

$("#game").hide();
$("#win").hide();
$("#lose").hide();

var characters = ["☀", "☁", "☂", "☃", "☄", "★", "☆", "☇", "☈", "☉", "☊", "☋", 
"☌", "☍", "☎", "☐", "☑", "☒", "☓", "☔", "☕", "☖", "☗", "☘", "☜", "☝", "☞", "☟",
"☠", "☡", "☢", "☣", "☤", "☥", "☧", "☨", "☩", "☪", "☭", "☮", "☯", "♕", "♖", "♗",
"♘", "♙", "♡", "♢", "♣", "♤", "♥", "♦", "♧", "♨", "♩", "♪", "♫", "♬", "♭", "♮"];

var mineImage = new Image();
mineImage.src = "/images/mine.gif";
var flagImage = new Image();
flagImage.src = "/images/flag.png";

var server_ip = "" + location.host;
server_ip = server_ip.substring(0, server_ip.length - 5);

var socket = new WebSocket("ws://" + server_ip + ":7777");
var hexagon_grid;

$('#teams').show();

// set up cookies js
socket.onopen = function(event) {
    $.getScript("../js/js.cookie.js", function(){
        var sendData = {
            requestType: "INITIALIZE",
            minesweepId: $.cookie("minesweepId"),
            minesweepId: $.cookie("minesweepId"),
            minesweepRoomId: $.cookie("minesweepRoomId"),
            minesweepName: $.cookie("minesweepName")
        };  
        socket.send(JSON.stringify(sendData));
    });
    $('#teams').show();
}

socket.onmessage = function (event) {
    var responseJson = JSON.parse(event.data);

    var updateType = responseJson.updateType;

    // Pre game setup    
    if (updateType === "ROOM_UPDATE") {
        drawRoom(responseJson);
    } 

    else if (updateType === "TEAM_ASSIGNMENT") {
        $.getScript("../js/js.cookie.js", function() {
            $.cookie("minesweepTeamId", responseJson.data);
        });
    }
    
    else if (updateType === "INIT_BOARD") {
        init();
        globalData = responseJson.data;
        drawBoard();
        $("#board").show();
        $("#teams").hide();

    }
    
    else if (updateType === "INIT_INFO") {
        $("#infoBox").show();
        drawInfo(responseJson);
    }
    
    else if (updateType === "INFO_UPDATE") {
        drawInfo(responseJson);
    }

    // Begin game, i.e. draw game board
    else if (updateType === "BOARD_UPDATE") {
        globalData = responseJson.data;
        drawBoard();
    }
    
    else if (updateType === "VICTORY") {
        win();
    }

    else if (updateType === "DEFEAT") {
        lose();
    }

    else if (updateType === "SESSION_DISBANDED") {
        alert(responseJson.data);
        window.href.location = "/";
    }

    else if (updateType === "ERROR") {
        alert(responseJson.data);
    }
}

var timer;

function drawInfo(responseJson) {
    var board = globalData.board;
    var flags = globalData.flags;

    var isTimedMode = false;
    $("#infoBox").empty();

    var info = "";
    var revealedBombs = 0;
    $.each(board.tiles, function(index, tile) {
        if (tile.isBomb && tile.visited) {
            revealedBombs++;
        }
    });
    
    var board = globalData.board;
    var flags = globalData.flags;
    
    
    console.log(board.bombCount + " " + flags.length + revealedBombs);
    console.log(info);
    info = "Mines Remaining: " + (board.bombCount - flags.length - revealedBombs) + "<br>";

    $.each(responseJson.data, function(id, teamInfo) {
        info += '<img class="info-icon" src="images/user.png">' + teamInfo.name + "<br>" 
        if(teamInfo.hasOwnProperty("lives")) {
            info += '<img class="info-icon" src="images/heart.png"> ' + teamInfo.lives + "<br>";
        } else if (teamInfo.hasOwnProperty("time")) {
            isTimedMode = true;
            info += '<img class="info-icon" src="images/time.png"> <div id="timer-' + id + '">' + Math.floor(teamInfo.time / 1000) + "</div><br>";
        }
        info += "<br>"
    });
    
    console.log(info);
    $("#infoBox").html(info);

    if(isTimedMode) {
        if(timer !== undefined) {
            clearInterval(timer);
        }
        timer = setInterval(function() {
            $('[id^="timer-"]').each(function() {
                var currTime = $(this).html();
                if(currTime > 0) {
                    $(this).html($(this).html() - 1)
                }
            });
        }, 1000);
    }

}

// draw pre game rooms
function drawRoom(responseJson) {
    $.getScript("/webplate/stack.js", function() {
        var innerBox = "";

        var roomInfo = responseJson.data;
        var isHost = roomInfo.isHost;

        var teams = roomInfo.teams;

        var sortedIds = Object.keys(teams).sort(function(a,b) {
            return teams[a].name.localeCompare(teams[b].name);
        });

        $.each(sortedIds, function(i, teamId) {
            var team = teams[teamId];
            innerBox += '<div class="span-2" style="padding-top:30px"><h4>' + team.name + "</h4>";
            $.each(team.players, function(playerId, player) {
                if (player.type=="HUMAN")
                    innerBox += '<a class="button line-purple">' + player.name + "</a><br>";
                else 
                    innerBox += '<a class="button line-aqua">' + player.name + "</a><br>";
            });
            // add ai button
            innerBox += '<a class="button aqua modal-trigger" data-modal-open="ai-choose-'+ teamId +'" id="ai' + teamId + '">' + "Add AI</a><br>";
            // remove all ais
            if (isHost) {
                console.log('heya');
                innerBox += '<a class="button aqua" id="removeAI-' + teamId + '">' + "Remove All AIs</a><br>";
            }
            // join team button
            innerBox += '<a class="button purple" id="buttonId' + teamId + '">' + "Join Team</a></div>";  
            // choose ai difficulty modal
            innerBox += '<div class="modalplate" data-modal-id="ai-choose-'+ teamId +'"><div class="modalplate-title-bar"><a class="close">Close</a><h4>Choose AI Difficulty</h4></div><div class="modalplate-content"><div class="row"><div class="ai span-2"><a class="button aqua large icon close" id="easy'+ teamId +'"><span class="icon icon-smile"></span></a>Easy</div><div class="span-2"><a class="button aqua large icon close" id="medium'+ teamId +'"><span class="icon icon-evil"></span></a>Medium</div><div class="span-2"><a class="button aqua large icon close" id="hard'+ teamId +'"><span class="icon icon-crying"></span></a>Hard</div><div class="span-2"><a class="button aqua large icon close" id="random'+ teamId +'"><span class="icon icon-hipster"></span></a>Random</div></div></div></div>';  
        });

        // have to readd the sidebar; css issues
        var sidebar;
        if (isHost) {
            sidebar = '<div id="start" class="span-2"><a class="button line-white large" id="startButton">Start Game!</a><a href="/" class="button line-white large" id="disbandButton">Disband</a></div>';
        } else {
            sidebar = '<div id="start" class="span-2"><a href="/" class="button line-white large" id="leaveButton">Leave Room</a></div>';
        }

        $("#teams").html(sidebar + innerBox);
        
        $("#startButton").click(function() {
            //init();
            console.log('start button clicked');
            $.getScript("../js/js.cookie.js", function(){
                var sendData = {
                    requestType: "START_GAME",
                    minesweepId: $.cookie("minesweepId"),
                    minesweepRoomId: $.cookie("minesweepRoomId")
                };  
                socket.send(JSON.stringify(sendData));
            });
        });

        $("#disbandButton").click(function() {
            console.log('disband button clicked');
            $.getScript("../js/js.cookie.js", function(){
                var sendData = {
                    requestType: "DISBAND_ROOM",
                    minesweepId: $.cookie("minesweepId"),
                    minesweepRoomId: $.cookie("minesweepRoomId")
                };  
                socket.send(JSON.stringify(sendData));
            });
        });

        $("#leaveButton").click(function() {
            console.log('leave button clicked');
            $.getScript("../js/js.cookie.js", function(){
                var sendData = {
                    requestType: "LEAVE_ROOM",
                    minesweepId: $.cookie("minesweepId"),
                    minesweepTeamId: $.cookie("minesweepTeamId"),
                    minesweepRoomId: $.cookie("minesweepRoomId")
                };  
                socket.send(JSON.stringify(sendData));
            });
        });
        
        $.each(teams, function(i, team) {
            $('#removeAI-' + i).click(function(){
               $.getScript("../js/js.cookie.js", function(){
                    var sendData = {
                        requestType: "REMOVE_AIS",
                        minesweepId: $.cookie("minesweepId"),
                        minesweepTeamId: $.cookie("minesweepTeamId"),
                        minesweepRoomId: $.cookie("minesweepRoomId")
                    };  
                    socket.send(JSON.stringify(sendData));
                }); 
            });
        });   

        // switch team button
        $.each(teams, function(i, team) {
            $('#buttonId' + i).click(function(){
                joinRoom(i);
            });
        });

        // create ai from modal screen
        $.each(teams, function(i, team) {
            $('#easy' + i).click(function(){
                addAi(i, "EASY");
            });
            $('#medium' + i).click(function(){
                addAi(i, "MEDIUM");
            });
            $('#hard' + i).click(function(){
                addAi(i, "HARD");
            });
            $('#random' + i).click(function(){
                var difficultyChoice = Math.random();
                if (difficultyChoice < 1/3) {
                    addAi(i, "EASY");
                } else if (difficultyChoice < 2/3) {
                    addAi(i, "MEDIUM");
                } else {
                    addAi(i, "HARD");
                }
            });
        });
    });
}

// allow player to join a team
function joinRoom(teamId) {
    $.getScript("../js/js.cookie.js", function(){
        var sendData = {
            requestType: "SWITCH_TEAM",
            minesweepId: $.cookie("minesweepId"),
            minesweepTeamId: $.cookie("minesweepTeamId"),
            minesweepRoomId: $.cookie("minesweepRoomId"),
            newTeamId: teamId

        };
        socket.send(JSON.stringify(sendData));
    });
}

// add an ai to a team
function addAi(teamId, difficulty) {
    $.getScript("../js/js.cookie.js", function(){
        var sendData = {
            requestType: "ADD_AI",
            minesweepId: $.cookie("minesweepId"),
            minesweepTeamId: teamId,
            minesweepRoomId: $.cookie("minesweepRoomId"),
            difficulty: difficulty
        };
        socket.send(JSON.stringify(sendData));
    });
}

function init() {
    var canvasBoard = $("#board")[0];
    canvasBoard.height = CANVAS_Y;
    canvasBoard.width = CANVAS_X;
    _ctx = canvasBoard.getContext("2d");
    //_ctx.scale(.9,.9);
    $("#game").show();
    $("#teams").hide();
}

function drawBoard() {

    var data = globalData;
    
    var board = data.board;
    var flags = data.flags;
    var colors = data.colors;
    
    var width = board.width;
    var height = board.height;
    
    tileWidth = CANVAS_X / width;
    tileHeight = CANVAS_Y / height;

    var tiles = board.tiles;

    if (board.type == "DEFAULT"){
        _ctx.clearRect(0, 0, CANVAS_X, CANVAS_Y);

        $.each(tiles, function(index, tile) {
            var tileX = tile.column * tileWidth;
            var tileY = tile.row * tileHeight;
            var color = colors[tile.row][tile.column];
            _ctx.lineWidth = 1;
            if (tile.visited) {
                if(tile.isBomb) {
                    _ctx.fillStyle = color;
                    _ctx.fillRect(tileX, tileY, tileWidth, tileHeight);
                    _ctx.drawImage(mineImage, tileX, tileY, tileWidth, tileHeight);
                    _ctx.strokeStyle = NORMAL_BORDER;
                    _ctx.strokeRect(tileX + 1, tileY + 1, tileWidth - 2, tileHeight - 2);
                } else {
                    _ctx.fillStyle = color;
                    _ctx.fillRect(tileX, tileY, tileWidth, tileHeight);
                    _ctx.strokeStyle = NORMAL_BORDER;
                    //_ctx.lineWidth = 2;
                    _ctx.strokeRect(tileX + 1, tileY + 1, tileWidth - 2, tileHeight - 2);
                    //_ctx.lineWidth = 1;
                    if (tile.adjacentBombs > 0) {
                        _ctx.fillStyle = getTextColor(tile.adjacentBombs);
                        _ctx.font= getFontSize(tileHeight, tileWidth) + "px Verdana";
                        _ctx.textAlign = "center";
                        _ctx.textBaseline = "middle";
                        _ctx.fillText(tile.adjacentBombs, tileX + tileWidth / 2, tileY + tileHeight / 2);
                        _ctx.strokeText(tile.adjacentBombs, tileX + tileWidth / 2, tileY + tileHeight / 2);
                    }
                }
            } else {
                _ctx.fillStyle = UNEXPLORED;
                _ctx.fillRect(tileX, tileY, tileWidth, tileHeight);
                if (isFlag(flags, tile.column, tile.row)) {
                    _ctx.drawImage(flagImage, tileX, tileY, tileWidth, tileHeight);
                }
                _ctx.strokeStyle = NORMAL_BORDER;
                _ctx.strokeRect(tileX + 1, tileY + 1, tileWidth  - 2, tileHeight  - 2);
            }
        });
    } else if (board.type == "TRIANGULAR"){

         _ctx.clearRect(0, 0, CANVAS_X, CANVAS_Y);
         tileWidth = CANVAS_X / (width / 2 + height / 2);

        $.each(tiles, function(index, tile) {
            var offset = tile.row * tileWidth / 2;
            if (tile.column % 2 === 0) {
                var x1 = tile.column / 2 * tileWidth + offset;
                var x2 = (tile.column / 2 + 1) * tileWidth + offset;
                var x3 = (tile.column / 2 + 0.5) * tileWidth + offset;
                var y1 = tile.row * tileHeight;
                var y2 = tile.row * tileHeight;
                var y3 = (tile.row + 1) * tileHeight;
                triangleDraw(x1, x2, x3, y1, y2, y3, tile);
                if (isFlag(flags, tile.column, tile.row)) {
                    _ctx.drawImage(flagImage, x1 + tileWidth / 4, y1, tileWidth / 2, tileHeight / 2);
                }
                if (tile.isBomb && tile.visited) {
                    _ctx.drawImage(mineImage, x1 + tileWidth / 4, y1, tileWidth / 2, tileHeight / 2);
                }
            } else {
                var x1 = (tile.column / 2 + 0.5) * tileWidth + offset;
                var x2 = tile.column / 2 * tileWidth + offset;
                var x3 = (tile.column / 2 + 1) * tileWidth + offset;
                var y1 = tile.row * tileHeight;
                var y2 = (tile.row + 1) * tileHeight;
                var y3 = (tile.row + 1) * tileHeight;
                triangleDraw(x1, x2, x3, y1, y2, y3, tile);
                if (tile.isBomb && tile.visited) {
                    _ctx.drawImage(mineImage, x1 - tileWidth / 4, y1 + tileHeight / 2, tileWidth / 2, tileHeight / 2);
                }
                if (isFlag(flags, tile.column, tile.row)) {
                    _ctx.drawImage(flagImage, x1 - tileWidth / 4, y1 + tileHeight / 2, tileWidth / 2, tileHeight / 2);
                }
            } 
        });
   
        _ctx.stroke();
    } else if (board.type == "HEXAGONAL"){
        console.log("hexagonal");
        drawHexGrid(hexagon_grid, _ctx);
        $.each(tiles, function(index, tile)
        {
            var hex = hexagon_grid.GetHexAtPos(tile.column, tile.row);
            console.log(hex);
            if(tile.visited){
                if(tile.isBomb){
                    console.log("this tile is a bomb");
                    hex.fillColor = EXPLORED;
                } else {
                    console.log("This is suppose to be visited");
                    
                    hex.fillColor = EXPLORED;
                }
            } else {
                hex.fillColor = UNEXPLORED;
            }
            hex.Id = tile.adjacentBombs;
            var hexIsFlag = isFlag(flags, tile.column, tile.row);
            //ctx, color, isMine, isFlag, visited, numMines
            hex.preDraw(_ctx, hex.fillColor, tile.isBomb, hexIsFlag, tile.visited, tile.adjacentBombs);
            //hex.preDraw(_ctx, hex.fillColor);
            
        });
        _ctx.stroke();
        //drawHexGrid(hexagon_grid, _ctx);
    } else if (board.type == "RECTANGULAR"){
        console.log("Drawing Rectangle");
        console.log(board);
        tilesArray = board.tilesArray;

        _ctx.clearRect(0, 0, CANVAS_X, CANVAS_Y);

        $.each(tiles, function(index, tile) {
            var x = index % globalBoard.width;
            var y = Math.floor(index / globalBoard.width);

            var tileX = index % globalBoard.width * tileWidth;
            var tileY = Math.floor(index / globalBoard.width) * tileHeight;
            if (tilesArray[x][y] === null) {
                var newWidth = tileWidth;
                var newHeight = tileHeight;
                var newX = tileX;
                var newY = tileY;
            } else {
                var otherTile = tilesArray[x][y];
                var otherX = otherTile.column;
                var otherY = otherTile.row;
                if (x < otherX) {
                    var newWidth = tileWidth * 2;
                    var newHeight = tileHeight;
                    var newX = tileX;
                    var newY = tileY;
                } else if (x > otherX) {
                    var newWidth = tileWidth * 2;
                    var newHeight = tileHeight;
                    var newX = tileX - tileWidth;
                    var newY = tileY;
                } else if (y > otherY) {
                    var newWidth = tileWidth;
                    var newHeight = tileHeight * 2;
                    var newX = tileX;
                    var newY = tileY;
                } else if (y < otherY) {
                    var newWidth = tileWidth;
                    var newHeight = tileHeight * 2;
                    var newX = tileX;
                    var newY = tileY - tileHeight;
                }
            }
            if (tile.visited) {
                if(tile.isBomb) {
                    _ctx.fillStyle = EXPLORED;
                    _ctx.fillRect(newX, newY, newWidth, newHeight);
                    _ctx.drawImage(mineImage, tileX, tileY, tileWidth, tileHeight);
                    _ctx.strokeStyle = NORMAL_BORDER;
                    _ctx.strokeRect(newX, newY, newWidth, newHeight);
                } else {
                    _ctx.fillStyle = EXPLORED;
                    _ctx.fillRect(newX, newY, newWidth, newHeight);
                    _ctx.strokeStyle = NORMAL_BORDER;
                    _ctx.strokeRect(newX, newY, newWidth, newHeight);
                    if (tile.adjacentBombs > 0) {
                        _ctx.fillStyle = getTextColor(tile.adjacentBombs);
                        _ctx.font= getFontSize(tileHeight, tileWidth) + "px Verdana";
                        _ctx.textAlign = "center";
                        _ctx.textBaseline = "middle";
                        _ctx.fillText(tile.adjacentBombs, newX + newWidth / 2, newY + newHeight / 2);
                    }
                }
            } else {
                _ctx.fillStyle = UNEXPLORED;
                _ctx.fillRect(newX, newY, newWidth, newHeight);
                _ctx.strokeStyle = NORMAL_BORDER;
                _ctx.strokeRect(newX, newY, newWidth, newHeight);
                /*if (isFlag()) {
                    _ctx.drawImage(flagImage, tileX, tileY, tileWidth, tileHeight);
                }*/
            }

        });
    } if (board.type == "ENTANGLED") {
    console.log("Drawing Rectangle");
        console.log(board);

        _ctx.clearRect(0, 0, CANVAS_X, CANVAS_Y);

        var entangledCount =  0;
        $.each(tiles, function(index, tile) {
            var tileX = index % board.width * tileWidth;
            var tileY = Math.floor(index / board.width) * tileHeight;
            if (tile.visited) {
                if(tile.isBomb) {
                    _ctx.fillStyle = EXPLORED;
                    _ctx.fillRect(tileX, tileY, tileWidth, tileHeight);
                    _ctx.drawImage(mineImage, tileX + 1, tileY + 1, tileWidth - 2, tileHeight - 2);
                    _ctx.strokeStyle = NORMAL_BORDER;
                    _ctx.strokeRect(tileX + 1, tileY + 1, tileWidth - 2, tileHeight - 2);
                } else {
                    _ctx.fillStyle = EXPLORED;
                    _ctx.fillRect(tileX, tileY, tileWidth, tileHeight);

                    _ctx.strokeStyle = BOMB_BORDER;
                    _ctx.lineWidth = 2;
                    _ctx.strokeRect(tileX + 1, tileY + 1, tileWidth - 2, tileHeight - 2);
                    _ctx.lineWidth = 1;
                    var output = "";
                    if (tile.adjacentBombs > 0) {
                        _ctx.fillStyle = getTextColor(tile.adjacentBombs);
                        output+= tile.adjacentBombs;
                    }
                    var link = board.neighborTable[neighbor.row][neighbor.col];
                    if (typeeof(link) != 'undefined'){
                        output += characters[entangledCount];
                        entangledCount++;
                    }
                    _ctx.font= getFontSize(tileHeight, tileWidth) + "px Verdana";
                    _ctx.textAlign = "center";
                    _ctx.textBaseline = "middle";
                    //_ctx.fillText(tileX + tileWidth / 2, tileY + tileHeight / 2);
                    _ctx.fillText(output, tileX + tileWidth / 2, tileY + tileHeight / 2);

                }
            } else {
                _ctx.fillStyle = UNEXPLORED;
                _ctx.fillRect(tileX, tileY, tileWidth, tileHeight);
                if (isFlag(flags, tile.column, tile.row)) {
                    _ctx.drawImage(flagImage, tileX + 1, tileY + 1, tileWidth - 2, tileHeight - 2);
                }
                _ctx.strokeStyle = NORMAL_BORDER;
                _ctx.strokeRect(tileX + 1, tileY + 1, tileWidth  - 2, tileHeight  - 2);
            }
        });
        
    } else {
        console.log("I had a stroke. Undefined board");
    }
}

function getTextColor(surrounding) {
    if (surrounding === 1) {
        return ONE_MINE;
    } else if (surrounding === 2) {
        return TWO_MINE;
    } else if (surrounding === 3) {
        return THREE_MINE;
    } else if (surrounding === 4) {
        return FOUR_MINE;
    } else if (surrounding === 5) {
        return FIVE_MINE;
    } else if (surrounding === 6) {
        return SIX_MINE;
    } else if (surrounding === 7) {
        return SEVEN_MINE;
    } else if (surrounding === 8) {
        return EIGHT_MINE;
    } else {
        return TEXT_COLOR;
    }
}

function isFlag(flags, y, x) {
    return flags[x][y];
}

function getFontSize(tileHeight, tileWidth) {
    return Math.min(Math.floor(tileHeight / 2), Math.floor(tileWidth / 1.5));
}

function triangleDraw(x1, x2, x3, y1, y2, y3, tile) {
    _ctx.beginPath();
    _ctx.moveTo(x1, y1);
    _ctx.lineTo(x2, y2);
    //_ctx.moveTo(x2, y2);
    _ctx.lineTo(x3, y3);
    //_ctx.moveTo(x3, y3);
    _ctx.lineTo(x1, y1);
    _ctx.closePath();
    _ctx.strokeStyle = NORMAL_BORDER;
    _ctx.stroke();

    if (tile.visited) {
        if(tile.isBomb) {
            _ctx.fillStyle = EXPLORED;
            _ctx.fill();
            //_ctx.strokeStyle = NORMAL_BORDER;
        } else {
            _ctx.fillStyle = EXPLORED;
            _ctx.fill();
            //_ctx.strokeStyle = NORMAL_BORDER;
            if (tile.adjacentBombs > 0) {
                _ctx.fillStyle = getTextColor(tile.adjacentBombs);
                _ctx.font = getFontSize(tileHeight, tileWidth) + "px Verdana";
                _ctx.textAlign = "center";
                _ctx.textBaseline = "middle";
                _ctx.fillText(tile.adjacentBombs, (x1 + x2 + x3) / 3, (y1 + y2 + y3) / 3);  
            }                  
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
    _ctx.fillText(tile.adjacentBombs, (x1 + http://localhost:4686/playx2 + x3) / 3, (y1 + y2 + y3) / 3);  */
}

$("#board").bind("contextmenu", function(e){
 click("FLAG");
 return false;
}); 


$("#board").bind('click', function(event){
    click("CHECK");
});

function click(clickType) {

    var boardCanvas = $("#board")[0];

    var x = event.pageX - boardCanvas.offsetLeft;
    var y = event.pageY - boardCanvas.offsetTop;

    var board = globalData.board;
    var flags = globalData.flags;

    if (board.type == "DEFAULT" || board.type == "RECTANGULAR" || board.type == "ENTANGLED") {

        var row = Math.floor(y / tileHeight);
        var column = Math.floor(x / tileWidth);

        if (!isFlag(flags, column, row) || clickType === "FLAG") {

            $.getScript("../js/js.cookie.js", function() {
                var sendData = {
                    requestType: "MAKE_MOVE",
                    minesweepId: $.cookie("minesweepId"),
                    minesweepRoomId: $.cookie("minesweepRoomId"),
                    minesweepTeamId: $.cookie("minesweepTeamId"),
                    row: row,
                    col: column,
                    moveType: clickType
                };
                socket.send(JSON.stringify(sendData));
            });
        }

    } else if (board.type == "TRIANGULAR") {
        var row = Math.floor(y / tileHeight);
        var offset = row * tileWidth / 2;
        var estimate = Math.floor((x - offset) / tileWidth * 2);
        //console.log(row + " " + estimate);


        var tiles = board.tiles;
        var selectedTile;
        var edge = false;

        if (estimate === board.width) {
            estimate--;
            edge = true;
        }

        $.each(tiles, function(index, tile) {
            if (tile.row == row && tile.column == estimate) {
                //console.log(tile.row + " " + tile.column);
                selectedTile = tile;
            }
            
        });

        if (selectedTile.column % 2 === 0) {
            var x1 = selectedTile.column / 2 * tileWidth + offset;
            var x2 = (selectedTile.column / 2 + 1) * tileWidth + offset;
            var x3 = (selectedTile.column / 2 + 0.5) * tileWidth + offset;
            var y1 = selectedTile.row * tileHeight;
            var y2 = selectedTile.row * tileHeight;
            var y3 = (selectedTile.row + 1) * tileHeight;
        } else {
            var x1 = selectedTile.column / 2 * tileWidth + offset;
            var x2 = (selectedTile.column / 2 + 1) * tileWidth + offset;
            var x3 = (selectedTile.column / 2 + 0.5) * tileWidth + offset;
            var y1 = (selectedTile.row + 1) * tileHeight;
            var y2 = (selectedTile.row + 1) * tileHeight;
            var y3 = selectedTile.row * tileHeight;
        }   

        if (edge) {
            var borderSlope = (y3 - y2) / (x3 - x2);
            //console.log("y1:" + y1 + ", y2:" + y2 + ", x1:" + x1 + ", x2:" + x2 + "y:" + y + ", x:" + x);
            var clickSlope = (y - y2) / (x - x2);
            //console.log(borderSlope + " " + clickSlope);
            if (Math.abs(borderSlope) < Math.abs(clickSlope)) {
                //var column = estimate - 1;
                //console.log("Above border");
            } else {
                var column = estimate;
                //console.log("Below border");
                //console.log(row + " " + column);

                if (!isFlag(flags, column, row) || clickType === "FLAG") {

                    $.getScript("../js/js.cookie.js", function() {
                        var sendData = {
                            requestType: "MAKE_MOVE",
                            minesweepId: $.cookie("minesweepId"),
                            minesweepRoomId: $.cookie("minesweepRoomId"),
                            minesweepTeamId: $.cookie("minesweepTeamId"),
                            row: row,
                            col: column,
                            moveType: clickType
                        };
                        socket.send(JSON.stringify(sendData));
                    });
                }
            }

        } else {
            var borderSlope = (y3 - y1) / (x3 - x1);
            var clickSlope = (y - y1) / (x - x1);
            //console.log(borderSlope + " " + clickSlope);
            if (Math.abs(borderSlope) < Math.abs(clickSlope)) {
                var column = estimate - 1;
                //console.log("Above border");
            } else {
                var column = estimate;
                //console.log("Below border");
            }
            //console.log(row + " " + column);
            if (!isFlag(flags, column, row) || clickType === "FLAG") {

                $.getScript("../js/js.cookie.js", function() {
                    var sendData = {
                        requestType: "MAKE_MOVE",
                        minesweepId: $.cookie("minesweepId"),
                        minesweepRoomId: $.cookie("minesweepRoomId"),
                        minesweepTeamId: $.cookie("minesweepTeamId"),
                        row: row,
                        col: column,
                        moveType: clickType
                    };
                    socket.send(JSON.stringify(sendData));
                });
            }
        }
    } else if (board.type == "HEXAGONAL"){
        var p = new HT.Point(x, y);
        var hex = hexagon_grid.GetHexAt(p);
        var row = hex.PathCoOrdY;
        var column = hex.PathCoOrdX;

        if (!isFlag(flags, column, row) || clickType === "FLAG") {

            $.getScript("../js/js.cookie.js", function() {
                var sendData = {
                    requestType: "MAKE_MOVE",
                    minesweepId: $.cookie("minesweepId"),
                    minesweepRoomId: $.cookie("minesweepRoomId"),
                    minesweepTeamId: $.cookie("minesweepTeamId"),
                    row: row,
                    col: column,
                    moveType: clickType
                };
                socket.send(JSON.stringify(sendData));
            });
        }
    }
}

function win() {
    $("#game").hide();
    $("#board").hide();
    $("#infoBox").hide();
    $("#win").show();
}

function lose() {
    $("#game").hide();
    $("#board").hide();
    $("#infoBox").hide();
    $("#lose").show();
}

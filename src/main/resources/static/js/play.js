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

var CANVAS_X = 800;
var CANVAS_Y = 800;

var tileWidth;
var tileHeight;

var globalBoard;
var globalFlags;

var _ctx;

$("#board").hide();
$("#win").hide();
$("#lose").hide();
//$("#start").hide();

var mineImage = new Image();
mineImage.src = "/images/mine.png";
var flagImage = new Image();
flagImage.src = "/images/flag.png";

var server_ip = "" + location.host;
server_ip = server_ip.substring(0, server_ip.length - 5);

var socket = new WebSocket("ws://" + server_ip + ":7777");
var hexagon_grid;

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
}

socket.onmessage = function (event) {
    var responseJson = JSON.parse(event.data);
    
    console.log(responseJson);

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
        drawBoard(responseJson.data);
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
        drawBoard(responseJson.data);
    }
    
    else if (updateType === "VICTORY") {
        win();
    }

    else if (updateType === "DEFEAT") {
        lose();
    }

    else if (updateType === "ERROR") {
        alert(responseJson.data);
    }
}

function drawInfo(responseJson) {
    $("#infoBox").empty();
    var info = "";
    $.each(responseJson.data, function(id, teamInfo) {
        info += "Name: " + teamInfo.name + "<br>" + "Lives: " + teamInfo.lives + "<br>";
    });
    var revealedBombs = 0;
    $.each(globalBoard.tiles, function(index, tile) {
        if (tile.isBomb && tile.visited) {
            revealedBombs++;
        }
    })
    console.log(globalBoard.bombCount + " " + globalFlags.length + revealedBombs);
    console.log(info);
    info += "Mines Remaining: " + (globalBoard.bombCount - globalFlags.length - revealedBombs) + "<br>";
    console.log(info);
    $("#infoBox").html(info);
}

// draw pre game rooms
function drawRoom(responseJson) {
    $.getScript("/webplate/stack.js", function() {
        var innerBox = "";
        
        var roomInfo = responseJson.data;

        var teams = roomInfo.teams;

        var sortedIds = Object.keys(teams).sort(function(a,b) {
            return teams[a].name.localeCompare(teams[b].name);
        });

        console.log(sortedIds);

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
            // join team button
            innerBox += '<a class="button purple" id="buttonId' + teamId + '">' + "Join Team</a></div>";  
            // choose ai difficulty modal
            innerBox += '<div class="modalplate" data-modal-id="ai-choose-'+ teamId +'"><div class="modalplate-title-bar"><a class="close">Close</a><h4>Choose AI Difficulty</h4></div><div class="modalplate-content"><div class="row"><div class="ai span-2"><a class="button aqua large icon close" id="easy'+ teamId +'"><span class="icon icon-smile"></span></a>Easy</div><div class="span-2"><a class="button aqua large icon close" id="medium'+ teamId +'"><span class="icon icon-evil"></span></a>Medium</div><div class="span-2"><a class="button aqua large icon close" id="hard'+ teamId +'"><span class="icon icon-crying"></span></a>Hard</div><div class="span-2"><a class="button aqua large icon close" id="random'+ teamId +'"><span class="icon icon-hipster"></span></a>Random</div></div></div></div>';  
        });

        // have to readd the sidebar; css issues
        var sidebar = '<div id="start" class="span-2"><a class="button line-white large" id="startButton">Start Game!</a><a class="button line-white large" id="disbandButton">Disband</a></div>'

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
    $("#board").show();
    $("#start").hide();
    $("#teams").hide();
    
}

function drawBoard(data) {
    
    var board = data.board;
    var flags = data.flags;
    
    console.log(board);
    
    var width = board.width;
    var height = board.height;
    
    tileWidth = CANVAS_X / width;
    tileHeight = CANVAS_Y / height;
    
    if(typeof(hexagon_grid) === 'undefined' || hexagon_grid == []){
        hexagon_grid = new HT.Grid(width, height);
        console.log(tileWidth);
        findHexWithWidthAndHeight(tileWidth * 68/50, tileHeight *  36/50);
        
        console.log("A grid is born");
    }
    
    var tiles = board.tiles;
    
    globalBoard = board;
    globalFlags = flags;

    if (board.type == "DEFAULT"){
        _ctx.clearRect(0, 0, CANVAS_X, CANVAS_Y);

        $.each(tiles, function(index, tile) {
            var tileX = tile.column * tileWidth;
            var tileY = tile.row * tileHeight;
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
                    if (tile.adjacentBombs > 0) {
                        _ctx.fillStyle = getTextColor(tile.adjacentBombs);
                        _ctx.font= getFontSize(tileHeight, tileWidth) + "px Verdana";
                        _ctx.textAlign = "center";
                        _ctx.textBaseline = "middle";
                        _ctx.fillText(tile.adjacentBombs, tileX + tileWidth / 2, tileY + tileHeight / 2);
                    }
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
                if (isFlag(globalFlags, tile.column, tile.row)) {
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
                if (isFlag(globalFlags, tile.column, tile.row)) {
                    _ctx.drawImage(flagImage, x1 - tileWidth / 4, y1 + tileHeight / 2, tileWidth / 2, tileHeight / 2);
                }
            } 
        });
   
        _ctx.stroke();
    } else if (board.type = "HEXAGONAL"){
    	console.log("hexagonal");
        drawHexGrid(hexagon_grid, _ctx);
        $.each(tiles, function(index, tile)
        {
            var hex = hexagon_grid.GetHexAtPos(tile.column, tile.row);
            console.log(hex);
            if(tile.visited){
                if(tile.isBomb){
                	console.log("this tile is a bomb");
                    hex.fillColor = BOMB;
                } else {
                    console.log("This is suppose to be visited");
                    
                    hex.fillColor = EXPLORED;
                }
            } else {
            	hex.fillColor = UNEXPLORED;
            }
            hex.Id = tile.adjacentBombs;
            hex.preDraw(_ctx, hex.fillColor);
            
        });
        _ctx.stroke();
        //drawHexGrid(hexagon_grid, _ctx);
    } else if (board.type = "RECTANGULAR"){
    	console.log("Drawing Rectangle");
        console.log(board);

        _ctx.clearRect(0, 0, CANVAS_X, CANVAS_Y);

        $.each(tiles, function(index, tile) {
            var tileX = tile.column * tileWidth;
            var tileY = tile.row * tileHeight;
            if (tile.visited) {
                if(tile.isBomb) {
                    _ctx.fillStyle = EXPLORED;
                    _ctx.fillRect(tileX, tileY, tileWidth, tileHeight);
                    _ctx.drawImage(mineImage, tileX, tileY, tileWidth, tileHeight);
                    _ctx.strokeStyle = NORMAL_BORDER;
                    _ctx.strokeRect(tileX, tileY, tileWidth, tileHeight);
                } else {
                    _ctx.fillStyle = EXPLORED;
                    _ctx.fillRect(tileX, tileY, tileWidth, tileHeight);
                    _ctx.strokeStyle = NORMAL_BORDER;
                    _ctx.strokeRect(tileX, tileY, tileWidth, tileHeight);
                    if (tile.adjacentBombs > 0) {
                        _ctx.fillStyle = getTextColor(tile.adjacentBombs);
                        _ctx.font= getFontSize(tileHeight, tileWidth) + "px Verdana";
                        _ctx.textAlign = "center";
                        _ctx.textBaseline = "middle";
                        _ctx.fillText(tile.adjacentBombs, tileX + tileWidth / 2, tileY + tileHeight / 2);
                    }
                }
            } else {
                _ctx.fillStyle = UNEXPLORED;
                _ctx.fillRect(tileX, tileY, tileWidth, tileHeight);
                _ctx.strokeStyle = NORMAL_BORDER;
                _ctx.strokeRect(tileX, tileY, tileWidth, tileHeight);
                /*if (isFlag()) {
                    _ctx.drawImage(flagImage, tileX, tileY, tileWidth, tileHeight);
                }*/
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
   console.log("Right click!!");
   click("FLAG");
   return false;
}); 


$("#board").bind('click', function(event){
	console.log(event.which);
    click("CHECK");
});

function click(clickType) {

    var board = $("#board")[0];

    var x = event.pageX - board.offsetLeft;
    var y = event.pageY - board.offsetTop;

    if (globalBoard.type == "DEFAULT") {

        console.log("click");

        var row = Math.floor(y / tileHeight);
        var column = Math.floor(x / tileWidth);

        if (!isFlag(globalFlags, column, row) || clickType === "FLAG") {

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

    } else if (globalBoard.type == "TRIANGULAR") {
        var row = Math.floor(y / tileHeight);
        var offset = row * tileWidth / 2;
        var estimate = Math.floor((x - offset) / tileWidth * 2);
        //console.log(row + " " + estimate);


        var tiles = globalBoard.tiles;
        var selectedTile;
        var edge = false;

        if (estimate === globalBoard.width) {
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

                if (!isFlag(globalFlags, column, row) && clickType === "CHECK") {

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
            if (!isFlag(globalFlags, column, row) && clickType === "CHECK") {

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
    } else if (globalBoard.type = "HEXAGONAL"){
        var p = new HT.Point(x, y);
        var hex = hexagon_grid.GetHexAt(p);
        var row = hex.PathCoOrdY;
        var column = hex.PathCoOrdX;

        if (!isFlag(globalFlags, column, row) && clickType === "CHECK") {

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
    $("#board").hide();
    $("#win").show();
}

function lose() {
    $("#board").hide();
    $("#lose").show();
}

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
            type: "init",
            minesweepId: $.cookie("minesweepId"),
            minesweepRoomId: $.cookie("minesweepRoomId"),
            name: $.cookie("playerName")
        };  
        socket.send(JSON.stringify(sendData));
    });
}

//$("#startGame").hide();

console.log('made it this far');

// start the game
$("#startButton").click(function() {
	//init();
    console.log('start button clicked');
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
    
    // Pre game setup    
    if(responseJson.type === "init") {
        var teamToJoin;
        $.each(responseJson.data.teams, function (teamId, teamInfo) {
            teamToJoin = teamId;
        });
        joinRoom(teamToJoin);
    }
    
    else if (responseJson.type === "update") {
        drawRoom(responseJson);
    } 

    // Begin game, i.e. draw game board
    else if (responseJson.type === "gameData") {
    init();
        console.log(responseJson);
        drawBoard(responseJson.data);
        $("#board").show();
        $("#teams").hide();
        $("#infoBox").show();
        $("#infoBox").empty();
        $("#infoBox").html("Score: " + responseJson.score 
            + "<br>" + "Lives: " + responseJson.lives);
    }
    
    else if (responseJson.type === "victory") {
        console.log("test");
        victoryOrDefeat(responseJson.teamId);
    }
}

function victoryOrDefeat(teamId) {
    $.getScript("../js/js.cookie.js", function(){
        if(teamId === $.cookie("minesweepTeamId")) {
            console.log("test");
            win();
        } else {
            lose();
        }
  });
}

// draw pre game rooms
function drawRoom(responseJson) {
    $.getScript("/webplate/stack.js", function() {
        var innerBox = "";
        
        var roomInfo = responseJson.data;
    
        var teams = roomInfo.teams;
    
        $.each(teams, function(i, team) {
            innerBox += '<div class="span-2" style="padding-top:30px"><h4>' + team.name + "</h4>";
            $.each(team.players, function(j, player) {
                if (player.type=="HUMAN")
                    innerBox += '<a class="button line-purple">' + player.name + "</a><br>";
                else 
                    innerBox += '<a class="button line-aqua">' + player.name + "</a><br>";
            });
            // add ai button
            innerBox += '<a class="button aqua modal-trigger" data-modal-open="ai-choose-'+i+'" id="ai' + i + '">' + "Add AI</a><br>";
            // join team button
            innerBox += '<a class="button purple" id="buttonId' + i + '">' + "Join Team</a></div>";  
            // choose ai difficulty modal
            innerBox += '<div class="modalplate" data-modal-id="ai-choose-'+i+'"><div class="modalplate-title-bar"><a class="close">Close</a><h4>Choose AI Difficulty</h4></div><div class="modalplate-content"><div class="row"><div class="ai span-2"><a class="button aqua large icon close" id="easy'+i+'"><span class="icon icon-smile"></span></a>Easy</div><div class="span-2"><a class="button aqua large icon close" id="medium'+i+'"><span class="icon icon-evil"></span></a>Medium</div><div class="span-2"><a class="button aqua large icon close" id="hard'+i+'"><span class="icon icon-crying"></span></a>Hard</div><div class="span-2"><a class="button aqua large icon close" id="random'+i+'"><span class="icon icon-hipster"></span></a>Random</div></div></div></div>';  
        });
        
        // have to readd the sidebar; css issues
        var sidebar = '<div id="start" class="span-2"><a class="button line-white large" id="startButton">Start Game!</a><a class="button line-white large" id="disbandButton">Disband</a></div>'

        $("#teams").html(sidebar + innerBox);

        $("#startButton").click(function() {
            //init();
            console.log('start button clicked');
            $.getScript("../js/js.cookie.js", function(){
                var sendData = {
                    type: "startGame",
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
        $.cookie("minesweepTeamId", teamId);
        var sendData = {
            type: "joinRoom",
            minesweepId: $.cookie("minesweepId"),
            minesweepTeamId: teamId,
            minesweepRoomId: $.cookie("minesweepRoomId")
        };
        socket.send(JSON.stringify(sendData));
  });
}

// add an ai to a team
function addAi(teamId, difficulty) {
        $.getScript("../js/js.cookie.js", function(){
            var sendData = {
                type: "addAIPlayer",
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
    
}

function drawBoard(responseJSON) {
	
    var board = JSON.parse(responseJSON);
    
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

    if (board.type == "DefaultBoard"){
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
                /*if (tile.isFlag) {
                    _ctx.drawImage(flagImage, tileX, tileY, tileWidth, tileHeight);
                }*/
            }

        });
    } else if (board.type == "TriangularBoard"){

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
            } 
        });
   
        _ctx.stroke();
    } else if (board.type = "HexagonalBoard"){
    	//drawHexGrid();
    	$.each(tiles, function(index, tile)
    	{
    		var hexes = hexagon_grid.GetHexAtPos(tile.column, tile.row);
    		console.log(hexes);
    		if(tile.visited){
    			if(tile.isBomb){
    				hexes.fillColor = BOMB;
    			} else {
    				console.log("This is suppose to be visited");
    			    
    				hexes.fillColor = EXPLORED;
    			}
    			console.log(hexes);
    		}
    	});
    	for (var h in HT.Grid.Static.Hexes)
    	{
    		var hex = HT.Grid.Static.Hexes[h];
    		if (hex.fillColor = EXPLORED)
    		{
    			console.log("Visisted: " + hex);
    			console.log(hex);
    			
    		}
    	}
    	drawHexGrid(hexagon_grid, _ctx);
    } else if (board.type = "RectangularBoard"){
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
                /*if (tile.isFlag) {
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
    _ctx.fillText(tile.adjacentBombs, (x1 + x2 + x3) / 3, (y1 + y2 + y3) / 3);  */
}

$("#board").bind("contextmenu", function(e){
   console.log("Right click!!");
   return false;
}); 


$("#board").bind('click', function(event){
    var board = $("#board")[0];

    var x = event.pageX - board.offsetLeft;
    var y = event.pageY - board.offsetTop;

    if (globalBoard.type == "DefaultBoard"){
        
        console.log("click");
    	
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

    } else if (globalBoard.type == "TriangularBoard") {
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
        }
    } else if (globalBoard.type = "HexagonalBoard"){
    	var p = new HT.Point(x, y);
    	var hex = hexagon_grid.GetHexAt(p);
    	var row = hex.PathCoOrdY;
    	var column = hex.PathCoOrdX;
    		
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
    }
});

function win() {
    $("#board").hide();
    $("#win").show();
}

function lose() {
    $("#board").hide();
    $("#lose").show();
}

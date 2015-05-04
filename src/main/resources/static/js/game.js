UNEXPLORED = "#C0C0C0";
EXPLORED = "#FFFFFF";
BOMB = "#000000";
TEXT_COLOR = "#000000";

var CANVAS_X = 800;
var CANVAS_Y = 800;

var tileWidth;
var tileHeight;

var _ctx;

init();
updateBoardLoop();



function updateBoardLoop() {
        window.setInterval("drawBoard()", 500);
}

function drawBoard() {
    $.get("/board", function(responseJSON) {
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
    });
}

$("#board").bind('click', function(event){
    var board = $("#board")[0];

    var x = event.pageX - board.offsetLeft;
    var y = event.pageY - board.offsetTop;

    var row = Math.floor(y / tileHeight);
    var column = Math.floor(x / tileWidth);
    
    var postParameters = {
        row: row,
        column: column,
    };

    console.log(postParameters);

    $.post("/move", postParameters);
});


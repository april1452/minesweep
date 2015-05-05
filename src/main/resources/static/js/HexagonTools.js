var mineImage = new Image();
mineImage.src = "/images/mine.png";
var flagImage = new Image();
flagImage.src = "/images/flag.png";

TEXT_COLOR = "#000000";
ONE_MINE = "#0000FF";
TWO_MINE = "#00FF00";
THREE_MINE = "#FF0000";
FOUR_MINE = "#000066";
FIVE_MINE = "#006600";
SIX_MINE = "#660000";
SEVEN_MINE = "#666666";
EIGHT_MINE = "#000000";

var HT = HT || {};
/**
 * A Point is simply x and y coordinates
 * @constructor
 */
HT.Point = function(x, y) {
	this.X = x;
	this.Y = y;
};

/**
 * A Rectangle is x and y origin and width and height
 * @constructor
 */
HT.Rectangle = function(x, y, width, height) {
	this.X = x;
	this.Y = y;
	this.Width = width;
	this.Height = height;
};

/**
 * A Line is x and y start and x and y end
 * @constructor
 */
HT.Line = function(x1, y1, x2, y2) {
	this.X1 = x1;
	this.Y1 = y1;
	this.X2 = x2;
	this.Y2 = y2;
};

/**
 * A Hexagon is a 6 sided polygon, our hexes don't have to be symmetrical, i.e. ratio of width to height could be 4 to 3
 * @constructor
 */
HT.Hexagon = function(id, x, y) {
	this.Points = [];//Polygon Base
	var x1 = null;
	var y1 = null;
	if(HT.Hexagon.Static.ORIENTATION == HT.Hexagon.Orientation.Normal) {
		x1 = (HT.Hexagon.Static.WIDTH - HT.Hexagon.Static.SIDE)/2;
		y1 = (HT.Hexagon.Static.HEIGHT / 2);
		this.Points.push(new HT.Point(x1 + x, y));
		this.Points.push(new HT.Point(x1 + HT.Hexagon.Static.SIDE + x, y));
		this.Points.push(new HT.Point(HT.Hexagon.Static.WIDTH + x, y1 + y));
		this.Points.push(new HT.Point(x1 + HT.Hexagon.Static.SIDE + x, HT.Hexagon.Static.HEIGHT + y));
		this.Points.push(new HT.Point(x1 + x, HT.Hexagon.Static.HEIGHT + y));
		this.Points.push(new HT.Point(x, y1 + y));
	}
	else {
		x1 = (HT.Hexagon.Static.WIDTH / 2);
		y1 = (HT.Hexagon.Static.HEIGHT - HT.Hexagon.Static.SIDE)/2;
		this.Points.push(new HT.Point(x1 + x, y));
		this.Points.push(new HT.Point(HT.Hexagon.Static.WIDTH + x, y1 + y));
		this.Points.push(new HT.Point(HT.Hexagon.Static.WIDTH + x, y1 + HT.Hexagon.Static.SIDE + y));
		this.Points.push(new HT.Point(x1 + x, HT.Hexagon.Static.HEIGHT + y));
		this.Points.push(new HT.Point(x, y1 + HT.Hexagon.Static.SIDE + y));
		this.Points.push(new HT.Point(x, y1 + y));
	}
	
	this.Id = id;
	
	this.x = x;
	this.y = y;
	this.x1 = x1;
	this.y1 = y1;
	
	this.TopLeftPoint = new HT.Point(this.x, this.y);
	this.BottomRightPoint = new HT.Point(this.x + HT.Hexagon.Static.WIDTH, this.y + HT.Hexagon.Static.HEIGHT);
	this.MidPoint = new HT.Point(this.x + (HT.Hexagon.Static.WIDTH / 2), this.y + (HT.Hexagon.Static.HEIGHT / 2));
	
	this.P1 = new HT.Point(x + x1, y + y1);
	this.P2 = new HT.Point(this.BottomRightPoint.X - x1, this.BottomRightPoint.Y);
	
	this.selected = false;
	this.fillColor = "#C0C0C0";
};
	
/**
 * draws this Hexagon to the canvas
 * @this {HT.Hexagon}
 */
HT.Hexagon.prototype.draw = function(ctx) {

	if(!this.selected)
		ctx.strokeStyle = "grey";
	else
		ctx.strokeStyle = "black";
	ctx.lineWidth = 1;
	ctx.beginPath();
	ctx.moveTo(this.Points[0].X, this.Points[0].Y);
	for(var i = 1; i < this.Points.length; i++)
	{
		var p = this.Points[i];
		ctx.lineTo(p.X, p.Y);
	}
	ctx.closePath();
	ctx.fillStyle = this.fillColor;
	ctx.fill();
	ctx.stroke();
	
	if(this.Id)
	{
		//draw text for debugging
		ctx.fillStyle = "black"
		ctx.font = "bolder 8pt Trebuchet MS,Tahoma,Verdana,Arial,sans-serif";
		ctx.textAlign = "center";
		ctx.textBaseline = 'middle';
		//var textWidth = ctx.measureText(this.Planet.BoundingHex.Id);
		ctx.fillText(this.Id, this.MidPoint.X, this.MidPoint.Y);
	}
	
	if(this.PathCoOrdX !== null && this.PathCoOrdY !== null && typeof(this.PathCoOrdX) != "undefined" && typeof(this.PathCoOrdY) != "undefined")
	{
		//draw co-ordinates for debugging
		ctx.fillStyle = "black"
		ctx.font = "bolder 8pt Trebuchet MS,Tahoma,Verdana,Arial,sans-serif";
		ctx.textAlign = "center";
		ctx.textBaseline = 'middle';
		//var textWidth = ctx.measureText(this.Planet.BoundingHex.Id);
		ctx.fillText("("+this.PathCoOrdX+","+this.PathCoOrdY+")", this.MidPoint.X, this.MidPoint.Y + 10);
	}
	
	if(HT.Hexagon.Static.DRAWSTATS)
	{
		ctx.strokeStyle = "black";
		ctx.lineWidth = 2;
		//draw our x1, y1, and z
		ctx.beginPath();
		ctx.moveTo(this.P1.X, this.y);
		ctx.lineTo(this.P1.X, this.P1.Y);
		ctx.lineTo(this.x, this.P1.Y);
		ctx.closePath();
		ctx.stroke();
		
		ctx.fillStyle = "black"
		ctx.font = "bolder 8pt Trebuchet MS,Tahoma,Verdana,Arial,sans-serif";
		ctx.textAlign = "left";
		ctx.textBaseline = 'middle';
		//var textWidth = ctx.measureText(this.Planet.BoundingHex.Id);
		ctx.fillText("z", this.x + this.x1/2 - 8, this.y + this.y1/2);
		ctx.fillText("x", this.x + this.x1/2, this.P1.Y + 10);
		ctx.fillText("y", this.P1.X + 2, this.y + this.y1/2);
		ctx.fillText("z = " + HT.Hexagon.Static.SIDE, this.P1.X, this.P1.Y + this.y1 + 10);
		ctx.fillText("(" + this.x1.toFixed(2) + "," + this.y1.toFixed(2) + ")", this.P1.X, this.P1.Y + 10);
	}
};

HT.Hexagon.prototype.draw = function(ctx, color, isFlag, isMine) {

	if(!this.selected)
		ctx.strokeStyle = "grey";
	else
		ctx.strokeStyle = "black";
	ctx.lineWidth = 1;
	ctx.beginPath();
	ctx.moveTo(this.Points[0].X, this.Points[0].Y);
	for(var i = 1; i < this.Points.length; i++)
	{
		var p = this.Points[i];
		ctx.lineTo(p.X, p.Y);
	}
	ctx.closePath();
	ctx.fillStyle = color;
	ctx.fill();
	ctx.stroke();
	
	if(this.Id)
	{
		//draw text for debugging
		ctx.fillStyle = "black"
		ctx.font = "bolder 8pt Trebuchet MS,Tahoma,Verdana,Arial,sans-serif";
		ctx.textAlign = "center";
		ctx.textBaseline = 'middle';
		//var textWidth = ctx.measureText(this.Planet.BoundingHex.Id);
		ctx.fillText(this.Id, this.MidPoint.X, this.MidPoint.Y);
	}
	
	if(this.PathCoOrdX !== null && this.PathCoOrdY !== null && typeof(this.PathCoOrdX) != "undefined" && typeof(this.PathCoOrdY) != "undefined")
	{
		//draw co-ordinates for debugging
		ctx.fillStyle = "black"
		ctx.font = "bolder 8pt Trebuchet MS,Tahoma,Verdana,Arial,sans-serif";
		ctx.textAlign = "center";
		ctx.textBaseline = 'middle';
		//var textWidth = ctx.measureText(this.Planet.BoundingHex.Id);
		ctx.fillText("("+this.PathCoOrdX+","+this.PathCoOrdY+")", this.MidPoint.X, this.MidPoint.Y + 10);
	}
	
	if(HT.Hexagon.Static.DRAWSTATS)
	{
		ctx.strokeStyle = "black";
		ctx.lineWidth = 2;
		//draw our x1, y1, and z
		ctx.beginPath();
		ctx.moveTo(this.P1.X, this.y);
		ctx.lineTo(this.P1.X, this.P1.Y);
		ctx.lineTo(this.x, this.P1.Y);
		ctx.closePath();
		ctx.stroke();
		
		ctx.fillStyle = "black"
		ctx.font = "bolder 8pt Trebuchet MS,Tahoma,Verdana,Arial,sans-serif";
		ctx.textAlign = "left";
		ctx.textBaseline = 'middle';
		//var textWidth = ctx.measureText(this.Planet.BoundingHex.Id);
		ctx.fillText("z", this.x + this.x1/2 - 8, this.y + this.y1/2);
		ctx.fillText("x", this.x + this.x1/2, this.P1.Y + 10);
		ctx.fillText("y", this.P1.X + 2, this.y + this.y1/2);
		ctx.fillText("z = " + HT.Hexagon.Static.SIDE, this.P1.X, this.P1.Y + this.y1 + 10);
		ctx.fillText("(" + this.x1.toFixed(2) + "," + this.y1.toFixed(2) + ")", this.P1.X, this.P1.Y + 10);
	}
};


/**
 * draws this Hexagon to the canvas
 * @this {HT.Hexagon}
 */
HT.Hexagon.prototype.preDraw = function(ctx) {

	if(!this.selected)
		ctx.strokeStyle = "grey";
	else
		ctx.strokeStyle = "black";
	ctx.lineWidth = 1;
	ctx.beginPath();
	ctx.moveTo(this.Points[0].X, this.Points[0].Y);
	for(var i = 1; i < this.Points.length; i++)
	{
		var p = this.Points[i];
		ctx.lineTo(p.X, p.Y);
	}
	ctx.closePath();
	ctx.fillStyle = this.fillColor;
	ctx.fill();
	if(this.Id)
	{
		//draw text for debugging
		ctx.fillStyle = "black"
		ctx.font = "bolder 8pt Trebuchet MS,Tahoma,Verdana,Arial,sans-serif";
		ctx.textAlign = "center";
		ctx.textBaseline = 'middle';
		//var textWidth = ctx.measureText(this.Planet.BoundingHex.Id);
		ctx.fillText(this.Id, this.MidPoint.X, this.MidPoint.Y);
	}
	
	if(this.PathCoOrdX !== null && this.PathCoOrdY !== null && typeof(this.PathCoOrdX) != "undefined" && typeof(this.PathCoOrdY) != "undefined")
	{
		//draw co-ordinates for debugging
		ctx.fillStyle = "black"
		ctx.font = "bolder 8pt Trebuchet MS,Tahoma,Verdana,Arial,sans-serif";
		ctx.textAlign = "center";
		ctx.textBaseline = 'middle';
		//var textWidth = ctx.measureText(this.Planet.BoundingHex.Id);
		ctx.fillText("("+this.PathCoOrdX+","+this.PathCoOrdY+")", this.MidPoint.X, this.MidPoint.Y + 10);
	}
	
	if(HT.Hexagon.Static.DRAWSTATS)
	{
		ctx.strokeStyle = "black";
		ctx.lineWidth = 2;
		//draw our x1, y1, and z
		ctx.beginPath();
		ctx.moveTo(this.P1.X, this.y);
		ctx.lineTo(this.P1.X, this.P1.Y);
		ctx.lineTo(this.x, this.P1.Y);
		ctx.closePath();
		ctx.stroke();
		
		ctx.fillStyle = "black"
		ctx.font = "bolder 8pt Trebuchet MS,Tahoma,Verdana,Arial,sans-serif";
		ctx.textAlign = "left";
		ctx.textBaseline = 'middle';
		//var textWidth = ctx.measureText(this.Planet.BoundingHex.Id);
		ctx.fillText("z", this.x + this.x1/2 - 8, this.y + this.y1/2);
		ctx.fillText("x", this.x + this.x1/2, this.P1.Y + 10);
		ctx.fillText("y", this.P1.X + 2, this.y + this.y1/2);
		ctx.fillText("z = " + HT.Hexagon.Static.SIDE, this.P1.X, this.P1.Y + this.y1 + 10);
		ctx.fillText("(" + this.x1.toFixed(2) + "," + this.y1.toFixed(2) + ")", this.P1.X, this.P1.Y + 10);
	}
};

HT.Hexagon.prototype.preDraw = function(ctx, color, isMine, isFlag, visited, numMines) {

	console.log(numMines);

	if(!this.selected)
		ctx.strokeStyle = "grey";
	else
		ctx.strokeStyle = "black";
	ctx.lineWidth = 1;
	ctx.beginPath();
	ctx.moveTo(this.Points[0].X, this.Points[0].Y);
	for(var i = 1; i < this.Points.length; i++)
	{
		var p = this.Points[i];
		ctx.lineTo(p.X, p.Y);
	}
	ctx.closePath();
	ctx.fillStyle = color;
	ctx.fill();
	//ctx.stroke();
	
	
	if(this.Id)
	{
		//draw text for debugging
		ctx.fillStyle = "black"
		ctx.font = "bolder 8pt Trebuchet MS,Tahoma,Verdana,Arial,sans-serif";
		ctx.textAlign = "center";
		ctx.textBaseline = 'middle';
		//var textWidth = ctx.measureText(this.Planet.BoundingHex.Id);
		//ctx.fillText(this.Id, this.MidPoint.X, this.MidPoint.Y);
	}
	
	if(this.PathCoOrdX !== null && this.PathCoOrdY !== null && typeof(this.PathCoOrdX) != "undefined" && typeof(this.PathCoOrdY) != "undefined")
	{
		//draw co-ordinates for debugging
		console.log("MINE: " + isMine + " FLAG: " + isFlag);

		ctx.fillStyle = "black"
		ctx.font = "bolder 8pt Trebuchet MS,Tahoma,Verdana,Arial,sans-serif";
		ctx.textAlign = "center";
		ctx.textBaseline = 'middle';
		//var textWidth = ctx.measureText(this.Planet.BoundingHex.Id);
		//ctx.fillText("("+this.PathCoOrdX+","+this.PathCoOrdY+")", this.MidPoint.X, this.MidPoint.Y + 10);
		//ctx.fillText(numMines, this.MidPoint.X, this.MidPoint.Y);

	}

	if (visited && !isMine && numMines > 0) {
			ctx.fillStyle = getTextColor(numMines);
			ctx.font = "bolder 8pt Trebuchet MS,Tahoma,Verdana,Arial,sans-serif";
			ctx.textAlign = "center";
			ctx.textBaseline = 'middle';
			ctx.fillText(numMines, this.MidPoint.X, this.MidPoint.Y);
		}
	
	if(HT.Hexagon.Static.DRAWSTATS)
	{
		ctx.strokeStyle = "black";
		ctx.lineWidth = 2;
		//draw our x1, y1, and z
		ctx.beginPath();
		ctx.moveTo(this.P1.X, this.y);
		ctx.lineTo(this.P1.X, this.P1.Y);
		ctx.lineTo(this.x, this.P1.Y);
		ctx.closePath();
		//ctx.stroke();
		if (visited && !isMine && numMines > 0) {
			ctx.fillStyle = getTextColor(numMines);
			ctx.font = "bolder 8pt Trebuchet MS,Tahoma,Verdana,Arial,sans-serif";
			ctx.textAlign = "center";
			ctx.textBaseline = 'middle';
			ctx.fillText(numMines, this.MidPoint.X, this.MidPoint.Y);
		}
		//var textWidth = ctx.measureText(this.Planet.BoundingHex.Id);
		/*ctx.fillText("z", this.x + this.x1/2 - 8, this.y + this.y1/2);
		ctx.fillText("x", this.x + this.x1/2, this.P1.Y + 10);
		ctx.fillText("y", this.P1.X + 2, this.y + this.y1/2);
		ctx.fillText("z = " + HT.Hexagon.Static.SIDE, this.P1.X, this.P1.Y + this.y1 + 10);
		ctx.fillText("(" + this.x1.toFixed(2) + "," + this.y1.toFixed(2) + ")", this.P1.X, this.P1.Y + 10);*/
	}

	if (isMine && visited) {
		console.log("MINE");
		console.log((this.P2.X - this.P1.X) + " " + (this.P2.Y - this.P1.Y));
        _ctx.drawImage(mineImage, this.P1.X, 2 * this.P1.Y - this.P2.Y, this.P2.X - this.P1.X, 2 * (this.P2.Y - this.P1.Y));
    }
    if (isFlag) {
    	console.log("FLAG");
        _ctx.drawImage(flagImage, this.P1.X, 2 * this.P1.Y - this.P2.Y, this.P2.X - this.P1.X, 2 * (this.P2.Y - this.P1.Y));
    }
};


/**
 * Returns true if the x,y coordinates are inside this hexagon
 * @this {HT.Hexagon}
 * @return {boolean}
 */
HT.Hexagon.prototype.isInBounds = function(x, y) {
	return this.Contains(new HT.Point(x, y));
};
	

/**
 * Returns true if the point is inside this hexagon, it is a quick contains
 * @this {HT.Hexagon}
 * @param {HT.Point} p the test point
 * @return {boolean}
 */
HT.Hexagon.prototype.isInHexBounds = function(/*Point*/ p) {
	if(this.TopLeftPoint.X < p.X && this.TopLeftPoint.Y < p.Y &&
	   p.X < this.BottomRightPoint.X && p.Y < this.BottomRightPoint.Y)
		return true;
	return false;
};

//grabbed from:
//http://www.developingfor.net/c-20/testing-to-see-if-a-point-is-within-a-polygon.html
//and
//http://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html#The%20C%20Code
/**
 * Returns true if the point is inside this hexagon, it first uses the quick isInHexBounds contains, then check the boundaries
 * @this {HT.Hexagon}
 * @param {HT.Point} p the test point
 * @return {boolean}
 */
HT.Hexagon.prototype.Contains = function(/*Point*/ p) {
	var isIn = false;
	if (this.isInHexBounds(p))
	{
		//turn our absolute point into a relative point for comparing with the polygon's points
		//var pRel = new HT.Point(p.X - this.x, p.Y - this.y);
		var i, j = 0;
		for (i = 0, j = this.Points.length - 1; i < this.Points.length; j = i++)
		{
			var iP = this.Points[i];
			var jP = this.Points[j];
			if (
				(
				 ((iP.Y <= p.Y) && (p.Y < jP.Y)) ||
				 ((jP.Y <= p.Y) && (p.Y < iP.Y))
				//((iP.Y > p.Y) != (jP.Y > p.Y))
				) &&
				(p.X < (jP.X - iP.X) * (p.Y - iP.Y) / (jP.Y - iP.Y) + iP.X)
			   )
			{
				isIn = !isIn;
			}
		}
	}
	return isIn;
};

/**
* Returns absolute distance in pixels from the mid point of this hex to the given point
* Provided by: Ian (Disqus user: boingy)
* @this {HT.Hexagon}
* @param {HT.Point} p the test point
* @return {number} the distance in pixels
*/
HT.Hexagon.prototype.distanceFromMidPoint = function(/*Point*/ p) {
	// Pythagoras' Theorem: Square of hypotenuse = sum of squares of other two sides
	var deltaX = this.MidPoint.X - p.X;
	var deltaY = this.MidPoint.Y - p.Y;

	// squaring so don't need to worry about square-rooting a negative number 
	return Math.sqrt( (deltaX * deltaX) + (deltaY * deltaY) );
};

HT.Hexagon.Orientation = {
	Normal: 0,
	Rotated: 1
};

HT.Hexagon.Static = {HEIGHT:91.14378277661477
					, WIDTH:91.14378277661477
					, SIDE:50.0
					, ORIENTATION:HT.Hexagon.Orientation.Normal
					, DRAWSTATS: false};//hexagons will have 25 unit sides for now

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



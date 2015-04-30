<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>minesweep+</title>  
    <link href='http://fonts.googleapis.com/css?family=Noto+Sans' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/html5bp.css">
    <link rel="stylesheet" href="css/all.css">
    <link rel="stylesheet" href="css/setup.css">
</head>
    
<body>
    <center><h1>minesweep+</h1></center>
    <div id="form">
    <form id="createGame">
        <h3>Game name</h3>
        <input type="text" name="gameName" placeholder="Insert game name here">
        <br><br>

        <h3>Play mode</h3>
        <input type="radio" name="gameMode" value="classic" checked>Classic
        <input type="radio" name="gameMode" value="layers">Layers
        <input type="radio" name="gameMode" value="territory">Territory
        <input type="radio" name="gameMode" value="paths">Paths
        <input type="radio" name="gameMode" value="fsu">F.S.U.
        <br>
        <p class="label">Number of lives: <input type="number" name="lives" value="3"></p>
        <br>

        <h3>Board</h3>
        <input type="radio" name="boardShape" value="rectangle" checked>Rectangle
        <input type="radio" name="boardShape" value="triangle">Triangle
        <br>
        <p class="label">Dimensions:
        <input type="number" name="width" placeholder="Width" value="20"> x
        <input type="number" name="height" placeholder="Height" value="20"></p>
        <br>

        <h3>Teams</h3>
        <p class="label">Number of teams: 
        <input type="number" name="teams" value="2"></p>
        <p class="label">Players per team: 
        <input type="number" name="players" value="2"></p>
        <br>
        <input type="submit" id="create" value="Host Game">
    </form>
    </div>

    <div id="info">
        <div id="classic">
            <h3>Classic Mode</h3>
            <p>Teams race to complete a simple Minesweeper board. Each team has the same board setup,<br> and the a team wins if it finishes the board first or its opponents run out of lives.</p>
            
        </div>
        <div id="layers" style="display: none">
            <h3>Layers Mode</h3>
            <p>Teams are given a stack of boards to race through. The first team to complete all of its<br> boards without losing its lives is the winner.</p>
            
        </div>
        <div id="territory" style="display: none">
            <h3>Territory Mode</h3>
            <p>Teams attempt to conquer the most territory on a single board of Minesweeper. Each <br> space a team reveals without a mine is added that that team's territory and the team<br> that finds the most spaces wins.</p>
            
        </div>
        <div id="paths" style="display: none">
            <h3>Paths Mode</h3>
            <p>Teams race from one side of the board to the other. The first team to find a continuous<br> path between both sides of the board wins.
            
        </div>
        <div id="fsu" style="display: none">
            <h3>F.S.U. Mode</h3>
            <p>In this hostile game mode, teams directly interfere with their opponents. Each team can<br> spent the points they've gained by uncovering mines on their board to move around<br> undiscovered mmines on the board of their opponents.</p>
            
        </div>

    </div>

    <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
    <script src="../js/setup.js"></script>
</body>
</html>

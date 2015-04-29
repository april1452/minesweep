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
        <p class="label">Number of lives: <input type="number" name="lives"></p>
        <br>

        <h3>Board</h3>
        <input type="radio" name="boardShape" value="rectangle" checked>Rectangle
        <input type="radio" name="boardShape" value="triangle">Triangle
        <br>
        <p class="label">Dimensions:
        <input type="number" name="width" placeholder="Width"> x
        <input type="number" name="height" placeholder="Height"></p>
        <br>

        <h3>Teams</h3>
        <p class="label">Number of teams: 
        <input type="number" name="teams"></p>
        <p class="label">Players per team: 
        <input type="number" name="players"></p>
        <br>
        <input type="submit" id="create" value="Host Game">
    </form>
    </div>

    <div id="info">
        <div id="classic">
            <h3>Classic Mode</h3>
            
        </div>
        <div id="layers" style="display: none">
            <h3>Layers Mode</h3>
            
        </div>
        <div id="territory" style="display: none">
            <h3>Territory Mode</h3>
            
        </div>
        <div id="paths" style="display: none">
            <h3>Paths Mode</h3>
            
        </div>
        <div id="fsu" style="display: none">
            <h3>F.S.U. Mode</h3>
            
        </div>

    </div>

    <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
    <script src="js/setup.js"></script>
</body>
</html>

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
    <div id="form">
    <form id="createGame">
        <h3>Gameplay</h3>
        <input type="radio" name="gameMode" value="classic" checked>Classic
        <input type="radio" name="gameMode" value="layers">Layers
        <input type="radio" name="gameMode" value="territory">Territory
        <input type="radio" name="gameMode" value="paths">Paths
        <input type="radio" name="gameMode" value="fsu">F.S.U.
        <br>
        <p class="label">Number of lives: <input type="number" name="lives"></p>
        <br>

        <h3>Board</h3>
        <input type="radio" name="boardShape" value="rectangle">Rectangle
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
        <input type="submit" value="Host Game">
    </form>
    </div>

    <div id="info">
        Column 2
    </div>

    <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
    <script src="js/setup.js"></script>
</body>
</html>

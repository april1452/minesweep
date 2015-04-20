<!DOCTYPE html>
    <html>
        <head>
            <meta charset="utf-8">
            <title>${title}</title>  
            <link rel="stylesheet" href="/css/normalize.css">
            <link rel="stylesheet" href="/css/html5bp.css">
        </head>
        
        <body>
            <form id="createGame">
                Game mode:<br>
                <input type="radio" name="gameMode" value="classic" checked>Classic
                <input type="radio" name="gameMode" value="layers">Layers
                <input type="radio" name="gameMode" value="territory">Territory
                <br>

                Board shape:<br>
                <input type="radio" name="boardShape" value="default" checked>Default
                <input type="radio" name="boardShape" value="rectangle">Rectangle
                <input type="radio" name="boardShape" value="triangle">Triangle
                <br>

                Dimensions:<br>
                <input type="number" name="width" placeholder="Width"> x
                <input type="number" name="height" placeholder="Height">
                <br>

                <input type="submit" value="Host Game">
            </form>
        </body>
        <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
        <script src="../js/setup.js"></script>
    </html>

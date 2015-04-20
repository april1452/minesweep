<!DOCTYPE html>
    <html>
        <head>
            <meta charset="utf-8">
            <title>${title}</title>  
            <link rel="stylesheet" href="/css/normalize.css">
            <link rel="stylesheet" href="/css/html5bp.css">
        </head>
        
        <body>
            <form action="/setup">
                <input type="submit" value="Host Game">
            </form>
            <p> Existing Rooms: </p>
            <form id="joinGame">
                Game mode: 
                <select id="gameId">
                </select>
                <input type="submit" value="Join Game">
            </form>
        </body>
        <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
        <script src="../js/main.js"></script>
    </html>

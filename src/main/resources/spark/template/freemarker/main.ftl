<!DOCTYPE html>
    <html>
        <head>
            <meta charset="utf-8">
            <title>${title}</title>  
            <link href='http://fonts.googleapis.com/css?family=Noto+Sans' rel='stylesheet' type='text/css'>
            <link rel="stylesheet" href="/css/normalize.css">
            <link rel="stylesheet" href="/css/html5bp.css">
            <link rel="stylesheet" href="/css/all.css">
            <link rel="stylesheet" href="/css/main.css">
        </head>
        
        <body>    
            <p> Existing Rooms: </p>
            <!--<form id="joinGame">
                Game mode: 
                <select id="gameId">
                </select>
                <input type="submit" value="Join Game">
            </form>-->
            <div id="games">
                <ul id="gamesList">
                </ul>
            </div>
            <form action="/setup">
                <input type="submit" value="Host Game">
            </form>

        </body>
        <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
        <script src="../js/main.js"></script>
    </html>

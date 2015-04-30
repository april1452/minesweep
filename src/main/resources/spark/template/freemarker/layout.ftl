<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>${title}</title>  

    <link href='http://fonts.googleapis.com/css?family=Noto+Sans' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="/css/normalize.css">
    <link rel="stylesheet" href="/css/html5bp.css">
    <link rel="stylesheet" href="/css/layout.css">

    <script id="webplate-stack" src="/webplate/stack.js"></script>
</head>

<body class="center">

	<div id="nav" class="span-12 back-black">
		<h2 style="color:white; display:inline">minesweep+</h2>
		<a href="#" id="name" class="button gradient-pink small"></a>
	</div>

	<!-- name -->
	<div id="name-modal" class="modalplate">
    <div class="modalplate-title-bar">
        <a href="#" class="close">Close</a>
        <h5>Welcome to minesweep+</h5>
    </div>
    <div class="modalplate-content">
        <div class="formplate">
			    <label for="name-input">Enter your name:</label>
			    <input type="text" id="name-input" name="name-input">
				</div>
    </div>
	</div>

	${body}

<script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
<script src="/js/layout.js" type="text/javascript"></script>
</body>
</html>

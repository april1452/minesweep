<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>${title}</title>  

    <link href='http://fonts.googleapis.com/css?family=Noto+Sans' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="/css/normalize.css">
    <link rel="stylesheet" href="/css/html5bp.css">
    <link rel="stylesheet" href="/css/layout.css">

    <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
    <script src="/js/js.cookie.js"></script>
    <script id="webplate-stack" src="/webplate/stack.js"></script>
</head>

<body class="center" data-icon-font="icomoon" data-formplate-colour="pink">

	<div id="nav" class="span-12 back-black">
		<a href="/"><h2 style="color:white; display:inline">minesweep+</h2></a>
		<a href="#" id="about" class="button line-purple small modal-trigger" data-modal-open="about-modal">About</a>
		<a href="#" id="help" class="button line-pink small modal-trigger" data-modal-open="help-modal">Help</a>
	</div>

	<!-- help -->
	<div class="modalplate" data-modal-id="help-modal">
    <div class="modalplate-title-bar">
        <a href="#" class="close">Close</a>
        <h5>Gameplay modes</h5>
    </div>
    <div class="modalplate-content">
        Classic mode<br>
        Layers mode<br>
        Territory mode<br>
        Paths mode<br>
        Shake mode<br>
    </div>
	</div>

	<!-- name -->
	<div class="modalplate" data-modal-id="about-modal">
    <div class="modalplate-title-bar">
        <a href="#" class="close">Close</a>
        <h5>minesweep+</h5>
    </div>
    <div class="modalplate-content">
        Created for Professor John Jannotti's Intro to Software Engineering course at Brown University.<br><br> © 2015 Aravind Elangovan, Clayton Sanford, Phuc Anh (April) Tran, Aaron Gokaslan. All rights reserved.
    </div>
	</div>

	${body}

<script src="/js/layout.js" type="text/javascript"></script>
</body>
</html>

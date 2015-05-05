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
        Teams race to complete a simple Minesweeper board. Each team has the same board setup, and the a team wins if it finishes the board first or its opponents run out of lives.<br><br>
        Layers mode<br>
        Teams are given a stack of five boards to race through. The first team to complete all of its boards without losing its lives is the winner.<br><br>
        Territory mode<br>
        Teams attempt to conquer the most territory on a single board of Minesweeper. Each  space a team reveals without a mine is added that that team's territory and the team that finds the most spaces wins.<br><br>
        Timer mode<br>
        Teams must race against the clock to complete their boards! Each team starts with one minute. Checking a square without a mine adds five seconds to the clock, but hitting a mine subtracts thirty!
    </div>
	</div>

	<!-- name -->
	<div class="modalplate" data-modal-id="about-modal">
    <div class="modalplate-title-bar">
        <a href="#" class="close">Close</a>
        <h5>minesweep+</h5>
    </div>
    <div class="modalplate-content">
        © 2015 Aravind Elangovan, Clayton Sanford, Phuc Anh (April) Tran, Aaron Gokaslan. All rights reserved. Created for Professor John Jannotti's Intro to Software Engineering course at Brown University.<br><br><center><img src="images/bsr.jpg" style="width: 50%"></center>
    </div>
	</div>

	${body}

<script src="/js/layout.js" type="text/javascript"></script>
</body>
</html>

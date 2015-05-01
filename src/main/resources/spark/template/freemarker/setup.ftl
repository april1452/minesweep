<#assign body>
<link rel="stylesheet" href="/css/setup.css">


<div class="formplate span-6" id="createGame">
    <h5>Room name</h5>
    <input type="text" id="gameName" name="gameName" placeholder="Room name will appear on main screen">
    <br><br>
    <h5>Play mode</h5>
    <input type="radio" name="gameMode" value="CLASSIC" id="CLASSICrad" checked>
    <label for="CLASSICrad">Classic</label>
    <input type="radio" name="gameMode" value="LAYERS" id="layersrad">
    <label for="layersrad">Layers</label>
    <input type="radio" name="gameMode" value="TERRITORY" id="territoryrad">
    <label for="territoryrad">Territory</label>
    <input type="radio" name="gameMode" value="PATHS" id="pathsrad">
    <label for="pathsrad">Paths</label>
    <input type="radio" name="gameMode" value="FSU" id="fsurad">
    <label for="fsurad">F.S.U.</label>
    <br>

    <label for="lives">Number of lives:</label><input type="number" name="lives" id="lives" value="3">
    <br><br>

    <h5>Board</h5>
    <input type="radio" name="boardType" value="DEFAULT" id="rectangle" checked>
    <label for="rectangle">Rectangle</label>
    <input type="radio" name="boardType" value="TRIANGULAR" id="triangle">
    <label for="triangle">Triangle</label>
    <input type="radio" name="boardType" value="HEXAGONAL" id="hexagonal">
    <label for="hexagonal">Hexagonal</label>
    <br>

    <label>Dimensions:</label>
    <input type="number" name="width" id="width" placeholder="Width" value="20"> x
    <input type="number" name="height" id="height" placeholder="Height" value="20">
    <br><br>

    <h5>Teams</h5>
    <label>Number of teams:</label>
    <input type="radio" name="teams" value="1" id="teams-1">
    <label for="teams-1">1</label>
    <input type="radio" name="teams" value="2" id="teams-2" checked>
    <label for="teams-2">2</label>
    <input type="radio" name="teams" value="3" id="teams-3">
    <label for="teams-3">3</label>
    <input type="radio" name="teams" value="4" id="teams-4">
    <label for="teams-4">4</label>
    <br>
    <label>Players per team:</label> 
    <input type="radio" name="players" value="1" id="players-1">
    <label for="players-1">1</label>
    <input type="radio" name="players" value="2" id="players-2" checked>
    <label for="players-2">2</label>
    <input type="radio" name="players" value="3" id="players-3">
    <label for="players-3">3</label>
    <input type="radio" name="players" value="4" id="players-4">
    <label for="players-4">4</label>
    <br><br>
    
    <a id="create" class="button pink large">Host Game</a>
</div>

<div id="info" class="span-6">
    <div id="classic">
        <h5>Classic Mode</h5>
        <p>Teams race to complete a simple Minesweeper board. Each team has the same board setup, and the a team wins if it finishes the board first or its opponents run out of lives.</p>
        
    </div>
    <div id="layers" style="display: none">
        <h5>Layers Mode</h5>
        <p>Teams are given a stack of boards to race through. The first team to complete all of its boards without losing its lives is the winner.</p>
        
    </div>
    <div id="territory" style="display: none">
        <h5>Territory Mode</h5>
        <p>Teams attempt to conquer the most territory on a single board of Minesweeper. Each  space a team reveals without a mine is added that that team's territory and the team that finds the most spaces wins.</p>
        
    </div>
    <div id="paths" style="display: none">
        <h5>Paths Mode</h5>
        <p>Teams race from one side of the board to the other. The first team to find a continuous path between both sides of the board wins.
        
    </div>
    <div id="fsu" style="display: none">
        <h5>F.S.U. Mode</h5>
        <p>In this hostile game mode, teams directly interfere with their opponents. Each team can spent the points they've gained by uncovering mines on their board to move around undiscovered mmines on the board of their opponents.</p>
        
    </div>
</div>

<script src="/js/setup.js"></script>    
</#assign>
<#include "layout.ftl">

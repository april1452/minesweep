<#assign body>
<link rel="stylesheet" href="/css/play.css">
      
<div id="teams" class="row">
  <div id="start" class="span-2">
    <a class="button line-white large" id="startButton">Start Game!</a>
    <a class="button line-white large" id="disbandButton">Disband</a>
  </div>
</div>

<div id="game">
  <div id="boardDiv">
    <canvas id="board"></canvas>
  </div>
  <div id="infoBox">
  </div>
</div>

<div class="flickerplate" data-dot-navigation="false" data-auto-flick="false" data-theme="dark" id="win">
  <ul>
    <li>
      <div class="flick-title">WINNER, WINNER</div>
      <a href="/"><div class="flick-sub-text">Play again?</div></a>
    </li>
  </ul>
</div>

<div class="flickerplate" data-dot-navigation="false" data-auto-flick="false" data-theme="dark" id="lose">
  <ul>
    <li>
      <div class="flick-title">TOUGH LUCK</div>
      <a href="/"><div class="flick-sub-text">You lost. Try again?</div></a>
    </li>
  </ul>
</div>

<script src="/js/HexagonTools.js"></script>
<script src="/js/hexagon_grid.js"></script>
<script src="/js/HexCalcs.js"></script>
<script src="/js/play.js"></script>   
</#assign>
<#include "layout.ftl">

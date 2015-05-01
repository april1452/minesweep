<#assign body>
<link rel="stylesheet" href="/css/play.css">

<div id="start">
	<a class="button pink large" id="startButton">Start Game!</a>
</div>
      
<div id="teams" class="row">
</div>

<canvas id="board"></canvas>  

<div class="flickerplate" id="win">
  <ul>
    <li>
      <div class="flick-title">WINNER, WINNER</div>
      <a href="/"><div class="flick-sub-text">Play again?</div></a>
    </li>
  </ul>
</div>

<div class="flickerplate" id="lose">
  <ul>
    <li>
      <div class="flick-title">CHICKEN DINNER'S ON YOU BUDDY</div>
      <a href="/"><div class="flick-sub-text">Try again?</div></a>
    </li>
  </ul>
</div>

<script src="/js/play.js"></script>   
</#assign>
<#include "layout.ftl">

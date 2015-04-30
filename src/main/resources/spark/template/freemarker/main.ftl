<#assign body>
<link rel="stylesheet" href="/css/main.css">
<div class="row">
    <div id="games" class="span-6">  
        <h4>Existing Rooms:</h4>
    <!--<form id="joinGame">
        Game mode: 
        <select id="gameId">
        </select>
        <input type="submit" value="Join Game">
    </form>-->
    
        <ul id="gamesList">
        </ul>

        <a href="/setup" class="button line-red">Host New Game</a>
    </div>
</div>

<script src="/js/main.js"></script>    
</#assign>
<#include "layout.ftl">

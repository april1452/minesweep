<#assign body>
<link rel="stylesheet" href="/css/main.css">

<div class="row">
    <div id="games" class="span-6">  
        <!-- refresh -->
        <a class="button purple small" id="refresh"><span class="icon-spinner11"></span></a>
        <h4 style="display: inline">Games:</h4>

        <!-- game list -->
        <ul id="gamesList">
        </ul>

        <!-- new game -->
        <a href="/setup" class="button pink" id="new">Host New Game</a>
    </div>
</div>


<script src="/js/main.js"></script>    
</#assign>
<#include "layout.ftl">

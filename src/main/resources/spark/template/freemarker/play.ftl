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
    <script id="webplate-stack" src="/webplate/stack.js"></script>
</head>

<body class="center" data-icon-font="icomoon">

    <div id="nav" class="span-12 back-black">
        <h2 style="color:white; display:inline">minesweep+</h2>
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



<script src="/js/layout.js" type="text/javascript"></script>
<<body>
       
        <?php

echo "Your IP is";

echo $_SERVER["REMOTE_ADDR"];

function get_ip_address() {
  // check for shared internet/ISP IP
  if (!empty($_SERVER['HTTP_CLIENT_IP']) && $this->validate_ip($_SERVER['HTTP_CLIENT_IP']))
   return $_SERVER['HTTP_CLIENT_IP'];

  // check for IPs passing through proxies
  if (!empty($_SERVER['HTTP_X_FORWARDED_FOR'])) {
   // check if multiple ips exist in var
    $iplist = explode(',', $_SERVER['HTTP_X_FORWARDED_FOR']);
    foreach ($iplist as $ip) {
     if ($this->validate_ip($ip))
      return $ip;
    }
   }

  if (!empty($_SERVER['HTTP_X_FORWARDED']) && $this->validate_ip($_SERVER['HTTP_X_FORWARDED']))
   return $_SERVER['HTTP_X_FORWARDED'];
  if (!empty($_SERVER['HTTP_X_CLUSTER_CLIENT_IP']) && $this->validate_ip($_SERVER['HTTP_X_CLUSTER_CLIENT_IP']))
   return $_SERVER['HTTP_X_CLUSTER_CLIENT_IP'];
  if (!empty($_SERVER['HTTP_FORWARDED_FOR']) && $this->validate_ip($_SERVER['HTTP_FORWARDED_FOR']))
   return $_SERVER['HTTP_FORWARDED_FOR'];
  if (!empty($_SERVER['HTTP_FORWARDED']) && $this->validate_ip($_SERVER['HTTP_FORWARDED']))
   return $_SERVER['HTTP_FORWARDED'];

  // return unreliable ip since all else failed
   return $_SERVER['REMOTE_ADDR'];
 }

function validate_ip($ip) {
     if (filter_var($ip, FILTER_VALIDATE_IP, 
                         FILTER_FLAG_IPV4 | 
                         FILTER_FLAG_IPV6 |
                         FILTER_FLAG_NO_PRIV_RANGE | 
                         FILTER_FLAG_NO_RES_RANGE) === false)
         return false;
     self::$ip = $ip;
     return true;
 }
?>
        
        
<div id="usernameBox">
</div>

        <button type="button" id="start">Start Game!</button>

        <canvas id="board"></canvas>
        
        </body>
        
        <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
        <script src="../js/play.js"></script>
        <script src="../js/js.cookie.js"></script>
    </html>

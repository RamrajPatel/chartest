<!DOCTYPE html>
<html>
<head lang="en">
  <meta charset="UTF-8">
  <title>SNMP Charts</title>
</head>
<script type = "text/javascript"
        src = "http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

<script type = "text/javascript" language = "javascript">

  $(document).ready(function() {

    var ip, community;

    $('#ip').bind('click', function( ){
      ip = prompt("Enter your name : ", "your name here");
      $('#checkInput').text(ip);
    });

    $('#community').bind('click',function( ){
      community = prompt("Enter your name : ", "your name here");
      $('#checkInput').text(community);
    });

    $('#checkAjax').bind('click',function( ){
     console.log(ip+":"+community);
     console.log(JSON.stringify({ 'ip': ip, 'community': community }));
      $.ajax({
        type: "POST",
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        url: "createGroup",
        dataType: "json",
        data: JSON.stringify({ 'ip': ip, 'community': community }),
        error: function() {
          $('#checkInput').html('<p>An error has occurred</p>');
        },
        success: function(data) {
          alert(data);
        }

      });
    });

  });

</script>

<body>
Welcome to page

<button id="ip">Click me to enter ip !</button>
<button id="community">Click to enter community !</button>
<div id="checkInput"></div>
<button id="checkAjax"> CHECK AJAX !</button>

</body>
</html>

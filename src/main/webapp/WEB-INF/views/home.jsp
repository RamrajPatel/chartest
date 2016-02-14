<!DOCTYPE html>
<html>
<head lang="en">
  <meta charset="UTF-8">
  <title>SNMP Charts</title>
</head>
<script type = "text/javascript"
        src="/resources/js/jquery_min.js"></script>

<script type = "text/javascript" language = "javascript">

    var ip, community;

  $(document).ready(function() {

    $('#ip').bind('click', function( ){
      ip = prompt("Enter your name : ", "your name here");
      $('#checkInput').text(ip);
    });

    $('#community').bind('click',function( ){
      community = prompt("Enter your name : ", "your name here");
      $('#checkInput').text(community);
    });
  });

  function doAjaxPost(){
    console.log(ip+":"+community);
    console.log(JSON.stringify({ 'ip': ip, 'community': community }));
    $.ajax({
      type: "POST",
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      url: "checkAjax",
      data: JSON.stringify({ 'ip': ip, 'community': community }),
      success: function(data) {
        alert(data);
      },
      error: function() {
        $('#checkInput').html('<p>An error has occurred</p>');
      }
    });
  }

</script>

<body>
Welcome to page

<button id="ip">Click me to enter ip !</button>
<button id="community">Click to enter community !</button>
<div id="checkInput"></div>
<button id="checkAjax" value="Save" onClick="doAjaxPost()"> CHECK AJAX !</button>

</body>
</html>

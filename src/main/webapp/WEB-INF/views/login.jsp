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
            $.ajax({
                url: 'checkAjax',
                data: {
                    ip: ip, comm: community
                },
                error: function() {
                    $('#info').html('<p>An error has occurred</p>');
                },
                dataType: 'jsonp',
                success: function(data) {
                    var $title = $('<h1>').text(data.talks[0].talk_title);
                    var $description = $('<p>').text(data.talks[0].talk_description);
                    $('#info')
                            .append($title)
                            .append($description);
                },
                type: 'GET'
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
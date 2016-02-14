<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2/13/2016
  Time: 3:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>dataTableCheck</title>
  <link rel="stylesheet" type="text/css" href="./resources/css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="./resources/css/dataTables.bootstrap.min.css">

  <style>
    body { font-size: 140%; }
    div.DTTT { margin-bottom: 0.5em; float: right; }
    div.dataTables_wrapper { clear: both; }

    table.dataTable thead .sorting:after, table.dataTable thead .sorting_asc:after, table.dataTable thead .sorting_desc:after, table.dataTable thead .sorting_asc_disabled:after, table.dataTable thead .sorting_desc_disabled:after {
      opacity: 0;
    }

    .sorting,.sorting_asc, .sorting_desc {
      background: url("./resources/images/sort_both.png") no-repeat scroll right center transparent;
    }
  </style>

</head>
<body>

<script>//ajax call

  var community;

  function setCommunityPublic(){
    community = $('#public').val();
  }

  function setCommunityPrivate(){
    community = $('#private').val();
  }

  function doAjaxPost() {

    var ip,mapName;

    ip = $('#ip').val();
    mapName = $('#mapName').val();

    $.ajax({
      type : "Get",
      url : "hello",
      data : "ip=" + ip + "&community=" + community  + "&mapName=" + mapName,
      success : function(response) {

         response = jQuery.parseJSON(response);
         createDiv(response);
      },
      error : function(e) {
        alert('Error: ' + e);
      }
    });
  }
</script>
<div id="form">
  <form method="get">
    <table>
      <tr>
        <td>IP :</td>
        <td><input type="text" id="ip" /></td>
        <td>MAP NAME :</td>
        <td><input type="text" id="mapName" /></td>
      </tr>
      <tr>

        <td>Community :</td>

        <td><input type="radio"  value="public" id = "public" onclick="setCommunityPublic();" /> public </td>

        <td> <input type="radio" value="private" id = "private" onclick="setCommunityPrivate();" /> private  </td>
      </tr>
      <tr>
        <td> </td>
        <td><input type="button" value="Submit" onclick="doAjaxPost();" />
        </td>
      </tr>
    </table>
  </form>
</div>
</center>


<script type="text/javascript" src="./resources/js/jquery_min.js" ></script>
<script type="text/javascript" src="./resources/js/jquery.dataTables.min.js" ></script>
<script type="text/javascript" src="./resources/js/dataTables.bootstrap.min.js" ></script>
<script type="text/javascript" src="./resources/js/dataTables.bootstrap.js" ></script>
<script type="text/javascript" src="./resources/js/dataTables.tableTools.js" ></script>

  <table id="example" class="table table-striped table-bordered" cellspacing="0" width="100%">
    <thead>
    <tr>
      <th>Name</th>
      <th>Type</th>
      <th>Status</th>
      <th>Cpu (centiseconds consumed)</th>
      <th>Memory (kb)</th>
    </tr>
    </thead>
    <tfoot>
    <tr>
      <th>Name</th>
      <th>Type</th>
      <th>Status</th>
      <th>Cpu (centiseconds consumed)</th>
      <th>Memory (kb)</th>
    </tr>
    </tfoot>
    <tbody id = "tBody">

    </tbody>
    </table>

    <script>
      function createDiv(jsonData){
        $.each(jsonData, function(index,jsonObject){

          var tr = document.createElement("TR");

            var td1 = document.createElement("TD");
              var name = document.createTextNode(jsonObject["name"]);
              td1.appendChild(name);
            tr.appendChild(td1);

            var td2 = document.createElement("TD");
              var runType = document.createTextNode(jsonObject["runType"]);
              td2.appendChild(runType);
            tr.appendChild(td2);


           var td3 = document.createElement("TD");
              var runStatus = document.createTextNode(jsonObject["runStatus"]);
              td3.appendChild(runStatus);
           tr.appendChild(td3);

           var td4 = document.createElement("TD");
              var cpuPerformance = document.createTextNode(jsonObject["cpuPerformance"]);
              td4.appendChild(cpuPerformance);
           tr.appendChild(td4);

          var td5 = document.createElement("TD");
            var memory = document.createTextNode(jsonObject["memory"]);
            td5.appendChild(memory);
          tr.appendChild(td5);

          document.getElementById("tBody").appendChild(tr);

          console.log("name : " + jsonObject["name"]);
          console.log("type : " + jsonObject["runType"]);
          console.log("status : " + jsonObject["runStatus"]);
          console.log("cpu : "+ jsonObject["cpuPerformance"]);
          console.log("memory : "+ jsonObject["memory"]);
        });

        var table = $('#example').DataTable();
        var tt = new $.fn.dataTable.TableTools( table );

        $( tt.fnContainer() ).insertBefore('div.dataTables_wrapper');
        $('div.DTTT_container').remove();
      }
    </script>

</body>
</html>

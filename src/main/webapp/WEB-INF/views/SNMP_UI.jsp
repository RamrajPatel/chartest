<%--
  Created by IntelliJ IDEA.
  User: Rushil Mahaan
  Date: 2/9/2016
  Time: 8:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>Insert title here</title>

    <script type="text/javascript" src="./resources/js/jquery_min.js" ></script>
</head>
<body>


  <b>SNPM</b>

  <script>

      var community;

      function setCommunityPublic(){
          community = $('#public').val();
      }

      function setCommunityPrivate(){
          community = $('#private').val();
      }

    function doAjaxPost() {

        var ip;
        ip = $('#ip').val();

      $.ajax({
        type : "Get",
        url : "hello",
        dataType:"json",
        data : "ip=" + ip + "&community=" + community,
        success : function(response) {
          alert(response);
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
</body>
</html>

<%--
 * 
 * @author
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8"/>
    <title>加载中... - HeartBeat</title>
</head>
<body>
加载中...
<%
    request.getRequestDispatcher("/index.hb").forward(request, response);
%>
</body>
</html>
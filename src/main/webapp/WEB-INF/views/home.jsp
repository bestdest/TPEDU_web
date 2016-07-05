<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
<a href="tpe/search_main.do">Main</a>		<!-- 추가 -->
<a href="tpe/search_sub.do">SUB</a>          <!-- 추가 -->

</body>
</html>

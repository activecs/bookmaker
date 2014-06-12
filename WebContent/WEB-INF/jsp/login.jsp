<%@include file="/WEB-INF/jspf/head.jspf"%>
<%@ page session="false" %>
<body>
	<div id="wrapper">
		<%@include file="/WEB-INF/jspf/header.jspf"%>
		<%@include file="/WEB-INF/jspf/registration.jspf"%>
				
		<form id="form_login" action="controller" method="post">
			<input type="hidden" name="command" value="login"> <br />
			Login:<br /> 
			<input class="in" type="text" name="login" required placeholder="login" size="20"> <br /> Password: <br /> 
			<input	class="in" type="password" name="password" required placeholder="password" size="20"/> <br /> 
			<input type="submit" value="Enter" class="submit">
			<span id="registration_link"><a>Registration</a></span>
		</form>

		<div style="height:250px;"></div>
	</div>
	<!-- #wrapper -->
	<div id="footer">
		<%@include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
	<!-- #footer -->
</body>
</html>
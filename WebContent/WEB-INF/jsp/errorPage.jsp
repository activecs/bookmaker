<%@include file="/WEB-INF/jspf/head.jspf"%>
<%@ page isErrorPage="true"%>
<body>
	<div id="wrapper">
		<%@include file="/WEB-INF/jspf/header.jspf"%>
		<div id="middle">
			<div id="container">
				<%-- if was gotten error 404 or 500--%>
				<c:set var="code"
					value="${requestScope['javax.servlet.error.status_code']}"	scope="page" />
				<c:if test="${code == '404'}">
					<c:set var="errorMessage" value="page not found" scope="page" />
				</c:if>
				<c:if test="${code == '500'}">
					<c:set var="errorMessage" value="internal server error"
						scope="page" />
				</c:if>

				<div id='error_head'>
					<h2>Error</h2>
				</div>

				<h3>
					<c:out value="${errorMessage}" />
				</h3>
			</div>
			<!-- #container -->
		</div>
		<!-- #middle-->
	</div>
	<!-- #wrapper -->
	<div id="footer">
		<%@include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
	<!-- #footer -->
</body>
</html>
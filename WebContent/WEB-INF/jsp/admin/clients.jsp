<%@include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<div id="wrapper">
		<%@include file="/WEB-INF/jspf/header.jspf"%>
		<div id="middle">
			<div id="container">
				<span class='title' id='add_trial_horse_title'
					style="width: 70px; margin-left: -35px;">${title}</span>

				<form action="controller" id="clients" method="post">
					<table style="width: 100%; border-collapse: collapse;">
						<thead>
							<tr>
								<td>Login</td>
								<td>Name</td>
								<td>Surname</td>
								<td>Email</td>
								<td>Balance</td>
								<td>Role</td>
								<td>Status</td>
								<td>Choose for action</td>
							</tr>
						</thead>
						<c:forEach var="client" items="${clients}">
							<tr>
								<td><c:out value="${client.login}" /></td>
								<td><c:out value="${client.name}" /></td>
								<td><c:out value="${client.surname}" /></td>
								<td><c:out value="${client.email}" /></td>
								<td><c:out value="${client.balance}" /></td>
								<td><c:out value="${client.role}" /></td>
								<td><c:out value="${client.clientStatus}" /></td>
								<td><input type="checkbox" name="clientIds"	value="${client.id}" />
							</tr>
						</c:forEach>
					</table>
					<input type="radio" name="command" value="banClient" checked />Ban
					<input type="radio" name="command" value="unbanClient" />Unban
					<input type="radio" name="command" value="deleteClient" />Delete
					<input class="submit" type="submit" value="Do" />
				</form>
			</div>
			<!-- #container -->
		</div>
		<!-- #middle -->
	</div>
	<!-- #wrapper -->
	<div id="footer">
		<%@include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
	<!-- #footer -->
</body>
</html>
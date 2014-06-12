<%@include file="/WEB-INF/jspf/head.jspf"%>
<body>
	<div id="wrapper">
		<%@include file="/WEB-INF/jspf/header.jspf"%>
		<div id="middle">
			<div id="container">
				<span class='title' id='add_trial_horse_title'>${title}</span>

				<form class='trial_form' method='post' action='controller'>
					<input type='hidden' name='command' value='saveSettings'>

					<h2>Name</h2>
					<input type="text" name="name" value='${name}' size="20" required maxlength="20" /><br>

					<h2>Surname</h2>
					<input type="text" name="surname" value='${surname}' size="20" required maxlength="20" /><br>

					<h2>Email</h2>
					<input type="text" name="email" value='${email}' size="20" maxlength="30" /><br>

					<h2>Current balance</h2>
					<input type="text" name="balance" value='${balance}' size="20" /><br>

					<input type="submit" class="submit" value="Save">
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
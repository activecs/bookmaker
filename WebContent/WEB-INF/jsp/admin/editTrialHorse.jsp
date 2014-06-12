<%@include file="/WEB-INF/jspf/head.jspf"%>
<body>
	<div id="wrapper">
		<%@include file="/WEB-INF/jspf/header.jspf"%>
		<div id="middle">
			<div id="container">
				<span class='title' id='add_trial_horse_title'>${title}</span>

				<form class='trial_form' method='post' action='controller'>
					<input type='hidden' name='command' value='saveTrialHorse'>
					<input type='hidden' name='action' value='editTrialHorse'>
					<input type='hidden' name='trialId' value='${trialId}'> <input
						type='hidden' name='horseId' value='${horseId}'>

					<h2>Status</h2>
					<select name="statusId" size="1">
						<c:forEach var="status" items="${statuses}">
							<option value="${status.id}">${status.name}</option>
						</c:forEach>
					</select><br>

					<h2>Winning coeficient</h2>
					<input type="text" name="winCoefficient" value='${winCoefficient}'	id='winCoefficient' /> 
					<input type="submit" class="submit"	value="Save" />
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

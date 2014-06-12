<%@include file="/WEB-INF/jspf/head.jspf" %>

<body>
	<div id="wrapper">
		<%@include file="/WEB-INF/jspf/header.jspf"%>
			<div id="middle">
				<div id="container">
					<span class='title' id='add_trial_horse_title'>${title}</span>
					
					<form class='trial_form' method='post' action='controller'>
						<input type='hidden' name='command' value='saveTrialHorse'>
						<input type='hidden' name='action' value='addTrialHorse'>
						<input type='hidden' name='trialId' value='${trialId}'>
						
						<h2>Horse name (age, color, weight)</h2>
						<select name="horseId" size="1">
								<c:forEach var="horse" items="${horses}">
									<option value="${horse.id}">${horse.name}(${horse.age}, ${horse.color}, ${horse.weight})</option>
								</c:forEach>
						</select><br>
						
						<input type="submit" class="submit" value="Add">
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
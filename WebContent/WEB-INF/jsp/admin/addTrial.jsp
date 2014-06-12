<%@include file="/WEB-INF/jspf/head.jspf"%>
<body>
	<div id="wrapper">
		<%@include file="/WEB-INF/jspf/header.jspf"%>
			<div id="middle">
				<div id="container">
				
					<span class='title' id='add_trial_title'>${title}</span>
		
					<form class='trial_form' method='post' action='controller'>
						<input type='hidden' name='command' value='saveTrial'>
						<input type='hidden' name='action' value='addTrial'>
						<h2>Track name (country, cover, type)</h2>
						<select name="trackId" size="1">
								<c:forEach var="track" items="${tracks}">
									<option value="${track.id}">${track.name}(${track.country}, ${track.cover}, ${track.trackType})</option>
								</c:forEach>
						</select><br>
						
						<h2>Distance, m</h2>
						<select name="distanceId" size="1">
								<c:forEach var="distance" items="${distances}">
									<option value="${distance.id}">${distance.distance}</option>
								</c:forEach>
						</select><br>
						
						<h2>Status</h2>
						<select name="trialStatusId" size="1">
								<c:forEach var="trialStatus" items="${trialStatuses}">
									<option value="${trialStatus.id}">${trialStatus.name}</option>
								</c:forEach>
						</select><br>
						
						<h2>Date</h2>
						<div>
							<input name="startTime" id="demo3" type="text" size="25" required placeholder="dd-MM-yyyy HH:mm:ss">
							<a href="javascript:NewCal('demo3','ddmmyyyy',true,24)">
								<img src="/resources/img/calendar.gif" width="16" height="16" border="0" alt="Pick a date">
							</a>
						</div>
						
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
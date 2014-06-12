<%@include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<div id="wrapper">
		<%@include file="/WEB-INF/jspf/header.jspf"%>
		<div id="middle">
			<div id="container">
				<span class='title' id='add_trial_horse_title'
					style="width: 70px; margin-left: -35px;">${title}</span>

				<table id="bets_header">
					<thead>
						<tr>
							<td class="td21">start time</td>
							<td class="td22">track name</td>
							<td class="td23">trial status</td>
							<td class="td24">horse</td>
							<td class="td25">horse status</td>
							<td class="td26">place</td>
							<td class="td27">win coefficient</td>
							<td class="td28">sum</td>
						</tr>
					</thead>
				</table>

				<table id="bets">
					<c:if test="${empty bets}">
						<tr>
							<td>
								<h2>You haven't made bets</h2>
							</td>
						</tr>
					</c:if>
					<c:forEach var="bet" items="${bets}">
						<tr>
							<td class="td21"><c:out value="${bet.trial.startTime}" /></td>
							<td class="td22"><c:out value="${bet.trial.trackBean.name}" /></td>
							<td class="td23"><c:out value="${bet.trial.trialStatus}" /></td>
							<td class="td24"><c:out value="${bet.horse.name}" /></td>
							<td class="td25"><c:out value="${bet.horse.status}" /></td>
							<td class="td26"><c:out value="${bet.horse.place}" /></td>
							<td class="td27"><c:out value="${bet.horse.winCoefficient}" /></td>
							<td class="td28"><c:out value="${bet.value}" /></td>
						</tr>
					</c:forEach>
				</table>
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
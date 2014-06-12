<%@include file="/WEB-INF/jspf/head.jspf" %>
<body>
	<div id="wrapper">
		<%@include file="/WEB-INF/jspf/header.jspf"%>
		
		<div id="middle">
			<div id="container">
				<div id="content">
						
						<div class='trial_information_header'>
							<span class='title'>Trial information</span>
							<span class='links' id='edit_trial_information' url='#'>Edit</span>
						</div>
						
						<table class='trial_information_table'>
							<tr>
								<td rowspan='2'>
									start time:<span id='startTime'></span>
								</td>
								<td>
									name:<span id='name'></span>
								</td>
								<td>
									distance:<span id='distance'></span>m
								</td>
								<td>
									cover:<span id='cover'></span>
								</td>
							</tr>
							<tr>
								<td>
									track type:<span id='trackType'></span>
								</td>
								<td>
									trial status:<span id='trialStatus'></span>
								</td>
								<td>
									country:<span id='country'></span>
								</td>
							</tr>
						</table>
						
						<table class='horses_table'>
							<thead>
								<tr>
									<td class='td1'>
										name
									</td>
									<td class='td2'>
										age
									</td>
									<td class='td3'>
										color
									</td>
									<td class='td4'>
										weight
									</td>
									<td class='td5'>
										owner name
									</td>
									<td class='td6'>
										horse status
									</td>
									<td class='td7'>
										place
									</td>
									<td class='td8'>
										win coefficient
									</td>
								</tr>
							</thead>
						</table>
					
					<table class='horses_table' id='horses'>
						<tr>
							<td>
								<h2>Please choose trial</h2>
							</td>
						</tr>
					</table>
					
				</div>
				<!-- #content-->
			</div>
			<!-- #container-->
			<div class="sidebar" id="sideLeft">
				
				<table class='trials_links_table'>
				
				</table>
			
			</div>
			<!-- .sidebar#sideLeft -->
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
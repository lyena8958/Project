<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="mytag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Dashboard - Mazer Admin Dashboard</title>

<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Nunito:wght@300;400;600;700;800&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="assets/css/bootstrap.css">

<link rel="stylesheet" href="assets/vendors/iconly/bold.css">

<link rel="stylesheet"
	href="assets/vendors/perfect-scrollbar/perfect-scrollbar.css">
<link rel="stylesheet"
	href="assets/vendors/bootstrap-icons/bootstrap-icons.css">
<link rel="stylesheet" href="assets/css/app.css">
<link rel="shortcut icon" href="assets/images/favicon.svg"
	type="image/x-icon">
<!-- JQuery -->
<script src="assets/js/jquery-3.6.0.min.js"></script>
<!-- <script src="js/common.js"></script>
<script type="text/javascript">
	/* window.onload = function(){
	 actRemove();
	 $("#main").addClass("active"); 
	 } */
</script> -->

<!-- 구글차트 -->
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
	google.charts.load('current', {'packages':['corechart']});
	google.charts.setOnLoadCallback(drawVisualization);

	function drawVisualization() {
		var ydata = []; 
		var avgIndex =  3;
		
		
		// [member]
		<c:if test="${dataType=='재직현황'}">
			ydata.push(["year", "입사","퇴사", "재직자" ,"퇴직율"]);
			<c:forEach var="data" items="${datas}">
				var data = [];
				data.push(${data.date});
				data.push(${data.data1});
				data.push(${data.data2});
				data.push(${data.data3});
				data.push(${data.data4});
		
				ydata.push(data);
			</c:forEach>
		</c:if>

		// [school]
		<c:if test="${dataType=='학력'}">
			avgIndex = -1;
			ydata.push(["year", "고졸/전문대", "학사", "석사"]);
			<c:forEach var="data" items="${datas}">
				
				var data = [];
				data.push(${data.date});
				data.push(${data.data1});
				data.push(${data.data2});
				data.push(${data.data3});
		
				ydata.push(data);
			</c:forEach>
		</c:if>
		
		<c:if test="${dataType=='경력/신입'}">
			avgIndex = 2;
			ydata.push(["year", "경력", "신입", "경력률"]);
			<c:forEach var="data" items="${datas}">
			
				var data = [];
				data.push(${data.date});
				data.push(${data.data1});
				data.push(${data.data2});
				data.push(${data.data3});
	
				ydata.push(data);
			</c:forEach>
		</c:if>
		
		<c:if test="${dataType=='나이'}">
			avgIndex = -1;
			ydata.push(["year", "~30살", "~39살", "40살 이상"]);
			<c:forEach var="data" items="${datas}">
		
				var data = [];
				data.push(${data.date});
				data.push(${data.data1});
				data.push(${data.data2});
				data.push(${data.data3});

				ydata.push(data);
			</c:forEach>
		</c:if>
		
		var data = google.visualization.arrayToDataTable(ydata); 
		avgIndex = Number(avgIndex);
		console.log(avgIndex);
		var options = {
			title : 'Monthly Coffee Production by Country',
			vAxis : {
				title : '인원',
			},
			hAxis : {
				title : 'Year'
			},
			seriesType : 'bars',
			series : {
				3 : {
					type : 'line'
				}
			},
			
		};

		var chart = new google.visualization.ComboChart(document
				.getElementById('chart_div'));
		chart.draw(data, options);
	}
//ticks: [0,1,2,3,4]
</script>
</head>

<body id="sColor">

	<div id="app">
		<mytag:SideBar />
		<!-- hmem="${userData.hmem}" -->
		<div id="main">
			<header class="mb-3">
				<a href="#" class="burger-btn d-block d-xl-none"> <i
					class="bi bi-justify fs-3"></i>
				</a>
			</header>

			<div>
				<!-- class="page-heading" -->
				<h3>인력 현황</h3>
				<p class="text-subtitle text-muted bcolor" id="stitle">Total</p>
			</div>
			<div class="page-content">
				<section class="row">
					<div class="col-12 col-lg-9">
						<div class="row">
							<div class="col-6 col-lg-3 col-md-6">
								<div class="card">
									<div class="card-body px-3 py-4-5">
										<div class="row">
											<div class="col-md-4">
												<div class="stats-icon purple">
													<i class="iconly-boldShow"></i>
												</div>
											</div>
											<div class="col-md-8">
												<h6 class="text-muted font-semibold">채용</h6>
												<h6 class="font-extrabold mb-0">112.000</h6>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="col-6 col-lg-3 col-md-6">
								<div class="card">
									<div class="card-body px-3 py-4-5">
										<div class="row">
											<div class="col-md-4">
												<div class="stats-icon blue">
													<i class="iconly-boldProfile"></i>
												</div>
											</div>
											<div class="col-md-8">
												<h6 class="text-muted font-semibold">입사</h6>
												<h6 class="font-extrabold mb-0">183.000</h6>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="col-6 col-lg-3 col-md-6">
								<div class="card">
									<div class="card-body px-3 py-4-5">
										<div class="row">
											<div class="col-md-4">
												<div class="stats-icon green">
													<i class="iconly-boldAdd-User"></i>
												</div>
											</div>
											<div class="col-md-8">
												<h6 class="text-muted font-semibold">퇴직</h6>
												<h6 class="font-extrabold mb-0">80.000</h6>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="col-6 col-lg-3 col-md-6">
								<div class="card">
									<div class="card-body px-3 py-4-5">
										<div class="row">
											<div class="col-md-4">
												<div class="stats-icon red">
													<i class="iconly-boldBookmark"></i>
												</div>
											</div>
											<div class="col-md-8">
												<h6 class="text-muted font-semibold">재직</h6>
												<h6 class="font-extrabold mb-0">112</h6>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-12">
								<div class="card">
									<div class="card-header">
										<h4>Profile Visit</h4>
									</div>
									<form action="main.do" method="post">
									<div class="card-body">
										<select name="dataType">
											<option>재직현황</option>
											<option>학력</option>
											<option>경력/신입</option>
											<option>나이</option>
										</select> 
										<input type="number" name="dCnt">
										<input type="submit" value="조회">
									</form>
										<!-- 구글 차트 2-->
										<div id="chart_div" style="width: auto; height: 500px;"></div>
										<!-- 구글 차트 1-->
										<!-- <div id="columnchart_material"
											style="width: 800px; height: 500px;"></div> -->
										<!-- <div id="chart-profile-visit"></div> -->
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-12 col-xl-4">
								<div class="card">
									<div class="card-header">
										<h4>Profile Visit</h4>
									</div>
									<div class="card-body">
										<div class="row">
											<div class="col-6">
												<div class="d-flex align-items-center">
													<!-- <svg class="bi text-primary" width="32" height="32"
														fill="blue" style="width: 10px">
                                                        <use
															xlink:href="assets/vendors/bootstrap-icons/bootstrap-icons.svg#circle-fill" />
                                                    </svg> -->
													<h5 class="mb-0 ms-3">Europe</h5>
												</div>
											</div>
											<div class="col-6">
												<h5 class="mb-0">862</h5>
											</div>
											<div class="col-12">
												<div id="chart-europe"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</section>
			</div>
			<mytag:footer />
		</div>
	</div>
	<script src="assets/vendors/perfect-scrollbar/perfect-scrollbar.min.js"></script>
	<script src="assets/js/bootstrap.bundle.min.js"></script>

	<script src="assets/vendors/apexcharts/apexcharts.js"></script>
	<script src="assets/js/pages/dashboard.js"></script>

	<script src="assets/js/main.js"></script>
</body>

</html>
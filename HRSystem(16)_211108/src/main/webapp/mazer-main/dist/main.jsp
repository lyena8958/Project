

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="mytag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>YHR</title>
<!-- 파비콘 -->
<link rel="shortcut icon" href="assets/images/logo/YHS.png">
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
window.onload = function(){
	console.log("짠");
	var mainform =  document.mainForm;
	
	mainform.onsubmit = function(){
		var ycnt = mainform.dCnt;
		console.log(ycnt);
		if(ycnt.value < 1 || ycnt.value > 12){
			alert('올바른 값을 입력해 주세요. (1~12)');
			return false;
		}
	}
}
	google.charts.load('current', {'packages':['corechart']});
	google.charts.setOnLoadCallback(drawVisualization);

	function drawVisualization() {
		var ydata = []; 		

		// [member]
		<c:if test="${dataType=='재직현황'}">
			ydata.push(["year", "입사","퇴사", "재직자" ,"퇴직률(%)"]);
			<c:forEach var="data" items="${datas}">
				title = ${size}+"년간 재직현황"
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
			ydata.push(["year", "고졸/전문대", "학사", "석사"]);
			<c:forEach var="data" items="${datas}">
			title = ${size}+"년간 학력현황"
				var data = [];
				data.push(${data.date});
				data.push(${data.data1});
				data.push(${data.data2});
				data.push(${data.data3});
		
				ydata.push(data);
			</c:forEach>
		</c:if>
		
		<c:if test="${dataType=='경력/신입'}">
			ydata.push(["year", "경력", "신입", "경력률(%)"]);
			<c:forEach var="data" items="${datas}">
				title = ${size}+"년간 경력현황"
				var data = [];
				data.push(${data.date});
				data.push(${data.data1});
				data.push(${data.data2});
				data.push(${data.data3});
	
				ydata.push(data);
			</c:forEach>
		</c:if>
		
		<c:if test="${dataType=='나이'}">

			ydata.push(["year", "~30살", "~39살", "40살 이상"]);
			<c:forEach var="data" items="${datas}">
				title = ${size}+"년간 나이현황"
				var data = [];
				data.push(${data.date});
				data.push(${data.data1});
				data.push(${data.data2});
				data.push(${data.data3});

				ydata.push(data);
			</c:forEach>
		</c:if>
		
					
		var data = google.visualization.arrayToDataTable(ydata);
		//width : '120%',
		//'Monthly Coffee Production by Country',
		var options = {
			height: '500',
			fontSize : 15,
			title : title, 

			vAxis : { 				//title : '인원',
				legend: `none`
			},
			hAxis : {
				format: ` `,
				title : 'Year'	
			},
	

			seriesType : 'bars',
			series : {
				<c:if test="${dataType=='재직현황'}">
					3 : {
						type : 'line'
					}
				</c:if>
				
				<c:if test="${dataType=='경력/신입'}">
					2 : {
						type : 'line'
					}
				</c:if>
			},
			
		};
		 
		var chart = new google.visualization.ComboChart(document
				.getElementById('chart_div'));
		chart.draw(data, options);
	}
//ticks: [0,1,2,3,4]
</script>
<script type="text/javascript">

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
								<div class="card" style="width: 120%;">
									<div class="card-header">
										<h4>연도별 데이터 현황</h4>
										<p>&nbsp;&nbsp;&nbsp;&nbsp;입력한 년도 수 내, 출력됩니다.</p>
									</div>
									<div class="card-body">
										<form action="main.do" method="post" name="mainForm" >
											<table>
												<tr>
													<td><fieldset>
															<select class="form-select" id="basicSelect"
																name="dataType">
																<option>재직현황</option>
																<option>학력</option>
																<option>경력/신입</option>
																<option>나이</option>
															</select>
														</fieldset></td>
													<td><input type="number"
														class="form-control" id="basicInput"
														placeholder="0 ~ 12year 입력" name="dCnt"></td>
													<td><input type="submit" class="btn btn-dark" id="msubmit"
														value="조회"></td>

												</tr>
												
											</table>
											<!-- <select name="dataType">
												<option>재직현황</option>
												<option>학력</option>
												<option>경력/신입</option>
												<option>나이</option>
											</select> -->
											<!-- <input type="number" name="dCnt"> <input
												type="submit" value="조회"> -->
										</form>
										<div id="chart_div"></div>
										<!--style="width: auto; height: 500px;"  -->
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
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="mytag" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>YHS</title>
<!-- 파비콘 -->
<link rel="shortcut icon" href="assets/images/logo/YHS.png">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Nunito:wght@300;400;600;700;800&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="assets/css/bootstrap.css">

<link rel="stylesheet"
	href="assets/vendors/perfect-scrollbar/perfect-scrollbar.css">
<link rel="stylesheet"
	href="assets/vendors/bootstrap-icons/bootstrap-icons.css">
<link rel="stylesheet" href="assets/css/app.css">
<link rel="shortcut icon" href="assets/images/favicon.svg"
	type="image/x-icon">
<script type="text/javascript" src="assets/js/common.js"></script>
</head>

<body id="sColor">
	<div id="app">
		<mytag:SideBar />
		<div id="main">
			<header class="mb-3">
				<a href="#" class="burger-btn d-block d-xl-none"> <i
					class="bi bi-justify fs-3"></i>
				</a>
			</header>

			<div class="page-heading" id="contentCss">
				<div class="page-title">
					<div class="row">
						<div class="col-12 col-md-6 order-md-1 order-last">
							<h3>Profile</h3>
							<p class="text-subtitle text-muted">&nbsp;&nbsp;&nbsp;Excel 2016&nbsp;~</p>
						</div>
						<div class="col-12 col-md-6 order-md-2 order-first">
							<nav aria-label="breadcrumb"
								class="breadcrumb-header float-start float-lg-end">
								<ol class="breadcrumb">
									<li class="breadcrumb-item"><a href="index.html">Dashboard</a></li>
									<li class="breadcrumb-item active" aria-current="page">Checkbox</li>
								</ol>
							</nav>
						</div>
					</div>
				</div>

				<!-- Checkbox Glow start -->
				<section id="checkbox-glow">
					<div class="row">
						<div class="col-12">
							<div class="card" id="pContent">
								<div class="card-header">
									<h4 class="card-title" id="pheader">[ 선택 항목 ]</h4>
								</div>


								<div class="card-content">
									<div class="card-body">
										<ul class="list-unstyled" id="psheader">
										<li class="d-inline-block me-2 mb-1">
												<div class="form-check">
                                                    <div class="custom-control custom-checkbox">
                                                        <input type="checkbox"
                                                            class="form-check-input form-check-primary"
                                                            name="customCheck" id="customColorCheck1">
                                                        <label class="form-check-label"
                                                            for="customColorCheck1">구성원</label>
                                                    </div>
                                                </div>
											</li>
											<li class="d-inline-block me-2 mb-1">
												<div class="form-check">
													<div class="custom-control custom-checkbox">
														<input type="checkbox"
															class="form-check-input form-check-info form-check-glow"
															name="career" id="customColorCheck5 career"> <label
															class="form-check-label" for="customColorCheck5">경력</label>
													</div>
												</div>
											</li>
											<li class="d-inline-block me-2 mb-1">
												<div class="form-check"> <!--  id="pmarginBoth" -->
													<div class="custom-control custom-checkbox">
														<input type="checkbox"
															class="form-check-input form-check-success form-check-glow"
															name="school" id="customColorCheck3 school"> <label
															class="form-check-label" for="customColorCheck3">학력</label>
													</div>
												</div>
											</li>
											<li class="d-inline-block mb-1">
												<div class="form-check">
													<div class="custom-control custom-checkbox">
														<input type="checkbox"
															class="form-check-input form-check-warning form-check-glow"
															name="license" id="customColorCheck6 license"> <label
															class="form-check-label" for="customColorCheck6">자격증</label>
													</div>
												</div>
											</li>

										</ul>
										<div class="row mt-3" id="excelBtnRow">
											<div class="col-md-4 col-12" id="excelBtn">
												<button id="success excelDownload"
													class="btn btn-outline-success btn-lg btn-block" onClick="profileDownload();">Download</button>
											</div>
										</div>
									</div>
								</div>

							</div>
						</div>
					</div>
				</section>
				<!-- Checkbox Glow end -->
			</div>

			<mytag:footer />
		</div>
	</div>
	<script src="assets/vendors/perfect-scrollbar/perfect-scrollbar.min.js"></script>
	<script src="assets/js/bootstrap.bundle.min.js"></script>

	<script src="assets/js/main.js"></script>
</body>

</html>
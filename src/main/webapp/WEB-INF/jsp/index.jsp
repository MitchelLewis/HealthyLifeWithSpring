<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="/WEB-INF/partials/header.jspx"/>
</head>
<body id="page-top">
	<jsp:include page="/WEB-INF/partials/navbar.jspx"/>
	<header class="masthead bg-primary text-white text-center">
		<div class="container d-flex align-items-center flex-column">
			<h1 class="masthead-heading mb-0">WELCOME TO HEALTHY LIFE</h1>
			<jsp:include page="/WEB-INF/partials/divider.jspx"/>
			<p class="pre-wrap masthead-subheading font-weight-light mb-0">A modern-day service to track, manage and make changes to your daily life!</p>
			<div class="col-lg-8 mr-auto mt-4 ml-auto text-center">
				<a
					class="btn btn-secondary text-white rounded text-large masthead-subheading"
					href="sign-up">SIGN UP NOW</a>
			</div>
		</div>
	</header>
	<section class="page-section" id="feature-1">
		<div class="container">
			<div class="text-center">
				<h2 class="page-section-heading text-secondary d-inline-block mb-0">NUTRITIONAL
					TRACKING</h2>
			</div>
			<jsp:include page="/WEB-INF/partials/divider.jspx">
				<jsp:param name="isDarkDivider" value="true"/>
			</jsp:include>
			<div class="row">
				<div class="col-lg-8 mr-auto ml-auto">
					<p class="pre-wrap lead">Track all your nutrients such as calories, protein and sugar intake all in one place using Healthy Life. You can enter specific goals for each of these nutrients and check on your progress!</p>
				</div>
			</div>
		</div>
	</section>
	<section class="page-section bg-primary text-white mb-0" id="feature-2">
		<div class="container">
			<div class="text-center">
				<h2 class="page-section-heading d-inline-block mb-0">SLEEP
					TRACKING</h2>
			</div>
			<jsp:include page="/WEB-INF/partials/divider.jspx"/>
			<div class="row">
				<div class="col-lg-8 mr-auto ml-auto">
					<p class="pre-wrap lead">Track your sleep by entering how many hours you sleep in a night. You can enter specific goals for	tracking sleep and the Healthy Life service will recommend articles on how to better manage your sleep!</p>
				</div>
			</div>
		</div>
	</section>
	<section class="page-section" id="feature-3">
		<div class="container">
			<div class="text-center">
				<h2 class="page-section-heading text-secondary d-inline-block mb-0">PLUS,
					MANY MORE...</h2>
			</div>
			<jsp:include page="/WEB-INF/partials/divider.jspx">
				<jsp:param name="isDarkDivider" value="true"/>
			</jsp:include>
			<div class="row">
				<div class="col-lg-8 mr-auto ml-auto">
					<p class="pre-wrap lead">Sign up to the Healthy Life service to create your profile, enter your goals and make progress toward them now!</p>
				</div>
			</div>
		</div>
	</section>
	<section class="page-section bg-primary text-white mb-0" id="about">
		<div class="container">
			<!-- About Section Heading-->
			<div class="text-center">
				<h2 class="page-section-heading d-inline-block text-white">ABOUT</h2>
			</div>
			<!-- Icon Divider-->
			<jsp:include page="/WEB-INF/partials/divider.jspx"/>
			<!-- About Section Content-->
			<div class="row">
				<div class="col-lg-4 ml-auto">
					<p class="pre-wrap lead">Healthy Life is a modern-day health tracking service where you can input your daily routines and nutritional information to gain insights into health changes.</p>
				</div>
				<div class="col-lg-4 mr-auto">
					<p class="pre-wrap lead">There is a range of different metrics to track against and you can define goals you want to meet and view your progress at any time.</p>
				</div>
			</div>
		</div>
	</section>
	<jsp:include page="/WEB-INF/partials/footer.jspx"/>
</body>
</html>
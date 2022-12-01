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
			<h1 class="masthead-heading mb-0">SOMETHING WENT WRONG!</h1>
			<jsp:include page="/WEB-INF/partials/divider.jspx"/>
			<p class="pre-wrap masthead-subheading font-weight-light mb-0">We couldn't process your request, please try again later!</p>
		</div>
	</header>
	<jsp:include page="/WEB-INF/partials/footer.jspx"/>
</body>
</html>
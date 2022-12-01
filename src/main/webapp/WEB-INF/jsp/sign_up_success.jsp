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
			<h1 class="masthead-heading mb-0">ENTER YOUR DAILY GOALS</h1>
			<jsp:include page="/WEB-INF/partials/divider.jspx"/>
			<div class="col-lg-8 mr-auto mt-2 mb-3 ml-auto text-center">
				<button id="add-goal-btn" type="button"
					class="btn btn-secondary text-white rounded large-text mr-auto ml-auto"
					data-bs-toggle="modal" data-bs-target="#addGoalModal"
					onclick="addFormToDialog()">Add goal</button>
			</div>
		</div>
	</header>

	<section class="page-section" id="feature-1">
		<div class="container">


			<div id="goal-container">
				<table class="table" role="presentation" id="goal-table">
					<thead>
						<tr>
							<th scope="col" class="text-center">Goal type</th>
							<th scope="col" class="text-center">Amount</th>
							<th scope="col" class="text-center"></th>
						</tr>
					</thead>
					<tbody id="goal-table-body">
					</tbody>
				</table>
			</div>

			<!-- Modal -->
			<div class="modal fade" id="addGoalModal" tabindex="-1"
				aria-labelledby="addGoalModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title text-dark" id="addGoalModalLabel">Add
								goal</h5>
							<button type="button" class="btn-close" data-bs-dismiss="modal"
								aria-label="Close"></button>
						</div>
						<div class="modal-body" id="modal-body"></div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">Close</button>
							<input type="submit" class="btn btn-primary"
								form="goal-entry-form" value="Update" />
						</div>
					</div>
				</div>
			</div>

			<div class="col-lg-8 mr-auto mt-2 mb-3 ml-auto text-center">
				<form id="goal-info-form" method="POST" action="add-goals">
				<button type="submit" id="next-btn" class="btn btn-primary" style="visibility: hidden;">Next</button>
				</form>
			</div>
		</div>
	</section>
	<script src="js/add-goal.js"><!-- some thing --></script>
	<jsp:include page="/WEB-INF/partials/footer.jspx"/>
</body>
</html>
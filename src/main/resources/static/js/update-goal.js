var counter = 1;
var goalsAdded = [];
console.log('imported');
function updateGoal(e, goalName, units) {
	e.preventDefault();
	const modalElement = document.getElementById('addGoalModal');
	const goalValue = document.getElementById('goal-amount').value;
	const goalValueTableEntry = document.getElementById(`${goalName}-target`);
   	if(modalElement) {
		var modal = bootstrap.Modal.getInstance(modalElement);
		modal.hide();
	}
	if(document.querySelector(`[name="${goalName}"]`)) {
		document.querySelector(`[name="${goalName}"]`).remove();
		document.querySelector(`[name="${goalName}.target"]`).remove();
	}
	goalValueTableEntry.innerHTML = goalValue + ' ' + units;
	addGoalToForm(goalName, goalValue, `goal-input-${counter}`);
	counter += 1;
}


function addFormToDialog(goalName, units) {
	const element = document.createElement('div');
	element.innerHTML = `			        
					<form id="goal-entry-form">
					<div class="container">
						<div class="form-group row">
			        	<label class="text-dark text-right col-sm-4 col-form-label" for="goal-amount" id="goal-name">${goalName}</label>
			        	<input type="number" min="0" id="goal-amount" class="text-dark col-sm-4" required/>
			        	<p id="units" class="text-dark col-sm-4 text-start col-form-label">${units}</p>
			        	</div>
			        	</div>
			        </form>`;
	document.getElementById('modal-body').appendChild(element);
	document.getElementById('goal-entry-form').addEventListener('submit', function(e) { updateGoal(e, goalName, units) });
	document.getElementById('addGoalModal').addEventListener('hidden.bs.modal', removeFormFromDialog);
}

function removeFormFromDialog() {
	document.getElementById('goal-entry-form').remove();
}

function submitForm() {
	document.getElementById('goal-entry-form').submit();
}

function addGoalToForm(goalName, target, goalId) {
	const inputElementForGoalName = document.createElement('input');
	const inputElementForGoalTarget = document.createElement('input');
	inputElementForGoalName.type = 'hidden';
	inputElementForGoalName.name = goalName;
	inputElementForGoalName.id = goalId;
	inputElementForGoalName.value = goalName;
	inputElementForGoalTarget.type = 'hidden';
	inputElementForGoalTarget.name = goalName + '.target';
	inputElementForGoalTarget.id = `${goalId}.target`;
	inputElementForGoalTarget.value = target;
	document.getElementById('goal-info-form').appendChild(inputElementForGoalName);
	document.getElementById('goal-info-form').appendChild(inputElementForGoalTarget);
}
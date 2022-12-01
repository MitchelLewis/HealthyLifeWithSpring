var counter = 1;
var goalsAdded = [];
console.log('imported');
function addGoal(e) {
	e.preventDefault();
	const modalElement = document.getElementById('addGoalModal');
	const tableBody = document.getElementById('goal-table-body');
	const tableRow = document.createElement('tr');
	tableRow.id = `goal-${counter}`;
	const goalType = document.getElementById('goal-select').value;
	const goalValue = document.getElementById('goal-amount').value;
	const goalUnits = document.getElementById('units').innerText;
	goalsAdded.push(goalType);
	tableRow.innerHTML = `<td class="align-middle text-center">${goalType}</td>
      <td class="align-middle text-center">${goalValue} ${goalUnits}</td>
      <td><button type="button" class="btn btn-secondary text-center" onclick="removeGoal(${counter})")>Remove</button></td>`;
    tableBody.appendChild(tableRow);
   	if(modalElement) {
		var modal = bootstrap.Modal.getInstance(modalElement);
		modal.hide();
	}	
	//7 is all the options (calories, protein, salt, sugar etc.) - needs to be updated if the more options are added
	if(goalsAdded.length == 7) {
		document.getElementById('add-goal-btn').style.visibility = 'hidden';
	}
	addGoalToForm(goalType, goalValue, `goal-input-${counter}`);
	counter += 1;
	document.getElementById('next-btn').style.visibility = '';
}

function removeGoal(id) {
	document.getElementById(`goal-${id}`).remove();
	var goalName = document.getElementById(`goal-input-${id}`).value;
	document.getElementById(`goal-input-${id}`).remove();
	document.getElementById(`goal-input-${id}.target`).remove();
	counter -= 1;
	if(counter == 1) {
		document.getElementById('next-btn').style.visibility = 'hidden';
	}
	document.getElementById('add-goal-btn').style.visibility = '';
	goalsAdded = goalsAdded.filter(el => el != goalName);
}

function addFormToDialog() {
	const element = document.createElement('div');
	element.innerHTML = `			        <form id="goal-entry-form">
					<div class="container">
			        <div class="form-group row mb-2">
			        	<label class="text-dark text-right col-sm-4 col-form-label" for="goal-select">Goal</label>
			        	<select class="text-dark col-sm-4" aria-label="Goal type" id="goal-select" onchange="onChangeForGoalSelection();" required>
						  <option selected disabled value >Type</option>
						  <option id="Calories" value="Calories">Calories</option>
						  <option id="Protein" value="Protein">Protein</option>
						  <option id="Salt" value="Salt">Salt</option>
						  <option id="Sugar" value="Sugar">Sugar</option>
						  <option id="Exercise" value="Exercise">Exercise</option>
						  <option id="Working hours" value="Working hours">Working hours</option>
						  <option id="Sleep" value="Sleep">Sleep</option>
						</select>
						</div>
						<div class="form-group row">
			        	<label class="text-dark text-right col-sm-4 col-form-label" for="amount">Amount</label>
			        	<input type="number" min="0" id="goal-amount" class="text-dark col-sm-4" required/>
			        	<p id="units" class="text-dark col-sm-4 text-start col-form-label"></p>
			        	</div>
			        	</div>
			        </form>`;
	document.getElementById('modal-body').appendChild(element);
	goalsAdded.forEach(function (goal) {var el = document.getElementById(goal); if(el) el.remove()});
	document.getElementById('goal-entry-form').addEventListener('submit', addGoal);
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

function onChangeForGoalSelection() {
	const selectElement = document.getElementById('goal-select');
	const unitsElement = document.getElementById('units');
	const selection = selectElement.value;
	switch(selection) {
		case 'Calories':
			unitsElement.innerText = 'kcals';
			break;
		case 'Protein':
		case 'Salt':
		case 'Sugar':
			unitsElement.innerText = 'grams';
			break;
		case 'Exercise':
			unitsElement.innerText = 'minutes';
			break;
		case 'Working hours':
		case 'Sleep':
			unitsElement.innerText = 'hours';
			break;
		default:
			unitsElement.innerText = '';
	}
}
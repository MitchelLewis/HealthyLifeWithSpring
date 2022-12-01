console.log('imported');

function populateProgressFields(selectElement) {
	var selectedValue = selectElement.value;
	var goalUnits = document.getElementById(`${selectedValue}.units`).value;
	var goalTarget = document.getElementById(`${selectedValue}.target`).value;
	var goalProgress = document.getElementById(`${selectedValue}.progress`).value;
	document.getElementById('goal-units').innerHTML = goalUnits;
	document.getElementById('target-units').innerHTML = goalUnits;
	document.getElementById('progress-units').innerHTML = goalUnits;
	document.getElementById('update-units').innerHTML = goalUnits;
	document.getElementById('total-units').innerHTML = goalUnits;
	document.getElementById('target').innerHTML = goalTarget;
	document.getElementById('progress').innerHTML = goalProgress;
	document.querySelectorAll('.hidden').forEach(function(e) { e.className = 'row large-text mb-2' });
	
}

function updateTotal(updateElement) {
	console.log('running');
	document.getElementById('total').innerHTML = parseInt(updateElement.value) + parseInt(document.getElementById('progress').innerHTML);
}
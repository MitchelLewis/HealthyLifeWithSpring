/* global expect, updateGoal */
//Import necessary dependencies - fake DOM, fake runtime etc. and the code to be tested i.e. update-goal.js
var jsdom = require("jsdom");
var fs = require('fs');
var vm = require('vm');
var expect = require('chai')
    .expect;
var code = fs.readFileSync('../update-goal.js');
//Run the add-goal JS file in the VM
vm.runInThisContext(code);
describe('Update Progress', function() {
	//Create a new DOM and set the necessary global elements
	const dom = new jsdom.JSDOM(`<!DOCTYPE html><body></body>`);
	doc = dom.window.document;
	global.window = dom.window;
	global.document = dom.window.document;
	//Create a fresh test container
	beforeEach(() => {
		if(doc.getElementById('test-container')) {
		doc.getElementById('test-container').remove();
		doc.body.innerHTML = '';
		}
		const testParent = doc.createElement('div');
		testParent.id = 'test-container';
		
		testParent.innerHTML = `<div class="container d-flex align-items-center flex-column">
			<input type="hidden" id="Sugar.units"
				name="Sugar.units"
				value="grams"> <input type="hidden"
				id="Sugar.target"
				name="Sugar.target"
				value="50"> <input type="hidden"
				id="Sugar.progress"
				name="Sugar.progress"
				value="40">
			<%
			}
			%>
			<form method="POST" action="update-progress">
				<div class="row large-text mb-2">
					<div class="col-sm-4">Goal:</div>
					<div class="col-sm-4">
						<select name="goal" id="select-element" onchange="populateProgressFields(this)">
							<option selected>Select goal</option>
							<option value="Sugar">Sugar</option>

						</select>
					</div>
					<div class="col-sm-4" id="goal-units"></div>
				</div>
				<div class="row large-text hidden mb-2">
					<div class="col-sm-4">Target:</div>
					<div class="col-sm-4" id="target"></div>
					<div class="col-sm-4" id="target-units"></div>
				</div>
				<div class="row large-text hidden mb-2">
					<div class="col-sm-4">Current progress:</div>
					<div class="col-sm-4" id="progress"></div>
					<div class="col-sm-4" id="progress-units"></div>
				</div>
				<div class="row large-text hidden mb-2">
					<div class="col-sm-4">Update:</div>
					<input class="col-sm-4" type="number" min="0" name="goal-update" id="updated-goal"
						onchange="updateTotal(this)">
					<div class="col-sm-4" id="update-units"></div>
				</div>
				<div class="row large-text hidden mb-2">
					<div class="col-sm-4">Total:</div>
					<div class="col-sm-4" id="total"></div>
					<div class="col-sm-4" id="total-units"></div>
				</div>
				<div class="d-flex align-items-center flex-column">
					<input type="submit" value="Save changes" class="btn btn-primary mt-3">
				</div>
			</form>
		</div>
			`;
		doc.body.appendChild(testParent);
	});


	it('populate the fields on the page based on the selected goal', function () {
		expect(doc.getElementById('target').innerHTML).to.equal('');
		expect(doc.getElementById('target-units').innerHTML).to.equal('');
		expect(doc.getElementById('progress').innerHTML).to.equal('');
		expect(doc.getElementById('progress-units').innerHTML).to.equal('');
		expect(doc.getElementById('total').innerHTML).to.equal('');
		expect(doc.getElementById('total-units').innerHTML).to.equal('');
		document.getElementById('select-element').value = 'Sugar';
		populateProgressFields(document.getElementById('select-element'));
		expect(doc.getElementById('target').innerHTML).to.equal('50');
		expect(doc.getElementById('target-units').innerHTML).to.equal('grams');
		expect(doc.getElementById('progress').innerHTML).to.equal('40');
		expect(doc.getElementById('progress-units').innerHTML).to.equal('grams');

	});
	
	it('calculates the new total value', function () {
		expect(doc.getElementById('target').innerHTML).to.equal('');
		expect(doc.getElementById('target-units').innerHTML).to.equal('');
		expect(doc.getElementById('progress').innerHTML).to.equal('');
		expect(doc.getElementById('progress-units').innerHTML).to.equal('');
		expect(doc.getElementById('total').innerHTML).to.equal('');
		expect(doc.getElementById('total-units').innerHTML).to.equal('');
		document.getElementById('select-element').value = 'Sugar';
		populateProgressFields(document.getElementById('select-element'));
		expect(doc.getElementById('target').innerHTML).to.equal('50');
		expect(doc.getElementById('target-units').innerHTML).to.equal('grams');
		expect(doc.getElementById('progress').innerHTML).to.equal('40');
		expect(doc.getElementById('progress-units').innerHTML).to.equal('grams');
		document.getElementById('updated-goal').value = '10';
		updateTotal(document.getElementById('updated-goal'));
		expect(doc.getElementById('total').innerHTML).to.equal('50');
		expect(doc.getElementById('total-units').innerHTML).to.equal('grams');

	});
	
});
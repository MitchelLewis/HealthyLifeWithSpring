//Import necessary dependencies - fake DOM, fake runtime etc. and the code to be tested i.e. update-goal.js
var jsdom = require("jsdom");
var fs = require('fs');
var vm = require('vm');
var expect = require('chai')
    .expect;
$ = require('jquery');
var code = fs.readFileSync('../update-goal.js');
//Run the add-goal JS file in the VM
vm.runInThisContext(code);
describe('Update Goal', function() {
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
		testParent.innerHTML = `<form id="goal-entry-form">
					<div class="container">
						<div class="form-group row">
			        	<label class="text-dark text-right col-sm-4 col-form-label" for="goal-amount" id="goal-name">Sugar</label>
			        	<input type="number" min="0" id="goal-amount" class="text-dark col-sm-4" required/>
			        	<p id="units" class="text-dark col-sm-4 text-start col-form-label">grams</p>
			        	</div>
			        	</div>
			        </form>
			        <div class="modal fade" id="addGoalModalTest" tabindex="-1"
				aria-labelledby="addGoalModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title text-dark" id="addGoalModalLabel">Update
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
			<form id="goal-info-form" method="POST" action="update-goals">
				<div class="d-flex align-items-center flex-column">
				<input type="submit"
					class="btn btn-primary mt-2 mb-4 align-middle"
					value="Save changes" />
				</div>
			</form>
			<table class="table" role="presentation" id="goal-table">
					<thead>
						<tr>
							<th scope="col" class="text-center">Goal type</th>
							<th scope="col" class="text-center">Current target</th>
							<th scope="col" class="text-center"></th>
						</tr>
					</thead>
					<tbody id="goal-table-body">
						<tr id="Sugar.row">
							<td class="align-middle text-center" id="Sugar">Sugar</td>
							<td class="align-middle text-center" id="Sugar-target">2 grams</td>
							<td class="align-middle text-center"></td>
						</tr>
					</tbody>
				</table>
			`;
		doc.body.appendChild(testParent);
	});
	
	it('should remove the goal from the page and the associated form elements', function () {
		 $.ajax = function(params){
		       params.success('');
		   };
		counter = 1;
		window.HTMLFormElement.prototype.submit = () => {}
		doc.getElementById('goal-amount').value = '1';
		updateGoal(new Event('submit'), 'Sugar', 'grams');
		expect(doc.getElementById('goal-input-1').value).to.equal('Sugar');
		expect(doc.getElementById('goal-input-1.target').value).to.equal('1');
		expect(doc.getElementById('Sugar').innerHTML).to.equal('Sugar');
		expect(doc.getElementById('Sugar-target').innerHTML).to.equal('1 grams');
		deleteGoal('Sugar');
		expect(!!doc.getElementById('goal-input-1')).to.be.false;
		expect(!!doc.getElementById('goal-input-1.target')).to.be.false;
		expect(!!doc.getElementById('Sugar')).to.be.false;
		expect(!!doc.getElementById('Sugar-target')).to.be.false;
	});
	
	it('should add a hidden input field to the page and update the page value', function () {
		counter = 1;
		window.HTMLFormElement.prototype.submit = () => {}
		doc.getElementById('goal-amount').value = '1';
		updateGoal(new Event('submit'), 'Sugar', 'grams');
		expect(doc.getElementById('goal-input-1').value).to.equal('Sugar');
		expect(doc.getElementById('goal-input-1.target').value).to.equal('1');
		expect(doc.getElementById('Sugar').innerHTML).to.equal('Sugar');
		expect(doc.getElementById('Sugar-target').innerHTML).to.equal('1 grams');
	});
	
	it('should update the hidden input field to the page and update the page value if goal is updated more than once', function () {
		counter = 1;
		window.HTMLFormElement.prototype.submit = () => {}
		doc.getElementById('goal-amount').value = '1';
		updateGoal(new Event('submit'), 'Sugar', 'grams');
		expect(doc.getElementById('goal-input-1').value).to.equal('Sugar');
		expect(doc.getElementById('goal-input-1.target').value).to.equal('1');
		expect(doc.getElementById('Sugar').innerHTML).to.equal('Sugar');
		expect(doc.getElementById('Sugar-target').innerHTML).to.equal('1 grams');
		doc.getElementById('goal-amount').value = '3';
		updateGoal(new Event('submit'), 'Sugar', 'grams');
		expect(!!doc.getElementById('goal-input-1')).to.be.false;
		expect(!!doc.getElementById('goal-input-1.target')).to.be.false;
		expect(doc.getElementById('goal-input-2').value).to.equal('Sugar');
		expect(doc.getElementById('goal-input-2.target').value).to.equal('3');
		expect(doc.getElementById('Sugar').innerHTML).to.equal('Sugar');
		expect(doc.getElementById('Sugar-target').innerHTML).to.equal('3 grams');
	});
});
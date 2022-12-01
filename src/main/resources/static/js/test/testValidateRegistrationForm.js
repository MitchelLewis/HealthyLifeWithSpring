//Import necessary dependencies - fake DOM, fake runtime etc. and the code to be tested i.e. validate-registration-form.js
var jsdom = require("jsdom");
var fs = require('fs');
var vm = require('vm');
var expect = require('chai')
    .expect;
var code = fs.readFileSync('../validate-registration-form.js');
//Run the add-goal JS file in the VM
vm.runInThisContext(code);
describe('Validate Registration', function() {
	//Create a new DOM and set the necessary global elements
	const dom = new jsdom.JSDOM(`<!DOCTYPE html><body></body>`);
	doc = dom.window.document;
	global.window = dom.window;
	global.document = dom.window.document;
    //Create a fresh test container
	beforeEach(() => {
        if(doc.getElementById('test-container')) {
            doc.getElementById('test-container').remove();
        }
        const testParent = doc.createElement('div');
        testParent.id = 'test-container';
        testParent.innerHTML = `<div id="input-col">
            <form>
                <input type="text" id="first-name" class="form-control" name="first-name" required/>
                <input type="text" id="surname" class="form-control" name="surname" required/>
                <input type="email" id="email" class="form-control" name="email" required/>
                <input type="password" id="password" class="form-control" name="password" required/>
            </form>
        </div>`;
        doc.body.appendChild(testParent);
    });

    it('should reject an invalid email', function() {
        document.getElementById('email').value = 'invalid@email';
        window.HTMLFormElement.prototype.submit = () => {}
        expect(!!doc.getElementById('error-dialog')).to.be.false;
        validateRegistrationFormData(new Event('submit'));
		expect(!!doc.getElementById('error-dialog')).to.be.true;
        expect(doc.getElementById('error-dialog').querySelector('li').innerHTML).to.equal('Please enter a valid email address');
    });

    it('should reject a password without a special character', function() {
        document.getElementById('email').value = 'foo@bar.com';
        document.getElementById('password').value = 'password123';
        window.HTMLFormElement.prototype.submit = () => {}
        expect(!!doc.getElementById('error-dialog')).to.be.false;
        validateRegistrationFormData(new Event('submit'));
		expect(!!doc.getElementById('error-dialog')).to.be.true;
        expect(doc.getElementById('error-dialog').querySelector('li').innerHTML).to.equal('Passwords must have at least eight characters, one letter, one number and one special character');
    });

    it('should reject a password without a character', function() {
        document.getElementById('email').value = 'foo@bar.com';
        document.getElementById('password').value = '123456789!';
        window.HTMLFormElement.prototype.submit = () => {}
        expect(!!doc.getElementById('error-dialog')).to.be.false;
        validateRegistrationFormData(new Event('submit'));
		expect(!!doc.getElementById('error-dialog')).to.be.true;
        expect(doc.getElementById('error-dialog').querySelector('li').innerHTML).to.equal('Passwords must have at least eight characters, one letter, one number and one special character');
    });
    
    it('should reject a password without a number', function() {
        document.getElementById('email').value = 'foo@bar.com';
        document.getElementById('password').value = 'password!!!';
        window.HTMLFormElement.prototype.submit = () => {}
        expect(!!doc.getElementById('error-dialog')).to.be.false;
        validateRegistrationFormData(new Event('submit'));
		expect(!!doc.getElementById('error-dialog')).to.be.true;
        expect(doc.getElementById('error-dialog').querySelector('li').innerHTML).to.equal('Passwords must have at least eight characters, one letter, one number and one special character');
    });

    it('should reject a password that is shorter than 8 characters', function() {
        document.getElementById('email').value = 'foo@bar.com';
        document.getElementById('password').value = 'pa12!';
        window.HTMLFormElement.prototype.submit = () => {}
        expect(!!doc.getElementById('error-dialog')).to.be.false;
        validateRegistrationFormData(new Event('submit'));
		expect(!!doc.getElementById('error-dialog')).to.be.true;
        expect(doc.getElementById('error-dialog').querySelector('li').innerHTML).to.equal('Passwords must have at least eight characters, one letter, one number and one special character');
    });

    it('should reject an invalid first name', function() {
        document.getElementById('first-name').value = 'L3wis';
        document.getElementById('password').value = 'password123!';
        document.getElementById('email').value = 'foo@bar.com';
        window.HTMLFormElement.prototype.submit = () => {}
        expect(!!doc.getElementById('error-dialog')).to.be.false;
        validateRegistrationFormData(new Event('submit'));
		expect(!!doc.getElementById('error-dialog')).to.be.true;
        expect(doc.getElementById('error-dialog').querySelector('li').innerHTML).to.equal('Names must not have numbers or special characters except \'-\'');
        doc.getElementById('error-dialog').remove();
        document.getElementById('first-name').value = 'Lew!s';
        validateRegistrationFormData(new Event('submit'));
		expect(!!doc.getElementById('error-dialog')).to.be.true;
        expect(doc.getElementById('error-dialog').querySelector('li').innerHTML).to.equal('Names must not have numbers or special characters except \'-\'');
    });

    it('should reject an invalid surname', function() {
        document.getElementById('email').value = 'foo@bar.com';
        document.getElementById('password').value = 'password123!';
        document.getElementById('surname').value = 'L3wis';
        window.HTMLFormElement.prototype.submit = () => {}
        expect(!!doc.getElementById('error-dialog')).to.be.false;
        validateRegistrationFormData(new Event('submit'));
		expect(!!doc.getElementById('error-dialog')).to.be.true;
        expect(doc.getElementById('error-dialog').querySelector('li').innerHTML).to.equal('Names must not have numbers or special characters except \'-\'');
        doc.getElementById('error-dialog').remove();
        document.getElementById('surname').value = 'Lew!s';
        validateRegistrationFormData(new Event('submit'));
		expect(!!doc.getElementById('error-dialog')).to.be.true;
        expect(doc.getElementById('error-dialog').querySelector('li').innerHTML).to.equal('Names must not have numbers or special characters except \'-\'');
    });
});
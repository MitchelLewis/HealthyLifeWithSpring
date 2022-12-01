console.log('imported');
function validateRegistrationFormData(e) {
    var previousErrorDialog = document.getElementById('error-dialog');
    if(previousErrorDialog) previousErrorDialog.remove();
    const errorDialog = document.createElement('div');
    errorDialog.id = 'error-dialog';
    errorDialog.role = 'alert';
    errorDialog.classList = 'alert alert-danger';
    const container = document.getElementById('input-col');
    const errorList = document.createElement('ul');
    var isErrors = false;
    if(!validateEmail(document.getElementById('email').value)) {
        var errorMessage = document.createElement('li');
        errorMessage.innerHTML = 'Please enter a valid email address';
        errorList.appendChild(errorMessage);
        isErrors = true;
    }
    if(!validatePassword(document.getElementById('password').value)) {
        var errorMessage = document.createElement('li');
        errorMessage.innerHTML = 'Passwords must have at least eight characters, one letter, one number and one special character';
        errorList.appendChild(errorMessage);
        isErrors = true;
    }
    var fName = document.getElementById('first-name').value;
    var sName = document.getElementById('surname').value; 
    if(!validateName(fName) || !validateName(sName)) {
        var errorMessage = document.createElement('li');
        errorMessage.innerHTML = 'Names must not have numbers or special characters except \'-\'';
        errorList.appendChild(errorMessage);
        isErrors = true;
    }

    if(isErrors) {
        errorDialog.appendChild(errorList);
        container.prepend(errorDialog);
        e.preventDefault();
    }
}

//Credits: https://stackoverflow.com/questions/46155/how-can-i-validate-an-email-address-in-javascript
const validateEmail = (email) => {
    return String(email)
      .toLowerCase()
      .match(
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      );
};

//Credits: https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a
const validatePassword = (password) => {
    return String(password)
    .toLowerCase()
    .match(
        /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/
    )
}

//Credits: https://stackoverflow.com/questions/32311081/check-for-special-characters-in-string (some adaptation to allow '-')
const validateName = (name) => {
    return String(name)
    .toLowerCase()
    .match(
        /^[a-z ,.'-]+$/i
    )
}
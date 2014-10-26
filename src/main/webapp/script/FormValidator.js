function FormValidator(form) {
    this.form = form;
}

FormValidator.prototype.validateLoginAndPassword = function() {
    var login = this.form.children.j_username.value;
    document.getElementById("processError").style.display = "none";
    if(login.length < 5) {
        this.form.children.loginError.style.display = "inline";
    }else{
        this.form.children.loginError.style.display = "none";
    }
    var password = this.form.children.j_password.value;
    if(password.length < 6) {
        document.getElementById("passwordError").style.display = "inline";
    } else{
        document.getElementById("passwordError").style.display = "none";
    }
    if(login.length >= 5 && password.length >= 6){
        this.form.submit();
    }
}
var validateObject = {
    showLabel: function(label, text){
        if (label){
            $(label).text(text);
            $(label).show();
        }
    },

    hideLabel: function(label){
        if (label) {
            $(label).hide();
        }
    },

    validateUsername: function (value, inputAttributes) {
        if (value.length < 5) {
            this.showLabel(inputAttributes,"Логин должен содержать не менее 5 знаков");
            return false;
        }
        if (value.length > 20) {
            this.showLabel(inputAttributes,"Логин должен содержать не более 20 знаков");
            return false;
        }
        this.hideLabel(inputAttributes);
        return true;
    },

    validatePassword: function (value, inputAttributes) {
        if (value.length < 6) {
            this.showLabel(inputAttributes, "Пароль должен содержать не менее 6 знаков");
            return false;
        }
        this.hideLabel(inputAttributes);
        return true;
    },

    validateEmail: function(value, inputAttributes){
        var regexp = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if(!regexp.test(value)){
            this.showLabel(inputAttributes, "Некорректный emil");
            return false;
        }
        this.hideLabel(inputAttributes);
        return true;
    },

    validateAccountNumber: function(value, inputAttributes){
        var regexp = /(([0-9]){3}){1}\/(([0-9]){8}){1}$/;
        if(regexp.test(value)){
            var tokens = value.split('/');
            var year = tokens[1].substring(0,4);
            var month = tokens[1].substring(4,6);
            var day = tokens[1].substring(6,8);
            var currentDate = new Date();
            if(year == currentDate.getFullYear() && month == currentDate.getMonth() + 1 && day == currentDate.getDate()){
                this.hideLabel(inputAttributes);
                return true;
            }
        }
        this.showLabel(inputAttributes, "Некорректный номер платежного счета");
        return false;
    },

    validate: function (formSelector) {
        var instance = this;
        var correct = true;

        $(formSelector + ' input:not([type="button"])').each(function () {

            if ($(this).attr('validate')) {
                if ($(this).attr('validate') === 'username') {
                    correct = instance.validateUsername($(this).val(), $(this).attr('errorLabel')) && correct;
                }
                if ($(this).attr('validate') === 'password') {
                    correct = instance.validatePassword($(this).val(), $(this).attr('errorLabel')) && correct;
                }
                if ($(this).attr('validate') === 'accountNumber') {
                    correct = instance.validateAccountNumber($(this).val(), $(this).attr('errorLabel')) && correct;
                }
            }
        });
        return correct;
    },
    
    validateAndSubmit: function (formSelector) {
        if (validateObject.validate(formSelector)) {
            $(formSelector).submit();
        }
    }
};
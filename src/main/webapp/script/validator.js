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
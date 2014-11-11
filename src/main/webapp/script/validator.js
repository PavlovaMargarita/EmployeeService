var validateObject = {
    showLabel: function(label, text){
        if (label){
            $(label.value).text(text);
            $(label.value).show()
        }
    },
    hideLabel: function(label){
        if (label) {
            $(label.value).hide();
        }
    },
    validateUsername: function (value, inputAttributes) {
        if (value.length < 5) {
            this.showLabel(inputAttributes.errorLabel,"Логин должен содержать не менее 5 знаков");
            return false;
        } else{
            this.hideLabel(inputAttributes.errorLabel);
        }
        if (value.length > 20) {
            this.showLabel(inputAttributes.errorLabel,"Логин должен содержать не более 20 знаков");
            return false;
        } else{
            this.hideLabel(inputAttributes.errorLabel);
        }
        return true;
    },
    validatePassword: function (value, inputAttributes) {
        if (value.length < 6) {
            this.showLabel(inputAttributes.errorLabel, "Пароль должен содержать не менее 6 знаков");
            return false;
        } else{
            this.hideLabel(inputAttributes.errorLabel);
        }
        return true;
    },
    validate: function (formSelector) {
        var instance = this;
        var correct = true;
        $(formSelector + ' input:not([type="button"])').each(function () {
            var input = $(this);
            if (input.context.attributes.validate) {
                if (input.context.attributes.validate.value === 'username') {
                    if (!instance.validateUsername(input.context.value, input.context.attributes)) {
                        correct = false;
                    }
                }
                if (input.context.attributes.validate.value === 'password') {
                    if (!instance.validatePassword(input.context.value, input.context.attributes)) {
                        correct = false;

                    }
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
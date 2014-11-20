var validateObject = {
    showLabel: function(label, text){
        if (label){
            $(label).text(text);
            $(label).showStandardPhotoAndFiredButton();
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
            this.showLabel(inputAttributes, "Некорректный email");
            return false;
        }
        this.hideLabel(inputAttributes);
        return true;
    },

    validateAccountNumber: function(value, inputAttributes){
        //var regexp = /(([0-9]){3}){1}\/(([0-9]){8}){1}$/;
        var regexp = /((([0-9]){3}){1}\/(19|20)?[0-9]{2}(0?[1-9]|[12][0-9]|3[01]){2}(0?[1-9]|1[012]){2})$/;
        if(regexp.test(value)){

                this.hideLabel(inputAttributes);
                return true;

        }
        this.showLabel(inputAttributes, "Некорректный номер платежного счета");
        return false;
    },

    validateFirstName: function(value, inputAttributes){
        var regexp =  /^[A-Za-zА-Яа-яЁё]+$/;
        if(regexp.test(value)){
            this.hideLabel(inputAttributes);
            return true;
        }
        this.showLabel(inputAttributes, "Некорректное имя");
        return false;
    },

    validateSecondName: function(value, inputAttributes){
        var regexp =  /^[A-Za-zА-Яа-яЁё -]+$/;
        if(regexp.test(value)){
            this.hideLabel(inputAttributes);
            return true;
        }
        this.showLabel(inputAttributes, "Некорректная фамилия");
        return false;
    },

//    validateSex: function(value, inputAttributes){
//        if(value == '?'){
//            this.showLabel(inputAttributes, "Выберить пол");
//            return false;
//        }
//        this.hideLabel(inputAttributes);
//        return true;
//    },
//
//    validatePosition: function(value, inputAttributes){
//        if(value == '?'){
//            this.showLabel(inputAttributes, "Выберить должность");
//            return false;
//        }
//        this.hideLabel(inputAttributes);
//        return true;
//    },
//
//    validateRole: function(value, inputAttributes){
//        if(value == '?'){
//            this.showLabel(inputAttributes, "Выберить роль");
//            return false;
//        }
//        this.hideLabel(inputAttributes);
//        return true;
//    },
//
//    validateCountry: function(value, inputAttributes){
//        if(value == '?'){
//            this.showLabel(inputAttributes, "Выберить страну");
//            return false;
//        }
//        this.hideLabel(inputAttributes);
//        return true;
//    },

    validateDate: function(value, inputAttributes){
        if(value == ""){
            this.showLabel(inputAttributes, "Введите дату");
            return false;
        }
        this.hideLabel(inputAttributes);
        return true;
    },

    validateCity: function(value, inputAttributes){
        var regexp =  /^[A-Za-zА-Яа-яЁё -]+$/;
        if(regexp.test(value)){
            this.hideLabel(inputAttributes);
            return true;
        }
        this.showLabel(inputAttributes, "Некорректный город");
        return false;
    },

    validateStreet: function(value, inputAttributes){
        var regexp =  /^[A-Za-zА-Яа-яЁё0-9 -]+$/;
        if(regexp.test(value)){
            this.hideLabel(inputAttributes);
            return true;
        }
        this.showLabel(inputAttributes, "Некорректная улица");
        return false;
    },

    validateHouse: function(value, inputAttributes){
        var regexp =  /^\d+/;
        if(regexp.test(value)){
            this.hideLabel(inputAttributes);
            return true;
        }
        this.showLabel(inputAttributes, "Некорректный номер дом");
        return false;
    },

    validateFlat: function(value, inputAttributes){
        var regexp =  /^[0-9]+[A-Za-zА-Яа-я]*$/;
        if(regexp.test(value)){
            this.hideLabel(inputAttributes);
            return true;
        }
        this.showLabel(inputAttributes, "Некорректный номер помещения");
        return false;
    },

    validateCompanyName: function(value, inputAttributes){
        var regexp =  /^[A-Za-zА-Яа-яЁё0-9 -]+$/;
        if(regexp.test(value)){
            this.hideLabel(inputAttributes);
            return true;
        }
        this.showLabel(inputAttributes, "Некорректное название компании");
        return false;
    },

    validateProgramCost: function(value, inputAttributes){
        var regexp =  /^\d+/;
        if(regexp.test(value)){
            this.hideLabel(inputAttributes);
            return true;
        }
        this.showLabel(inputAttributes, "Некорректная стоимость");
        return false;
    },

    validate: function (formSelector) {
        var instance = this;
        var correct = true;

//        $(formSelector + ' input:not([type="button"]), ' + formSelector + ' select').each(function () {
        $(formSelector + ' [validate]').each(function () {
            switch ($(this).attr('validate')) {
                case 'username':
                    correct = instance.validateUsername($(this).val(), $(this).attr('errorLabel')) && correct;
                    break;
                case 'password':
                    correct = instance.validatePassword($(this).val(), $(this).attr('errorLabel')) && correct;
                    break;
                case 'accountNumber':
                    correct = instance.validateAccountNumber($(this).val(), $(this).attr('errorLabel')) && correct;
                    break;
                case 'first_name':
                    correct = instance.validateFirstName($(this).val(), $(this).attr('errorLabel')) && correct;
                    break;
                case 'second_name':
                    correct = instance.validateSecondName($(this).val(), $(this).attr('errorLabel')) && correct;
                    break;
//                case 'sex-select':
//                    correct = instance.validateSex($(this).val(), $(this).attr('errorLabel')) && correct;
//                    break;
//                case 'position-select':
//                    correct = instance.validatePosition($(this).val(), $(this).attr('errorLabel')) && correct;
//                    break;
//                case 'role-select':
//                    correct = instance.validateRole($(this).val(), $(this).attr('errorLabel')) && correct;
//                    break;
//                case 'country-select':
//                    correct = instance.validateCountry($(this).val(), $(this).attr('errorLabel')) && correct;
//                    break;
                case 'date':
                    correct = instance.validateDate($(this).val(), $(this).attr('errorLabel')) && correct;
                    break;
                case 'city':
                    correct = instance.validateCity($(this).val(), $(this).attr('errorLabel')) && correct;
                    break;
                case 'street':
                    correct = instance.validateStreet($(this).val(), $(this).attr('errorLabel')) && correct;
                    break;
                case 'house':
                    correct = instance.validateHouse($(this).val(), $(this).attr('errorLabel')) && correct;
                    break;
                case 'flat':
                    correct = instance.validateFlat($(this).val(), $(this).attr('errorLabel')) && correct;
                    break;
                case 'email':
                    correct = instance.validateEmail($(this).val(), $(this).attr('errorLabel')) && correct;
                    break;
                case 'companyName':
                    correct = instance.validateCompanyName($(this).val(), $(this).attr('errorLabel')) && correct;
                    break;
                case 'programCost':
                    correct = instance.validateProgramCost($(this).val(), $(this).attr('errorLabel')) && correct;
                    break;
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
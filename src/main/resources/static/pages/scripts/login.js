var Login = function () {

    var handleLogin = function () {
        $('.login-form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                },
                remember: {
                    required: false
                }
            },

            messages: {
                username: {
                    required: "Username is required."
                },
                password: {
                    required: "Password is required."
                }
            },

            invalidHandler: function (event, validator) { //display error alert on form submit
                $('.alert-danger', $('.login-form')).show();
            },

            highlight: function (element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            success: function (label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            },

            errorPlacement: function (error, element) {
                error.insertAfter(element.closest('.input-icon'));
            },

            submitHandler: function (form) {
                dealPassword();
                form.submit();
            }
        });

        $('.login-form input').keypress(function (e) {
            if (e.which === 13) {
                if ($('.login-form').validate().form()) {
                    // dealPassword();
                    $('.login-form').submit();
                }
                return false;
            }
        });

        function dealPassword() {
            $("#password").val(md5($("#password").val()))
        }

        $("#language").on('change', function () {
			var lang = $(this).val();
			location.href = "/login?lang=" + lang;
		})
    };

    return {
        //main function to initiate the module
        init: function () {

            handleLogin();
        }
    };

}();

jQuery(document).ready(function () {
    Login.init();
});
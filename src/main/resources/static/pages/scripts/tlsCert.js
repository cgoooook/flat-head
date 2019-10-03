var tlsCert = function () {

    var handleEvents = function () {
        $("#sign").on('click', function () {
            if ($('#signForm').validate().form()) {
                $.ajax({
                    url: "/certsign/genTlsCert",
                    type: "post",
                    dataType:"json",
                    data: {
                        subject: $("#subject").val(),
                        alg: $("#alg").val(),
                        algLength: $("#algLength").val(),
                        signAlg: $("#signAlg").val(),
                        pass: $("#pass").val()
                    }
                }).done(function (data) {
                    if (data.ok) {
                        window.location.href = "/certsign/tlsDown?file=" + data.data + "&fileName=" + $("#subject").val();
                    } else {
                        alert(data.message)
                    }
                })
            }

        })
    };

    return {
        init: function () {
            handleEvents();
        }
    }

}();
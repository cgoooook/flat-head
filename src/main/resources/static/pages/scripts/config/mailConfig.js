var mailConfig = function () {


    var handleConfig = function () {

        $.ajax({
            type: "GET",
            url: "/mail/config/mailConfig",
            dataType: "json",
            success: function(result){
                $("#addr").val(result.addr);
                $("#port").val(result.port);
                $("#timeOut").val(result.timeOut);
                $("#sendMailbox").val(result.sendMailbox);
                $("#password").val(result.password);
                $("#receivingMailbox").val(result.receivingMailbox);

            }

        });

        $("#test").on('click', function () {
            var data =JSON.stringify({
                addr : $("#addr").val(),
                port : $("#port").val(),
                timeOut : $("#timeOut").val(),
                sendMailbox : $("#sendMailbox").val(),
                password : $("#password").val(),
                receivingMailbox : $("#receivingMailbox").val()
            });
            $.ajax({
                type: "POST",
                url: "/mail/config/test",
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data:data,


            }).done(function (data) {
                if (flat.ajaxCallback(data)) {
                    //成功后逻辑
                }
            });


        })



        $("#save").on('click', function () {
            var data =JSON.stringify({
                addr : $("#addr").val(),
                port : $("#port").val(),
                timeOut : $("#timeOut").val(),
                sendMailbox : $("#sendMailbox").val(),
                password : $("#password").val(),
                receivingMailbox : $("#receivingMailbox").val()
            });
            $.ajax({
                type: "POST",
                url: "/mail/config/save",
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data:data,


            }).done(function (data) {
                if (flat.ajaxCallback(data)) {
                    //成功后逻辑
                }
            });


        })




    };



    return {
        init: function () {
            handleConfig();
        }
    }

}();


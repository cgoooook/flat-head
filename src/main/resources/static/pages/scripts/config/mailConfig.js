var mailConfig = function () {


    var handleConfig = function () {

       /* $.ajax({
            type: "put",
            url: "/mail/config/jdbc",
            dataType: "json",
            success: function(result){
                $("#url").val(result.url);
                $("#username").val(result.username);
                $("#password").val(result.password);
                $("#driver").val(result.driver);
                $("#maxIdle").val(result.maxIdle);
                $("#maxActive").val(result.maxActive);

            }

        });*/

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
                url : $("#url").val(),
                username : $("#username").val(),
                password : $("#password").val(),
                driver : $("#driver").val(),
                maxIdle : $("#maxIdle").val(),
                maxActive : $("#maxActive").val()
            });
            $.ajax({
                type: "POST",
                url: "/db/config/jdbc",
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


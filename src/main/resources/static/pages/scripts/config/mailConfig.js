var mailConfig = function () {


    var handleConfig = function () {

        $.ajax({
            type: "GET",
            url: "/mail/config/mailConfig",
            dataType: "json",
            success: function(result){

                 if(result.returnState=="OK"){
                   $("#addr").val(result.data.addr);
                   $("#port").val(result.data.port);
                   $("#timeOut").val(result.data.timeOut);
                   $("#sendMailbox").val(result.data.sendMailbox);
                   $("#password").val(result.data.password);
                   $("#receivingMailbox").val(result.data.receivingMailbox);
               }
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
              if(data.returnState=="ERROR"){
                  toast.error($.i18n.prop('mail.send.error'));
              }if(data.returnState=="OK"){
                    toast.success($.i18n.prop('mail.send.success'));
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


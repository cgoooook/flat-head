var logConfig = function () {


    var handleConfig = function () {

        $.ajax({
            type: "GET",
            url: "/log/config/config",
            dataType: "json",
            success: function(result){
                $("#retretion").val(result.log_days);
                $("#level").find("option[value = '"+result.log_level+"']").attr("selected","selected");
            }

        });
        $("#save").on('click', function () {
            var data =JSON.stringify({
                logLevel : $("#level option:selected").val(),
                logDays : $("#retretion").val(),
            });
            console.log(data);
            $.ajax({
                type: "POST",
                url: "/log/config/config",
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


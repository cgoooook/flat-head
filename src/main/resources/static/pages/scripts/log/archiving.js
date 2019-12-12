var archiving = function () {

    var handleinit= function () {
        $("input.datepicker").datetimepicker({
            isRTL: App.isRTL(),
            language: DATETIME_PICKER,
            format: "yyyy-mm-dd HH:mm:ss",
            autoclose: true,
            todayBtn: true,
            pickerPosition: (App.isRTL() ? "bottom-right" : "bottom-left"),
            minuteStep: 10
        });

        $("#archiving").on('click', function () {
            var data =JSON.stringify({
                operateType : $("#type option:selected").val(),
                operatorResult : $("#result option:selected").val(),
                strategy : $("#strategy option:selected").val(),
                operateTimeBegin :$("#beginTime").val(),
                operateTimeEnd :$("#endTime").val(),
                maxActive :$("#maxActive").val()
            });
            console.log(data);
            $.ajax({
                type: "POST",
                url: "/log/archiving/archiving",
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data:data
            }).done(function (data) {
                if (flat.ajaxCallback(data)) {
                    //成功后逻辑
                }
            });


        });




    };

    function changeFunc() {
        $("#strategy").on('change',function(){
            if($("#strategy option:selected").val()=="day"){
                $("#beginTime").removeAttr("name");
                $("#endTime").removeAttr("name");
                if($("#maxActive").attr("name")==""||$("#maxActive").attr("name")==null||$("#maxActive").attr("name")==undefined){

                    $("#maxActive").attr("name","day")
                }


            }else if($("#strategy option:selected").val()=="time"){
                $("#maxActive").removeAttr("name");

                $("#endTime").removeAttr("name");
                if($("#beginTime").attr("name")==""||$("#beginTime").attr("name")==null||$("#beginTime").attr("name")==undefined){

                    $("#maxActive").attr("name","day")
                }

            }


        });
    }



    function initDatePicker() {
        $("input.datepicker").datetimepicker({
            isRTL: App.isRTL(),
            language: DATETIME_PICKER,
            format: "yyyy-mm-dd HH:mm:ss",
            autoclose: true,
            todayBtn: true,
            pickerPosition: (App.isRTL() ? "bottom-right" : "bottom-left"),
            minuteStep: 10
        });
    }


    return {
        init: function () {
            handleinit();
            initDatePicker();
            changeFunc();
        }
    }

}();
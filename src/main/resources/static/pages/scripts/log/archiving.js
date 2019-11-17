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


    };



    return {
        init: function () {
            handleinit();

        }
    }

}();
var adminLog = function () {

    var grid;
    var $table;

    var handleTables = function () {
        grid = new Datatable();
        $table = $("#ajax_tables");
        grid.init({
            src: $table,
            onQuery:function (data) {
                data.operateType=$("#operationType option:selected").val();
                data.operatorResult=$("#operateResultQuery option:selected").val();
                data.operateTimeBegin=$("#beginTime").val();
                data.operateTimeEnd=$("#endTime").val();

            },
            dataTable: {
                ajax: {
                    url: "/log/manage/list"
                },
                "columns": [
                    {data: 'operateType', orderable: true},
                    {data: 'operateUser', orderable: true},
                    {data: 'operateTime', orderable: true},
                    {data: 'operatorResult', orderable: true},
                    {data: 'operateContent', orderable: true},
                    {data: 'operate', orderable: false,
                    render: function () {

                        return "";
                    }}
                ]
            }
        });
    };


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
            handleTables();
            initDatePicker();

        }
    }

}();
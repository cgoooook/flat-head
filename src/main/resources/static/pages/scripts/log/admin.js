var adminLog = function () {

    var grid;
    var $table;

    var handleTables = function () {
        grid = new Datatable();
        $table = $("#adminTables");
        grid.init({
            src: $table,
            dataTable: {
                "ajax": {
                    "url": "/log/manage/list"
                },
                "columns": [
                    {data: 'operateType', orderable: true},
                    {data: 'operateUser', orderable: true},
                    {data: 'operateTime', orderable: true},
                    {data: 'operatorResult', orderable: true},
                    {data: 'operateContent', orderable: true},
                    {
                        data: 'operate', orderable: false,
                        render: function (data, type, full) {
                            return template("actionBtn", {data: data, type: type, full: full});
                        }
                    }
                ],
                "paging": false
            }
        });

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


    };



    return {
        init: function () {
            handleTables();
        }
    }

}();
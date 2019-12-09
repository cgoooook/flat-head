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
                data.operatorResult=Number($("#operateResultQuery option:selected").val());
                data.operateTimeBegin=$("#beginTime").val();
                data.operateTimeEnd=$("#endTime").val();

            },
            dataTable: {
                ajax: {
                    method:"post",
                    url: "/log/manage/list"
                },
                "columns": [
                    {data: 'operateType', orderable: true},
                    {data: 'operateUser', orderable: true},
                    {data: 'operateTime', orderable: true,render: function (data, type, full) {
                            var unixTimestamp = new Date( data) ;
                            commonTime = unixTimestamp.toLocaleString();
                            return commonTime;
                        }},
                    {data: 'operatorResult', orderable: true,
                        render: function (data, type, full) {
                            return flat.i18n("operator.status" + data);
                        }},
                    {data: 'operateContent', orderable: true},
                    {data: 'operate', orderable: false,
                        render: function (data, type, full) {
                            return template("actionBtn", {data: data, type: type, full: full});
                        }}
                ]
            }
        });
    };
    Date.prototype.toLocaleString = function() {
        return this.getFullYear() + "/" + (this.getMonth() + 1) + "/" + this.getDate() + "/ " + this.getHours() + ":" + this.getMinutes() + ":" + this.getSeconds();
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
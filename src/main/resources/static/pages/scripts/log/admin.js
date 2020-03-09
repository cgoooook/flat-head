var adminLog = function () {

    var grid;
    var $table;

    var handleTables = function () {
        grid = new Datatable();
        $table = $("#ajax_tables");
        grid.init({
            src: $table,
            onQuery:function (data) {
                var operationTime = $("#defaultrange input").val();
                var ationTime = operationTime.split(" - ")[0];
                var endTime = operationTime.split(" - ")[1];
                data.operateType=$("#operationType option:selected").val();
                data.operatorResult=Number($("#operateResultQuery option:selected").val());
                data.operateTimeBegin=ationTime;
                data.operateTimeEnd=endTime;

            },
            dataTable: {
                ajax: {
                    method:"post",
                    url: "/log/manage/list"
                },
                "columns": [
                    {data: 'operateType', orderable: true,
                        render: function (data, type, full) {
                            return flat.i18n("log." + data);
                        }
                    },
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
                            return "";
                        }}
                ]
            }
        });
    };
    Date.prototype.toLocaleString = function() {
        return this.getFullYear() + "/" + (this.getMonth() + 1) + "/" + this.getDate() + "/ " + this.getHours() + ":" + this.getMinutes() + ":" + this.getSeconds();
    };

        function initDatePicker() {
            $('#defaultrange').daterangepicker({
                    opens: (App.isRTL() ? 'left' : 'right'),
                    separator: ' 至 ',
                    startDate: moment().subtract('days', 29),
                    endDate: moment(),
                    ranges: {
                        '今天': [moment(), moment()],
                        '昨天': [moment().subtract('days', 1), moment().subtract('days', 1)],
                        '最近7天': [moment().subtract('days', 6), moment()],
                        '最近30天': [moment().subtract('days', 29), moment()],
                        '这个月': [moment().startOf('month'), moment().endOf('month')],
                        '上个月': [moment().subtract('month', 1).startOf('month'), moment().subtract('month', 1).endOf('month')]
                    },
                    locale: {
                        format: "YYYY-MM-DD HH:mm:ss",
                        applyLabel: '确认',
                        cancelLabel: '清除',
                        fromLabel: '从',
                        toLabel: '至',
                        customRangeLabel: '自定义范围',
                        daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
                        monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
                        firstDay: 1
                    },
                },
                function (start, end) {
                    $('#defaultrange input').val(start.format('YYYY-MM-DD HH:mm:ss') + ' - ' + end.format('YYYY-MM-DD HH:mm:ss'));
                }
            );
            $('#defaultrange input').val(moment().subtract('days', 29).format('YYYY-MM-DD HH:mm:ss') + ' - ' + moment().format('YYYY-MM-DD HH:mm:ss'));
        }


    return {
        init: function () {
            handleTables();
            initDatePicker();

        }
    }

}();
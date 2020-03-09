var ApiLog = function () {

    var grid;
    var $table;

    var handleTables = function () {
        grid = new Datatable();
        $table = $("#ajax_tables");
        grid.init({
            src: $table,
            dataTable: {
                "ajax": {
                    "url": "/log/apiLog/list"
                },
                "columns": [
                    {data: 'url', orderable: true},
                    {data: 'accessTime', orderable: true,
                        render: function (data, type, full) {
                            var unixTimestamp = new Date( data) ;
                            return unixTimestamp.pattern("yyyy-MM-dd hh:mm:ss");
                        }
                    },
                    {data: 'result', orderable: true,
                        render: function (data, type, full) {
                            return flat.i18n("apiLog.result" + data)
                        }
                    },
                    {data: 'momo', orderable: true},
                ]
            }
        });
    };

    return {
        init: function () {
            handleTables()
        }
    }

}();
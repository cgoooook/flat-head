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
                    {data: 'accessTime', orderable: true},
                    {data: 'result', orderable: true},
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
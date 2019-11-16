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
    };



    return {
        init: function () {
            handleTables();
        }
    }

}();
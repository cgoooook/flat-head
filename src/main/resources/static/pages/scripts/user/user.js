var User = function () {

    var grid;
    var $table;

    var handleTables = function () {
        grid = new Datatable();
        $table = $("#userTables");
        grid.init({
            src: $table,
            dataTable: {
                "ajax": {
                    "url": "admin/user/findAllUser"
                },
                "columns": [
                    {data: 'loginName', orderable: true},
                    {data: 'username', orderable: true},
                    {
                        data: 'operate', orderable: false,
                        render: function (data, type, full) {
                            return template("actionBtn", {data: data, type: type, full: full});
                        }
                    }
                ]
            }
        });
    };


    return {
        init: function () {
            handleTables();
        }
    }

}();
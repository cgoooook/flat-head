var server = function () {

    var grid;
    var $table;

    var handleTables = function () {
        grid = new Datatable();
        $table = $("#taskTables");
        grid.init({
            src: $table,
            dataTable: {
                "ajax": {
                    "url": "/log/server/list"
                },
                "columns": [
                    {data: 'fileName', orderable: true},
                    {data: 'fileType', orderable: true},
                    {
                        data: 'operate', orderable: false,
                        render: function (data, type, full) {
                            return template("actionBtn", {data: data, type: type, full: full});
                        }
                    }
                ]
            }
        });

        $table.on('click', 'a.see', function () {
       /*     var $this = $(this);
            flat.showConfirm();
            $("#confirmBtn").off("click").on("click", function () {
                console.log($table.DataTable().row($this.parents('tr')[0]));
                var $row = $table.DataTable().row($this.parents('tr')[0]);
                $.ajax({
                    url: "/task/config/" + $row.data().id,
                    dataType: "json",
                    type: "DELETE"
                }).done(function (data) {
                    if (flat.ajaxCallback(data)) {
                        grid.reload();
                        $("#confirmDialog").modal("hide");
                    }
                })
            })*/
        });



    };



    return {
        init: function () {
            handleTables();
        }
    }

}();





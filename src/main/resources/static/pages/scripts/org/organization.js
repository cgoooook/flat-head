var Org = function () {

    var grid;
    var $table;

    var handleTables = function () {
        grid = new Datatable();
        $table = $("#orgTables");
        grid.init({
            src: $table,
            dataTable: {
                "ajax": {
                    "url": "/sys/org/list"
                },
                "columns": [
                    {data: 'orgName', orderable: true},
                    {data: 'orgCode', orderable: true},
                    {data: 'parentName', orderable: true},
                    {
                        data: 'operate', orderable: false,
                        render: function (data, type, full) {
                            return template("actionBtn", {data: data, type: type, full: full});
                        }
                    }
                ]
            }
        });

        $table.on('click', 'a.delete', function () {
            var $this = $(this);
            flat.showConfirm();
            $("#confirmBtn").off("click").on("click", function () {
                var $row = $table.DataTable().row($this.parents('tr')[0]);
                $.ajax({
                    url: "/sys/org/" + $row.data().orgId,
                    dataType: "json",
                    type: "DELETE"
                }).done(function (data) {
                    if (flat.ajaxCallback(data)) {
                        grid.reload();
                        $("#confirmDialog").modal("hide");
                    }
                })
            })
        });

    };


    return {
        init: function () {
            handleTables();
        }
    }

}();
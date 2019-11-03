var device = function () {

    var grid;
    var $table;

    var handleTables = function () {
        grid = new Datatable();
        $table = $("#devTables");
        grid.init({
            src: $table,
            dataTable: {
                "ajax": {
                    "url": "/sys/device/list"
                },
                "columns": [
                    {data: 'deviceName', orderable: true},
                    {data: 'deviceCode', orderable: true},
                    {data: 'deviceIp', orderable: true},
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


    var handleEvents = function () {
        $("#addOrg").on('click', function () {
            var htmlTemplate = flat.remoteTemplate("/template/organization/addOrg.html", "");
            $("#modalDialog").html(htmlTemplate).modal('show');
            initSaveBtn();
        });


        $table.on('click', 'a.edit', function () {
            var $this = $(this);
            var $row = $table.DataTable().row($this.parents('tr')[0]);
            $.get("/sys/org/" + $row.data().orgId, function (data) {
                if (data.ok) {
                    var org = data.data;
                    var htmlTemplate = flat.remoteTemplate("/template/organization/editOrg.html", { reqData: org});
                    $("#modalDialog").html(htmlTemplate).modal('show');
                    initSaveBtn();
                } else {
                    flat.ajaxCallback(data);
                }
            });
        });




        function initSaveBtn() {
            $("#addBtn").on('click', function () {
                if ($('#dialogForm').validate().form()) {
                    $.ajax({
                        url: "/sys/org",
                        type: "PUT",
                        dataType: "json",
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify({
                            orgCode: $("#orgCode").val(),
                            orgName: $("#orgName").val(),

                        })
                    }).done(function (data) {
                        if (flat.ajaxCallback(data)) {
                            $("#modalDialog").modal('hide');
                            grid.reload()
                        }
                    })
                }

            });
            $("#editBtn").on('click', function () {
                if ($('#dialogForm').validate().form()) {
                    $.ajax({
                        url: "/sys/org/edit",
                        type: "PUT",
                        dataType: "json",
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify({
                            orgId :$("#orgId").attr("text"),
                            orgCode: $("#orgCode").val(),
                            orgName: $("#orgName").val()
                        })
                    }).done(function (data) {
                        if (flat.ajaxCallback(data)) {
                            $("#modalDialog").modal('hide');
                            grid.reload()
                        }
                    })
                }

            });


        }
    };

    return {
        init: function () {
            handleTables();
            handleEvents();
        }
    }

}();

$('#jstree_div a').on('click', function () {
    alert("begin");
    //get_selected返回选中的列
    console.log($('#jstree_div').jstree().get_selected(true));
});
var Template = function () {

    var grid;
    var $table;
    var handleTables = function () {


        grid = new Datatable();
        $table = $("#datatables_ajax");
        grid.init({
            src: $table,
            dataTable: {
                "ajax": {
                    "url": "/key/template/list"
                },
                "columns": [
                    {data: 'templateName', orderable: true},
                    {
                        data: 'isBuiltIn', orderable: false,
                        render: function (data, type, full) {
                            return flat.i18n("template.isBuiltIn" + data);
                        }
                    },
                    {
                        data: "node", orderable: false,
                        render: function (data, type, full) {
                            return flat.i18n("template.node" + data)
                        }
                    },
                    {
                        data: "status", orderable: true,
                        render: function (data, type, full) {
                            return flat.i18n('template.status' + data)
                        }
                    },
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
                    url: "/sys/user/" + $row.data().userId,
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

        $table.on('click', 'a.edit', function () {
            var $this = $(this);
            var $row = $table.DataTable().row($this.parents('tr')[0]);
            $.get("/sys/user/" + $row.data().templateId, function (data) {
                if (data.ok) {
                    var user = data.data.user;
                    var roleList = data.data.roleList;
                    var htmlTemplate = flat.remoteTemplate("/template/security/editUser.html", {
                        roleList: roleList,
                        user: user
                    });
                    $("#modalDialog").html(htmlTemplate).modal('show');
                    initUpdateBtn();
                } else {
                    flat.ajaxCallback(data);
                }
            });
        });

        $table.on('click', 'a.enable', function () {
            var $this = $(this);
            flat.showConfirm({
                confirmContent: flat.i18n("template.enableTips"),
                confirmBtn: flat.i18n("template.status1")
            });
            $("#confirmBtn").off("click").on("click", function () {
                var $row = $table.DataTable().row($this.parents('tr')[0]);
                $.ajax({
                    url: "/key/template/status/" + $row.data().templateId,
                    dataType: "json",
                    type: "POST",
                    data: {
                        status: 1
                    }
                }).done(function (data) {
                    if (flat.ajaxCallback(data)) {
                        grid.reload();
                        $("#confirmDialog").modal("hide");
                    }
                })
            })
        });

        $table.on('click', 'a.disable', function () {
            var $this = $(this);
            flat.showConfirm({
                confirmContent: flat.i18n("template.disableTips"),
                confirmBtn: flat.i18n("template.status0")
            });
            $("#confirmBtn").off("click").on("click", function () {
                var $row = $table.DataTable().row($this.parents('tr')[0]);
                $.ajax({
                    url: "/key/template/status/" + $row.data().templateId,
                    dataType: "json",
                    type: "POST",
                    data: {
                        status: 0
                    }
                }).done(function (data) {
                    if (flat.ajaxCallback(data)) {
                        grid.reload();
                        $("#confirmDialog").modal("hide");
                    }
                })
            })
        });

        function initUpdateBtn() {
            $("#addBtn").on('click', function () {
                if ($('#dialogForm').validate({
                    rules: {
                        repassword: {
                            equalTo: "#password"
                        }
                    }
                }).form()) {
                    $.ajax({
                        url: "/sys/user",
                        type: "POST",
                        dataType: "json",
                        data: {
                            nickName: $("#nickName").val(),
                            username: $("#username").val(),
                            password: $("#password").val(),
                            roleId: $("#roleId").val()
                        }
                    }).done(function (data) {
                        if (flat.ajaxCallback(data)) {
                            $("#modalDialog").modal('hide');
                            grid.reload()
                        }
                    })
                }
            })
        }

    };


    var handleEvents = function () {
        $("#addTemplate").on('click', function () {

        })
    };

    return {
        init: function () {
            handleTables();
            handleEvents();
        }
    }

}();
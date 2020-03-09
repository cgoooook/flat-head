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
                    "url": "/sys/user/list"
                },
                "columns": [
                    {data: 'nickName', orderable: true},
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
            $.get("/sys/user/" + $row.data().userId, function (data) {
                if (data.ok) {
                    var user = data.data.user;
                    var roleList = data.data.roleList;
                    var htmlTemplate = flat.remoteTemplate("/template/security/editUser.html", {roleList: roleList, user: user});
                    $("#modalDialog").html(htmlTemplate).modal('show');
                    initUpdateBtn();
                } else {
                    flat.ajaxCallback(data);
                }
            });
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
                            userId: $("#userId").val(),
                            nickName: $("#nickName").val(),
                            username: $("#username").val(),
                            password: $("#password").val() !== '' ? md5($("#password").val()) : "",
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
        $("#addUser").on('click', function () {
            $.ajax({
                url: "/sys/user/roleList",
                dataType: "json",
                type: "GET"
            }).done(function (data) {
                if (data.ok) {
                    var htmlTemplate = flat.remoteTemplate("/template/security/addUser.html", {roleList: data.data});
                    $("#modalDialog").html(htmlTemplate).modal('show');
                    initSaveBtn();
                }
            });
        });

        function initSaveBtn() {
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
                        type: "PUT",
                        dataType: "json",
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify({
                            nickName: $("#nickName").val(),
                            username: $("#username").val(),
                            password: md5($("#password").val()),
                            roleId: $("#roleId").val()
                        })
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


    return {
        init: function () {
            handleTables();
            handleEvents();
        }
    }

}();
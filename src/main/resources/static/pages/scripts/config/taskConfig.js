var task = function () {

    var grid;
    var $table;

    var handleTables = function () {
        grid = new Datatable();
        $table = $("#taskTables");
        grid.init({
            src: $table,
            dataTable: {
                "ajax": {
                    "url": "/task/config/list"
                },
                "columns": [
                    {data: 'taskName', orderable: true},
                    {data: 'algorithm', orderable: true},
                    {data: 'plannedQuantity', orderable: true},
                    {data: 'currentQuantity', orderable: true},
                    {data: 'status', orderable: true},
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
            })
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
                    url: "/task/config/status/" + $row.data().id,
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
                    url: "/task/config//status/" + $row.data().id,
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

    };


    var handleEvents = function () {
        $("#addTask").on('click', function () {
            $.get("/task/config/add", function (data) {
                if (data.ok) {
                    var htmlTemplate = flat.remoteTemplate("/template/task/addTask.html",
                        "");
                    $("#modalDialog").html(htmlTemplate).modal('show');
                    initAlgorithmSelect();
                    initSaveBtn();
                } else {
                    flat.ajaxCallback(data);
                }
            })
        });

        function initAlgorithmSelect() {
            var keyLengthDiv = $("#keyLength");
            $("#alg").on('change', function () {
                var $this = $(this);
                if ($this.val() === "RSA") {
                    var htmlTemplate = flat.remoteTemplate("/template/task/keyLength.html",
                        "");
                    keyLengthDiv.html(htmlTemplate);
                } else {
                    keyLengthDiv.html("");
                }
            })
        }

        $table.on('click', 'a.edit', function () {
            var $this = $(this);
            var $row = $table.DataTable().row($this.parents('tr')[0]);
            $.get("/task/config/" + $row.data().id, function (data) {
                if (data.ok) {
                    var task = data.data;
                    console.log(task);
                    var collections = ["1024","2048","3072","4096"];
                    var htmlTemplate = flat.remoteTemplate("/template/task/taskDetail.html", { reqData: task,cols:collections});
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
                        url: "/task/config/add",
                        type: "PUT",
                        dataType: "json",
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify({
                            taskName: $("#taskName").val(),
                            algorithm: $("#alg option:selected").val(),
                            length: $("#length option:selected").val(),
                            plannedQuantity: $("#num").val(),

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
                        url: "/task/config/edit",
                        type: "PUT",
                        dataType: "json",
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify({
                            id : $("#taskId").val(),
                            taskName: $("#taskName").val(),
                            algorithm: $("#alg option:selected").val(),
                            length: $("#length option:selected").val(),
                            plannedQuantity: $("#num").val(),
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





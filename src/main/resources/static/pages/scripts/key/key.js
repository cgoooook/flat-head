var Key = function () {

    var grid;
    var $table;

    var handleTables = function () {
        grid = new Datatable();
        $table = $("#datatables_ajax");
        grid.init({
            src: $table,
            onQuery: function (data) {
                data.orgId = $("#orgId").val()
            },
            dataTable: {
                "ajax": {
                    "url": "/key/key/list"
                },
                "columns": [
                    {data: 'keyName', orderable: true},
                    {data: 'keyAlg', orderable: true},
                    {data: 'length', orderable: true},
                    {data: 'orgName', orderable: true},
                    {data: 'checkValue', orderable: true},
                    {data: 'templateName', orderable: true},
                    {data: 'version', orderable: true},
                    {data: 'status', orderable: true,
                        render: function (data, type, full) {
                            return flat.i18n("key.status" + data);
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


        $table.on('click', 'a.inactive', function () {
            var $this = $(this);
            flat.showConfirm({
                confirmContent: flat.i18n("key.inactiveTips"),
                confirmBtn: flat.i18n("key.status3")
            });
            $("#confirmBtn").off("click").on("click", function () {
                var $row = $table.DataTable().row($this.parents('tr')[0]);
                $.ajax({
                    url: "/key/key/status/" + $row.data().keyId,
                    dataType: "json",
                    type: "POST",
                    data: {
                        status: 3
                    }
                }).done(function (data) {
                    if (flat.ajaxCallback(data)) {
                        grid.reload();
                        $("#confirmDialog").modal("hide");
                    }
                })
            })
        });

        $table.on('click', 'a.active', function () {
            var $this = $(this);
            flat.showConfirm({
                confirmContent: flat.i18n("key.activeTips"),
                confirmBtn: flat.i18n("key.status2")
            });
            $("#confirmBtn").off("click").on("click", function () {
                var $row = $table.DataTable().row($this.parents('tr')[0]);
                $.ajax({
                    url: "/key/key/status/" + $row.data().keyId,
                    dataType: "json",
                    type: "POST",
                    data: {
                        status: 2
                    }
                }).done(function (data) {
                    if (flat.ajaxCallback(data)) {
                        grid.reload();
                        $("#confirmDialog").modal("hide");
                    }
                })
            })
        });


        $table.on('click', 'a.archive', function () {
            var $this = $(this);
            flat.showConfirm({
                confirmContent: flat.i18n("key.archiveTips"),
                confirmBtn: flat.i18n("key.status4")
            });
            $("#confirmBtn").off("click").on("click", function () {
                var $row = $table.DataTable().row($this.parents('tr')[0]);
                $.ajax({
                    url: "/key/key/status/" + $row.data().keyId,
                    dataType: "json",
                    type: "POST",
                    data: {
                        status: 4
                    }
                }).done(function (data) {
                    if (flat.ajaxCallback(data)) {
                        grid.reload();
                        $("#confirmDialog").modal("hide");
                    }
                })
            })
        });

        $table.on('click', 'a.delete', function () {
            var $this = $(this);
            flat.showConfirm();
            $("#confirmBtn").off("click").on("click", function () {
                var $row = $table.DataTable().row($this.parents('tr')[0]);
                $.ajax({
                    url: "/key/key/status/" + $row.data().keyId,
                    dataType: "json",
                    type: "POST",
                    data: {
                        status: 5
                    }
                }).done(function (data) {
                    if (flat.ajaxCallback(data)) {
                        grid.reload();
                        $("#confirmDialog").modal("hide");
                    }
                })
            })
        });

        $table.on('click', 'a.detail', function () {
            var $this = $(this);
            var $row = $table.DataTable().row($this.parents('tr')[0]);
            var keyId = $row.data().keyId;
            $.get("/key/key/detail/" + keyId, function (data) {
                if (data.ok) {
                    var key = data.data.key;
                    var historyList = data.data.historyList;
                    var htmlTemplate = flat.remoteTemplate("/template/key/keyDetail.html",{key: key, historyList: historyList});
                    $("#modalDialog").html(htmlTemplate).modal('show');
                } else {
                    flat.ajaxCallback(data);
                }
            });


        });

    };

    var handleEvents = function () {
        $("#addKey").on('click', function () {
            $.get("/key/key/keyGenInfo", function (data) {
                if (data.ok) {
                    var orgList = data.data.orgList;
                    var templateList = data.data.templateList;
                    var rootKey = data.data.rootKey;
                    var htmlTemplate = flat.remoteTemplate("/template/key/addKey.html",
                        {orgList: orgList, templateList: templateList, rootKey: rootKey});
                    $("#modalDialog").html(htmlTemplate).modal('show');
                    initAddBtn();
                } else {
                    flat.ajaxCallback(data);
                }
            })
        });

        function initAddBtn() {
            $("#addBtn").on('click', function () {
                var validation = {};
                if ($('#dialogForm').validate(validation).form()) {
                    $.ajax({
                        url: "/key/key",
                        type: "PUT",
                        dataType: "json",
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify({
                            keyName: $('#keyName').val(),
                            keyAlg: $("#keyAlg").val(),
                            length: $("#length").val(),
                            templateId: $("#templateId").val(),
                            mode: $("#genMode").val(),
                            orgId :$("#orgIdSelect").val(),
                            collectionName: $("#collectionName").val()
                        })
                    }).done(function (data) {
                        if (flat.ajaxCallback(data)) {
                            $("#modalDialog").modal("hide");
                            grid.reload();
                        }
                    })
                }
            });

        }
    };

    var handleOrgTree = function () {
        var option = {
            url: "/sys/org/tree",
            clickCall: clickCall
        };
        zTreeOrg.init(option);

        function clickCall(event, treeId, treeNode, clickFlag) {
            if (treeNode.orgId) {
                $("#orgId").val(treeNode.orgId);
                grid.reload();
            }
        }
    };

    return {
        init: function () {
            handleTables();
            handleOrgTree();
            handleEvents();
        }
    }

}();
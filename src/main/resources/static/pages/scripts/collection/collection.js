var Collection = function () {

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
                    "url": "/key/collection/list"
                },
                "columns": [
                    {data: 'collectionName', orderable: true},
                    {data: 'orgName', orderable: true},
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
                    url: "/key/collection/" + $row.data().collectionId,
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
            $.get("/key/collection/" + $row.data().collectionId, function (data) {
                if (data.ok) {
                    var collection = data.data;
                    var htmlTemplate = flat.remoteTemplate("/template/collection/updateCollection.html", {collection: collection});
                    $("#modalDialog").html(htmlTemplate).modal('show');
                    initUpdateBtn();
                } else {
                    flat.ajaxCallback(data);
                }
            })
        });

        function initUpdateBtn() {
            $("#updateBtn").on("click", function () {
                if ($('#dialogForm').validate().form()) {
                    $.ajax({
                        url: "/key/collection",
                        type: "POST",
                        dataType: "json",
                        data: {
                            collectionId :$("#collectionId").val(),
                            collectionName: $("#collectionName").val(),
                        }
                    }).done(function (data) {
                        if (flat.ajaxCallback(data)) {
                            $("#modalDialog").modal("hide");
                            grid.reload();
                        }
                    })
                }
            })
        }
    };

    var handleEvents = function () {
        $("#addCollection").on('click', function () {
            $.get("/sys/org/childList", function (data) {
                if (data.ok) {
                    var orgList = data.data;
                    var htmlTemplate = flat.remoteTemplate("/template/collection/addCollection.html", {orgList: orgList});
                    $("#modalDialog").html(htmlTemplate).modal('show');
                    initAddBtn();
                } else {
                    flat.ajaxCallback(data);
                }
            })
        });

        function initAddBtn() {
            $("#addBtn").on('click', function () {
                if ($('#dialogForm').validate().form()) {
                    $.ajax({
                        url: "/key/collection",
                        type: "PUT",
                        dataType: "json",
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify({
                            orgId :$("#orgIdSelect").val(),
                            collectionName: $("#collectionName").val(),
                        })
                    }).done(function (data) {
                        if (flat.ajaxCallback(data)) {
                            $("#modalDialog").modal("hide");
                            grid.reload();
                        }
                    })
                }
            })
        }
    };

    var handleTree = function () {
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
            handleTree();
            handleEvents();
        }
    }

}();
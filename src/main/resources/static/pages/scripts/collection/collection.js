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
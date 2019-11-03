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
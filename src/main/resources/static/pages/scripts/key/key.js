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
        }
    }

}();
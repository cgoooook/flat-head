var Role = function () {
    var grid;
    var $table;

    var handleTables = function () {
        grid = new Datatable();
        $table = $("#ajax_datatables");
        grid.init({
            src: $table,
            dataTable: {
                "ajax": {
                    "url": "/sys/role/list"
                },
                "columns": [
                    {data: 'roleName', orderable: true},
                    {data: 'roleDescription', orderable: true},
                    {
                        data: 'operate', orderable: false,
                        render: function (data, type, full) {
                            return template("actionBtn", {data: data, type: type, full: full});
                        }
                    }
                ]
            }
        });

        $table.on('click', 'a.edit', function () {
            var $this = $(this);
            var $row = $table.DataTable().row($this.parents('tr')[0]);
            $.get("/sys/role/" + $row.data().roleId, function (data) {
                if (data.ok) {
                    var role = data.data.role;
                    var menu = data.data.menu;
                    var htmlTemplate = flat.remoteTemplate("/template/security/editRole.html", {role: role});
                    $("#modalDialog").html(htmlTemplate).modal('show');
                    menuTreeInit(menu);
                    initUpdateBtn();
                } else {
                    flat.ajaxCallback(data);
                }
            });
        });

        $table.on('click', 'a.delete', function () {
            var $this = $(this);
            flat.showConfirm();
            $("#confirmBtn").off("click").on("click", function () {
                var $row = $table.DataTable().row($this.parents('tr')[0]);
                $.ajax({
                    url: "/sys/role/" + $row.data().roleId,
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

    function initUpdateBtn() {
        $("#updateBtn").on('click', function () {
            if ($("#dialogForm").validate().form()) {
                var role = {
                    roleId: $("#roleId").val(),
                    roleName: $("#roleName").val(),
                    roleDescription: $("#description").val(),
                    permTokens: getPermTokens()
                };
                $.ajax({
                    url: "/sys/role/updateRole",
                    data: JSON.stringify(role),
                    type: "post",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json"
                }).done(function (data) {
                    if (flat.ajaxCallback(data)) {
                        $table.DataTable().draw();
                        $("#modalDialog").modal('hide');
                    }
                })
            }
        })
    }

    var handleEvents = function () {
        $("#addRole").on('click', function () {
            var htmlTemplate = flat.remoteTemplate("/template/security/addRole.html", {});
            $("#modalDialog").html(htmlTemplate).modal("show");
            $.get("/sys/role/getMenuList", function (data) {
                menuTreeInit(data.data);
            });
            initAddBtnInit();
        })
    };

    function initAddBtnInit() {
        $("#addBtn").on('click', function () {
            if ($("#dialogForm").validate().form()) {
                var role = {
                    roleName: $("#roleName").val(),
                    roleDescription: $("#description").val(),
                    permTokens: getPermTokens()
                };
                $.ajax({
                    url: "/sys/role",
                    data: JSON.stringify(role),
                    type: "put",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json"
                }).done(function (data) {
                    if (flat.ajaxCallback(data)) {
                        grid.reload();
                        $("#modalDialog").modal('hide');
                    }
                })
            }
        })
    }

    var setting = {
        view: {
            addDiyDom: addDiyDom,
            selectedMulti: false,
            showIcon: false,
            showLine: true
        },
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: {"Y": "ps", "N": "ps"}
        },
        data: {
            simpleData: {
                enable: true,
                rootId: -1,
                idKey: "menuId",
                pIdKey: "parentId"
            },
            keep: {
                parent: true
            }
        },
        callback: {
            beforeClick: beforeClick
        }
    };

    function addDiyDom(treeId, treeNode) {
        var menuIcon = treeNode.icon == undefined ? "" : treeNode.icon;
        var afterString =
            "<span class='diy-dom font-blue'>menuId:</span>" + treeNode.menuId +
            "<span class='diy-dom font-blue'>permToken:</span>" + treeNode.permToken +
            "<span class='diy-dom font-blue'>level:</span>" + treeNode.level;
        if (treeNode.url) {
            afterString = afterString + "<span class='diy-dom font-blue'>url:</span>" + treeNode.url;
        }
        if (treeNode.memo) {
            afterString = afterString + "<span class='diy-dom font-blue'>备注:</span>" + treeNode.memo;
        }
        $( "#" + treeNode.tId + "_a").before("<i class='" + menuIcon + "'></i>").after(afterString);

    }

    function beforeClick(treeNode) {
        var zTreeRole = $.fn.zTree.getZTreeObj("treeDemo");
        zTreeRole.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
    }

    function getPermTokens() {
        var nodes = treeObj.getCheckedNodes();
        var permTokenObj = {};
        for (var node in nodes) {
            if (nodes[node].permToken !== "") {
                permTokenObj[nodes[node].permToken] = nodes[node].permToken;
            }
        }
        var permTokens = [];
        for (node in permTokenObj) {
            permTokens.push(permTokenObj[node]);
        }
        return permTokens;
    }

    var treeObj;

    function menuTreeInit(data) {
        for (var i in data) {
            data[i].name = data[i].menuName;
        }
        treeObj = $.fn.zTree.init($("#treeDemo"), setting, data);
        treeObj.expandAll(true);
    }

    return {
        init: function () {
            handleTables();
            handleEvents();
        }
    }

}();
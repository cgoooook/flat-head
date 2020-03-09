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

        $table.on('click', 'a.keyMange', function () {
            var $this = $(this);
            var $row = $table.DataTable().row($this.parents('tr')[0]);
            $.get("/key/collection/getCollectionKeys?collectionId=" + $row.data().collectionId, function (data) {
                if (data.ok) {
                    var orgId = $row.data().orgId;
                    var key = data.data;
                    var htmlTemplate = flat.remoteTemplate("/template/collection/collectionKey.html", {keys: key,
                        orgId: orgId, collectionId: $row.data().collectionId});
                    $("#modalDialog").html(htmlTemplate).modal('show');
                    initSubKeyDelete();
                    initAddSubKey();
                } else {
                    flat.ajaxCallback(data);
                }
            })
        });

        function initSubKeyDelete() {
            $("a.deleteSubKey").on("click", function () {
                var $this = $(this);
                flat.showConfirm();
                $("#confirmBtn").off("click").on("click", function () {
                    var $row = $table.DataTable().row($this.parents('tr')[0]);
                    $.ajax({
                        url: "/key/collection/delSubKey",
                        dataType: "json",
                        type: "DELETE",
                        data: {
                            collectionId: $("#collectionIdSub").val(),
                            keyId: $this.attr("attr-keyId")
                        }
                    }).done(function (data) {
                        if (flat.ajaxCallback(data)) {
                            $("#confirmDialog").modal("hide");
                            var orgId = $("#subOrgId").val();
                            var collectionId = $("#collectionIdSub").val();
                            $.get("/key/collection/getCollectionKeys?collectionId=" + collectionId, function (data) {
                                if (data.ok) {
                                    var key = data.data;
                                    var htmlTemplate = flat.remoteTemplate("/template/collection/collectionKey.html", {keys: key,
                                        orgId: orgId, collectionId: collectionId});
                                    $("#modalDialog").html(htmlTemplate);
                                    initSubKeyDelete();
                                    initAddSubKey();
                                } else {
                                    flat.ajaxCallback(data);
                                }
                            })
                        }
                    })
                })
            });
        }

        function initAddSubKey() {
            $("#addKey").on("click", function () {
                var orgId = $("#subOrgId").val();
                $.get('/key/collection/getKeyListByOrgId?orgId=' + orgId + "&collectionId=" + $("#collectionIdSub").val(), function (data) {
                    if (data.ok) {
                        var keys = data.data;
                        var htmlTemplate = flat.remoteTemplate("/template/collection/addSubKey.html", {keys: keys});
                        $("#modalDialogSub").html(htmlTemplate).modal('show');
                        initCheck();
                        initAddSubKeyBtn();
                    } else {
                        flat.ajaxCallback(data);
                    }
                });

            });
        }

        function initCheck() {
            $("table.subKeyTable").on("click", "input", function () {
                var $this = $(this);
                var checkBoxes = $("table.subKeyTable").find("input[type=checkbox]");
                if ($this.hasClass("checkAll")) {
                    if ($this.is(':checked')) {
                        $.each(checkBoxes, function () {
                            var $this = $(this);
                            $this.prop("checked", true)
                        })
                    } else {
                        $.each(checkBoxes, function () {
                            var $this = $(this);
                            $this.prop("checked", false)
                        })
                    }
                } else {
                    if ($this.is(':checked')) {
                        var checkNum = $("table.subKeyTable").find("input[type=checkbox]:checked").length;
                        if (checkNum === checkBoxes.length - 1) {
                            $.each(checkBoxes, function () {
                                var $this = $(this);
                                $this.prop("checked", true)
                            })
                        }
                    } else {
                        var checkNum = $("table.subKeyTable").find("input[type=checkbox]:checked").length;
                        if (checkNum !== checkBoxes.length) {
                            $(".checkAll").prop("checked", false)
                        }
                    }
                }

            })
        }

        function initAddSubKeyBtn() {
            $("#addSubKey").on("click", function () {
                var keyIds = [];
                var $input = $("input.singleCheck:checked");
                if ($input.length <= 0) {
                    toast.error("请选要添加的密钥");
                    return;
                }
                    $.each($input, function () {
                    keyIds.push($(this).attr("attr-keyId"));
                });
                $.ajax({
                    url: "/key/collection/addSubKeys",
                    type: "POST",
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify({
                        collectionId :$("#collectionIdSub").val(),
                        keyIds: keyIds
                    })
                }).done(function (data) {
                    if (flat.ajaxCallback(data)) {
                        $("#modalDialogSub").modal('hide');
                        var orgId = $("#subOrgId").val();
                        var collectionId = $("#collectionIdSub").val();
                        $.get("/key/collection/getCollectionKeys?collectionId=" + collectionId, function (data) {
                            if (data.ok) {
                                var key = data.data;
                                var htmlTemplate = flat.remoteTemplate("/template/collection/collectionKey.html", {keys: key,
                                    orgId: orgId, collectionId: collectionId});
                                $("#modalDialog").html(htmlTemplate);
                                initSubKeyDelete();
                                initAddSubKey();
                            } else {
                                flat.ajaxCallback(data);
                            }
                        })
                    }
                })
            })
        }

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
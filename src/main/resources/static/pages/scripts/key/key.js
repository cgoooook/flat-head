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
                    {
                        data: 'status', orderable: true,
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
                    var htmlTemplate = flat.remoteTemplate("/template/key/keyDetail.html", {
                        key: key,
                        historyList: historyList
                    });
                    $("#modalDialog").html(htmlTemplate).modal('show');
                } else {
                    flat.ajaxCallback(data);
                }
            });


        });

    };

    var handleEvents = function () {

        $table.on('click', 'a.update', function () {
            var $this = $(this);
            var $row = $table.DataTable().row($this.parents('tr')[0]);
            var keyId = $row.data().keyId;
            $.get("/key/key/detail/" + keyId, function (data) {
                if (data.ok) {
                    var key = data.data.key;
                    var htmlTemplate = flat.remoteTemplate("/template/key/updateKey.html", {key: key});
                    $("#modalDialog").html(htmlTemplate).modal('show');
                    if (key.mode === "compose") {
                        composeSelectInit();
                    }
                    initUpdateBtn();
                } else {
                    flat.ajaxCallback(data);
                }
            });


        });

        function initUpdateBtn() {
            $("#addBtn").on('click', function () {
                var validation = {};
                var genMode = $("#genMode").val();
                var data = {};
                if (genMode === "derive") {
                    data = {
                        keyId: $("#keyId").val(),
                        mode: genMode,
                        deriveParam: $("#deriveParams").val()
                    }
                } else if (genMode === 'compose') {
                    var composes = [];
                    var comNums = $("#genModeComposeNum").val();
                    var rules = {};
                    for (var i = 1; i <= comNums; i++) {
                        rules['confirmCompose' + i] = {equalTo: "#compose" + i}
                        composes.push($("#compose" + i).val())
                    }
                    validation = {
                        rules: rules
                    };

                    data = {
                        keyId: $("#keyId").val(),
                        mode: genMode,
                        orgId: $("#orgIdSelect").val(),
                        composes: composes
                    };

                } else {
                    data = {
                        keyId: $("#keyId").val(),
                        mode: genMode
                    }

                }
                if ($('#dialogForm').validate(validation).form()) {
                    $.ajax({
                        url: "/key/key",
                        type: "POST",
                        dataType: "json",
                        data: data
                    }).done(function (data) {
                        if (flat.ajaxCallback(data)) {
                            $("#modalDialog").modal("hide");
                            grid.reload();
                        }
                    })
                }
            });
        }

        $("#addKey").on('click', function () {
            $.get("/key/key/keyGenInfo", function (data) {
                if (data.ok) {
                    var orgList = data.data.orgList;
                    var templateList = data.data.templateList;
                    var rootKey = data.data.rootKey;
                    var htmlTemplate = flat.remoteTemplate("/template/key/addKey.html",
                        {orgList: orgList, templateList: templateList, rootKey: rootKey});
                    $("#modalDialog").html(htmlTemplate).modal('show');
                    orgSelectInit();
                    initGenMode();
                    initAddBtn();
                } else {
                    flat.ajaxCallback(data);
                }
            })
        });

        $("#importKey").on('click', function () {
            var htmlTemplate = flat.remoteTemplate("/template/key/importKey.html", {});
            $("#modalDialog").html(htmlTemplate).modal('show');
            $("#keyBackData").on("change", function () {
                var obj = document.getElementById("keyBackData");
                $("#fileName").html(obj.files[0].name);
            });
            $("#importBtn").on("click", function () {
                var obj = document.getElementById("keyBackData");
                var len = obj.files.length;
                if (len === 0) {
                    toast.error("请选择文件");
                    return;
                }
                var formData = new FormData();
                formData.append("keyBackFile",obj.files[0]);
                $.ajax({
                    url: '/key/key/importKey',
                    type: 'POST',
                    processData: false,
                    contentType: false,
                    data: formData
                }).done(function (data) {
                    if (flat.ajaxCallback(data)) {
                        $("#modalDialog").modal("hide");
                        grid.reload();
                    }
                });
            })

        });

        function initGenMode() {
            $("#genMode").on('change', function () {
                $("#genModeComposeNum").removeAttr("required");
                $("#genModeCompose").css("display", "none");
                var $this = $(this);
                if ($this.val() === 'random') {
                    $("#genModeCompose").css("display", "none");
                    return;
                }
                if ($this.val() === 'derive') {
                    $('#innerDom').html("");
                    var htmlTemplate = flat.remoteTemplate("/template/key/derive.html", {});
                    $('#innerDom').html(htmlTemplate);
                    if ($("#orgIdSelect").val() !== '') {
                        $.get("/key/key/getOrg?orgId=" + $("#orgIdSelect").val(), function (data) {
                            if (data.ok) {
                                var org = data.data;
                                $("#deriveParams").val(org.orgCode)
                            }
                        })
                    }
                }

                if ($this.val() === 'compose') {
                    $("#genModeCompose").css("display", "block");
                    $("#genModeComposeNum").attr("required", "required");
                    composeSelectInit();

                }
            })
        }

        function composeSelectInit() {
            $("#genModeComposeNum").off().on('change', function () {
                var $this = $(this);
                var composeNum = $this.val() / 1;
                var composeArray = [];
                for (var i = 0; i < composeNum; i++) {
                    composeArray.push(i + 1);
                }
                var htmlTemplate = flat.remoteTemplate("/template/key/compose.html", {composes: composeArray});
                $('#innerDom').html(htmlTemplate);
            })
        }

        function orgSelectInit() {
            $("#orgIdSelect").on('change', function () {
                var $this = $(this);
                if ($this.val() !== '' && $("#genMode").val() === 'derive') {
                    $.get("/key/key/getOrg?orgId=" + $("#orgIdSelect").val(), function (data) {
                        if (data.ok) {
                            var org = data.data;
                            $("#deriveParams").val(org.orgCode)
                        }
                    })
                }
            })
        }

        function initAddBtn() {
            $("#addBtn").on('click', function () {
                var validation = {};
                var genMode = $("#genMode").val();
                var data = {};
                if (genMode === "derive") {
                    data = {
                        keyName: $('#keyName').val(),
                        keyAlg: $("#keyAlg").val(),
                        length: $("#length").val(),
                        templateId: $("#templateId").val(),
                        mode: genMode,
                        orgId: $("#orgIdSelect").val(),
                        deriveParam: $("#deriveParams").val()
                    }
                } else if (genMode === 'compose') {
                    var composes = [];
                    var comNums = $("#genModeComposeNum").val();
                    var rules = {};
                    for (var i = 1; i <= comNums; i++) {
                        rules['confirmCompose' + i] = {equalTo: "#compose" + i}
                        composes.push($("#compose" + i).val())
                    }
                    validation = {
                        rules: rules
                    };

                    data = {
                        keyName: $('#keyName').val(),
                        keyAlg: $("#keyAlg").val(),
                        length: $("#length").val(),
                        templateId: $("#templateId").val(),
                        mode: genMode,
                        orgId: $("#orgIdSelect").val(),
                        composes: composes
                    };

                } else {
                    data = {
                        keyName: $('#keyName').val(),
                        keyAlg: $("#keyAlg").val(),
                        length: $("#length").val(),
                        templateId: $("#templateId").val(),
                        mode: genMode,
                        orgId: $("#orgIdSelect").val()
                    }

                }
                if ($('#dialogForm').validate(validation).form()) {
                    $.ajax({
                        url: "/key/key",
                        type: "PUT",
                        dataType: "json",
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify(data)
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
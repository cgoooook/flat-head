var Template = function () {

    var grid;
    var $table;
    var handleTables = function () {


        grid = new Datatable();
        $table = $("#datatables_ajax");
        grid.init({
            src: $table,
            dataTable: {
                "ajax": {
                    "url": "/key/template/list"
                },
                "columns": [
                    {data: 'templateName', orderable: true},
                    {
                        data: 'isBuiltIn', orderable: false,
                        render: function (data, type, full) {
                            return flat.i18n("template.isBuiltIn" + data);
                        }
                    },
                    {
                        data: "node", orderable: false,
                        render: function (data, type, full) {
                            return flat.i18n("template.node" + data)
                        }
                    },
                    {
                        data: "status", orderable: true,
                        render: function (data, type, full) {
                            return flat.i18n('template.status' + data)
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

        $table.on('click', 'a.delete', function () {
            var $this = $(this);
            flat.showConfirm();
            $("#confirmBtn").off("click").on("click", function () {
                var $row = $table.DataTable().row($this.parents('tr')[0]);
                $.ajax({
                    url: "/key/template/" + $row.data().templateId,
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
            $.get("/key/template/" + $row.data().templateId, function (data) {
                if (data.ok) {
                    var template = data.data;
                    var htmlTemplate = flat.remoteTemplate("/template/template/updateTemplate.html", {
                        template: template
                    });
                    $("#modalDialog").html(htmlTemplate).modal('show');
                    initDatePicker();
                    initUsagesChecker(template);
                    initUpdateBtn();
                } else {
                    flat.ajaxCallback(data);
                }
            });
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
                    url: "/key/template/status/" + $row.data().templateId,
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
                    url: "/key/template/status/" + $row.data().templateId,
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

        function initUsagesChecker(template) {
            var usages = template.keyUsages;
            var usagesArray = usages.split(",");
            var usageChecker = $("input[name=keyUsages]");
            $.each(usageChecker, function () {
                if (usagesArray.indexOf($(this).val()) !== -1) {
                    $(this).attr("checked", "checked")
                }
            });
            flat.handleUniform();
        }

        function initUpdateBtn() {
            $("#addBtn").on('click', function () {
                if ($('#dialogForm').validate().form()) {
                    var usages = getKeyUsages();
                    if (usages.length === 0) {
                        toast.error(flat.i18n("template.noUsageSelect"));
                        return;
                    }
                    $.ajax({
                        url: "/key/template",
                        type: "POST",
                        dataType: "json",
                        data: {
                            templateId: $("#templateId").val(),
                            templateName: $("#templateName").val(),
                            node: $("#node").val(),
                            startDate: $("#startDate").val(),
                            endDate: $("#endDate").val(),
                            keyUsages: usages.join(","),
                            extendUsages: $("#extendUsgages").val()
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

    function initDatePicker() {
        $("input.datepicker").datetimepicker({
            isRTL: App.isRTL(),
            language: DATETIME_PICKER,
            format: "yyyy-mm-dd HH:mm:ss",
            autoclose: true,
            todayBtn: true,
            pickerPosition: (App.isRTL() ? "bottom-right" : "bottom-left"),
            minuteStep: 10
        });
    }

    var getKeyUsages = function () {
        var checkedUsages = $("input[name=keyUsages]:checked");
        var usages = [];
        $.each(checkedUsages, function () {
            usages.push($(this).val())
        });
        return usages;
    };

    function initSaveBtn() {
        $("#addBtn").on('click', function () {
            if ($('#dialogForm').validate().form()) {
                var usages = getKeyUsages();
                if (usages.length === 0) {
                    toast.error(flat.i18n("template.noUsageSelect"))
                    return;
                }
                $.ajax({
                    url: "/key/template",
                    type: "PUT",
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify({
                        templateName: $("#templateName").val(),
                        node: $("#node").val(),
                        startDate: $("#startDate").val(),
                        endDate: $("#endDate").val(),
                        keyUsages: usages.join(","),
                        extendUsages: $("#extendUsgages").val()
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


    var handleEvents = function () {
        $("#addTemplate").on('click', function () {
            var htmlTemplate = flat.remoteTemplate("/template/template/addTemplate.html", {});
            $("#modalDialog").html(htmlTemplate).modal('show');
            initDatePicker();
            flat.handleUniform();
            initSaveBtn();
        })
    };

    return {
        init: function () {
            handleTables();
            handleEvents();
        }
    }

}();
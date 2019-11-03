var device = function () {

    var grid;
    var $table;

    var handleTables = function () {
        grid = new Datatable();
        $table = $("#devTables");
        grid.init({
            src: $table,
            dataTable: {
                "ajax": {
                    "url": "/sys/device/list"
                },
                "columns": [
                    {data: 'deviceCode', orderable: true},
                    {data: 'deviceName', orderable: true},
                    {data: 'deviceIp', orderable: true},
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
                    url: "/sys/device/" + $row.data().deviceId,
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


    var handleEvents = function () {
        $("#addDev").on('click', function (){
            $.get("/sys/org/childList", function (data) {
                if (data.ok) {

                    var htmlTemplate = flat.remoteTemplate("/template/device/addDev.html",  {"orgList":data.data});
                    $("#modalDialog").html(htmlTemplate).modal('show');
                    initSaveBtn();
                } else {
                    flat.ajaxCallback(data);
                }
            });



        });


        $table.on('click', 'a.edit', function () {
            var $this = $(this);
            var $row = $table.DataTable().row($this.parents('tr')[0]);
            $.get("/sys/device/" + $row.data().deviceCode, function (data) {
                if (data.ok) {
                    var dev = data.data;
                    console.log(dev);
                    var collections = data.data.collectionIds
                    var htmlTemplate = flat.remoteTemplate("/template/device/editDev.html", { reqData: dev,cols:collections});
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
                        url: "/sys/device",
                        type: "PUT",
                        dataType: "json",
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify({
                            deviceCode: $("#devCode").val(),
                            deviceName: $("#devName").val(),
                            deviceIp: $("#devIp").val(),
                            orgId: $("#orgName option:selected").val(),
                            collectionId: $("#keys option:selected").val(),

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
                        url: "/sys/device/edit",
                        type: "PUT",
                        dataType: "json",
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify({
                            deviceId  : $("#deviceId").val(),
                            deviceCode: $("#deviceCode").val(),
                            deviceName: $("#devName").val(),
                            deviceIp: $("#devIp").val(),
                            orgId: $("#orgName option:selected").val(),
                            collectionId: $("#keys option:selected").val(),
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

$('#jstree_div a').on('click', function () {
    alert("begin");
    //get_selected返回选中的列
    console.log($('#jstree_div').jstree().get_selected(true));
});
function orgChange(data) {
    if(data!=null&&data!=""&&data!=undefined){
        $.ajax({
            url: "/key/collection/collection/"+data,
            type: "get",
            dataType: "json",
            success: function(result){
                console.log(result.data);
                //    <option value="{{org.orgId}}">{{org.orgName}}</option>
                var html ;
                for(var i=0;i<result.data.length;i++){
                    html+= "<option value='"+result.data[i].collectionId+"'>"+result.data[i].collectionName+"</option>";

                }
                $("#keys").html(html);
            }
        })
    }

}
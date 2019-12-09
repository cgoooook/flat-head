var audit = function () {

    var grid;
    var $table;

    var handleTables = function () {

        grid = new Datatable();
        $table = $("#ajax_tables");
        grid.init({
            src: $table,
            onQuery:function (data) {
                data.operateType=$("#operationType option:selected").val();
                data.operatorResult= Number($("#operateResultQuery option:selected").val());
                data.operateTimeBegin=$("#beginTime").val();
                data.operateTimeEnd=$("#endTime").val();

            },
            dataTable: {
                ajax: {
                    method:"POST",
                    url: "/log/audit/list"
                },
                "columns": [
                    {data: 'operateType', orderable: true},
                    {data: 'operateUser', orderable: true},
                    {data: 'operateTime', orderable: true,render: function (data, type, full) {
                            var unixTimestamp = new Date( data) ;
                            commonTime = unixTimestamp.toLocaleString();
                            return commonTime;
                        }},
                    {data: 'operatorResult', orderable: true,
                        render: function (data, type, full) {
                            return flat.i18n("operator.status" + data);
                        }},
                    {data: 'operateContent', orderable: true},
                    {data: 'operate', orderable: false,
                        render: function (data, type, full) {
                        console.log(full);
                            return template("actionBtn", {data: data, type: type, full: full});
                        }}
                ]
            }
        });

        $table.on('click', 'a.audit', function () {
            var $this = $(this);
            var $row = $table.DataTable().row($this.parents('tr')[0]);
            var logId = $row.data().logId;
            alert(logId);
            $.ajax({
                url: " /log/audit/audit",
                dataType: "json",
                type: "POST",
                data: {
                    logId: logId
                }
            }).done(function (data) {
                if (flat.ajaxCallback(data)) {
                    grid.reload();
                }
            })

        });






    };
    Date.prototype.toLocaleString = function() {
        return this.getFullYear() + "/" + (this.getMonth() + 1) + "/" + this.getDate() + "/ " + this.getHours() + ":" + this.getMinutes() + ":" + this.getSeconds();
    };



    return {
        init: function () {
            handleTables();


        }
    }

}();
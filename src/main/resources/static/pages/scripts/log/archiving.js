var archiving = function () {

    var handleinit= function () {

        $("#archiving").on('click', function () {
            var operationTime = $("#reportrange span").html();
            var ationTime = operationTime.split(" - ")[0];
            var endTime = operationTime.split(" - ")[1];
            var data =JSON.stringify({
                operateType : $("#type option:selected").val(),
                operatorResult : $("#result option:selected").val(),
                strategy : $("#strategy option:selected").val(),
                operateTimeBegin :ationTime,
                operateTimeEnd :endTime,
                maxActive :$("#maxActive").val()
            });
            console.log(data);
            $.ajax({
                type: "POST",
                url: "/log/archiving/archiving",
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data:data
            }).done(function (data) {
                if (flat.ajaxCallback(data)) {
                    //成功后逻辑
                }
            });


        });




    };

    function changeFunc() {
        $("#strategy").on('change',function(){
            var $this = $(this);
            var star = $this.val();
            if (star === 'day') {
                $("#dayArchive").css("display", "block");
                $("#timeArchive").css("display", "none");
            } else {
                $("#dayArchive").css("display", "none");
                $("#timeArchive").css("display", "block");
            }
            if($("#strategy option:selected").val()=="day"){
                $("#beginTime").removeAttr("name");
                $("#endTime").removeAttr("name");
                if($("#maxActive").attr("name")==""||$("#maxActive").attr("name")==null||$("#maxActive").attr("name")==undefined){

                    $("#maxActive").attr("name","day")
                }


            }else if($("#strategy option:selected").val()=="time"){
                $("#maxActive").removeAttr("name");

                $("#endTime").removeAttr("name");
                if($("#beginTime").attr("name")==""||$("#beginTime").attr("name")==null||$("#beginTime").attr("name")==undefined){

                    $("#maxActive").attr("name","day")
                }

            }


        });
    }



    function initDatePicker() {
        $('#reportrange').daterangepicker({
                opens: (App.isRTL() ? 'left' : 'right'),
                startDate: moment().subtract('', 29),
                endDate: moment(),
                dateLimit: {
                    days: 60
                },
                showDropdowns: true,
                showWeekNumbers: false,
                timePicker: true,
                timePickerIncrement: 2,
                timePicker12Hour: true,
                ranges: {
                    '今天': [moment(), moment()],
                    '昨天': [moment().subtract('days', 1), moment().subtract('days', 1)],
                    '最近7天': [moment().subtract('days', 6), moment()],
                    '最近30天': [moment().subtract('days', 29), moment()],
                    '这个月': [moment().startOf('month'), moment().endOf('month')],
                    '上个月': [moment().subtract('month', 1).startOf('month'), moment().subtract('month', 1).endOf('month')]
                },
                buttonClasses: ['btn'],
                applyClass: 'green',
                cancelClass: 'default',
                separator: ' 至 ',
                locale: {
                    format: "YYYY-MM-DD HH:mm:ss",
                    applyLabel: '确认',
                    cancelLabel: '清除',
                    fromLabel: '从',
                    toLabel: '至',
                    customRangeLabel: '自定义范围',
                    daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
                    monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
                    firstDay: 1
                }
            },
            function (start, end) {
                $('#reportrange span').html(start.format('YYYY-MM-DD HH:mm:ss') + ' - ' + end.format('YYYY-MM-DD HH:mm:ss'));
            }
        );
        $('#reportrange span').html(moment().subtract('days', 29).format('YYYY-MM-DD HH:mm:ss') + ' - ' + moment().format('YYYY-MM-DD HH:mm:ss'));
    }


    return {
        init: function () {
            handleinit();
            initDatePicker();
            changeFunc();
        }
    }

}();
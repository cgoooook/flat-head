var archiving = function () {

    var handleinit= function () {

        $("#archiving").on('click', function () {
            if($("#type option:selected").val()==""||$("#type option:selected").val()==null||$("#type option:selected").val()==undefined){
                toast.warning($.i18n.prop('archiving.type.select'));
                return;
            }
            if($("#result option:selected").val()==""||$("#result option:selected").val()==null||$("#result option:selected").val()==undefined){
                toast.warning($.i18n.prop('archiving.result.select'));
                return;
            }
            var operationTime = $("#defaultrange input").val();
            var ationTime = operationTime.split(" - ")[0];
            var endTime = operationTime.split(" - ")[1];
            var data =JSON.stringify({
                operateType : $("#type option:selected").val(),
                operatorResult : $("#result option:selected").val(),
                operateTimeBegin :ationTime,
                operateTimeEnd :endTime,
            });
            console.log(data);
            $.ajax({
                type: "POST",
                url: "/log/archiving/archiving",
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data:data
            }).done(function (data) {
                if(data.returnState=="OK"){
                    if(data.msg=="ok"){
                        toast.success($.i18n.prop('archiving.archiving.success'));
                    }else if(data.msg=="null"){
                        toast.warning($.i18n.prop('archiving.archiving.warning'));
                    }

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
        });
    }



    function initDatePicker() {
        $('#defaultrange').daterangepicker({
                opens: (App.isRTL() ? 'left' : 'right'),
                separator: ' 至 ',
                startDate: moment().subtract('days', 29),
                endDate: moment(),
                ranges: {
                    '今天': [moment(), moment()],
                    '昨天': [moment().subtract('days', 1), moment().subtract('days', 1)],
                    '最近7天': [moment().subtract('days', 6), moment()],
                    '最近30天': [moment().subtract('days', 29), moment()],
                    '这个月': [moment().startOf('month'), moment().endOf('month')],
                    '上个月': [moment().subtract('month', 1).startOf('month'), moment().subtract('month', 1).endOf('month')]
                },
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
                },
            },
            function (start, end) {
                $('#defaultrange input').val(start.format('YYYY-MM-DD HH:mm:ss') + ' - ' + end.format('YYYY-MM-DD HH:mm:ss'));
            }
        );
        $('#defaultrange input').val(moment().subtract('days', 29).format('YYYY-MM-DD HH:mm:ss') + ' - ' + moment().format('YYYY-MM-DD HH:mm:ss'));
    }



    return {
        init: function () {
            handleinit();
            initDatePicker();

        }
    }

}();
var ServerLog = function () {

    var handleEvents = function () {
        $("#logTable").on("click", 'a.detail', function () {
            var $this = $(this);
            $.post("/log/serverLog/detail?fileName=" + $this.attr("fileName"), function (data) {
                if (data.ok) {
                    var htmlTemplate = flat.remoteTemplate("/template/log/serverLogDetail.html", {});
                    $("#modalDialog").html(htmlTemplate).modal('show');
                    var textarea = $("#log").append(data.data);
                    textarea.scrollTop(textarea[0].scrollHeight - textarea.height());
                } else {
                    flat.ajaxCallback(data);
                }
            })
        })
    };

    return {
        init: function () {
            handleEvents();
        }
    }

}();
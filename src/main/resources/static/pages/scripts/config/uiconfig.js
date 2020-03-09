var uiConfig = function () {


    var handleConfig = function () {
        $.ajax({
            type: "GET",
            url: "/ui/config/info",
            dataType: "json",
            success: function(result) {

                if (result.returnState == "OK") {
                    $("#ide").val(result.data.copyright);
                    $("#picImg").attr("src", "data:image/png;base64," + result.data.logo);
                }
            }
        });



        $('#uploadSubmit').click(function () {
            var img = document.getElementById("picID");
            var logo =img.files[0];
            console.log(logo);
            var copyright = $("#ide").val();
            var formData = new FormData();
            formData.append("logo",logo);
            formData.append("copyright",copyright);
            $.ajax({
                url: '/ui/config/upload',
                type: 'POST',
                processData: false,
                contentType: false,
                data: formData
            }).done(function (data) {
                if (flat.ajaxCallback(data)) {
                    //成功后逻辑
                }
            });
        });

    };



    return {
        init: function () {
            handleConfig();
        }
    }

}();


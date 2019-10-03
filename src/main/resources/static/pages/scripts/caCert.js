var caCert = function () {

    var handleEvents = function () {
       $("#addCA").on("click", function () {
           $("#modalDialog").modal("show");
           $("#modalDialog").on("shown.bs.modal", function(){
               $(".select2").select2();
           });
       });

       $("#generate").on('click', function () {
           $.ajax({
               url: '/cacert/generate',
               type: 'post',
               dataType:'json',
               data: {
                   subject: $("#subject").val(),
                   alg: $("#alg").val(),
                   algLength: $("#algLength").val(),
                   signAlg: $("#signAlg").val()
               }
           }).done(function (data) {
               if (data.ok) {
                   alert("产生成功");
                    window.location.reload()
               }
           })
       })


    };

    return {
        init: function () {
            $.fn.modal.Constructor.prototype.enforceFocus = function() {};
            handleEvents();
        }
    }

}();
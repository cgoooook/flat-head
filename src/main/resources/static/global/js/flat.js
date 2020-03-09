var flat = function () {

    var _i18nInit = function () {
        $.i18n.properties({
            path: '/i18n/',
            name: 'messages',
            language: LANG_COUNTRY,
            mode: 'both'
        });
    };

    var _initMenu = function () {
        var dtd = $.Deferred();
        var $sidebarWrapper = $(".page-sidebar-menu");
        if ($sidebarWrapper.size() === 1) {
            $.ajax({
                url: "/menu/sidebar" + "?random=" + Math.random(),
                dataType: "json",
                type: "get"
            }).done(function (data) {
                var sidebarData = data.data;
                var sidebarHtml = flat.remoteTemplate("/template/menu.html", sidebarData);
                localStorage["SidebarHtml"] = sidebarHtml;
                $sidebarWrapper.html(sidebarHtml);
                dtd.resolve();
            });
        }
        return dtd.promise();
    };

    function _initMenuStyle() {
        var $activeMenu = $("#menu_" + $("#menuId").val()).addClass("active");
        $activeMenu.parents("li.level1-menu").addClass("active open").find("a.level1-menu span.arrow").addClass("open");
        $activeMenu.parents("li.level2-menu").addClass("active open").find("a.level2-menu span.arrow").addClass("open");
        if ($("body").hasClass('page-sidebar-closed')) {
            $('.page-sidebar-menu').addClass('page-sidebar-menu-closed');
        }
    }

    function _initJqueryAjax() {
        $.ajaxSetup({
            cache: false,
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.responseJSON) {
                    toast.error(XMLHttpRequest.responseJSON.error);
                } else if (XMLHttpRequest.status !== 0) {
                    toast.error("state：" + XMLHttpRequest.status + ", error：" + XMLHttpRequest.statusText)
                }
                App.unblockUI();
            }
        });
    }

    function _initJqueryValidation() {
        // $.validator.messages.equalTo = i18n.common.diffInput;
        $.validator.setDefaults({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: true,
            ignore: "",  // validate all fields including form hidden input
            highlight: function (element) { // hightlight error inputs
                $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
            },
            unhighlight: function (element) { // revert the change done by hightlight
                $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
            },
            success: function (label) {
                label.closest('.form-group').removeClass('has-error'); // set success class to the control group
            },
            invalidHandler: function (event, validator) { //display error alert on form submit
                // toast.error(i18n.common.tableWrong);
            },
            errorPlacement: function (error, element) { // render error placement for each input type
                if (element.parent(".input-group").size() > 0) {
                    error.insertAfter(element.parent(".input-group"));
                } else if (element.hasClass("select2-hidden-accessible")) {
                    error.insertAfter(element.next());
                } else if (element.attr("data-error-container")) {
                    error.appendTo(element.attr("data-error-container"));
                } else if (element.parents('.radio-list').size() > 0) {
                    error.appendTo(element.parents('.radio-list').attr("data-error-container"));
                } else if (element.parents('.checkbox-list').size() > 0) {
                    error.appendTo(element.parents('.checkbox-list').attr("data-error-container"));
                } else if (element.parents('.mt-checkbox-inline').size() > 0) {
                    error.appendTo(element.parents('.mt-checkbox-inline'));
                } else {
                    error.insertAfter(element); // for other inputs, just perform default behavior
                }
            }
        });
    }

    var handleUniform = function() {
        if (!$().uniform) {
            return;
        }
        var test = $("input[type=checkbox]:not(.toggle, .md-check, .md-radiobtn, .make-switch, .icheck), input[type=radio]:not(.toggle, .md-check, .md-radiobtn, .star, .make-switch, .icheck)");
        if (test.size() > 0) {
            test.each(function() {
                if ($(this).parents(".checker").size() === 0) {
                    $(this).show();
                    $(this).uniform();
                }
            });
        }
    };

    Date.prototype.pattern=function(fmt) {
        var o = {
            "M+" : this.getMonth()+1, //月份
            "d+" : this.getDate(), //日
            "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时
            "H+" : this.getHours(), //小时
            "m+" : this.getMinutes(), //分
            "s+" : this.getSeconds(), //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S" : this.getMilliseconds() //毫秒
        };
        var week = {
            "0" : "/u65e5",
            "1" : "/u4e00",
            "2" : "/u4e8c",
            "3" : "/u4e09",
            "4" : "/u56db",
            "5" : "/u4e94",
            "6" : "/u516d"
        };
        if(/(y+)/.test(fmt)){
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        }
        if(/(E+)/.test(fmt)){
            fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);
        }
        for(var k in o){
            if(new RegExp("("+ k +")").test(fmt)){
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            }
        }
        return fmt;
    };

    function _initLogo() {
        $.ajax({
            url: "/ui/config/info",
            dataType: "json",
            type: "get"
        }).done(function (data) {
            var logoContainer = $("div.page-logo");
            var footerContainer = $("div.page-footer");
            if (data.ok) {
                logoContainer.html('<a th:href="/"><img width="100%" height="46px" src="data:image/jpeg;base64,' + data.data.logo + '" alt="logo"/> </a>')
                footerContainer.html(data.data.copyright)
            } else {
                logoContainer.html('<a th:href="/"><img width="100%" height="46px" src="../pages/img/logo.png" alt="logo"/> </a>')
                footerContainer.html('Copyright&nbsp;2019&nbsp; 北京安软天地科技有限公司.')
            }
        });
    }

    return {
        init: function () {
            template.defaults.imports.i18n = function (key) {
                return flat.i18n(key);
            };
            $.when(_initMenu())
                .done(function () {
                    _initMenuStyle();
                });
            _initJqueryAjax();
            _initJqueryValidation();
            _i18nInit();
                _initLogo();

        },

        i18n: function (key) {
            try {
                return $.i18n.prop(key);
            } catch (e) {
                return key;
            }
        },

        templateCache: {},

        remoteTemplate: function (url, data) {
            var render = flat.templateCache[url];
            if (render === undefined || render === null) {
                $.ajax({
                    url: url,
                    dataType: "html",
                    type: "get",
                    async: false
                }).done(function (html) {
                    render = template.compile(html);
                    flat.templateCache[url] = render;
                });
            }
            return render(data);
        },

        showConfirm: function (param) {
            var defaultData = {
                confirmTitle: flat.i18n("common.confirmTitle"),
                confirmContent: flat.i18n("common.confirmContent"),
                confirmBtn: flat.i18n("common.confirmBtn"),
                cancelBtn: flat.i18n("common.cancelBtn"),
                confirmDialogId: "confirmDialog",
                confirmBtnId: "confirmBtn",
                confirmDialogWrapperId: "confirmDialogWrapper"
            };
            var data = $.extend(true, {}, defaultData, param);
            if ($("#" + data.confirmDialogWrapperId).size() === 0) {
                $("body").append("<div id='" + data.confirmDialogWrapperId + "'></div>")
            }
            $("#" + data.confirmDialogWrapperId).html(flat.remoteTemplate("/template/commons/confirm.html", data));
            $("#" + data.confirmDialogId).modal("show")
        },

        ajaxCallback: function (data) {
            if (data.ok) {
                toast.success(data.msg);
                return true;
            } else if (data.error) {
                toast.error(data.msg);
                return false;
            } else if (data.warn) {
                toast.warn(data.msg);
                return true;
            }
        },
        handleUniform : function () {
            handleUniform();
        }
    }

}();

jQuery(document).ready(function () {
    flat.init();
});
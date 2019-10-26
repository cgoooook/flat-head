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


    return {
        init: function () {
            template.defaults.imports.i18n = function (key) {
                return flat.i18n(key);
            };
            $.when(_initMenu())
                .done(function () {
                    _initMenuStyle();
                });
            _i18nInit();
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
                toast.success(data.returnMsg);
                return true;
            } else if (data.error) {
                toast.error(data.returnMsg);
                return false;
            } else if (data.warn) {
                toast.warn(data.returnMsg);
                return true;
            }
        }
    }

}();

jQuery(document).ready(function () {
    flat.init();
});
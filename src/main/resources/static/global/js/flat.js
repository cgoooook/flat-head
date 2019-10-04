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
            var $sidebarWrapper = $(".page-sidebar-wrapper");
            if ($sidebarWrapper.size() === 1) {
                $.ajax({
                    url: "/menu/sidebar"+"?random="+Math.random(),
                    dataType: "json",
                    type: "get"
                }).done(function (data) {
                    var sidebarData = data.data;
                    var sidebarHtml = flat.remoteTemplate("/template/includes/menu.tpl", sidebarData);
                    localStorage["SidebarHtml"] = sidebarHtml;
                    $sidebarWrapper.html(sidebarHtml);
                    dtd.resolve();
                });
            }
            return dtd.promise();
    };


    return {
        init: function () {
            _i18nInit();
            _initMenu();
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
        }
    }

}();

jQuery(document).ready(function () {
    flat.init();
});
var zTreeOrg = function () {

    var treeObj;

    var options;

    var defaultOption = {
        setting: {
            view: {
                addDiyDom: addDiyDom,
                selectedMulti: false,
                showIcon: false,
                showLine: true
            },
            check: {
                enable: false
            },
            data: {
                simpleData: {
                    icon: "icon",
                    enable: true,
                    rootId: "orgRoot",
                    idKey: "orgId",
                    pIdKey: "parentId"
                },
                keep: {
                    parent: true
                }
            },
            callback: {
                beforeClick: beforeClick,
                onClick: onClick
            }
        },
        treeId: "zTree",
        url: "",
        clickCall: function (event, treeId, treeNode, clickFlag) {

        },
        rootClick: false
    };

    function addDiyDom(treeId, treeNode) {
        $("#" + treeNode.tId + "_a")
            .prepend("<i class='fa fa-folder'></i>");
    }
    
    function onClick(event, treeId, treeNode, clickFlag) {
        options.clickCall(event, treeId, treeNode, clickFlag);
    }

    function beforeClick(treeId, treeNode, clickFlag) {
        // if (options.rootClick) {
        //     return true;
        // }
        // return treeNode.orgId !== "orgRoot";
        return true;
    }

    function menuTreeInit(data) {
        treeObj = $.fn.zTree.init($("#" + defaultOption.treeId), options.setting, data);
        treeObj.expandAll(true);
    }

    var handleVents = function (option) {
        $.get(option.url,function(data) {
            var org = data.data;
            for (var i in org) {
                org[i].name = org[i].orgName;
            }
            menuTreeInit(org)
        })
    };

    return {
        init: function (option) {
            options = $.extend(true, defaultOption, option);
            handleVents(options);
        }
    }


}();
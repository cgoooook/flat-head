var Index = function () {

    var indexDataHandle = function () {
        $.get("/home/info", function (data) {
            if (data.ok) {
                var sm2Info = data.data.homeSm2Info;
                $("#sm2NotUseKey").html(sm2Info.spareNum);
                $("#sm2UseKey").html(sm2Info.inUseNum);
                var logInfo = data.data.homeLogInfo;
                $("#manageLogNum").html(logInfo.managerLogNum);
                $("#apiLogNum").html(logInfo.serviceLogNum);
                var orgInfo = data.data.homeOrganizationAndDeviceInfo;
                $("#orgNum").html(orgInfo.organizationNum);
                $("#devNumber").html(orgInfo.deviceNum);
                var tmplateInfoList = data.data.templateInfoList;
                var tbody = "";
                for (var i = 0; i < tmplateInfoList.length; i++) {
                    tbody += '<tr><td>' + tmplateInfoList[i].name + '</td><td>' + tmplateInfoList[i].num + '</td>'
                }
                $("#templateInfoTableBody").html(tbody);
            }
        });
    };

    return {
        init: function () {
            indexDataHandle();
        }
    }

}();
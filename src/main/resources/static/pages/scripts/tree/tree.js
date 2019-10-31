var myTree =function () {
    var handleTree = function () {
//一般data从后台返回，调用这个方法显示视图
        $('#jstree_div').jstree({
                'plugins':["search","themes","types","state","line"], 	//包含样式，选择框，图片等
                'state': {
                    "opened":false,
                },
                'types': {
                    'default': {
                        'icon': "tree_icon.png" // 默认图标,可以写路径名，但是必须将themes的icons打开，否则没有地方展示图标
                    },
                },
                "checkbox":{  // 去除checkbox插件的默认效果
                    tie_selection : true,
                    keep_selected_style : true,
                    whole_node : true
                },
                'core' : {	//core主要功能是控制树的形状，单选多选等等
                    'data' : function (obj, callback) {
                        var jsonstr="[]";
                        var jsonarray = eval('('+jsonstr+')');
                        $.ajax({
                            type: "POST",
                            url:"/sys/device/devTreeList",
                            dataType:"json",
                            async: false,
                            success:function(result) {
                                var arrays= result;
                                for(var i=0 ; i<arrays.length; i++){
                                    var arr = {
                                        "id":arrays[i].id,
                                        "parent":arrays[i].parentId=="-1"?"#":arrays[i].parentId,
                                        "text":arrays[i].text
                                    }
                                    jsonarray.push(arr);
                                }
                            }
                        });
                        callback.call(this, jsonarray);
                    }
                    ,
                    'themes':{
                        "icons":true,	//默认图标
                        "theme": "classic",
                        "dots": true,
                        //"stripes" : true,	//增加条纹
                    },	//关闭文件夹样式
                    'dblclick_toggle': true,   //允许tree的双击展开,默认是true
                    "multiple" : false, // 单选
                    "check_callback" : true
                }
            }
        )

    }


    var inputChange = function () {
        // 搜索功能的方法 jstree_div：数据展示的内容    plugins4_q 搜索框
        var to = false;
        $('#plugins4_q').keyup(function () {
            if(to) { clearTimeout(to); }
            to = setTimeout(function () {
                var v = $('#plugins4_q').val();
                $('#jstree_div').jstree(true).search(v);
            }, 250);
        });
    }

    return {
        init: function () {
            handleTree();
            inputChange();
        }
    }


}();

// 选择的时候调用的方法
/*    $('#jstree_div').on("changed.jstree", function (e, data) {
        //判断是否打开
        is_open (obj)
        console.log(data.selected);
        console.log("selected");
    });*/


$('a').on('click', function () {
    alert("begin");
    //get_selected返回选中的列
    console.log($('#jstree_div').jstree().get_selected(true));
});



function loadConfig(inst, selectedNode){
    var temp = selectedNode.id;
    //inst.open_node(selectedNode);
    $.ajax({
        url : "/sys/device/devTreeList",
        dataType : "json",
        data:{"parentId": temp},
        type : "POST",
        success : function(data) {
            if(data) {
                selectedNode.children = [];
                $.each(data, function (i) {
                    var obj = data[i];
                    $('#jstree_div').jstree('create_node', selectedNode, obj, 'last');
                    /*      inst.create_node(selectedNode,item,"last");*/
                });
                inst.open_node(selectedNode);
            }else{
                $("#jstree_div").html("暂无数据！");
            }
        }
    });
}

$('#jstree_div').bind("select_node.jstree", function(event, data) {
    var inst = data.instance;
    var selectedNode = inst.get_node(data.selected);
    if(!inst.is_open(data.selected)){
       //console.info(selectedNode.aria-level);
       var level = $("#"+selectedNode.id).attr("aria-level");
       if(parseInt(level) < 2){
           loadConfig(inst, selectedNode);
       }
   }else {
        inst.close_node(selectedNode);

    }
});
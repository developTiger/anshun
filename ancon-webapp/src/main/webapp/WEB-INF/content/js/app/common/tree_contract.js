/**
 * Created by wanggy on 2016/2/29.
 */
define(function(require,exports,module){
    var zTree=require("ztree");
    var zNodes;


    exports.init=function(options,callback){
        if(options.url){
            $.post(options.url,function(data){
                fuzhi(data);
                //if($("#zTree").length>0) {
                //    var ids = eval($("#zTree").val());
                //    var trees = $.fn.zTree.getZTreeObj("tree1");
                //    //var ids = [your ids], i = 0, l = ids.length, node = null;
                //    for (i = 0; i < ids.length; i++) {
                //        trees.checkNode(trees.getNodeByParam("id", ids[i]), true);
                //    }
                //}
                callback && (typeof  callback == "function") &&callback()
            });

        }else{
            fuzhi(options.jsonData);
        }

        function fuzhi(data){
            var setting = {
                view: {
                    showIcon: true,//图标
                    showLine:true,//连接线
                    addHoverDom: addHoverDom,
                    removeHoverDom: removeHoverDom
                    //addDiyDom: addDiyDom

                },
                check: {
                   enable: false
                },
                //edit:{
                //    enable:true,
                //    editNameSelectAll:true,
                //    removeTitle:'删除',
                //    renameTitle:'重命名'
                //},
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "id",            //自身id
                        pIdKey: "parentId",     //父集id
                        name:"name",
                        rootPId: 0,
                        checked:true
                    }
                },
                callback: {
                    onClick: options.click && (typeof  options.click == "function") &&options.click,
                    onCheck: options.onCheck && (typeof  options.onCheck == "function") &&options.onCheck,

                    onClick: zTreeOnClick
                    //onAsyncSuccess: zTreeOnAsyncSuccess,


                }
            };

            if(typeof(options.setting)!= "undefined")
            {
                setting=$.extend(true,{},setting,options.setting);
            }

            //if(typeof(options.setting)!= "undefined")
            //{
            //    setting.check=$.extend({},setting.check,options.setting.check);
            //}
            //if(typeof(options.setting.async)!= "undefined"){
            //    setting.async=$.extend({},setting.async,options.setting.async);
            //}
            zNodes=data;
            $.fn.zTree.init($(options.id), setting, zNodes);



                var treeObj = $.fn.zTree.getZTreeObj($(options.id).attr("id"));
                //var node = treeObj.getNodeByParam("tId", "ztree_1_1"); //此方法也可以，不过是写死的
                var nodes = treeObj.getNodes();
                var firstNode = nodes[0];
                //treeObj.selectNode(node);
                setting.callback.onClick = firstClick(firstNode);



        }
    }

    function firstClick(firstNode){
        $(".Js_li_first").trigger("change",[firstNode]);
    }

    function zTreeOnClick(event, treeId, treeNode){

        $(".sc_index_2").trigger("change",[event,treeNode,treeId]);

        //右边头部导航    例如：常州分公司>主合同>二级子合同
        if(treeNode.level == 0){
            $(".sc_index_1_company").html(treeNode.branchCompany);
            $(".sc_index_1_first_contract").html(">"+treeNode.name);
            $(".sc_index_1_second_contract").html("");
        }else{
            var parentNode = treeNode.getParentNode(treeNode.id);
            $(".sc_index_1_company").html(parentNode.branchCompany);
            $(".sc_index_1_first_contract").html(">"+parentNode.name);
            $(".sc_index_1_second_contract").html(">"+treeNode.name);
        }

        //alert(treeNode.tId + ", " + treeNode.name);
        //window.location.href="sra_s_i?id="+treeNode.id;

        //var tid = treeNode.tId;
        //var t_level = "level"+ treeNode.level;
        //
        //$("#"+treeNode.tId).css("background-color","#f0f3f4");//当前节点高亮
        //$("#"+treeNode.tId).find("a").removeClass("curSelectedNode");
        ////$("#"+treeNode.tId).find("a").addClass("treeNode_single_style");
        //
        //var node_idAndLevel = [];
        //
        //$(".ztree").find("li").find("a").each(function(){
        //    var nodeObject={};
        //    nodeObject.id=($(this).attr("id").substring(0,$(this).attr("id").length-2));
        //
        //    var str = $(this).attr("class").split(" ");
        //    var node_level = str[0];
        //    nodeObject.level = node_level;
        //
        //    node_idAndLevel.push(nodeObject);
        //
        //})
        //
        //for(var i=0;i<node_idAndLevel.length;i++){
        //    //除掉自身 判断节点等级
        //    if(node_idAndLevel[i].id != tid && node_idAndLevel[i].level == t_level){
        //        $("#"+node_idAndLevel[i].id).css("background-color","#141E27")
        //    }
        //}

    };

    function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {

            alert(treeNode.id + "sdljfldsj");

    };



    //function addDiyDom(treeId, treeNode,name) {
    //    var aObj = $("#" + treeNode.tId + "_a");
    //    if ($("#diyBtn_"+treeNode.id).length>0) return;
    //    var editStr = "<span id='diyBtn_space_" +treeNode.id+ "' > </span>"
    //        + "<button type='button' class='diyBtn1' id='diyBtn_" + treeNode.id
    //        + "' title='"+treeNode.name+"' onfocus='this.blur();'>新建合同</button>";
    //    aObj.append(editStr);
    //    var btn = $("#diyBtn_"+treeNode.id);
    //    if (btn) btn.bind("click", function(){alert("diy Button for " + treeNode.name);});
    //};

    //<button type="button" class="btn btn-primary i_modal" data-url="addSalecontract" data-title="添加销售合同">新增</button>
    function addHoverDom(treeId, treeNode) {
        var aObj = $("#" + treeNode.tId + "_a");
        if ($("#diyBtn_"+treeNode.id).length>0) return;

        var editStr =
            "<button style='height:20px;width:20px;background-color: #19a9d5;border-color: #19a9d5;' type='button' class='btn btn-primary i_modal' data-url='addSalecontract?parentId="+treeNode.id+"&companyName="+treeNode.branchCompany+"&companyId="+treeNode.companyId+"' data-title='添加销售合同' id='diyBtn_" + treeNode.id
            + "' title='"+treeNode.name+"' onfocus='this.blur();'><em class='iconfont' style='font-size: 12px;position: relative;'>&#xe6ac;</em></button>";

        if(treeNode.level == 1){
            return false;
        }else{
            //用户权限判断
            var companyId =  $("input[name='current_company_id']").val();
            var current_company_type = $("input[name='current_company_type']").val();

            //当前用户信息
            var current_user_company_id = $("input[name='current_user_company_id']").val();
            var current_user_companyOrDept_type = $("input[name='current_user_companyOrDept_type']").val();
            var current_user_companyOrDept_no = $("input[name='current_user_companyOrDept_no']").val();
            var current_user_add_contract_anthority = $("input[name='current_user_add_contract_anthority']").val();

            //当前用户权限和合同没关系
            if(current_user_companyOrDept_type == "1"){
                //部门
                if(current_user_companyOrDept_no == "1006"){
                    //必须为经营管理部 可以新建合同

                        aObj.after(editStr);


                }

            }else if(current_company_type == "2" && companyId == current_user_company_id){
                //独立分公司 本公司的人
                if(current_user_add_contract_anthority == "add_contract"){
                    aObj.after(editStr);
                }
            }


        }
        var btn = $("#diyBtn_"+treeNode.id);
        if (btn) btn.bind("click", function(){});
    };
    function removeHoverDom(treeId, treeNode) {
        $("#diyBtn_"+treeNode.id).unbind().remove();
        $("#diyBtn_space_" +treeNode.id).unbind().remove();
    };



    //exports.ztreeOnClick = function(callback){
    //    callback && (typeof  callback == "function") &&callback();
    //}


    //function addDiyDom(treeId, treeNode) {
    //    var aObj = $("#" + treeNode.tId + "_a");
    //    if ($("#diyBtn_"+treeNode.id).length>0) return;
    //    var editStr = "<span id='diyBtn_space_" +treeNode.id+ "' > </span>"
    //        + "<button type='button' class='diyBtn1' id='diyBtn_" + treeNode.id
    //        + "' title='"+treeNode.name+"' onfocus='this.blur();'>小苹果</button>";
    //    aObj.append(editStr);
    //    var btn = $("#diyBtn_"+treeNode.id);
    //    if (btn) btn.bind("click", function(){alert("diy Button for " + treeNode.name);});
    //};

});
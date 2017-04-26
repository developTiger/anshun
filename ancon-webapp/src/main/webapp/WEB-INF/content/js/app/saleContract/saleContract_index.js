/**
 * Created by admin on 2016/11/23.
 */
define(function (require, exports, module) {

    var List = require("../common/pagelist");
    var template = require("template");
    var widget = require("../common/widget");
    require("../common/jquery.serializeObject");
    var zcTree = require('../common/tree_contract');//注意 名字有时候会发生冲突
    var wDatePicker = require("wdatePicker");
    var validate = require("validate");
    require("select2");

    //弹窗 取消按钮
    $("body").on("click", ".cancelModal", function () {
        $("#myModal").modal("hide");
    });

    var result = [];
    querySaleContracts();
    function querySaleContracts() {


        $.ajax({
            url: "ajax_query_all_saleContracts?",
            type: 'post',
            success: function (data) {
                if (data) {
                    for (var i = 0; i < data.length; i++) {
                        result.push(data[i]);
                    }
                    //统计
                    var count_saleContractId = $("input[name='count_saleContractId']").val();
                    var count_companyId = $("input[name='count_companyId']").val();

                    //新增
                    var saleContract_add_id = $("input[name='saleContract_add_id']").val();
                    var saleContract_add_companyId = $("input[name='saleContract_add_companyId']").val();

                    if (saleContract_add_id != "") {

                        if (saleContract_add_companyId != "") {
                            $(".Js_li_first").each(function () {
                                var companyId = $(this).attr("data-value");
                                if (saleContract_add_companyId == companyId) {
                                    $(this).click();

                                    zTree = $.fn.zTree.getZTreeObj("ztree_" + saleContract_add_companyId);
                                    var nodes = zTree.getNodes();
                                    var current_node = {};

                                    if (nodes != null && nodes.length > 0) {
                                        for (var i = 0; i < nodes.length; i++) {
                                            if (nodes[i].id == saleContract_add_id) {
                                                current_node = nodes[i];
                                                zTree.selectNode(current_node);
                                                $("#" + current_node.tId).children("a").click();
                                            }
                                            if (nodes[i].children != null && nodes[i].children.length > 0) {
                                                for (var j = 0; j < nodes[i].children.length; j++) {
                                                    if (nodes[i].children[j].id == saleContract_add_id) {

                                                        current_node = nodes[i].children[j];
                                                        zTree.expandNode(nodes[i], true, true, true);//展开父节点
                                                        zTree.selectNode(current_node);//选中子节点

                                                        $("#" + current_node.tId).children("a").click();
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            })
                        }


                        //新增刷新完之后，这两个值需要清空
                        $("input[name='saleContract_add_id']").val("");
                        $("input[name='saleContract_add_companyId']").val("");
                    } else {
                        if (count_companyId == "") {
                            //第一次默认点击
                            $(".Js_li_first:first").click();//展开公司
                            $(".Js_li_first:first").next().find("a:first").click();//触发树形结构的callback点击事件
                        } else {
                            //统计 选中对应的合同
                            if (count_companyId != "") {
                                $(".Js_li_first").each(function () {
                                    var companyId = $(this).attr("data-value");
                                    if (count_companyId == companyId) {
                                        $(this).click();

                                        zTree = $.fn.zTree.getZTreeObj("ztree_" + count_companyId);
                                        var nodes = zTree.getNodes();
                                        var current_node = {};

                                        if (nodes != null && nodes.length > 0) {
                                            for (var i = 0; i < nodes.length; i++) {
                                                if (nodes[i].id == count_saleContractId) {
                                                    current_node = nodes[i];
                                                    zTree.selectNode(current_node);
                                                    $("#" + current_node.tId).children("a").click();
                                                }
                                                if (nodes[i].children != null && nodes[i].children.length > 0) {
                                                    for (var j = 0; j < nodes[i].children.length; j++) {
                                                        if (nodes[i].children[j].id == count_saleContractId) {

                                                            current_node = nodes[i].children[j];
                                                            zTree.expandNode(nodes[i], true, true, true);//展开父节点
                                                            zTree.selectNode(current_node);//选中子节点

                                                            $("#" + current_node.tId).children("a").click();
                                                        }
                                                    }
                                                }
                                            }
                                        }


                                        //
                                        //zTree.expandNode(firstNode,true,true,true);
                                        //zTree.selectNode(current_node);
                                        //zTree.checkNode(current_node,true,true);

                                    }
                                })
                            }
                        }
                    }

                }
            }
        });
    }

    var saleContract_add_company_id = "";
    $(".Js_li_first").on("click", function () {
        var that = $(this);
        //新增完之后 定位到当前合同 需要的公司id
        saleContract_add_company_id = $(this).attr("data-value");

        var currentPoint = [];//作用域问题 全局变量 局部变量

        var companyId = $(this).attr("data-value");
        //当前分公司的id
        $("input[name='current_company_id']").val(companyId);
        //当前分公司类型
        var companyType = $(this).attr("companyType");
        $("input[name='current_company_type']").val(companyType);


        for (var i = 0; i < result.length; i++) {
            if (result[i].companyId == companyId) {
                currentPoint.push(result[i]);
            }
        }

        var idSelector = $(this).next().children().attr("id");
        var currentCompanyId = idSelector.substring(6, idSelector.length);
        if (companyId == currentCompanyId) {
            //左侧 树形结构
            var options = {
                jsonData: currentPoint,
                //url: "ajax_query_all_saleContracts",
                id: $("#" + idSelector)
            }
            zcTree.init(options);

        }


        //树形 展开方式 遍历所有 去掉自身
        $(".Js_li_first").each(function () {
            var this_company_id = $(this).attr("data-value")
            if ($(this).children(".Js_first_icon").is(":hidden") && companyId != this_company_id) {
                //展开
                $(this).children(".Js_first_icon").show();
                $(this).children(".Js_second_icon").addClass("hidden");
                $(this).next().addClass("hidden");
                $(this).css({"background-color": "#1E3241", "color": "#577185"});
                $(this).children("span").css("color","#fff")
            }

        })



        //自身
        if ($(this).children(".Js_second_icon").is(":hidden")) {
            //展开
            $(this).children(".Js_first_icon").hide();
            $(this).children(".Js_second_icon").removeClass("hidden");
            $(this).next().removeClass("hidden");
            $(this).css({"background-color": "#1D2B36", "color": "#ffffff"});
            //$(this).children("span").css("color","#ffffff")


        } else {
            //收起
            $(this).children(".Js_first_icon").show();
            $(this).children(".Js_second_icon").addClass("hidden");
            $(this).next().addClass("hidden");
            $(this).css({"background-color": "#141E27", "color": "#fff"});
            //$(this).children("span").css("color","#577185")



        }

        //$(".Js_li_first").each(function(){
        //    var this_company_id =$(this) .attr("data-value");
        //    if(this_company_id == companyId){
        //        if($(this).css("background-color")=="rgb(20, 30, 39)"){
        //            $(this).css({"background-color":"#141E27","color": "#577185"});
        //        }else{
        //            $(this).css({"background-color": "#1D2B36","color": "#ffffff"});
        //        }
        //    }
        //
        //})



        $(this).next().css("background-color", "#ffffff")

    });


    $(".Js_li_first").mouseover(function () {

        //先判断是用户是本公司 还是部门 如果是部门 在判断是独立分公司还是非独立分公司
        //最后是判断有没有新建合同权限

        //点击的公司信息
        var companyId = $(this).attr("data-value");
        var current_company_type = $(this).attr("companyType");

        //当前用户信息
        var current_user_company_id = $("input[name='current_user_company_id']").val();
        var current_user_companyOrDept_type = $("input[name='current_user_companyOrDept_type']").val();
        var current_user_companyOrDept_no = $("input[name='current_user_companyOrDept_no']").val();
        var current_user_add_contract_anthority = $("input[name='current_user_add_contract_anthority']").val();

        //当前用户权限和合同没关系
        if (current_user_companyOrDept_type == "1") {
            //部门
            if (current_user_companyOrDept_no == "1006") {
                //必须为经营管理部 可以新建合同

                    $(this).children(".add_sale_contract").removeClass("hidden");


            }

        } else if (current_company_type == "2" && companyId == current_user_company_id) {
            //独立分公司 本公司的人
            if (current_user_add_contract_anthority == "add_contract") {
                $(this).children(".add_sale_contract").removeClass("hidden");
            }
        }



    })

    $(".Js_li_first").mouseout(function (event) {
        $(this).children(".add_sale_contract").addClass("hidden");

    })


    $("body").on("change", ".Js_li_first", function (event, firstNode) {
        //alert("dlsjfdsj")
    })

    //当前系统时间
    var currentOperateDate = new Date();
    var currentOperateTime = formatDate(currentOperateDate);


    $("body").delegate(".ete", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy-MM-dd',
            maxDate: currentOperateTime
        });
    });

    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({
            dateFmt: 'yyyy-MM-dd'
        });
    });



    //loadAllData()
    //loadData_4();
    function loadAllData() {
        loadData();
        loadData_2();
        loadData_3();
    }

    //开票管理 数据
    //loadData();
    $("#billingSelect").click(function () {
        loadData();
    })

    function loadData() {

        var saleContractId = $("input[name='saleContractId']").val();

        var url = $("#billingSelect").attr("data-url");
        var tpl = require("text!app/tpl/saleContract/saleContract_index_billing_tpl.html");
        var data = $("#billingSelectForm").serialize().toString() + "&saleContractId=" + saleContractId;
        var data_1 = $("#billingSelectForm").serialize().toString();

        if (saleContractId) {
            List(".sc_index_3_table", tpl, url, data, 1, 10);
        } else {
            List(".sc_index_3_table", tpl, url, data_1, 1, 10);
        }
        //用户权限
        var current_user_anthority_billing = $("input[name='current_user_anthority_billing']").val();
        if (current_user_anthority_billing == "login_Billing") {
            $(".Js_billing_saleContract_status").show();
        } else {
            $(".Js_billing_saleContract_status").hide();
        }

    }

    //收款管理 数据
    //loadData_2();
    $("#gatheringSelect").click(function () {
        loadData_2();
    })

    function loadData_2() {
        var saleContractId = $("input[name='saleContractId']").val();

        var url = $("#gatheringSelect").attr("data-url");
        var tpl = require("text!app/tpl/saleContract/saleContract_index_gathering_tpl.html");
        var data = $("#gatheringSelectForm").serialize().toString() + "&saleContractId=" + saleContractId;
        var data_1 = $("#gatheringSelectForm").serialize().toString();
        if (saleContractId) {
            List(".sc_index_4_table", tpl, url, data, 1, 10);
        } else {
            List(".sc_index_4_table", tpl, url, data_1, 1, 10);
        }

        //用户权限
        var current_user_anthority_gathering = $("input[name='current_user_anthority_gathering']").val();
        if (current_user_anthority_gathering == "login_payable") {
            $(".Js_gathering_saleContract_status").show();
        } else {
            $(".Js_gathering_saleContract_status").hide();
        }
    }

    //施工产值 数据
    //loadData_3();
    $("#conValueSelect").click(function () {
        loadData_3();
    })

    function loadData_3() {
        var saleContractId = $("input[name='saleContractId']").val();

        var url = $("#conValueSelect").attr("data-url");
        var tpl = require("text!app/tpl/saleContract/saleContract_index_conValue_tpl.html");
        var data = $("#conValueSelectForm").serialize().toString() + "&saleContractId=" + saleContractId;
        var data_1 = $("#conValueSelectForm").serialize().toString();

        if (saleContractId) {
            List(".sc_index_5_table", tpl, url, data, 1, 10);

        } else {
            List(".sc_index_5_table", tpl, url, data_1, 1, 10);
        }

        //用户权限
        var current_user_anthority_conValue = $("input[name='current_user_anthority_conValue']").val();
        if (current_user_anthority_conValue == "login_output") {
            $(".Js_conValue_saleContract_status").show();
        } else {
            $(".Js_conValue_saleContract_status").hide();
        }

        //左边菜单高度
        $(".saleContract_left").height(800);
        var left_height = $(document).height() - 60;
        $(".saleContract_left").height(left_height);

    }

    //基本信息  数据  同步加载
   // loadData_4();
    function loadData_4(saleContractId) {
        if (saleContractId) {
            var url = "ajax_query_single_saleContract?saleContractId=" + saleContractId;

        } else {
            var url = "ajax_query_single_saleContract";

        }
        var tpl = require("text!app/tpl/saleContract/saleContract_index_baseInfo_tpl.html");
        $.ajax({
            url: url,
            cache:false,
            type: 'get',
            async: false,            //此处不能异步加载，必须等这一步的数据加载完之后，才能执行下一步
            success: function (data) {
                var html = template.compile(tpl)(data);
                $(".sc_index_2").html(html);
                //合同状态 判断 用户权限
                //页面5个按钮 默认隐藏 除却新增按钮
                $(".cn_js_div_edit").addClass("hidden");
                $(".cn_js_div_judge").addClass("hidden");
                $("#login_billing").hide();
                $("#login_gathering").hide();
                $("#loginConValue").hide();

                //每次加载时，需要先清空这3个值
                $("input[name='current_user_anthority_billing']").val("");
                $("input[name='current_user_anthority_gathering']").val("");
                $("input[name='current_user_anthority_conValue']").val("");


                var judgeStatus = data.judgeStatus;

                var authority_control = data.authorityControl;
                for (var i = 0; i < authority_control.length; i++) {
                    var n = authority_control[i];
                    if (n.url == "edit_contract") {
                        $(".cn_js_div_edit").removeClass("hidden");
                    }

                    if (n.url == "judge_contract") {
                        $(".cn_js_div_judge").removeClass("hidden");
                    }

                    //开票
                    if (n.url == "login_Billing") {

                        $("#login_billing").show();
                        $("input[name='current_user_anthority_billing']").val(n.url)
                    }

                    //收款
                    if (n.url == "login_payable") {
                        $("#login_gathering").show();
                        $("input[name='current_user_anthority_gathering']").val(n.url)
                    }

                    //产值
                    if (n.url == "login_output") {
                        $("#loginConValue").show();
                        $("input[name='current_user_anthority_conValue']").val(n.url)
                    }

                    //定审
                    if(n.url == "judge_contract"){
                        if (judgeStatus == "已定审") {
                            $(".cn_js_div_judged").removeClass("hidden");
                            $(".cn_js_div_judge").addClass("hidden");
                        }
                    }

                    //新建合同
                    if (n.url == "add_contract") {
                        $("input[name='current_user_anthority']").val(n.url);
                    }

                }


                //已定审之后 不管什么权限 显示的都是已定审按钮 单独判断



            }
        })
    }

    //树形结构 change事件
    $("body").delegate(".sc_index_2", "change", function (event, treeId, treeNode) {
        //alert(treeNode.tId + ", " + treeNode.name);
        loadData_4(treeNode.id);
        $("input[name='saleContractId']").val(treeNode.id);

        //var url = "sra_s_i?saleContractId="+treeNode.id;
        loadAllData();
        //$.get(url);
        //window.location.href=url;
    });


    $("#login_billing").off("click");
    $("#login_billing").on("click", function () {
        var saleContractId = $("input[name='saleContractId']").val();
        var data = $(this).attr("data-url");
        var datas = data.split("=");
        var first_data = datas[0];
        $(this).attr("data-url", first_data + "=" + saleContractId);
    })

    $("#login_gathering").off("click");
    $("#login_gathering").on("click", function () {
        var saleContractId = $("input[name='saleContractId']").val();
        var data = $(this).attr("data-url");
        var datas = data.split("=");
        var first_data = datas[0];
        $(this).attr("data-url", first_data + "=" + saleContractId);
    });

    count_trigger();
    function count_trigger() {

    }

    var callba = function callback() {
        $(".select2_single").select2({placeholder:"输入甲方名称查询",allowClear:true  });
        $(".select2_edit_company").select2({placeholder:"输入机构名称查询",allowClear:true  });
        $.fn.modal.Constructor.prototype.enforceFocus = function() {};


        //新增或修改销售合同
        $(".addOrUpdateSaleContractForm").validate({
            rules: {
                contractMoney: {
                    required: true,
                    number: true,
                    min: 0
                }
            },
            messages: {
                contractMoney: {
                    required: "请输入合法的数字",
                    number: "请输入合法的数字",
                    min: "请输入合法的数字"
                }
            },

            submitHandler: function (form) {
                var _url = $(".addOrUpdateSaleContract").attr("data-url");
                //var conBeginTime = $("input[name='conBeginTime']").val();//合同开始时间
                //var conEndTime = $("input[name='conEndTime']").val();//合同结束时间
                //var proStartTime = $("input[name='proStartTime']").val();//合同开工时间
                //
                //var timestamp1 = Date.parse(new Date(conBeginTime));
                //var timestamp2 = Date.parse(new Date(conEndTime));
                //var timestamp3 = Date.parse(new Date(proStartTime));

                //if (timestamp1 > timestamp2) {
                //    alert("合同到期时间不能小于合同签订时间！");
                //    return;
                //}
                //if (timestamp3 < timestamp1) {
                //    alert("合同开工时间不能小于合同签订时间");
                //    return;
                //}
                $("input[name='edit_companyName']").val($(".select2_edit_company").find("option:selected").text())
                var saleContractId = $("input[name='saleContractId']").val();
                $.ajax({
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $(".addOrUpdateSaleContractForm").serializeObject(),
                    success: function (data) {
                        if (data.isSuccess) {
                            alert("操作成功！");
                            var current_company_id = $(".select2_edit_company").val();
                            $("#myModal").modal("hide");


                            var saleContract_add_id = data.id;//新增完之后的id
                            //if (saleContractId == saleContract_add_id) {
                            //    //id存在 是修改 不许刷新
                            //    loadData_4(saleContractId);
                            //}
                                //新增完之后 定位到当前新增的合同 在树形上 页面数据需要重新加载 相当于重新刷新页面
                                window.location.href = "sra_s_i?saleContract_add_id=" + data.id + "&saleContract_add_companyId=" + current_company_id;



                        } else {
                            alert(data.msg);
                        }

                    }
                })
            }


        });

        //多个弹窗放一起
        //定审新增
        $("#addFixedTrialForm").validate({
            rules: {
                judgeMoney: {
                    required: true,
                    number: true,
                    min: 0
                }
            },
            messages: {
                judgeMoney: {
                    required: "请输入合法的数字",
                    number: "请输入合法的数字",
                    min: "请输入合法的数字"
                }
            },

            submitHandler: function (form) {
                var _url = $("#addFixedTrial").attr("data-url");
                var id = $("input[name='saleContractId']").val();
                var saleContractId = $("input[name='saleContractId']").val();
                $.ajax({
                    url: _url + "?t=" + new Date().getMilliseconds() + "&id=" + id,
                    type: 'post',
                    data: $("#addFixedTrialForm").serializeObject(),
                    success: function (data) {
                        if (data == 1) {
                            alert("添加成功！");
                        }
                        $("#myModal").modal("hide");
                        loadData_4(saleContractId);
                    }
                })
            }
        });

        //开票管理
        //税额，价税合计计算
        $("#taxRate").change(function(){
            var taxRate = $(this).val();
            var money = $("#billingMoney").val();
            if(taxRate != "" && money != ""){
                var taxMoney = parseFloat(taxRate)*parseFloat(money)*0.01;
                $("#taxMoney").val(taxMoney.toFixed(4));
                var taxMoney = $("#taxMoney").val();
                var priceTaxCountMoney = parseFloat(money)+parseFloat(taxMoney);
                $("#priceTaxCountMoney").val(priceTaxCountMoney.toFixed(4));
            }
        })
        $("#billingMoney").change(function(){
            var money= $(this).val();
            var  taxRate  = $("#taxRate").val();
            if(taxRate != "" && money != ""){
                var taxMoney = parseFloat(taxRate)*parseFloat(money)*0.01;
                $("#taxMoney").val(taxMoney.toFixed(4));
                var taxMoney = $("#taxMoney").val();
                var priceTaxCountMoney = parseFloat(taxMoney)+parseFloat(money);
                $("#priceTaxCountMoney").val(priceTaxCountMoney.toFixed(4));
            }
        })

        $("#addBillingManagementForm").validate({
            rules: {
                billingMoney: {
                    required: true,
                    number: true,//负数，小数
                    min: 0
                },
                taxRate:{
                    required:true,
                    number:true,
                    min:0,
                    max:100
                },
                taxMoney:{
                    required: true,
                    number: true,
                    min: 0
                },
                priceTaxCountMoney:{
                    required: true,
                    number: true,
                    min: 0,
                    max:parseFloat($("#restMoney").val())
                }


            },
            messages: {
                billingMoney: {
                    required: "请输入合法的数字",
                    number: "请输入合法的数字",
                    min: "请输入合法的数字"
                },
                taxRate:{
                    required:"请输入0到100之间的数字",
                    number:"请输入0到100之间的数字",
                    min:"请输入0到100之间的数字",
                    max:"请输入0到100之间的数字"
                },
                taxMoney:{
                    required: "请输入合法的数字",
                    number: "请输入合法的数字",
                    min: "请输入合法的数字"
                },
                priceTaxCountMoney:{
                    required: "请输入合法的数字",
                    number: "请输入合法的数字",
                    min: "不得超过合同金额",
                    max:"不得超过合同金额"
                }

            },

            submitHandler: function (form) {
                var _url = $("#addBillingManagement").attr("data-url");

                $("#billing_saleContract_id").val($("input[name='saleContractId']").val());
                $.ajax({
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#addBillingManagementForm").serializeObject(),
                    success: function (data) {
                        if (data == 1) {
                            alert("添加成功！");
                        }
                        if (data == 2) {
                            alert("修改成功！");
                        }
                        $("#myModal").modal("hide");
                        loadData();
                        var saleContractId = $("input[name='saleContractId']").val();
                        loadData_4(saleContractId);
                    }
                })
            }
        })

        //收款管理
        $("#addGatheringManagementForm").validate({
            rules: {
                gatheringMoney: {
                    required: true,
                    number: true,
                    min: 0
                }
            },
            messages: {
                gatheringMoney: {
                    required: "请输入合法的数字",
                    number: "请输入合法的数字",
                    min: "请输入合法的数字"
                }
            },

            submitHandler: function (form) {
                var _url = $("#addGatheringManagement").attr("data-url");
                $("#gathering_saleContract_id").val($("input[name='saleContractId']").val());

                var restMoney = $("#restMoney").val();
                var gatheringMoney = $("input[name='gatheringMoney']").val();
                if (parseInt(gatheringMoney) > parseInt(restMoney)) {
                    alert("收款金额不能大于合同金额！");
                    return false;
                }

                $.ajax({
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#addGatheringManagementForm").serializeObject(),
                    success: function (data) {
                        if (data == 1) {
                            alert("添加成功！");
                        }
                        if (data == 2) {
                            alert("修改成功！");
                        }
                        $("#myModal").modal("hide");
                        loadData_2();
                        var saleContractId = $("input[name='saleContractId']").val();
                        loadData_4(saleContractId);
                    }
                })
            }
        })

        //施工产值
        $("#addProjectOutputValueForm").validate({
            rules: {
                outputValue: {
                    required: true,
                    number: true,
                    min: 0
                }
            },
            messages: {
                outputValue: {
                    required: "请输入合法的数字",
                    number: "请输入合法的数字",
                    min: "请输入合法的数字"
                }
            },

            submitHandler: function (form) {
                var _url = $("#addProjectOutputValue").attr("data-url");
                var conBeginTime = $("#con_begin_time").val();//开始时间
                var conEndTime = $("#con_end_time").val();//结束时间

                var timestamp1 = Date.parse(new Date(conBeginTime));
                var timestamp2 = Date.parse(new Date(conEndTime));

                if (timestamp1 > timestamp2) {
                    alert("开始时间不能大于结束时间！");
                    return;
                }
                $("#conValue_saleContract_id").val($("input[name='saleContractId']").val());

                $.ajax({
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#addProjectOutputValueForm").serializeObject(),
                    success: function (data) {
                        if (data == 1) {
                            alert("添加成功！");
                        }
                        if (data == 2) {
                            alert("修改成功！");
                        }
                        $("#myModal").modal("hide");
                        loadData_3();
                        var saleContractId = $("input[name='saleContractId']").val();
                        loadData_4(saleContractId);
                    }
                })
            }
        })

    }
    widget.init(callba);


    $("body").on("click", ".deleteBilling", function () {
        var _url = $(this).attr("data-url");
        $.ajax({
            url: _url,
            type: 'post',
            success: function (data) {
                if (data) {
                    alert("删除成功！");
                    loadData();
                    var saleContractId = $("input[name='saleContractId']").val();
                    loadData_4(saleContractId);
                }
            }
        })
    })

    $("body").on("click", ".deleteGathering", function () {
        var _url = $(this).attr("data-url");
        $.ajax({
            url: _url,
            type: 'post',
            success: function (data) {
                if (data) {
                    alert("删除成功！");
                    loadData_2();
                    var saleContractId = $("input[name='saleContractId']").val();
                    loadData_4(saleContractId);
                }
            }
        })
    })

    $("body").on("click", ".deleteConValue", function () {
        var _url = $(this).attr("data-url");
        $.ajax({
            url: _url,
            type: 'post',
            success: function (data) {
                if (data) {
                    alert("删除成功！");
                    loadData_3();
                    var saleContractId = $("input[name='saleContractId']").val();
                    loadData_4(saleContractId);
                }
            }
        })
    })


    //时间格式转换
    function formatDate(date, format) {
        if (!date) return;
        if (!format) format = "yyyy-MM-dd";
        switch (typeof date) {
            case "string":
                date = new Date(date.replace(/-/, "/"));
                break;
            case "number":
                date = new Date(date);
                break;
        }
        if (!date instanceof Date) return;
        var dict = {
            "yyyy": date.getFullYear(),
            "M": date.getMonth() + 1,
            "d": date.getDate(),
            "H": date.getHours(),
            "m": date.getMinutes(),
            "s": date.getSeconds(),
            "MM": ("" + (date.getMonth() + 101)).substr(1),
            "dd": ("" + (date.getDate() + 100)).substr(1),
            "HH": ("" + (date.getHours() + 100)).substr(1),
            "mm": ("" + (date.getMinutes() + 100)).substr(1),
            "ss": ("" + (date.getSeconds() + 100)).substr(1)
        };
        return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function () {
            return dict[arguments[0]];
        });
    };


});
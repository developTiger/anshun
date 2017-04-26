/**
 * 进项合同的主要操作
 * Created by xiazl on 2016/11/25.
 */
define(function (require, exports, module) {
    var List = require("../common/pagelist");
    var template = require("template");
    var widget = require("../common/widget");
    require("../common/jquery.serializeObject");
    var wDatePicker = require("wdatePicker");
    var validate = require("validate");
    var ZTree = require('../common/tree_inContract');
    require("jquery");
    require("select2");
    require("ztree");


//左侧树结构
    var result = [];


    querySaleContracts();


    function querySaleContracts() {
        $.ajax({
            url: "ajax_query_partyB_inContracts",
            cache:false,
            type: 'post',
            success: function (data) {
                /* if (data) {
                 for (var i = 0; i < data.length; i++) {
                 result.push(data[i]);
                 }
                 var count_id = $("input[name='count_id']").val();
                 var count_pid = $("input[name='count_pid']").val();
                 if (count_pid) {
                 $(".Js_li_first").each(function () {
                 var companyId = $(this).attr("data-value");
                 if (count_pid == companyId) {
                 $(this).click();

                 zTree = $.fn.zTree.getZTreeObj("ztree_" + count_pid);
                 var nodes = zTree.getNodes();
                 var current_node = {};

                 if (nodes != null && nodes.length > 0) {
                 for (var i = 0; i < nodes.length; i++) {
                 if (nodes[i].id == count_id) {
                 current_node = nodes[i];
                 zTree.selectNode(current_node);
                 $("#" + current_node.tId).children("a").click();
                 }
                 }
                 }
                 }
                 })

                 } else {
                 //第一次默认点击
                 $(".Js_li_first:first").click();//展开公司
                 $(".Js_li_first:first").next().find("a:first").click();//触发树形结构的callback点击事件
                 }
                 }
                 */

                if (data) {
                    for (var i = 0; i < data.length; i++) {
                        result.push(data[i]);
                    }
                    //统计
                    var count_saleContractId = $("input[name='count_saleContractId']").val();
                    var count_companyId = $("input[name='count_companyId']").val();

                    var count_id = $("input[name='count_id']").val();
                    var count_pid = $("input[name='count_pid']").val();
                    //新增
                    var saleContract_add_id = $("input[name='inContract_add_id']").val();
                    var saleContract_add_companyId = $("input[name='inContract_add_companyId']").val();

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
                        if (count_pid == "") {
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


    $(".Js_li_first").on("click", function () {

        var currentPoint = [];//作用域问题 全局变量 局部变量


        var companyId = $(this).attr("data-value");
        for (var i = 0; i < result.length; i++) {
            if (result[i].partyBId == companyId) {
                currentPoint.push(result[i]);
            }
        }

        //当前分公司的id
        $("input[name='current_company_id']").val(companyId);

        var idSelector = $(this).next().children().attr("id");
        var currentCompanyId = idSelector.substring(6, idSelector.length);
        if (companyId == currentCompanyId) {
            //左侧 树形结构
            var options = {
                jsonData: currentPoint,
                id: $("#" + idSelector)
            }
            ZTree.init(options);
        }
        var js_li_checked = $(this);
        var checkCount = false;
        $(".Js_li_first").each(function () {

            if ($(this).attr("data-value") == js_li_checked.attr("data-value")) {

                if ($(this).children(".Js_second_icon").is(":hidden")) {
                    $(this).children(".Js_first_icon").addClass("hidden");
                    $(this).children(".Js_second_icon").removeClass("hidden");
                    $(this).next().removeClass("hidden");
                    $(this).css({"background-color": "#1E3241", "color": "#ffffff"});
                } else {
                    $(this).children(".Js_first_icon").removeClass("hidden");
                    $(this).children(".Js_second_icon").addClass("hidden");
                    $(this).next().addClass("hidden");
                    $(this).css({"background-color": "#1E3241", "color": "#577185"});
                }
            } else {
                $(this).children(".Js_first_icon").removeClass("hidden");
                $(this).children(".Js_second_icon").addClass("hidden");
                $(this).next().addClass("hidden");
                $(this).css({"background-color": "#1D2B36", "color": "#577185"});
            }
        });


    });

    $(".Js_li_first").mouseover(function () {
        $(this).css({"background-color": "#141E27"});

    }).mouseout(function () {
        if ($(this).children(".Js_second_icon").is(":hidden")) {
            $(this).css("background-color", "#1E3241");
        } else {
            $(this).css("background-color", "#141E27");
        }
    })

    //点击展示日历
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dataFmt: "yyyy-MM-dd"})
    });


    function loadAllData() {
        loadData_0();
        loadData();
        loaddata_1();

    }

    //基本信息  数据

    function loadData_0() {
        var inContractId = $("input[name='inContractId']").val();
        if (inContractId) {
            var url = "ajax_query_single_inContract?inContractId=" + inContractId;
        } else {
            var url = "ajax_query_single_inContract";
        }
        var tpl = require("text!app/tpl/inContract/inContract_baseInfoTpl.html");
        $.get(url+"&t="+new Date().getMilliseconds(), function (data) {
            var html = template.compile(tpl)(data);
            $(".table_Info").html(html);
            if (data == null) {
                $(".pis_finished").addClass("hidden");
                $(".vis_finished").addClass("hidden");
            } else {
                if (data.canP == 1) {
                    $(".pis_finished").removeClass("hidden");
                } else {
                    $(".pis_finished").addClass("hidden");
                }
                if (data.canV == 1) {
                    $(".vis_finished").removeClass("hidden");
                } else {
                    $(".vis_finished").addClass("hidden");
                }
            }
            $(".js_height").height(800);
            var left_height = $(document).height() - 60;
            $(".js_height").height(left_height);
        })


    }

    //开票列表，用于基本信息页面
    function loadData() {
        var inContractId = $("input[name='inContractId']").val();
        var url = $(".select_invoice_condition").attr("data-url");
        var tpl = require("text!app/tpl/inContract/invoiceTpl.html");
        var data = $("#select_invoice_conditionForm").serialize().toString() + "&inContractId=" + inContractId;
        List(".table_invoice", tpl, url, data, 1, 10);

    };


    //关闭增改弹窗
    $("body").delegate("#cancelModel", "click", function () {
        $("#myModal").modal("hide");
    });

    $(".select_invoice_condition").click(function () {
        loadData();
    });

    $(".select_pay_condition").click(function () {
        loaddata_1();
    });


    //付款列表，用于基本信息页面
    function loaddata_1() {
        var inContractId = $("input[name='inContractId']").val();
        var url = $(".select_pay_condition").attr("data-url");
        var tpl = require("text!app/tpl/inContract/payTpl.html");
        var data = $("#select_pay_conditionFrom").serialize().toString() + "&inContractId=" + inContractId;
        List(".table_pay", tpl, url, data, 1, 10);
    };

    //验证与提交 进项合同的增加与修改
    jQuery.validator.addMethod("maxTime", function (value, element) {

        if (Date.parse(value) > new Date().getTime()) return false;
        return true;

    }, "晚于现时");
    var callbar = function callBack() {
        //进项合同的增改
        $(".addOrUpdateInContractForm").validate({

            rules: {
                sbillDate: {
                    maxTime: true
                },
                value: {
                    required: true,
                    number: true,
                    min: 0
                },
                payMoney: {
                    required: true,
                    number: true,
                    min: 0
                },
                billMoney: {
                    required: true,
                    number: true,
                    min: 0
                }
            },
            messages: {
                value: {
                    required: "填合同额",
                    number: "请填数字",
                    min: "不小于0"

                },
                payMoney: {
                    required: "填付款额",
                    number: "请填数字",
                    min: "不小于0"
                },
                billMoney: {
                    required: "填开票额",
                    number: "请填数字",
                    min: "不小于0"
                }
            },
            submitHandler: function (form) {
                var status = $("select[name='status']").val();
                if (status == 'Finish') {
                    var r = confirm("合同状态设定为‘已完结’，该合同将不可再操作。请确认是否继续？");
                    if (!r) {
                        $("#myModal").modal("hide");
                        return;
                    }
                }

                var _url = $(".addOrEdit_inContract").attr("data-url");
                $.ajax({
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    cache:false,
                    data: $(".addOrUpdateInContractForm").serializeObject(),
                    success: function (data) {
                        if (data.isSuccess) {
                            alert("操作成功");
                            var current_company_id = $("input[name='current_company_id']").val();
                            var inContract_add_id = data.id;//新增完之后的id
                            window.location.href = "sra_in_index?inContract_add_id=" + data.id + "&inContract_add_companyId=" + current_company_id;

                        } else {
                            alert(data.msg)
                        }
                        $("#myModal").modal("hide");

                    }

                });

            }

        });
        //开票的增改，开票的修改与增加，这里必须用submit
        $(".addOrUpdateInvoiceForm").validate({
            rules: {
                money: {
                    number: true,
                    min: 0,
                    max: Number($("input[name='leftInvoice']").val())
                },
                sinvoiceDate: {
                    maxTime: true
                }
            },
            messages: {
                money: {
                    number: "请填数字",
                    min: "不得小于0",
                    max: "不大于" + Number($("input[name='leftInvoice']").val())
                }

            },
            submitHandler: function (form) {
                var _url = $(".addOrEdit_invoice").attr("data-url");
                $.ajax({
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $(".addOrUpdateInvoiceForm").serializeObject(),
                    success: function (data) {
                        if (data.isSuccess) {
                            alert("操作成功");
                            loadAllData();
                        } else {
                            alert(data.msg);
                        }
                        $("#myModal").modal("hide");

                    }

                });

            }

        });
        //付款的增改 ,付款的增加与修改
        var leftPay = Number($("input[name='leftPay']").val());
        $(".addOrUpdatePayForm").validate({
            rules: {
                money: {
                    number: true,
                    min: 0,
                    max: leftPay
                },
                spayTime: {
                    maxTime: true
                }
            },
            messages: {
                money: {
                    number: "请填数字",
                    min: "不小于0的数",
                    max: "不大于" + leftPay
                }
            },
            submitHandler: function (form) {
                var _url = $(".addOrEdit_pay").attr("data-url");
                $.ajax({
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $(".addOrUpdatePayForm").serializeObject(),
                    success: function (data) {
                        if (data.isSuccess) {
                            alert("操作成功");

                        } else {
                            alert(data.msg)
                        }
                        $("#myModal").modal("hide");
                        loadAllData();
                    }

                })

            }
        });
        $.fn.modal.Constructor.prototype.enforceFocus = function () {
        };
        $(".select2").select2({
            placeholder:"关联销售合同",
            allowClear:true

        })


    }
    widget.init(callbar);

    //删除付款记录
    $("body").delegate(".deletePay", "click", function () {
        var _url = $(this).attr("data-url");
        $.ajax({
            url: _url,
            type: 'post',
            success: function (data) {
                if (data.isSuccess) {
                    alert("操作成功！");
                    loaddata_1();
                }
            }

        });
    });

    //删除开票记录
    $("body").delegate(".deleteInvoice", "click", function () {
        var _url = $(this).attr("data-url");
        $.ajax({
            url: _url,
            type: 'post',
            success: function (data) {
                if (data.isSuccess) {
                    alert("操作成功！");
                    loadData();
                }
            }
        });
    });


    //用于左侧树结构
    $("body").delegate(".table_Info", "change", function (event, treeId, treeNode) {
        //alert(treeNode.tId + ", " + treeNode.name);
        //loadData_0(treeNode.id);//加载页面首部
        $("input[name='inContractId']").val(treeNode.id);//需要赋值
        $(".js_modal_invoice").each(function () {
            $(this).attr("data-url", "addOrUpdate_invoice_modal?inContractId=" + treeNode.id);
        });
        $(".js_modal_pay").each(function () {
            $(this).attr("data-url", "addOrUpdate_pay_modal?inContractId=" + treeNode.id);
        })
        //var url = "sra_s_i?saleContractId="+treeNode.id;
        loadAllData();
        //$.get(url);
        //window.location.href=url;
    });

//设置左侧栏高度
    $("body").change(function () {
        var w = $(".js_height").find("ul").height() + 150;
        var r = $(".js_test_height").height() + 25;

        $("body").find(".js_height").css({'height': w > r ? w : r});
    });


    $(".sc_index_left_li").mouseover(function () {
        $(this).find(".add_new_inContract").removeClass("hidden");
    }).mouseout(function () {
        $(this).find(".add_new_inContract").addClass("hidden");
    });



})

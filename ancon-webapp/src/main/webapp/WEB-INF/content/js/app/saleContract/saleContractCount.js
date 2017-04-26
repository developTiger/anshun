/**
 * Created by admin on 2016/11/23.
 */
define(function(require,exports,module){

    var List = require("../common/pagelist");
    var template = require("template");
    var wDatePicker = require("wdatePicker");
    require("select2");
    require("ajaxUpload");
    $(".select2_single").select2({placeholder:"输入甲方名称查询" ,allowClear:true });
    $.fn.modal.Constructor.prototype.enforceFocus = function() {};

    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd'});
    });



    //数据统计
    //loadData();
    $("#selectForm_count").click(function () {
        loadData();
    }).trigger("click")

    function loadData(){
        var url = $("#selectForm_count").attr("data-url");
        var tpl = require("text!app/tpl/saleContract/saleContractCount.html");
        var data = $(".Js_saleContractCountForm").serialize().toString();
        List(".table",tpl,url,data,1,20);
        //左边菜单高度
        if(url != undefined || url != ""){
            $(".saleContract_left").height(800);
            var left_height = $(document).height()-60;
            $(".saleContract_left").height(left_height);
        }

    }

    //loadData_1()
    $("#selectForm_billing").click(function () {
        loadData_1();
    }).trigger("click")

    function loadData_1(){
        var url = $("#selectForm_billing").attr("data-url");
        var tpl = require("text!app/tpl/saleContract/saleContractCount_billing_tpl.html");
        var data = $(".Js_billingSelectForm").serialize().toString();
        List(".table",tpl,url,data,1,20);
//左边菜单高度
        if(url != undefined || url != ""){
            $(".saleContract_left").height(800);
            var left_height = $(document).height()-60;
            $(".saleContract_left").height(left_height);
        }

    }

    //loadData_2()
    $("#selectForm_gathering").click(function () {
        loadData_2();
    }).trigger("click")

    function loadData_2(){
        var url = $("#selectForm_gathering").attr("data-url");
        var tpl = require("text!app/tpl/saleContract/saleContractCount_gathering_tpl.html");
        var data = $(".Js_gatheringSelectForm").serialize().toString();
        List(".table",tpl,url,data,1,20);
//左边菜单高度
        if(url != undefined || url != ""){
            $(".saleContract_left").height(800);
            var left_height = $(document).height()-60;
            $(".saleContract_left").height(left_height);
        }
    }

    //loadData_3()
    $("#selectForm_conValue").click(function () {
        loadData_3();
    }).trigger("click")

    function loadData_3(){
        var url = $("#selectForm_conValue").attr("data-url");
        var tpl = require("text!app/tpl/saleContract/saleContractCount_conValue_tpl.html");
        var data = $(".Js_conValueSelectForm").serialize().toString();
        List(".table",tpl,url,data,1,20);

//左边菜单高度
        if(url != undefined || url != ""){
            $(".saleContract_left").height(800);
            var left_height = $(document).height()-60;
            $(".saleContract_left").height(left_height);
        }
    }

    //loadData_4();
    $("#selectForm_reportForm").click(function(){
        loadData_4();
    }).trigger("click")
    function loadData_4(){
        var url = $("#selectForm_reportForm").attr("data-url");
        var tpl = require("text!app/tpl/saleContract/saleContractCount_reportForm_tpl.html");
        var data = $(".Js_contractReportForm").serialize().toString();
        List(".table",tpl,url,data,1,20);
        //左边菜单高度
        if(url != undefined || url != ""){
            $(".saleContract_left").height(800);
            var left_height = $(document).height()-60;
            $(".saleContract_left").height(left_height);
        }
    }

    loadData_5();
    function loadData_5(){
        var url = $(".table").attr("data-url");
        var tpl = require("text!app/tpl/saleContract/saleContractCount_totalReportForm_tpl.html");
        url && List(".table",tpl,url,"",1,20);
//左边菜单高度
        if(url != undefined || url != ""){
            $(".saleContract_left").height(800);
            var left_height = $(document).height()-60;
            $(".saleContract_left").height(left_height);
        }
    }

    //导出列表 带有查询条件
    $(".billing_down").off("click");
    $(".billing_down").on("click",function () {

        var beginTime = $("input[name='beginTime']").val();
        var endTime = $("input[name='endTime']").val();
        var taxType = $("select[name='billing_taxType']").val();
        var name = $("input[name='billing_name']").val();
        var branchCompany = $("select[name='billing_branchCompany']").val();
        var jiafangName = $("select[name='billing_jiaFangName']").val();
        var url = encodeURI(encodeURI("ajax__billing_down"+"?billing_beginTime="+beginTime+"&billing_endTime="+endTime+"&billing_taxType="+taxType
            +"&billing_name="+name+"&billing_branchCompany="+branchCompany+"&billing_jiafangName="+jiafangName));
        window.location.href=url;
        //$(this).attr("href",url);

    })

    $(".saleContract_down").click(function(){
        var beginTime = $("input[name='begin_time']").val();
        var endTime = $("input[name='end_time']").val();
        var branchCompany = $("select[name='branch_company']").val();
        var jiafangName = $("select[name='jia_fangName']").val();
        var contractType = $("select[name='contract_type']").val();
        var projectMajor = $("select[name='project_major']").val();
        var contractStatus = $("select[name='contract_status']").val();

        var url = encodeURI(encodeURI("ajax__saleContract_down"+"?saleContract_beginTime="+beginTime+"&saleContract_endTime="+endTime+"&saleContract_branchCompany="+branchCompany
        +"&saleContract_jiafangName="+jiafangName+"&saleContract_contractType="+contractType+"&saleContract_projectMajor="+projectMajor
        +"&saleContract_contractStatus="+contractStatus));
        window.location.href=url;
        //$(this).attr("href",url);

    })

    $(".gathering_down").click(function(){
        var url = "ajax__gathering_down?"+encodeURI($(".Js_gatheringSelectForm").serialize().toString());
        window.location.href = url;
    })

    $(".conValue_down").click(function(){
        var url = "ajax_conValue_down?"+encodeURI($(".Js_conValueSelectForm").serialize().toString());
        window.location.href = url;
    })

    $(".reportForm_down").click(function () {
        var url = "ajax_reportForm_down?"+encodeURI($(".Js_contractReportForm").serialize().toString());
        window.location.href = url;
    })

    //表格排序
    //中标通知书
    $("body").on("click",".bidNotice",function(){
        $("input[name='fourSorts']").val("a_bidNotice");//a_bidNotice 顺序  d_bidNotice 倒叙

        var url = $("#selectForm_count").attr("data-url");
        var tpl = require("text!app/tpl/saleContract/saleContractCount.html");
        var data = $(".Js_saleContractCountForm").serialize().toString();
        List(".table",tpl,url,data,1,20);
        $(".bidNotice").hide();
        $(".2_bidNotice").removeClass("hidden");
    })

    $("body").on("click",".2_bidNotice",function(){
        $("input[name='fourSorts']").val("d_bidNotice");//a_bidNotice 顺序  d_bidNotice 倒叙

        var url = $("#selectForm_count").attr("data-url");
        var tpl = require("text!app/tpl/saleContract/saleContractCount.html");
        var data = $(".Js_saleContractCountForm").serialize().toString();
        List(".table",tpl,url,data,1,20);
        $(".2_bidNotice").hide();
        $(".bidNotice").removeClass("hidden");
    })

    //施工许可证
    $("body").on("click",".constructLicense",function(){
        $("input[name='fourSorts']").val("a_constrctLicense");//a_bidNotice 顺序  d_bidNotice 倒叙

        var url = $("#selectForm_count").attr("data-url");
        var tpl = require("text!app/tpl/saleContract/saleContractCount.html");
        var data = $(".Js_saleContractCountForm").serialize().toString();
        List(".table",tpl,url,data,1,20);
        $(".constructLicense").hide();
        $(".2_constructLicense").removeClass("hidden");
    })

    $("body").on("click",".2_constructLicense",function(){
        $("input[name='fourSorts']").val("d_constrctLicense");//a_bidNotice 顺序  d_bidNotice 倒叙

        var url = $("#selectForm_count").attr("data-url");
        var tpl = require("text!app/tpl/saleContract/saleContractCount.html");
        var data = $(".Js_saleContractCountForm").serialize().toString();
        List(".table",tpl,url,data,1,20);
        $(".2_constructLicense").hide();
        $(".constructLicense").removeClass("hidden");
    })

    //竣工验收单
    $("body").on("click",".finishCheck",function(){
        $("input[name='fourSorts']").val("a_finishCheck");//a_bidNotice 顺序  d_bidNotice 倒叙

        var url = $("#selectForm_count").attr("data-url");
        var tpl = require("text!app/tpl/saleContract/saleContractCount.html");
        var data = $(".Js_saleContractCountForm").serialize().toString();
        List(".table",tpl,url,data,1,20);
        $(".finishCheck").hide();
        $(".2_finishCheck").removeClass("hidden");
    })

    $("body").on("click",".2_finishCheck",function(){
        $("input[name='fourSorts']").val("d_finishCheck");//a_bidNotice 顺序  d_bidNotice 倒叙

        var url = $("#selectForm_count").attr("data-url");
        var tpl = require("text!app/tpl/saleContract/saleContractCount.html");
        var data = $(".Js_saleContractCountForm").serialize().toString();
        List(".table",tpl,url,data,1,20);
        $(".2_finishCheck").hide();
        $(".finishCheck").removeClass("hidden");
    })

    //工程结算单
    $("body").on("click",".projectSettlement",function(){
        $("input[name='fourSorts']").val("a_projectSettlement");//a_bidNotice 顺序  d_bidNotice 倒叙

        var url = $("#selectForm_count").attr("data-url");
        var tpl = require("text!app/tpl/saleContract/saleContractCount.html");
        var data = $(".Js_saleContractCountForm").serialize().toString();
        List(".table",tpl,url,data,1,20);
        $(".projectSettlement").hide();
        $(".2_projectSettlement").removeClass("hidden");
    })

    $("body").on("click",".2_projectSettlement",function(){
        $("input[name='fourSorts']").val("d_projectSettlement");//a_bidNotice 顺序  d_bidNotice 倒叙

        var url = $("#selectForm_count").attr("data-url");
        var tpl = require("text!app/tpl/saleContract/saleContractCount.html");
        var data = $(".Js_saleContractCountForm").serialize().toString();
        List(".table",tpl,url,data,1,20);
        $(".2_projectSettlement").hide();
        $(".projectSettlement").removeClass("hidden");
    })

    //合同是否已返回
    $("body").on("click",".contractIsReturn",function(){
        $("input[name='fourSorts']").val("a_contractIsReturn");//a_bidNotice 顺序  d_bidNotice 倒叙

        var url = $("#selectForm_count").attr("data-url");
        var tpl = require("text!app/tpl/saleContract/saleContractCount.html");
        var data = $(".Js_saleContractCountForm").serialize().toString();
        List(".table",tpl,url,data,1,20);
        $(".contractIsReturn").hide();
        $(".2_contractIsReturn").removeClass("hidden");
    })

    $("body").on("click",".2_contractIsReturn",function(){
        $("input[name='fourSorts']").val("d_contractIsReturn");//a_bidNotice 顺序  d_bidNotice 倒叙

        var url = $("#selectForm_count").attr("data-url");
        var tpl = require("text!app/tpl/saleContract/saleContractCount.html");
        var data = $(".Js_saleContractCountForm").serialize().toString();
        List(".table",tpl,url,data,1,20);
        $(".2_contractIsReturn").hide();
        $(".contractIsReturn").removeClass("hidden");
    })

    //合同编号
    $("body").on("click",".number",function(){
        $("input[name='fourSorts']").val("a_number");//a_bidNotice 顺序  d_bidNotice 倒叙

        var url = $("#selectForm_count").attr("data-url");
        var tpl = require("text!app/tpl/saleContract/saleContractCount.html");
        var data = $(".Js_saleContractCountForm").serialize().toString();
        List(".table",tpl,url,data,1,20);
        $(".number").hide();
        $(".2_number").removeClass("hidden");
    })

    $("body").on("click",".2_number",function(){
        $("input[name='fourSorts']").val("d_number");//a_bidNotice 顺序  d_bidNotice 倒叙

        var url = $("#selectForm_count").attr("data-url");
        var tpl = require("text!app/tpl/saleContract/saleContractCount.html");
        var data = $(".Js_saleContractCountForm").serialize().toString();
        List(".table",tpl,url,data,1,20);
        $(".2_number").hide();
        $(".number").removeClass("hidden");
    })



    //更新销售合同
    $(".saleContract_export").on("click",function(){
        window.location.href="exportSaleContracts";
        $("#myModal").modal("hide");
    })

    //导入合同信息
    $(".saleContract_import").click(
        function(){
            $.ajaxFileUpload({
                url: 'importSaleContracts',
                secureuri: false,
                fileElementId: ['fileId'],
                dataType: 'text',
                // data: $("#publishNews").serializeObject(),
                success: function (data, status) {

                    var resultStart = data.indexOf("{");
                    var resultEnd = data.indexOf("}");
                    var result = JSON.parse(data.substring(resultStart, resultEnd + 1));

                    if (result.isSuccess) {
                        alert("操作成功！");
                        $("#myModal").modal("hide");
                        loadData();
                    } else {
                        alert(result.msg);
                        mode=1;
                        $("#myModal").modal("show");
                    }
                },
                error: function (data, status, e) { //服务器响应失败时的处理函数
                    $('#result').html('文件上传失败，请重试！！');
                }
            });

        }
    );


})
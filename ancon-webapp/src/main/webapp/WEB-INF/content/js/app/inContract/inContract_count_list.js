/**
 *
 * 这个主要用于进项合同的统计表（进项合同，付款，开票）
 * Created by xiazl on 2016/12/8.
 */
define(function (require, exports, module) {
    var List = require("../common/pagelist");
    var template = require("template");
    var widget = require("../common/widget");
    require("../common/jquery.serializeObject");
    var wDatePicker = require("wdatePicker");
    var validate = require("validate");
    //点击展示日历
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dataFmt: "yyyy-MM-dd"})
    });
//sdf

    //加载进项合同列表
    loadInContractList();
    $("#select_inContract_list").click(function () {
        loadInContractList();
    });

    //进项合同列表统计
    function loadInContractList() {
        var url = $("#select_inContract_list").attr("data-url");
        var tpl = require("text!app/tpl/inContract/inContractListTpl.html");
        var data = $(".inContract_form").serialize().toString();
        List(".table", tpl, url, data, 1, 20);
        //setleft();
        //左边菜单高度
        if(url != undefined || url != ""){
            $(".saleContract_left").height(800);
            var left_height = $(document).height()-60;
            $(".saleContract_left").height(left_height);
        }
        $(".js_height").height(800);
        var left_height = $(document).height() - 60;
        $(".js_height").height(left_height);

    };


    //加载付款记录列表
    loadPayList();
    $("#select_partyB").click(function () {
        loadPayList();
    });
    //付款列表统计
    function loadPayList() {
        var url = $("#select_partyB").attr("data-url");
        var tpl = require("text!app/tpl/inContract/payListTpl.html");
        var data = $(".searchFrom").serialize().toString();
        List(".tablePayList", tpl, url, data, 1, 20);
        //setleft();
        //左边菜单高度
        if(url != undefined || url != ""){
            $(".saleContract_left").height(800);
            var left_height = $(document).height()-60;
            $(".saleContract_left").height(left_height);
        }
        $(".js_height").height(800);
        var left_height = $(document).height() - 60;
        $(".js_height").height(left_height);

    };

    //加载开票记录
    loadInvoiceList();
    $(".selecte_invoiceList").click(function () {
        loadInvoiceList();
    });
    //开票列表统计
    function loadInvoiceList() {
        var url = $(".selecte_invoiceList").attr("data-url");
        var tpl = require("text!app/tpl/inContract/invoiceListTpl.html");
        var data = $(".invoiceList").serialize().toString();
        List(".invoiceList1", tpl, url, data, 1, 20);
        //setleft();
        //左边菜单高度
        if(url != undefined || url != ""){
            $(".saleContract_left").height(800);
            var left_height = $(document).height()-60;
            $(".saleContract_left").height(left_height);
        }

        $(".js_height").height(800);
        var left_height = $(document).height() - 60;
        $(".js_height").height(left_height);

    };

    /**
     * 条件下载付款数据
     */
    $("#downloadPay").click(function () {
        var url = "ajax_downLoad_pay?" + $(".searchFrom").serialize().toString();
        window.location.href = url;
    });
    /**
     * 条件下载进项合同数据
     */
    $("#downloadInContract").click(function () {
        var url = "ajax_downLoad_inContract?" + $(".inContract_form").serialize().toString();
        window.location.href = url;
    });
    /**
     * 条件下载开票数据
     */
    $("#downloadInvoice").click(function () {
        var url = "ajax_downLoad_invoice?" + $(".invoiceList").serialize().toString();
        window.location.href = url;
    });

    //$("body").click(function () {
    //    setleft();
    //});

    //function setleft() {
    //    var b=$("body").height();
    //    var w=$(window).height()+300;;
    //    var l = $(".inContract_charts_left").height();
    //
    //    var d = $(document).height();
    //    var r = $(".inContract_count").height();
    //
    //    $(".inContract_charts_left").css({'height': w > r ? w : r});
    //}

})
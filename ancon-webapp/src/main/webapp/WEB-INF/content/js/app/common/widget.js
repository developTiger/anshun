/**
 * Created by zhouzh on 2016/5/19.
 */
define(function (require, exports, module) {
    require("jquery");

    var template = require("template");
    var modal = require("text!../tpl/modal.html");
    template.isEscape = false;
    //暴露modal方法
    exports.modal = function (opt) {
        var options = {
            id: "myModal",
            title: "",
            body: "",
            widths: "500px"
        };
        var data = $.extend({}, options, opt);
        var html = template.compile(modal)(data);
        _existModalDel();
        var $html = $(html);
        $(document.body).append($html);
        $("#" + data.id).modal({backdrop: 'static', keyboard: false});
        return $html
    }
    exports.init = function (callback) {
        _addBtn(".i_modal", callback);
        _addBtn1(".i_modal1", callback);
    }

    var t = new Date().getTime();


    //添加按钮事件
    var _addBtn = function (btn, callback) {

        $("body").delegate(btn, "click", function (event) {
            var t1 = new Date().getTime();

            if (t1 - t > 1000) {//至少2秒触发一次
                t = t1;
                var th = $(this);
                var width = "500px";
                if (th.attr("m_width"))
                    width = th.attr("m_width");
                $.post(encodeURI(encodeURI(th.attr("data-url"))), function (data) {
                    exports["modal"]({
                        title: th.attr("data-title"),
                        widths: width,
                        body:data
                    });

                    callback && (typeof  callback == "function") && callback();
                })
                //return false;
            }
        })

        //var that = $(btn);
        //that.each(function () {
        //
        //})

    }


    var _addBtn1 = function (btn, callback) {

        $(btn).click(function (event) {
            var th = $(this);
            var width = "500px";
            if (th.attr("m_width"))
                width = th.attr("m_width");

            $.post(encodeURI(encodeURI(th.attr("data-url"))), function (data) {
                exports["modal"]({
                    title: th.attr("data-title"),
                    widths: width,
                    body:data
                });
                callback && (typeof  callback == "function") && callback();
            })

            return false;
        })

        //var that = $(btn);
        //that.each(function () {
        //
        //})

    }


    exports.showModal = function (url, title, callback) {

        $.post(encodeURI(url), function (data) {
            exports["modal"]({
                title: title,
                body: data
            });
            callback && (typeof  callback == "function") && callback();
        });
    }

    //若页面存在modal，则删除重新创建，避免ID重复
    var _existModalDel = function () {
        var $Js_modal = $(".Js_modal");
        if ($Js_modal.length > 0) {
            $Js_modal.remove();
        }
    }
    //取消按钮
    $("body").delegate("#cancelModal", "click", function () {
        $("#myModal").modal("hide")
    });
})

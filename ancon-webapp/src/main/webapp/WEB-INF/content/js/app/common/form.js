define(function (require, exports, module) {
    require("jquery");
    require("bootstrap");
    var template = require("template");

    //审核页面核心js加载
    // var form = require("../common/form")
    //初始化 添加验证 及 做提交
    //form.init();  //直接form.init()无回调函数的 初始化，则默认使用默认validate自动验证，验证需要在html 标签内设置验证规则 如  <input type="text" id="reason" required="required" minlength="5" email="true" />
    // form.init(function () { // init 回调函数
    //   //此处自定义验证逻辑 ，最后调用 form.doSubmit 提交签核
    //    form.doSubmit();
    //})
    exports.init = function (submit) {

        var isPublicSubmit=false;
        if(submit){
            isPublicSubmit = false;
        }
        else {
            isPublicSubmit = true;
        }
        this.bindBtn(submit,isPublicSubmit);
    };
    exports.init2 = function (beforSubmit,submit) {
        beforSubmit();
        var isPublicSubmit=false;
        if(submit){
            isPublicSubmit = false;
        }
        else {
            isPublicSubmit = true;
        }
        this.bindBtn(submit,isPublicSubmit);
    };

    exports.render = function (target, tpl) {
        if (!target) return;
        var formNo = target.attr("form-no");
        var viewOnly = target.attr("view_only");
        var _url = target.attr("data-url");
        $.ajax({
            url: _url + "?formNo=" + formNo + "&viewOnly=" + viewOnly + "&t=" + new Date().getMilliseconds(),
            type: 'get',
            async: false,
            success: function (data) {
                if (data) {
                    var html = template.compile(tpl)(data);
                    target.html(html);
                }
            }
        });
    };
    exports.submit = function (validate,isPublicSubmit) {

        if(isPublicSubmit){
            $("#approveForm").submit();
        }else {
            validate();
        }
    };



    exports.doSubmit = function () {
        var appUrl = $("#approveUrl").val();
        $.ajax({
            url: appUrl+"?t=" + new Date().getMilliseconds(),
            type: 'post',
            data: $("#approveForm").serialize(),
            success: function (data) {
                if (data.isSuccess) {
                    alert("操作成功");

                    $(window.opener.document).find(".pagination li.active a")[0].click()
                    //window.opener.document.getElementsByClassName()
                    window.close();

                }
                else
                    alert(data.msg)
            }
        });

    };
    exports.bindPublicSubmit=function(){
        var that = this;
        $("#approveForm").validate({
            submitHandler: function (form) {
                that.doSubmit();
            }
        });

    };
    exports.bindBtn = function (validate,isPublicSubmit) {
        var that = this;
      //  $("body").undelegate('.appbtn', 'click');防止重复绑定
        if(isPublicSubmit){
            that.bindPublicSubmit();
        }
        $("body").delegate(".appbtn", "click", function () {
            var type = $(this).attr("btn-type")

            if (type == "approver") {
                $("#appValue").val(1);
                that.submit(validate,isPublicSubmit);
            }
            if (type == "reject") {
                $("#appValue").val(2);
                that.doSubmit();
            }
            if (type == "goBack") {
                $("#appValue").val(3);
                that.doSubmit();
            }

        })
    };

})

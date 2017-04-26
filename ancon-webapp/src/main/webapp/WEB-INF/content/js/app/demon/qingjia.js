/**
 * Created by swb on 2016/7/5.
 */
define(function (require, exports, module) {
    require('../init');
    var template = require("template");
    var List = require("../common/pagelist");
    require("../common/jquery.serializeObject");
    var widget = require("../common/widget");
    var check = require("../common/checkbox");
    var validate = require("validate");


    var wDatePicker = require("wdatePicker");
    //时间控件
    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({ dateFmt: 'yyyy-MM-dd HH:00:00' });
    });

    widget.init();
    check.checkAll("body", ".checkAll", ".checkBtn")


    /*var callb= function() {
        $("#qjApplyForm").validate({
            rules: {
                summery: {
                    required: true
                }

            },
            messages: {
                summery: {
                    required: "必填"
                }
            },
            submitHandler: function (form) {   //表单提交句柄,为一回调函数，带一个参数：form



                var _url = $("#qjSubmit").attr("data-url");
                var options = {
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#qjApplyForm").serializeObject(),
                    success: function (data) {
                        if (data == "success")
                            alert("新增成功");
                        $("#myModal").modal("hide")
                        loadData();
                    }
                };
                $.ajax(options);
            }

        })


    }
    widget.init(callb);*/
});
/**
 * Created by zhouz on 2016/5/15.
 */
define(function (require, exports, module) {
    require('../init');
    var template = require("template");
    var List = require("../common/pagelist");
    require("../common/jquery.serializeObject");
    var widget = require("../common/widget");
    var check = require("../common/checkbox");
    var validate = require("validate");
    check.checkAll("body", ".checkAll", ".checkBtn")
    loadData();
    $("#empQueryBtn").click(function () {
        loadData();
    });

    function loadData() {
        var tpl = require("text!app/tpl/uauth/userTableTpl.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize().toString();
        List("#table", tpl, url, data, 1, 10);
        var showOperate=$("#showOperate").val();
        if(showOperate==0){
            $(".showOperate").css("display","none");
        }
    }

    //员工信息库 页面数据展示
    loadData1();
    $("#empQueryBtn1").click(function () {
        loadData1();
    });

    function loadData1() {
        var tpl = require("text!app/tpl/uauth/userInfoDatabaseTpl.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize().toString();
        List("#table1", tpl, url, data, 1, 10);

    }

    //$("body").delegate("#cancelModal", "click", function () {
    //    $("#myModal").modal("hide")
    //});

    $("#disableUser").click(function () {
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        setUserStaus(chk_value.toString(), false);
    })
    $("#enableUser").click(function () {

        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        setUserStaus(chk_value.toString(), true);
    })

    function setUserStaus(selectids, isActive) {
        var options = {
            url: "ajax_setUserStstus?t=" + new Date().getMilliseconds(),
            type: 'post',
            data: {isActive: isActive, ids: selectids},
            success: function (data) {
                if (data == "success") {
                    alert("修改成功");
                    loadData();
                }
                else
                    alert(data);
            }
        };
        $.ajax(options);
    }

    $("body").delegate("#reset-holiday","click",function(){
        if(!(window.confirm("确定重置员工假日吗？"))){return;}
        var _url=$(this).attr("data-url");
        $.ajax({
            url:_url+"?t="+new Date().getMilliseconds(),
            success:function(data){
                if (data.isSuccess) {
                    alert("重置员工带薪年假与疗养假成功！");
                    $("#myModal").modal("hide")
                }
                else
                    alert("重置假日失败!");
            }

        });
    });
    $("body").delegate("#updateUserRole", "click", function () {
        var _url = $(this).attr("data-url");
        $.ajax({
            url: _url + "?t=" + new Date().getMilliseconds(),
            type: 'post',
            data: $("#addEmpRoleform").serializeObject(),
            success: function (data) {
                if (data.isSuccess) {
                    alert("修改成功");
                    $("#myModal").modal("hide")
                }
                else
                    alert("修改失败");
            }
        });
    });

    $("body").delegate(".resetPwd", "click", function () {
        var _url = $(this).attr("data-url");
        $.ajax({
            url: _url + "&t=" + new Date().getMilliseconds(),
            type: 'get',
            success: function (data) {
                if (data.isSuccess) {
                    alert("重置密码成功!");
                }
                else
                    alert("修改失败");
            }
        });
    });

    var vali = function validates() {

        // 联系电话(手机/电话皆可)验证
        jQuery.validator.addMethod("isTel", function (value, element) {
            var length = value.length;
            var mobile = /^1\d{10}$/;
            return (length == 11 && mobile.test(value));
        }, "");


        $("#addUserform").validate({
            rules: {
                phone: {
                    required: true,
                    isTel: $("#middle-name").val()
                }
            },
            messages: {
                phone: {
                    required: "必填",
                    isTel: "请输入正确的手机验证格式"
                }
            },
            submitHandler: function (form) {   //表单提交句柄,为一回调函数，带一个参数：form
                var _url = $("#addOrUpdateUser").attr("data-url");
                var options = {
                    url: _url + "?t=" + new Date().getMilliseconds(),
                    type: 'post',
                    data: $("#addUserform").serializeObject(),
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
    widget.init(vali);


    //修改密码
    $("body").on("click",".changePassword", function () {
        var oldPassword = $("#oldPassword").val();
        var newPassword = $("#newPassword").val();
        var checkPassword = $("#checkPassword").val();

        if(newPassword != checkPassword)
            return alert("新密码与确认密码不一致！");
        if(oldPassword == "")
            return alert("请输入原密码！");
        if(newPassword == "")
            return alert("请输入新密码！");
        if(checkPassword == "")
            return alert("请输入确认密码！");
        $.ajax({
            url:"ajax_changePassword",
            type:"post",
            data:{oldPassword:oldPassword,newPassword:newPassword},
            success: function (data) {
                if(data.isSuccess){
                    alert("修改成功！");
                    window.location.href="sra_index";
                }else{
                    alert(data.msg);
                }
            }
        })
    })


});
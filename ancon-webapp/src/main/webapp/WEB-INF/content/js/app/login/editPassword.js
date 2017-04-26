/**
 * Created by zhouz on 2016/5/15.
 */
define(function (require, exports, module) {
    require('../init');
    var List = require("../common/pagelist");
    var check = require("../common/checkbox");
    require("../common/jquery.serializeObject");
    var template = require("template");
    var widget = require("../common/widget");
    var validate = require("validate");
    var Checkpwdstrong=require("./checkpwdstrong");
    require("mutiSelect");

    loadData();
    function loadData(){
        if($(".old-password").val()==null){
            $(".show-old-password").removeClass("template");
        }
    }
    $("body").delegate(".old-password","keyup",function(){

        var old_Password=$(this).val();
        if(old_Password != null || old_Password != "") {
            $(".show-old-password").text("");
        }
    });
    $("body").delegate(".js_check_pwd_strong","blur",function(){

        if($(".js_sure_password").val()==null || $(".js_sure_password").val()==""){
            return;
        }
        if(checkPassword()){
            $(".js_cn_result_password").text("");
        }else{
            $(".js_cn_result_password").text("密码输入不一致");
        }
    });
    $("body").delegate(".js_sure_password","blur",function(){
        if(checkPassword()){
            $(".js_cn_result_password").text("");
        }else{
            $(".js_cn_result_password").text("密码输入不一致");
        }
    });
    function checkPassword() {
        var new_password=$(".js_check_pwd_strong").val();
        var new_check_password=$(".js_sure_password").val();
        if(new_password==new_check_password){
            return true;
        }else{
            return false;
        }
    };
    function checkPasswordLong() {
        var new_password=$(".js_check_pwd_strong").val();

        if(new_password.length<6){
            return false;
        }
        if(new_password.length>=6){
            return true;
        }
    };

    $("body").delegate(".cn-sure-password-btn","click",function(){

        if(!checkPasswordLong()){
            return;
        }
        var old_Password=$(".old-password").val();
        var temp;
        $.ajax({
            url:"ajax_sure_password",
            async:false,
            type:"post",
            data:{old_password:old_Password},
            success:function(data){
                temp=data.isSuccess;
                $(".show-old-password").text(data.msg);
            }
        });
        if(!temp) return;
        $.ajax({
            url:$("#js_changePassword").attr("data-url"),
            type:"get",
            data:{new_password:$(".js_check_pwd_strong").val()},
            success:function(data){
                if(data.isSuccess){
                    alert("密码更新成功!三秒钟后调至登录页");
                    window.setTimeout("window.location.href='sra_index'",3000);
                }else{
                    alert("密码更新失败!!!");
                };
            }
        });
    });


});
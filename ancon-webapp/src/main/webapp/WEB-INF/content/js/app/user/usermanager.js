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

    require('treeTable');
    var zTree=require("ztree");
    var myWidGet=require("../common/myWidGet");
    var zTree= require('../common/tree');

    require("mutiSelect");

    require("text!app/tpl/uauth/deptTableTpl.html");
    require("text!app/tpl/uauth/menuTableTpl.html");
    require("text!app/tpl/uauth/permissionTableTpl.html");
    require("text!app/tpl/uauth/roleTableTpl.html");
    require("text!app/tpl/uauth/userTableTpl.html");
    require("text!app/tpl/uauth/partyATableTpl.html");
    require("text!app/tpl/uauth/partyBTableTpl.html");
    require("text!app/tpl/uauth/parameterTableTpl.html");
    require("text!app/tpl/uauth/ptypemanagerTableTpl.html");


    $(function () {
        check.relCheck("#example-advanced");
        check.relCheck("#table");
    })

    check.checkAll("body", ".checkAll", ".checkBtn")
    loadData();
    $("#QueryBtn").click(function () {
        loadData();
    });
    function loadData() {
        var temp=$("#ajax_attend").val();
        var tpl = require("text!app/tpl/uauth/"+temp+"TableTpl.html");
        var url = $("#searchForm").attr("data-url");
        var data = $("#searchForm").serialize();
        List("#table", tpl, url, data, 1, 10);
        if(temp=="menu"){
            $(".treetable").treetable({ expandable: true ,initialState: "expanded" });
            var height= window.document.body.scrollHeight;
            var js_cnUsers=document.getElementById("js_cnUsers");
            js_cnUsers.style.height=(height-60)+"px";
        }

    }

    $("body").delegate("#cancelModal", "click", function () {
        $("#myModal").modal("hide")
    });

    $("#deleteMenu").click(function () {
        var chk_value = [];
        $('.checkBtn:checked').each(function () {
            chk_value.push($(this).val());
        });
        setUserStaus(chk_value.toString());
    })

    function setUserStaus(selectids) {
        var options = {
            url: "ajax_deleteMenu?t=" + new Date().getMilliseconds(),
            type: 'post',
            data: {ids: selectids},
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

    $("body").delegate("#deleteRole", "click", function () {
        var _url = $("#deleteRole").attr("data-url");
        var options = {
            url: _url + "&t=" + new Date().getMilliseconds(),
            type: 'post',
            success: function (data) {
                if (data == "success")
                    alert("删除成功");
                $("#myModal").modal("hide");
                loadData();
            }
        };
        $.ajax(options);
    });

    var callb=function callback() {
        var temp=$("#ajax_attend").val();
        if(temp=="role"){
            $('#role-name').multiselect({
                includeSelectAllOption: true,
                maxHeight: 400,
                minWidth:198,
                buttonWidth: '227px'
            });
            var hidPermissionGroupName = $("#Js_hidPermissionGroup").val();
            var h_1 = hidPermissionGroupName.substring(1,hidPermissionGroupName.length-1);
            var h_3=h_1.replace(/\s+/g,"");//去掉字符串所有空格
            var h_2 = [];
            h_2= h_3.split(",");
            $('#role-name').multiselect('select', h_2);//弹窗弹出之后再加载
        }

        $("#addOrUpdate").validate({
             rules: {
                 roleName: {
                     required: true
                 },
                 idCode: {
                     required: true
                 },
                 description: {
                     required: true
                 }
             },
             messages: {
                 roleName: {
                     required: "必填"
                 },
                 idCode: {
                     required: "必填"
                 },
                 description: {
                     required: "不能为空"
                 }
             },
             submitHandler: function (form) {   //表单提交句柄,为一回调函数，带一个参数：form


                 if(temp=="permission"){
                     var option = {};
                     option.treeId = "tree1";
                     option.inputId = "menuId";
                     myWidGet.submitTreeVal(option);
                 }
                 if(temp=="role"){
                     $("#pid").val($("#role-name").val().toString());
                 }

                 var _url = $("#add").attr("data-url");
                 var options = {
                     url: _url + "?t=" + new Date().getMilliseconds(),
                     type: 'post',
                     data: $("#addOrUpdate").serializeObject(),
                     success: function (data) {
                         if (data.isSuccess) {
                             alert("操作成功");
                             $("#myModal").modal("hide")
                             loadData();
                         }else{
                             alert(data.msg);
                         }
                     }
                 };
                 $.ajax(options);
             }

         })

        if(temp=="permission"){
            var options = {
                // jsonData:eval($("#tree1").attr("data-json")),
                url:"ajax_menu_query_list",
                id:"#tree1"
            }
            zTree.init(options);
        }
    }
    widget.init(callb);

    $("body").delegate(".resetPwd", "click", function () {
        if(window.confirm("确认重置密码吗！！！")){
            var _url = $(this).attr("data-url");
            $.ajax({
                url: _url + "&t=" + new Date().getMilliseconds(),
                type: 'get',
                success: function (data) {
                    if (data.isSuccess) {
                        alert("重置密码成功!");
                    }
                    else
                        alert("修改失败"+data.msg);
                }
            });
        }
    });

    $("body").delegate(".js_delete", "click", function () {
        if(window.confirm("确认要删除吗！！！")){
            var _url = $(this).attr("data-url");
            $.ajax({
                url: _url + "&t=" + new Date().getMilliseconds(),
                type: 'get',
                success: function (data) {
                    if (data.isSuccess) {
                        alert("删除成功!");
                        $("#myModal").modal("hide")
                        loadData();
                    }
                    else
                        alert("删除失败");
                }
            });
        }
    });

    $("body").delegate(".js_clear_cache","click",function(){
        $.ajax({
            url: "ajax_clear_cache",
            type:'get',
            success:function(data){
                if(data.isSuccess){
                    alert("操作成功!");
                }
            }
        });
    });

    $("body").delegate(".js_reset0","click",function(){
        var id=$(this).attr("data-title");
        var url=$(this).attr("data-url");
        $.ajax({
            url: url,
            type:'get',
            data: {status:1, id: id},
            success:function(data){
                if(data.isSuccess){
                    alert("操作成功!");
                    window.location.reload(true);
                }else{
                    alert(data.msg);
                }
            }
        });
    });

    $("body").delegate(".js_reset1","click",function(){
        var id=$(this).attr("data-title");
        var url=$(this).attr("data-url");
        $.ajax({
            url: url,
            type:'get',
            data: {status:0, id: id},
            success:function(data){
                if(data.isSuccess){
                    alert("操作成功!");
                    window.location.reload(true);
                }else{
                    alert(data.msg);
                }
            }
        });
    });


});



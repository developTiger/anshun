/**
 * Created by wangguangyang on 16/8/11.
 */

//exports 用于导出当前模块的方法或者变量。在node中它是唯一导出的出口，但是在dojo中我们可以直接在模块中返回一个对象字面量。

/*module　module引用module模块会返回一个对象，该对象有三个参数：
id：一个唯一的模块id，在程序中唯一标识一个模块；require([module.id], function(m){}); m代表了该id所对应的模块；
这个属性在一些需要需要根据模块id与一个计数器拼接起来的功能时常用到，比如dojo/request/script中callback参数
*/
define(function (require, exports, module) {
    function tableSave(config) {
        this.cfg = {
            targetTable: ".tableSave",
            modifyBtn: ".modifyBtn",
            saveBtn: ".saveBtn",
            postUrl: ""
        }
        this.cfg = $.extend({}, this.cfg, config)
    }

    tableSave.prototype.init = function () {
        this.do();
    }
    tableSave.prototype.do = function () {
        var self = this;
        var table = this.cfg.targetTable;
        var modifyBtn = this.cfg.modifyBtn;
        var saveBtn = this.cfg.saveBtn;
        $(table).on("click", modifyBtn, function () {
            $(this).closest("tr").removeClass("disabled").find("input[type=text]").attr("disabled", false);
            $(this).closest("tr").removeClass("disabled").find("select").attr("disabled", false);
            $(this).closest("tr").find(".js_hid").removeClass("hidSelect");
        }).on("click", saveBtn, function () {
            var cacheData = {};

            var that=this;
            $(this).closest("tr").find("input").each(function (index, element) {
                var inputName = $(element).attr("name");
                cacheData[inputName] = $(element).val();
            })
            $(this).closest("tr").find("select").each(function (index, element) {
                var inputName = $(element).attr("name");
                cacheData[inputName] = $(element).val();
            });
            console.log(cacheData)
            console.log(JSON.stringify(cacheData));

            $.post(self.cfg.postUrl, cacheData, function (data) {
                if (data.isSuccess) {
                    $(that).closest("tr").addClass("disabled").find("input[type=text]").attr("disabled", true);
                    $(that).closest("tr").addClass("disabled").find("select").attr("disabled", true);
                    $(that).closest("tr").find(".js_hid").addClass("hidSelect");
                    //$(".js_hid").css("width: 60px", "overflow: hidden");
                }
            })
        })
    }
    module.exports=tableSave;
});
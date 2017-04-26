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
        }).on("click", saveBtn, function () {
            var cacheData = {};
            var that=this;
            $(this).closest("tr").find("input[type=text]").each(function (index, element) {
                var inputName = $(element).attr("name");
                cacheData[inputName] = $(element).val();
            })
            console.log(cacheData)
            console.log(JSON.stringify(cacheData))
            $.post(self.cfg.postUrl, cacheData, function (data) {
                if (true) {
                    $(that).closest("tr").addClass("disabled").find("input[type=text]").attr("disabled", true);
                }
            })

        })
    }
    module.exports=tableSave;
});
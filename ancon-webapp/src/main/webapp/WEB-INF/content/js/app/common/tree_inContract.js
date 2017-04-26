/**
 * Created by wanggy on 2016/2/29.
 */
define(function (require, exports, module) {
    var zTree = require("ztree");
    var zNodes;
    var count = 0;

    exports.init = function (options, callback) {
        if (options.url) {
            $.post(options.url, function (data) {
                fuzhi(data);
                callback && (typeof  callback == "function") && callback()
            });

        } else {
            fuzhi(options.jsonData);
        }

        function fuzhi(data) {
            var setting = {
                view: {
                    showIcon: true,
                    showLine: true

                },
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "id",            //自身id
                        pIdKey: "parentId",     //父集id
                        name: "name",
                        rootPId: 0,
                        checked: true
                    }
                },
                callback: {
                    onClick: options.click && (typeof  options.click == "function") && options.click,
                    onCheck: options.onCheck && (typeof  options.onCheck == "function") && options.onCheck,

                    onClick: zTreeOnClick

                }
            };

            if (typeof(options.setting) != "undefined") {
                setting = $.extend(true, {}, setting, options.setting);
            }

            zNodes = data;
            $.fn.zTree.init($(options.id), setting, zNodes);

        }
    }


    function zTreeOnClick(event, treeId, treeNode) {

        $(".table_Info").trigger("change", [event, treeNode, treeId]);

        ////右边头部导航
        if (treeNode.level == 0) {
            $(".sc_index_1_company").html(treeNode.partyB);
            $(".sc_index_1_first_contract").html(">" + treeNode.name);
            $(".sc_index_1_second_contract").html("");
        } else {
            var parentNode = treeNode.getParentNode(treeNode.id);
            $(".sc_index_1_company").html(parentNode.branchCompany);
            $(".sc_index_1_first_contract").html(">" + parentNode.name);
            $(".sc_index_1_second_contract").html(">" + treeNode.name);
        }

    };

    function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {

        alert(treeNode.id + "sdljfldsj");

    };


    function addHoverDom(treeId, treeNode) {
        var aObj = $("#" + treeNode.tId + "_a");
        if ($("#diyBtn_" + treeNode.id).length > 0) return;
        var editStr =
            "<button type='button' class='btn btn-primary i_modal cn_jsclick' data-url='addSalecontract?parentId=" + treeNode.id + "&companyName=" + treeNode.branchCompany + "&companyId=" + treeNode.companyId + "' data-title='添加进项合同' id='diyBtn_" + treeNode.id
            + "' title='" + treeNode.name + "' onfocus='this.blur();'>新建合同</button>";

        if (treeNode.level == 1) {
            return false;
        } else {
            aObj.after(editStr);

        }
        var btn = $("#diyBtn_" + treeNode.id);
        if (btn) btn.bind("click", function () {
        });
    };
    function removeHoverDom(treeId, treeNode) {
        $("#diyBtn_" + treeNode.id).unbind().remove();
        $("#diyBtn_space_" + treeNode.id).unbind().remove();
    };

});
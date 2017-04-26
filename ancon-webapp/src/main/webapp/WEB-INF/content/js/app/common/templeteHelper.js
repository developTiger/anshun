define(function (require, exports, model) {
    require("template");
    template.helper('dateFormat', function (date, format) {
        if(!date)return;
        if (!format) {
            format = "yyyy-MM-dd hh:mm:ss";
        }
        date = new Date(date);
        var map = {
            "M": date.getMonth() + 1, //月份
            "d": date.getDate(), //日
            "h": date.getHours(), //小时
            "m": date.getMinutes(), //分
            "s": date.getSeconds(), //秒
            "q": Math.floor((date.getMonth() + 3) / 3), //季度
            "S": date.getMilliseconds() //毫秒
        };

        format = format.replace(/([yMdhmsqS])+/g, function (all, t) {
            var v = map[t];
            if (v !== undefined) {
                if (all.length > 1) {
                    v = '0' + v;
                    v = v.substr(v.length - 2);
                }
                return v;
            }
            else if (t === 'y') {
                return (date.getFullYear() + '').substr(4 - all.length);
            }
            return all;
        });
        return format;
    });





    var data = {
        time: (new Date).toString()
    };

    template.helper('enumsSelect', function (data) {
        if(data==null ||data=="")return;
        var map = {
            0:"出勤",
            1:"探亲",
            2:"休假",
            3:"护理",
            4:"事假",
            5:"病假",
            6:"学习",
            7:"野外",
            8:"出差",
            9:"婚丧",
            10:"产假",
            11:"工伤",
            12:"休息",
            13:"劳保",
            14:"旷工",
            15:"补休"
        };
        return map[data];
    });


    // exports.template=template;


});


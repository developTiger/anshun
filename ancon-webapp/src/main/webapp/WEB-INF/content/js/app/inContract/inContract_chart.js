/**
 * 进项合同涉及到的统计,目前只是用于柱状图统计
 * Created by xiazl on 2016/12/2.
 */
define(function (require, exports, module) {
    var echarts = require("echarts");
    var wdatePicker = require("wdatePicker");
    var validate = require("validate")
    $("body").delegate(".Wdate", "click", function () {
        wdatePicker({dataFmt: 'yyyy-MM-dd'})
    });

    var inContract_chart = echarts.init(document.getElementById("inContract_bar_chart"));
    var invoice_chart = echarts.init(document.getElementById("invoice_bar_chart"));
    var pay_chart = echarts.init(document.getElementById("pay_bar_chart"));

    data1();
    //点击事件
    $("#inContract_char_count_button").click(function () {
        data1();
    })

    function data1() {
        var names = [];
        var values = [];
        var _url = $("#inContract_char_count_button").attr("data-url");
        var beginTime = $("#time11").val();
        var endTime = $("#time21").val();
        if (Date.parse(beginTime) >= Date.parse(endTime)) {
            alert("起止日期有误！");
            return;
        }
        $.ajax({
            type: 'post',
            async: true,
            url: _url,
            data: {beginTime: beginTime, endTime: endTime},
            dataType: "json",
            success: function (result) {
                if (result.length>0) {
                    for (var i = 0; i < result.length; i++) {
                        names.push(result[i].partyB)
                        values.push(result[i].totalMoney);
                    }
                }
                //todo
                var inContract_option = {

                    tooltip: {},
                    grid: {
                        y2: 140
                    },
                    xAxis: {
                        axisLabel: {
                            rotate: 60,
                            interval: 0
                        },
                        data: names,
                        name: '乙方',
                        splitLine: {
                            show: false
                        }
                    },
                    yAxis: {
                        name: "单位：万元",
                        type: 'value',
                        splitLine: {
                            show: true
                        },
                        axisLabel: {
                            formatter: '{value} 万元'
                        }
                    },
                    series: [
                        {
                            name: '合同额',
                            type: 'bar',
                            barWidth: 50,
                            data: values,
                            lineStyle: {
                                normal: {
                                    color: '#5abbd0',
                                    width: 0.5
                                }
                            },
                            itemStyle: {
                                normal: {
                                    color: '#5abbd0'
                                }
                            },
                            markPoint: {
                                data: [
                                    {name: '最大值', type: 'max'},
                                    {name: '最小值', type: 'min'}
                                ]
                            }
                        }
                    ]
                }
                inContract_chart.setOption(inContract_option);
            },
            error: function (errorMsg) {
                alert("图表数据加载失败！")
                inContract_chart.hideLoading();
            }
        });

        var left_height = $(document).height() - 60;
        $(".js_height").height(left_height);

    };

    //付款额统计
    data2();
    $("#pay_char_count_button").click(function () {
        data2();
    })
    function data2() {
        var names = [];
        var values = [];
        var beginTime = $("#time12").val();
        var endTime = $("#time22").val();
        var _url = $("#pay_char_count_button").attr("data-url");
        if (Date.parse(endTime) <= Date.parse(beginTime)) {
            alert("起止时间有误！");
            return;
        }
        $.ajax({
            url: _url,
            type: 'post',
            async: true,
            data: {beginTime: beginTime, endTime: endTime},
            dataType: "json",
            success: function (result) {
                if (result.length>0) {
                    for (var i = 0; i < result.length; i++) {
                        names.push(result[i].partyB);
                        values.push(result[i].totalPay)
                    }
                }
                //todo
                var pay_char={

                    tooltip: {},
                    grid: {
                        y2: 140
                    },
                    xAxis: {
                        axisLabel:{
                            rotate:60,
                            interval: 0
                        },
                        data: names,
                        name: '乙方',
                        splitLine: {
                            show: false
                        }
                    },
                    yAxis: {
                        name: '单位:万元',
                        type: 'value',
                        splitLine: {
                            show: true
                        }
                        //axisLabel: {
                        //    formatter: '{value} 万元'
                        //}
                    },
                    series: [
                        {
                            name: '开票额',
                            type: 'bar',
                            barWidth: 50,
                            data: values,
                            lineStyle: {
                                normal: {
                                    color: '#5abbd0',
                                    width: 4
                                }
                            },
                            itemStyle: {
                                normal: {
                                    color: '#5abbd0'
                                }
                            },
                            markPoint: {
                                data: [
                                    {name: '最大值', type: 'max'},
                                    {name: '最小值', type: 'min'}
                                ]

                            }
                        }
                    ]


                };
                pay_chart.setOption(pay_char);
            },
            error: function (errorMsg) {
                alert("加载数据失败!");
                pay_chart.hideLoading();
            }

        });

        var left_height = $(document).height() - 60;
        $(".js_height").height(left_height);

    };

    //开票统计
    data3();
    $("#invoice_char_count_button").click(function () {
        data3();
    });
    function data3() {
        var names = [];
        var values_s = [];//专票
        var values_c = [];//普票
        var _url = $("#invoice_char_count_button").attr("data-url");
        var beginTime = $("#time13").val();
        var endTime = $("#time23").val();
        if (Date.parse(beginTime) >= Date.parse(endTime)) {
            alert("起止时间有误！");
            return;
        }
        $.ajax({
            url: _url,
            type: 'post',
            data: {beginTime: beginTime, endTime: endTime},
            dataType: "json",
            async: true,
            success: function (result) {
                if (result.length>0) {
                    for (var i = 0; i < result.length; i++) {
                        names.push(result[i].partyB);

                        values_c.push(result[i].totalc);

                        values_s.push(result[i].totals);

                    }
                }
                // todo
                var invoice_char = {

                    legend: {
                        data: ['增值普票', '增值专票'],
                        //x: 'right',
                        //right: 20
                        x: 'center'
                    },

                    tooltip: {},
                    grid: {
                        y2: 140
                    },

                    xAxis: {
                        axisLabel: {
                            rotate: 45,
                            interval: 0
                        },
                        data: names,
                        name: '乙方',

                        splitLine: {
                            show: false
                        }
                    },

                    yAxis: {
                        name: '单位:万元',
                        type: 'value',
                        splitLine: {
                            show: true
                        }
                        //axisLabel: {
                        //    formatter: '{value} 万元'
                        //}
                    },
                    series: [
                        {
                            name: '增值普票',
                            type: 'bar',
                            barWidth: 50,
                            stack: "总量",
                            lineStyle: {
                                normal: {
                                    color: '#638CA6'
                                }
                            },
                            itemStyle: {
                                normal: {
                                    color: '#47a1b4',
                                    label: {
                                        show: true,
                                        textStyle: {
                                            color: '#fff'
                                        },
                                        position: "insideTop",
                                        formatter: function (p) {
                                            return p.value > 0 ? (p.value) : '';

                                        }
                                    }

                                }
                            },
                            //data: []
                            data: values_c == null ? 0 : values_c

                        },
                        {
                            name: '增值专票',
                            type: 'bar',
                            barWidth: 50,
                            stack: "总量",
                            lineStyle: {
                                normal: {
                                    color: '#638CA6',
                                    width: 4
                                }
                            },
                            itemStyle: {
                                normal: {

                                    color: '#5abbd0',
                                    label: {
                                        show: true,
                                        position: "top",
                                        formatter: function (p) {
                                            return p.value > 0 ? (p.value) : '';
                                        }
                                    }
                                }
                            },
                            //data: []
                            data: values_s == null ? 0 : values_s
                        }
                    ]
                };
                invoice_chart.setOption(invoice_char);
            },
            error: function () {
                alert("加载数据失败！");
                invoice_chart.hideLoading();
            }
        });

        var left_height = $(document).height() - 60;
        $(".js_height").height(left_height);
    };

    setTimeout(function () {
        window.onresize = function () {
            inContract_chart.resize();
            invoice_chart.resize();
            pay_chart.resize();

        }
    }, 200);
    height();
    function height() {
        var left_height = $(document).height() - 60;
        $(".js_height").height(left_height);
    }

    $(".click_href").click(function () {
        var _url = $(this).attr("data-url");
        if (_url == "sra_q_i")
            window.location.href = $(this).attr("data-url") + "?" + $(".inContract_char_count").serialize().toString();
        if (_url == "sra_q_p")
            window.location.href = $(this).attr("data-url") + "?" + $(".pay_char_count").serialize().toString();
        if (_url == "sra_q_v")
            window.location.href = $(this).attr("data-url") + "?" + $(".invoice_char_count").serialize().toString();
    });

})
/**
 * Created by admin on 2016/11/28.
 */
define(function(require,exports,module){
    var echarts=require("echarts");
    require("../common/jquery.serializeObject");
    require("select2");
    var wDatePicker = require("wdatePicker");

    $(".select2_single").select2({placeholder:"输入甲方名称查询",allowClear:true  });
    $.fn.modal.Constructor.prototype.enforceFocus = function() {};

    $("body").delegate(".Wdate", "click", function () {
        wDatePicker({dateFmt: 'yyyy-MM-dd'});

    });

    $("body").delegate(".yearDate", "click", function () {
        wDatePicker({dateFmt: 'yyyy'});

    });


    // 基于准备好的dom，初始化echarts实例
    var myChart_1 = echarts.init(document.getElementById("chart_1"));
    var myChart_2 = echarts.init(document.getElementById("chart_2"));
    var myChart_3 = echarts.init(document.getElementById("chart_3"));
    var myChart_4 = echarts.init(document.getElementById("chart_4"));

    var data=$("#sale_contracts").serializeObject();
    loadData_1(data);
    function loadData_1(data) {
        var url = "ajax_query_echarts_saleContracts_first";
        $.ajax({
            url: url,
            type: 'post',
            data: data,
            success: function (data) {
                var option_1_data = [];
                //var option_1_month = [];
                if(data.length>0){


                    for(var i=0;i<data.length;i++){
                        option_1_data.push(data[i].sum_judge_money);
                    }
                }

                // 指定图表的配置项和数据
                var option_1 = {
                    title: {
                        //text: 'ECharts 入门示例'
                    },
                    tooltip: {},
                    legend: {
                        data: ['金额']
                        //right:'right'
                    },
                    xAxis: {
                        data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],

                        name: '月份',
                        splitLine: {
                            show: true
                        }
                    },
                    yAxis: {
                        name: "单位/万元",
                        splitLine: {
                            show: true
                        }
                    },
                    //grid:{
                    //    show:true
                    //},
                    series: [
                        {
                            name: '金额',
                            type: 'line',
                            data: option_1_data,
                            lineStyle: {
                                normal: {
                                    color: '#638CA6',
                                    width: 4
                                }
                            },
                            itemStyle: {
                                normal: {
                                    color: '#638CA6'
                                }
                            },
                            markPoint: {
                                data: [
                                    {name: '最大值', type: 'max'},
                                    {name: '最小值', type: 'min'}
                                ]
                            },
                            markLine: {
                                data: [
                                    {name: '平均值', type: 'average'}
                                ]
                            },
                            areaStyle: {
                                normal: {
                                    color: "#E2EFF7"
                                }
                            }
                        }
                    ]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart_1.setOption(option_1);

            }
        })
    }

    $(".sale_select").click(function(){
            loadData_1($("#sale_contracts").serializeObject());
    })



    var data_pie = [
        {value:335, name:'直接访问'},
        {value:310, name:'邮件营销'},
        {value:234, name:'联盟广告'},
        {value:135, name:'视频广告'},
        {value:1548, name:'搜索引擎'}
    ];

    loadData_2();
    function loadData_2(data){
        var url = "ajax_query_echarts_saleContracts_second";
        $.ajax({
            url:url,
            type:'post',
            data:data,
            success:function(data){
                if(data.length>0){
                    var option_2_legeng_data=[];
                    var option_2_data=[];

                    for(var i=0;i<data.length;i++){
                        var option_2_object={};
                        option_2_legeng_data.push(data[i].branch_company);
                        option_2_object.name = data[i].branch_company;
                        option_2_object.value = data[i].judge_money;

                        option_2_data.push(option_2_object);
                    }
                }

                var option_2 = {
                    //title : {
                    //    text: '某站点用户访问来源',
                    //    subtext: '纯属虚构',
                    //    x:'center',//标题位置
                    //
                    //},
                    tooltip : {
                        //trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {       //图例
                        orient: 'vertical',
                        left: 'left',
                        //data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
                        data:option_2_legeng_data
                    },
                    series : [
                        {
                            name: '合同额',
                            type: 'pie',
                            radius : '85%',
                            center: ['50%', '50%'],//饼图位置
                            //data:[
                            //    {value:335, name:'直接访问'},
                            //    {value:310, name:'邮件营销'},
                            //    {value:234, name:'联盟广告'},
                            //    {value:135, name:'视频广告'},
                            //    {value:1548, name:'搜索引擎'}
                            //],
                            data:option_2_data,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart_2.setOption(option_2);

            }
        })
    }
    $("#branchCompany_query_btn").click(function(){
        loadData_2($("#branchCompany_query_chart").serializeObject());
    })


    loadData_3();
    function loadData_3(data) {
        var url = "ajax_query_echarts_saleContracts_third";
        $.ajax({
            url: url,
            type: 'post',
            data: data,
            success: function (data) {
                var option_1_data_common = [];
                var option_1_data_important = [];
                if(data){
                    for(var i=0;i<data.common.length;i++){
                        option_1_data_common.push(data.common[i].sum_judge_money);
                    }
                    for(var i=0;i<data.important.length;i++){
                        option_1_data_important.push(data.important[i].sum_judge_money);
                    }
                }

                var option_3 = {
                    title: {
                        //text: 'ECharts 入门示例'
                    },
                    tooltip: {},
                    legend: {
                        data: ['标准合同', '重点合同']
                        //right:'right'
                    },
                    xAxis: {
                        data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
                        name: '月份',
                        type:'category',
                        splitLine: {
                            show: true
                        }
                    },
                    yAxis: {
                        name: "单位/万元",
                        splitLine: {
                            show: true
                        }
                    },
                    //grid:{
                    //    show:true
                    //},
                    series: [
                        {
                            name: '标准合同',
                            type: 'line',
                            data: option_1_data_common,
                            lineStyle: {
                                normal: {
                                    color: '#6DC6CD',
                                    width: 4
                                }
                            },
                            itemStyle: {
                                normal: {
                                    color: '#6DC6CD'
                                }
                            },
                            markPoint: {
                                data: [
                                    {name: '最大值', type: 'max'},
                                    {name: '最小值', type: 'min'}
                                ]
                            },
                            markLine: {
                                data: [
                                    {name: '平均值', type: 'average'}
                                ]
                            },
                            areaStyle: {
                                normal: {
                                    color: "#6DC6CD"
                                }
                            }

                        },
                        {
                            name: '重点合同',
                            type: 'line',
                            data: option_1_data_important,
                            lineStyle: {
                                normal: {
                                    color: '#638CA6',
                                    width: 4
                                }
                            },
                            itemStyle: {
                                normal: {
                                    color: '#638CA6'
                                }
                            },
                            markPoint: {
                                data: [
                                    {name: '最大值', type: 'max'},
                                    {name: '最小值', type: 'min'}
                                ]
                            },
                            markLine: {
                                data: [
                                    {name: '平均值', type: 'average'}
                                ]
                            },
                            areaStyle: {
                                normal: {
                                    color: "#E2EFF7"
                                }
                            }
                        }
                    ]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart_3.setOption(option_3);
            }


        })
    }










    $("#contractType_query_btn").click(function(){
        loadData_3($("#contractType_query_chart").serializeObject());
    })


    loadData_4()
    function loadData_4(data){
        var url = "ajax_query_echarts_saleContracts_fourth";
        $.ajax({
            url:url,
            type:'post',
            data:data,
            success:function(data){


                if(data.length>0){
                    var option_2_legeng_data=[];
                    var option_2_data=[];

                    for(var i=0;i<data.length;i++){
                        var option_2_object={};
                        option_2_legeng_data.push(data[i].project_major);
                        option_2_object.name = data[i].project_major;
                        option_2_object.value = data[i].judge_money;

                        option_2_data.push(option_2_object);
                    }
                }


                var option_4 = {
                    //title : {
                    //    text: '某站点用户访问来源',
                    //    subtext: '纯属虚构',
                    //    x:'center',//标题位置
                    //
                    //},
                    tooltip : {
                        //trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {       //图例
                        orient: 'vertical',
                        left: 'left',
                        //data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
                        data:option_2_legeng_data
                    },
                    series : [
                        {
                            name: '合同额',
                            type: 'pie',
                            radius : '85%',
                            center: ['50%', '50%'],//饼图位置
                            data:option_2_data,
                            //data:option_2_data,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart_4.setOption(option_4);

            }
        })
    }
    $("#projectMajor_query_btn").click(function () {
        loadData_4($("#projectMajor_query_chart").serializeObject())
    })

    //图表自适应
    setTimeout(function (){
        window.onresize = function () {
            myChart_1.resize();
            myChart_2.resize();
            myChart_3.resize();
            myChart_4.resize();
        }
    },200);

    //图表 查看明细 带有条件
    $(".saleContract_check").click(function(){
        var url = "sra_s_c?"+encodeURI($("#sale_contracts").serialize().toString());
        window.location.href = url;
    })

    $(".contractType_check").click(function(){
        var url = "sra_s_c?"+encodeURI($("#contractType_query_chart").serialize().toString());
        window.location.href = url;
    })

    $(".branchCompany_check").click(function(){
        var url = "sra_s_c?"+encodeURI($("#branchCompany_query_chart").serialize().toString());
        window.location.href = url;
    })

    $(".projectMajor_check").click(function(){
        var url = "sra_s_c?"+encodeURI($("#projectMajor_query_chart").serialize().toString());
        window.location.href = url;
    })

    $(document).ready(function(){
        //左边菜单高度

            $(".saleContract_left").height(800);
            var left_height = $(document).height()-60;
            $(".saleContract_left").height(left_height);

    })

})
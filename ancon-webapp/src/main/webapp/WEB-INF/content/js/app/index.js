/**
 * Created by admin on 2016/8/27.
 */
define(function(require,exports,module){


    var template = require("template");
    require("app/common/templeteHelper");
    var echarts=require("echarts");

    // 基于准备好的dom，初始化echarts实例
    var myChart_1 = echarts.init(document.getElementById("section_2_first_2"));
    var myChart_2 = echarts.init(document.getElementById("section_2_first_3"));
    var myChart_3 = echarts.init(document.getElementById("section_2_first_4"));
    var myChart_4 = echarts.init(document.getElementById("section_2_first_5"));
    var myChart_5 = echarts.init(document.getElementById("section_2_first_6"));


    loadData_1();
    function loadData_1(){
        var url = "ajax_query_index_charts_thisYearSaleContracts";
        $.ajax({
            url:url,
            type:'post',
            success:function(data){
                var option_1_data = [];
                //var option_1_month = [];
                if(data.length>0){


                    for(var i=0;i<data.length;i++){
                        option_1_data.push(data[i].judgeMoney);
                    }
                }


                // 指定图表的配置项和数据
                var option_1 = {
                    title: {
                        //text: 'ECharts 入门示例'
                    },
                    backgroundColor: '#FFFFFF',
                    tooltip: {},
                    legend: {
                        data:['金额']
                        //right:'right'
                    },
                    xAxis: {
                        data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月',],
                        name:'月份',
                        splitLine:{
                            show:true,
                            //网格线设置
                            lineStyle:{
                                color:'#eeeff4'
                            }
                        }
                    },
                    yAxis: {
                        name:"单位/万元",
                        splitLine:{
                            show:true,
                            lineStyle:{
                                color:'#eeeff4'
                            }

                        }
                    },
                    //grid:{
                    //    show:true,
                    //},
                    series: [
                        {
                            name: '金额',
                            type: 'line',
                            data: option_1_data,
                            lineStyle:{
                                normal:{
                                    color:'#638CA6',
                                    width:4
                                }
                            },
                            itemStyle : {
                                normal : {
                                    color:'#5192C0'
                                }
                            },
                            markPoint:{
                                data:[
                                    {name:'最大值',type:'max'},
                                    {name:'最小值',type:'min'}
                                ]
                            },
                            markLine:{
                                data:[
                                    {name:'平均值',type:'average'}
                                ]
                            },
                            areaStyle: {
                                normal: {
                                    color:"#E2EFF7"
                                }
                            }
                        }
                    ]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart_1.setOption(option_1);
                //$("#dcDataUseStatisticContainer").resize(function(){
                //    $(myChart_1).resize();
                //})
            }
        })
    }

    loadData_2();
    function loadData_2(){
        var url = "ajax_query_index_thisYearSaleContracts_number";
        $.ajax({
            url:url,
            type:'post',
            success:function(data){
                var option_1_data = [];
                //var option_1_month = [];
                if(data.length>0){


                    for(var i=0;i<data.length;i++){
                        option_1_data.push(data[i].number);
                    }
                }


                // 指定图表的配置项和数据
                var option_2 = {
                    title: {
                        //text: 'ECharts 入门示例'
                    },
                    backgroundColor: '#FFFFFF',
                    tooltip: {},
                    legend: {
                        data:['金额']
                        //right:'right'
                    },
                    xAxis: {
                        data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
                        name:'月份',
                        splitLine:{
                            show:true,
                            //网格线设置
                            lineStyle:{
                                color:'#eeeff4'
                            }
                        }
                    },
                    yAxis: {
                        name:"数量",
                        splitLine:{
                            show:true,
                            //网格线设置
                            lineStyle:{
                                color:'#eeeff4'
                            }
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
                            lineStyle:{
                                normal:{
                                    color:'#607E8D',
                                    width:4
                                }
                            },
                            itemStyle : {
                                normal : {
                                    color:'#5192C0'
                                }
                            },
                            markPoint:{
                                data:[
                                    {name:'最大值',type:'max'},
                                    {name:'最小值',type:'min'}
                                ]
                            },
                            markLine:{
                                data:[
                                    {name:'平均值',type:'average'}
                                ]
                            },
                            areaStyle: {
                                normal: {
                                    color:"#E9F3F5"
                                }
                            }
                        }
                    ]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart_2.setOption(option_2);
                //$("#dcDataUseStatisticContainer").resize(function(){
                //    $(myChart_2).resize();
                //})
            }
        })
    }


    loadData_3();
    function loadData_3(){
        var url = "ajax_query_index_thisYearSaleContracts_billing";
        $.ajax({
            url:url,
            type:'post',
            success:function(data){
                var option_1_data = [];
                //var option_1_month = [];
                if(data.length>0){


                    for(var i=0;i<data.length;i++){
                        option_1_data.push(data[i].billTotalMoney);
                    }
                }


                // 指定图表的配置项和数据
                var option_3 = {
                    title: {
                        //text: 'ECharts 入门示例'
                    },
                    backgroundColor: '#FFFFFF',
                    tooltip: {},
                    legend: {
                        data:['金额']
                        //right:'right'
                    },
                    xAxis: {
                        data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
                        name:'月份',
                        splitLine:{
                            show:true,
                            //网格线设置
                            lineStyle:{
                                color:'#eeeff4'
                            }
                        }
                    },
                    yAxis: {
                        name:"单位/万元",
                        splitLine:{
                            show:true,
                            //网格线设置
                            lineStyle:{
                                color:'#eeeff4'
                            }
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
                            lineStyle:{
                                normal:{
                                    color:'#638CA6',
                                    width:4
                                }
                            },
                            itemStyle : {
                                normal : {
                                    color:'#5192C0'
                                }
                            },
                            markPoint:{
                                data:[
                                    {name:'最大值',type:'max'},
                                    {name:'最小值',type:'min'}
                                ]
                            },
                            markLine:{
                                data:[
                                    {name:'平均值',type:'average'}
                                ]
                            },
                            areaStyle: {
                                normal: {
                                    color:"#E2EFF7"
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

    loadData_4();
    function loadData_4(){
        var url = "ajax_query_index_thisYearSaleContracts_gathering";
        $.ajax({
            url:url,
            type:'post',
            success:function(data){
                var option_1_data = [];
                //var option_1_month = [];
                if(data.length>0){


                    for(var i=0;i<data.length;i++){
                        option_1_data.push(data[i].gatheringMoney);
                    }
                }


                // 指定图表的配置项和数据
                var option_4 = {
                    title: {
                        //text: 'ECharts 入门示例'
                    },
                    backgroundColor: '#FFFFFF',
                    tooltip: {},
                    legend: {
                        data:['金额']
                        //right:'right'
                    },
                    xAxis: {
                        data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
                        name:'月份',
                        splitLine:{
                            show:true,
                            //网格线设置
                            lineStyle:{
                                color:'#eeeff4'
                            }
                        }
                    },
                    yAxis: {
                        name:"单位/万元",
                        splitLine:{
                            show:true,
                            //网格线设置
                            lineStyle:{
                                color:'#eeeff4'
                            }
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
                            lineStyle:{
                                normal:{
                                    color:'#607E8D',
                                    width:4
                                }
                            },
                            itemStyle : {
                                normal : {
                                    color:'#5192C0'
                                }
                            },
                            markPoint:{
                                data:[
                                    {name:'最大值',type:'max'},
                                    {name:'最小值',type:'min'}
                                ]
                            },
                            markLine:{
                                data:[
                                    {name:'平均值',type:'average'}
                                ]
                            },
                            areaStyle: {
                                normal: {
                                    color:"#E9F3F5"
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

    loadData_5();
    function loadData_5(){
        var url = "ajax_query_index_thisYearSaleContracts_conValue";
        $.ajax({
            url:url,
            type:'post',
            success:function(data){
                var option_1_data = [];
                //var option_1_month = [];
                if(data.length>0){


                    for(var i=0;i<data.length;i++){
                        option_1_data.push(data[i].totalMoney);
                    }
                }


                // 指定图表的配置项和数据
                var option_5 = {
                    title: {
                        //text: 'ECharts 入门示例'
                    },
                    backgroundColor: '#FFFFFF',
                    tooltip: {},
                    legend: {
                        data:['金额']
                        //right:'right'
                    },
                    xAxis: {
                        data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
                        name:'月份',
                        splitLine:{
                            show:true,
                            //网格线设置
                            lineStyle:{
                                color:'#eeeff4'
                            }
                        }
                    },
                    yAxis: {
                        name:"单位/万元",
                        splitLine:{
                            show:true,
                            //网格线设置
                            lineStyle:{
                                color:'#eeeff4'
                            }
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
                            lineStyle:{
                                normal:{
                                    color:'#638CA6',
                                    width:4
                                }
                            },
                            itemStyle : {
                                normal : {
                                    color:'#5192C0'
                                }
                            },
                            markPoint:{
                                data:[
                                    {name:'最大值',type:'max'},
                                    {name:'最小值',type:'min'}
                                ]
                            },
                            markLine:{
                                data:[
                                    {name:'平均值',type:'average'}
                                ]
                            },
                            areaStyle: {
                                normal: {
                                    color:"#E2EFF7"
                                }
                            }
                        }
                    ]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart_5.setOption(option_5);

            }
        })
    }

    //图表自适应
    setTimeout(function (){
        window.onresize = function () {
            myChart_1.resize();
            myChart_2.resize();
            myChart_3.resize();
            myChart_4.resize();
            myChart_5.resize();
        }
    },200)


})

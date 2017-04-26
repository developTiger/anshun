/**
 * 这是我的测试
 * Created by xiazl on 2016/12/12.
 */

define(function (required, exports, module) {
    var echarts = require("echarts");

    var test01 = echarts.init(document.getElementById("test01"));
    var option = {
        title: {
            //标题
            text: "我的天空",
            subtext: 'ddd'
        },
        tooltip: {
            //工具栏提示窗
            trigger: 'axis'
        },
        legend: {
            data: ['ss', 'rr']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {
                    show: true
                },
                dataView: {
                    show: true,
                    readOnly: false
                },
                magicType: {
                    show: true,
                    type: ['line', 'bar']
                },
                restore:{
                    show:true
                },
                saveAsImage:{
                    show:true
                }


            }
        },
        calculable:true,
        xAxis:[
            {
                type:'category',
                data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
            }
        ],
        yAxis:[
            {
                type:'value'
            }
        ],
        series:[
            {
                name:'水蒸气',
                type:'bar',
                data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
                markPoint:{
                    data:[
                        {type:'max',name:'最大值'},
                        {type:'min',name:'最小值'}
                    ]
                },
                markLine:{
                    data:[
                        {type:'average',name:'平均值'}
                    ]
                }
            },
            {
                name:'降水量',
                type:'bar',
                data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
                markPoint : {
                    data : [
                        {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
                        {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
                    ]
                },
                markLine : {
                    data : [
                        {type : 'average', name : '平均值'}
                    ]
                }
            }


        ]


    };

    test01.setOption(option);

});
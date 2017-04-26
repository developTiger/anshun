/**
 * Created by admin on 2016/12/15.
 */
define(function(require,exports,module){



    //数据报表 统计 左侧 菜单栏
    //先绑定事件 再触发事件 要明确js的加载顺序
    $("body").on("click",".saleContract_first",function(){
        $(".inContract_ninth").css("color","#7F98A9");
        if($(".Js_sale_icon_second").is(":hidden")){
            $(".Js_sale_icon_first").addClass("hidden");
            $(".Js_sale_icon_second").removeClass("hidden");
            $(".Js_sale_chartAndTable").each(function(){
                $(this).addClass("hidden");
            })
        }else{
            $(".Js_sale_icon_first").removeClass("hidden");
            $(".Js_sale_icon_second").addClass("hidden");
            $(".Js_sale_chartAndTable").each(function(){
                $(this).removeClass("hidden");
            })
        }
        $(this).css("color","white");
    });

    $("body").on("click",".inContract_ninth",function(){
        $(".saleContract_first").css("color","#7F98A9");
        if($(".Js_in_icon_second").is(":hidden")){
            $(".Js_in_icon_first").addClass("hidden");
            $(".Js_in_icon_second").removeClass("hidden");
            $(".Js_in_chartAndTable").each(function(){
                $(this).addClass("hidden");
            })
        }else{
            $(".Js_in_icon_first").removeClass("hidden");
            $(".Js_in_icon_second").addClass("hidden");
            $(".Js_in_chartAndTable").each(function(){
                $(this).removeClass("hidden");
            })
        }
        $(this).css("color","white");
    })

    var saleContract_left = $("input[name='saleContract_left']").val();
    if(saleContract_left && parseInt(saleContract_left) <= 7){
        $(".saleContract_first").click();
    }else{
        $(".inContract_ninth").click();
    }

})
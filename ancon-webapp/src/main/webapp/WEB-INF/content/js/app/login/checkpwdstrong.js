/**
 * Created by temp on 2016/8/17.
 */
define(function(require,exprots,model){

    $('.js_check_pwd_strong').keyup(function () {

        if($(this).val().length<6){
            $(".cn-check-new-password").text("不少于6个字符");
            return false;
        }

        Checkpwdstrong.prototype.checkPwdStrong('js_check_pwd_strong');
    });

    function Checkpwdstrong(){

    }
    Checkpwdstrong.prototype={

        pwdLevel:function(value) {
            var pattern_1 = /^.*([\W_])+.*$/i; //匹配任何非单词字符。等价于 '[^A-Za-z0-9_]'并且支持下划线_。
            var pattern_2 = /^.*([a-zA-Z])+.*$/i;//英文字母
            var pattern_3 = /^.*([0-9])+.*$/i;//数字
            var level = 0;
            if (pattern_1.test(value)) {
                level++;
            }
            if (pattern_2.test(value)) {
                level++;
            }
            if (pattern_3.test(value)) {
                level++;
            }
            if (level > 3) {
                level = 3;
            }
            return level;
        },
        checkPwdStrong:function(pwd) {
            var element = $(".passwordSecurity");
            var pwdLow = $(".pwd-low");
            var pwdCentre = $(".pwd-centre");
            var pwdHigh = $(".pwd-high");
            var showLevel=$(".show-level");

            var value = $("#"+pwd).val();
            if (value.length >= 6) {
                $(".cn-check-new-password").addClass("dis-none");
                element.show();
                var level = this.pwdLevel(value);
                switch (level) {
                    case 1:
                        pwdLow.css("background-color","#FF8D24");
                        pwdCentre.css("background-color","#D9D9D9");
                        pwdHigh.css("background-color","#D9D9D9");
                        showLevel.html("安全等级较低");
                        break;
                    case 2:
                        pwdLow.css("background-color","#D9D9D9");
                        pwdCentre.css("background-color","#FF8D24");
                        pwdHigh.css("background-color","#D9D9D9");
                        showLevel.html("安全等级较高");
                        break;
                    case 3:
                        pwdLow.css("background-color","#D9D9D9");
                        pwdCentre.css("background-color","#D9D9D9");
                        pwdHigh.css("background-color","#FF8D24");
                        showLevel.html("安全等级高");
                        break;
                    default:
                        break;
                }
            } else {
                element.hide();

            }
        }

    }
    model.exports=Checkpwdstrong;
});
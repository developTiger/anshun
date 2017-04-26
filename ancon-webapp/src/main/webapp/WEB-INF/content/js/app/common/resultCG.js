/**
 * Created by xubo on 2016/7/21 0021.
 */

define(function (require, method, module) {

        //初始化参数,第一个参数是所有人员，第二个参数是追加HTML，第三个参数是选中人员；
        method.init = function(win_main,out_obj,has_data){
            var show_data = split_data(has_data);
            out_obj.html("");
            win_main = remove_win_main_obj(win_main);
            var html = "";
            if(show_data == null){
                html += '<div class="control-group staff-row">'+
                        '<div class="controls controls-row doc-input-size">'+
                        '<label class="control-label col-sm-3">'+
                        '第' +
                        '<span class="staff-num">' + 1 + '</span>' +
                        '获奖人' +
                        '<span class="required">*</span>'+
                        '</label>'+
                        '<select id="special-sele" required="required" ' +
                        'class="span3 col-sm-3 special-sele" >';
                for( var f in win_main){
                    if( f == "98" ){
                        html += '<option selected value="' + f +'"> '+  win_main[f] + '</option>';
                    }else{
                        html += '<option value="' + f +'"> '+  win_main[f] + '</option>';
                    }
                }
                html += '</select>' +
                        '<input type="text" disabled class="input_outname col-sm-3 col-md-offset-1" value >';

                html += '<a class="btn-sm col-sm-2 delStaff" style="height: 28px;" href="javascript:void(0);"></a>';
                html +=  '</div></div>';
                out_obj.html(html);
                return;
            }
            for (var i = 0 ; i < show_data.A.length ; i++ ){
                html += '<div class="control-group staff-row">'+
                        '<div class="controls controls-row doc-input-size">'+
                        '<label class="control-label col-sm-3">'+
                        '第' +
                        '<span class="staff-num">' + (i + 1) + '</span>' +
                        '获奖人' +
                        '<span class="required">*</span>'+
                        '</label>'+
                        '<select id="special-sele" required="required" ' +
                        'class="span3 col-sm-3 special-sele" >';

                if(Number(show_data.A[i]) == 0){
                    //外部人员
                    for( var f in win_main){
                        if( f == "99" ){
                            html += '<option selected value="' + f +'"> '+  win_main[f] + '</option>';
                        }else{
                            html += '<option value="' + f +'"> '+  win_main[f] + '</option>';
                        }
                    }
                    html += '</select>' +
                            '<input type="text" class="input_outname col-sm-3 col-md-offset-1" ' +
                            'value="'+ show_data.C[i] +'">';
                    if(i == 0 ){
                        html += '<a class="btn-sm col-sm-2 delStaff" style="height: 28px;" href="javascript:void(0);"></a>';
                    }else{
                        html += '<a class="btn-sm col-sm-2 delStaff" href="javascript:void(0);">删除</a>';
                    }
                    html +=  '</div></div>';
                }else{
                    //内部人员
                    for( var f in win_main ){
                        if(show_data.C[i] == f){
                            html += '<option selected value="' + f +'"> '+  win_main[f] + '</option>';
                            delete win_main[f];
                        }else{
                            html += '<option value="' + f +'"> '+  win_main[f] + '</option>';
                        }
                    }
                    html += '</select>' +
                            '<input type="text" disabled class="input_outname col-sm-3 col-md-offset-1" >';
                    if(i == 0 ){
                        html += '<a class="btn-sm col-sm-2 delStaff" style="height: 28px;" href="javascript:void(0);"></a>';
                    }else{
                        html += '<a class="btn-sm col-sm-2 delStaff" href="javascript:void(0);">删除</a>';
                    }
                    html +=  '</div></div>';
                }
            }
            out_obj.html(html);
        };

        //拼接数据，并返回.
        //参数1：数据容器，参数2：提交数据容器，参数3：展示数据容器,参数4：验证数据
        method.concat_data = function(in_obj,out_sub_obj,show_obj,vail_obj){
            concat_data(in_obj,out_sub_obj,show_obj,vail_obj);
        }

        //点击新增获奖人按钮
        method.add_staff = function(win_main,out_obj){
            win_main = remove_win_main_obj(win_main);
            insert_row(win_main,out_obj);
        }

        //删除并刷新数据
        method.del_staff = function(win_main,self){
            //重新排获奖顺序
            reOrder(self);

            //把删除的数据重新加入集合
            win_main = remove_win_main_obj(win_main);
            var current = self.closest(".staff-row").find("select").find("option:selected");
            win_main = add_win_main(win_main,current);

            //第二步删除行数据
            self.closest(".staff-row").remove();

            //重新刷新各个select标签内数据
            refresh_select(win_main);
        }

        //select选择触发事件
        method.change_s = function(win_main,self){
            var current = $(self).val();
            var inputName = $(self).next(".input_outname");
            if( current == "99" ){
                inputName.removeAttr("disabled");
            }else{
                inputName.val("");
                inputName.attr("disabled","disabled");
                win_main = remove_win_main_obj(win_main);
                //重新刷新各个select标签内数据
                refresh_select(win_main);
            }
        }

        //验证数据
        method.vail = function(vail_obj){
            var info = vail_data(vail_obj);
            var obj = new Object();
            if(info.length > 0){
                obj.err  = false;
                obj.info = info;
            }else{
                obj.err = true;
            }
            return obj;
        }


        //顺序分割数据,返回对象数组，a：1是内部，2是外部人员，b：代表获奖顺序，c：代表人员ID或者人员姓名
        function split_data(data){
            if(data.length == 0){
                return null;
            }
            var t_arr = data.split(",");
            if(t_arr.length == 0){
                return null;
            }
            var obj = new Object();
            obj.A = [];
            obj.B = [];
            obj.C = [];
            for (var i = 0 ; i < t_arr.length ; i++){
                var arr = t_arr[i].split("@");
                if(arr.length == 3){
                    obj.A.push(arr[0]);
                    obj.B.push(arr[1]);
                    obj.C.push(arr[2]);
                }
            }
            return obj;
        }

        //顺序拼接数据
        function concat_data(obj,sub_obj,show_obj,vail_obj){
            var sub_html = "";
            var show_html = "";
            $(obj).each(function(i,e){
                var current = $(this).find("option:selected");
                var inputName = $(this).next(".input_outname");
                if (current.val() == "98" ){
                    return true;
                }
                if( current.val() == "99" ){
                    sub_html += "0@" + (i + 1) + "@" + inputName.val() + ",";
                    show_html +=inputName.val()+"," ;
                }else{
                    sub_html += "1@" + (i + 1) + "@" + current.val() + ",";
                    show_html +=current.text()+"," ;
                }
            });
            sub_html = sub_html.substring(0,sub_html.length - 1);
            show_html = show_html.substring(0,show_html.length - 1);
            sub_obj.val(sub_html);
            show_obj.html(show_html);
            vail_obj.val(sub_html);
        }


        //排除已选择的选项
        function remove_win_main_obj(win_obj){
            var has_Selected = [];
            //排除已选择的数据
            $(".staff-row").each(function(i,e){
                has_Selected.push($(e).find(".controls").children("select").val());
            });
            if(has_Selected.length > 0){
                for (var i in win_obj){
                    for( var j = 0 ; j < has_Selected.length ; j++ ){
                        if( i == has_Selected[j] || i == "99" || i == "98"){
                            delete win_obj[i];
                            break;
                        }
                    }
                }
            }
            return add_other(win_obj);
        }

        //选项中添加 请选择和其他
        function add_other(win_main){
            for(var i in win_main){
                if( i == "98" ||  i == "99"){
                    delete win_main[i];
                }
            }
            win_main["98"] = '请选择';
            win_main["99"] = "其他";
            return win_main;
        }


        //新增行数据，默认是请选择
        function insert_row(win_main,out_obj){
            var num = out_obj.children(".staff-row").length;
            var html =  '<div class="control-group staff-row">'+
                        '<div class="controls controls-row doc-input-size">'+
                        '<label class="control-label col-sm-3">'+
                        '第' +
                        '<span class="staff-num">' + (num + 1) + '</span>' +
                        '获奖人' +
                        '<span class="required">*</span>'+
                        '</label>'+
                        '<select required="required"  class="span3 col-sm-3 special-sele" >';


            for( var t in win_main ){
                if(t == "98"){
                    html += '<option selected value="' + t +'"> '+  win_main[t] + '</option>'
                }else{
                    html += '<option value="' + t +'"> '+  win_main[t] + '</option>'
                }
            }

            html += '</select>'+
                    '<input type="text" id="input_outname" class="input_outname col-sm-3 col-md-offset-1" disabled>'+
                    '<a class="btn-sm col-sm-2 delStaff" href="javascript:void(0);">删除</a>'+
                    '</div></div>';
            out_obj.append(html);
        }

        //重新排获奖顺序
        function reOrder(obj){
            var staff_row_next_all = $(obj).closest(".staff-row").nextAll();
            //如果后面有元素，重新排序
            if(staff_row_next_all.length > 0 ){
                staff_row_next_all.each(function(i,e){
                    var obj = $(e).closest(".staff-row").find(".staff-num");
                    var num = Number(obj.html());
                    obj.html(num - 1);
                });
            }
        }

        //向win_main中添加被删除的数据
        function add_win_main(win_main,obj){
            if( obj.val().length != 0 && Number(obj.val()) != 98 && Number(obj.val()) != 99){
                win_main[obj.val()] = obj.text();
            }
            return win_main;
        }

        //重新刷新select标签中的数据
        function refresh_select(win_main){
            $(".special-sele").each(function(i,e){
                var key = $(e).find("option:selected");
                var html = "<option selected value='" + key.val() + "'>" + key.text() + "</option>";
                for (var i in win_main){
                    if (i != key.val() ){
                        html += "<option value=" + i + ">" + win_main[i] + "</option>";
                    }
                }
                $(this).html(html);
            });
        }

        //验证数据格式是否正确
        function vail_data(obj){
            var info = "";
            $(obj).each(function(i,e){
                var current = $(e).find("option:selected");
                var inputName = $(e).next(".input_outname");
                if(current.val() == "98"){
                    info += (i + 1) + ",";
                }else if(current.val() == "99"){
                    var reg = /\s/;
                    var name = $.trim(inputName.val());
                    name = name.replace(/\s/g,"");
                    if(name.length == 0 || name.length > 20){
                        info += (i + 1) + ",";
                    }
                }

            });
            info = info.substring(0,info.length - 1 );
            return info;
        }
});

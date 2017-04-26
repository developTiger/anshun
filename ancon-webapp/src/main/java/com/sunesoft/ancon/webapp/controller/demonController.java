package com.sunesoft.ancon.webapp.controller;

import com.sunesoft.ancon.webapp.function.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhouz on 2016/6/29.
 */
@Controller
public class demonController  extends Layout{

    @Autowired
    UserSession us;


    @RequestMapping(value = "test")
    public ModelAndView mutiSelecddt(Model model) {

        return view("testdf/test", model);
    }

    @RequestMapping(value = "_mutiselect")
    public ModelAndView mutiSelect(Model model) {

        return view( layout,"demon/mutiselect", model);
    }

    @RequestMapping(value = "_fuxuan")
    public ModelAndView fuSelect(Model model) {

        return view(layout, "demon/fuxuan", model);
    }

    @RequestMapping(value = "ajax_fuxuan")
    public ModelAndView addfuSelect1(Model model) {
        System.out.println("1111111111111");
        return view(layout, "demon/_fuxuan", model);
    }


}

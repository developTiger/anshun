package com.sunesoft.ancon.webapp.controller.inContract;

import com.sunesoft.ancon.fr.utils.DateHelper;
import com.sunesoft.ancon.webapp.controller.Layout;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 我的测试网页，最终删除
 * Created by xiazl on 2016/11/28.
 */
@Controller
public class Test extends Layout {


    @RequestMapping(value = "xzl")
    public ModelAndView getTest(Model model, HttpServletRequest request) {

        return view(layout, "inContract/inContract_index", model);

    }

    @RequestMapping(value = "xzl01")
    public ModelAndView getTest01(Model model, HttpServletRequest request) {

        return view(layout, "inContract/inContract_list", model);

    }

    @RequestMapping(value = "xzl02")
    public ModelAndView getTest02(Model model, HttpServletRequest request) {

        return view(layout, "inContract/invoiceList", model);

    }

    @RequestMapping(value = "xzl03")
    public ModelAndView getTest03(Model model, HttpServletRequest request) {

        return view(layout, "inContract/payList", model);

    }

    @RequestMapping(value = "xzl04")
    public ModelAndView getLeft(Model model) {

        return view(layout, "inContract/inContract_left", model);
    }

    @RequestMapping(value = "xzl05")
    public ModelAndView test05(Model model) {
        Date d1 = DateHelper.addMonth(new Date(), -5);
        String s1 = DateHelper.formatDate(d1, "yyyy-MM-dd");
        model.addAttribute("beforeTime", DateHelper.formatDate(d1, "yyyy-MM-dd"));
        model.addAttribute("nowTime", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(layout, "inContract/inContract_count_lineChart", model);
    }

    @RequestMapping(value = "x1")
    public ModelAndView xx(Model model, HttpServletRequest request) {

        return view(layout, "myTest/test01", model);

    }
}

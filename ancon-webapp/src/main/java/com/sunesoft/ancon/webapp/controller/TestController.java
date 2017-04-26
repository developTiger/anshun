//package com.sunesoft.lemon.webapp.controller;
//
//import Logger;
//import PagedResult;
//import JsonHelper;
//import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
//
//import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
//import SysRoleService;
//import SysUserService;
//import AjaxResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by zhouz on 2016/5/13.
// */
//@Controller
//public class TestController extends Layout {
//    @Autowired
//    EmployeeService empService;
//    @Autowired
//    Logger logger;
//
//    @Autowired
//    SysRoleService sysRoleService;
//
//    @Autowired
//    SysUserService sysUserService;
//
//    @RequestMapping(value = "modal")
//    public ModelAndView modaView(Model model){
//        return view(testLayout,"test/modal", model);
//    }
//
//    @RequestMapping(value = "test")
//    public ModelAndView test(Model model){
//        return view("/test/test",model);
//    }
//
//
//
//
//    @RequestMapping(value = "hello")
//    public ModelAndView hello(Model model) {
//        Employee emp = new Employee();
//        emp.setPassword("123456");
//     //   emp.setEmpName("周");
//        Employee emp2 = new Employee();
//        emp2.setPassword("123456789");
//       // emp2.setEmpName("zhouzh2");
//
//        List<Employee> emplist = new ArrayList<>();
//        emplist.add(emp);
//        emplist.add(emp2);
//        model.addAttribute("name", "123456");
//
//        for (int i = 0; i < 20; i++) {
//            Employee emp3 = new Employee();
//            emp3.setPassword("123456");
//        //    emp3.setEmpName("直接批打实际u周zh"+i);
//            emplist.add(emp3);
//        }
//        PagedResult PagedResult = new PagedResult(emplist.subList(1,10),1,5,emplist.size());
//
//        model.addAttribute("beans", emplist);
//        return view(layout, "hello", model);
//    }
//
//    @RequestMapping(value = "ajax_empQuery_list.json")
//    public  void getEmpinfos(Model model,String p,String ps,HttpServletRequest request,HttpServletResponse response){
//
//        int pageNo = 1;
//        int pageSize = 10;
//        if (p != null && !p.equals("")) {
//            pageNo = Integer.parseInt(p);
//        }
//        if (ps != null && !ps.equals("")) {
//            pageSize = Integer.parseInt(ps);
//        }
//
//        String empname= request.getParameter("empName");
//        System.out.println(empname);
//        Employee emp = new Employee();
//        emp.setPassword("123456");
//        //emp.setEmpName("zhouzh");
//        Employee emp2 = new Employee();
//        emp2.setPassword("123456789");
//     //   emp2.setEmpName("zhouzh2");
//
//        List<Employee> emplist = new ArrayList<>();
//        emplist.add(emp);
//        emplist.add(emp2);
//        model.addAttribute("name", "123456");
//
//        for (int i = 0; i < 20; i++) {
//            Employee emp3 = new Employee();
//            emp3.setPassword("123456");
//         //   emp3.setEmpName("直接批打实际u周zh"+i);
//            emplist.add(emp3);
//        }
//        int size = emplist.size();
//            PagedResult PagedResult = new PagedResult(emplist.subList((pageNo-1)*pageSize,(pageNo*pageSize)>size?size:pageNo*pageSize),pageNo,pageSize,size);
//        String json=JsonHelper.toJson(PagedResult);
//        AjaxResponse.write(response, json);
//    }
//
//    @RequestMapping(value = "index")
//    public ModelAndView index(Model model) {
//        logger.info("only a test");
//        return view(layout, "index", model);
//    }
//
//    @RequestMapping(value = "/")
//    public String login(Model model) {
//
//        return "login";
//    }
//}

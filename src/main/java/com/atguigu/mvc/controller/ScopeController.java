package com.atguigu.mvc.controller;

import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ScopeController {

  // 使用ServletAPI向request域对象共享数据
  @RequestMapping("/testRequestByServletAPI")
  public String testRequestByServletAPI(HttpServletRequest request) {
    request.setAttribute("testRequestScope", "hello from ServletAPI");
    return "success";
  }

  @RequestMapping("/testModelAndView")
  public ModelAndView testModelAndView() { // 注意ModelAndView要作为方法的返回值返回
    ModelAndView modelAndView = new ModelAndView();
    // 处理模型数据，即向请求域request共享数据
    modelAndView.addObject("testRequestScope", "hello from ModelAndView");
    // 设置视图名称
    modelAndView.setViewName("success");
    return modelAndView;
  }

  @RequestMapping("/testModel")
  public String testModel(Model model) {
    model.addAttribute("testRequestScope", "hello from Model");
    return "success";
  }

  @RequestMapping("/testMap")
  public String testMap(Map<String, Object> map) {
    map.put("testRequestScope", "hello from Map");
    return "success";
  }

  @RequestMapping("/testModelMap")
  public String testModelMap(ModelMap modelMap) {
    modelMap.addAttribute("testRequestScope", "hello from ModelMap");
    return "success";
  }

  @RequestMapping("/testSession")
  public String testSession(HttpSession session) {
    session.setAttribute("testSessionScope", "hello from Session");
    return "success";
  }

  @RequestMapping("/testApplication")
  public String testApplication(HttpSession session) {
    ServletContext context = session.getServletContext();
    context.setAttribute("testApplicationScope", "hello from Application");
    return "success";
  }

}

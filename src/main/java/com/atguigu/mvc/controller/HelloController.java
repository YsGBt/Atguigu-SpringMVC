package com.atguigu.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

  // "/" -> /WEB-INF/templates/index.html
  @RequestMapping(value = "/")
  public String index() {
    // 返回视图名称
    return "index";
  }

  @RequestMapping(value = "/target")
  public String helloWorld() {
    return "target";
  }

}

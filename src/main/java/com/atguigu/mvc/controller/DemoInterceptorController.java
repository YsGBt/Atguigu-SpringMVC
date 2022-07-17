package com.atguigu.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/interceptor")
public class DemoInterceptorController {

  @RequestMapping("/testInterceptor")
  public String testInterceptor() {
    return "success";
  }

}

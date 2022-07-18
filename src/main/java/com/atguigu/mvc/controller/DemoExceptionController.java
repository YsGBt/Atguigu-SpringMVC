package com.atguigu.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exception")
public class DemoExceptionController {

  @RequestMapping("/testExceptionHandler")
  public String testExceptionHandler() {
    System.out.println(1 / 0);
    return "success";
  }
}

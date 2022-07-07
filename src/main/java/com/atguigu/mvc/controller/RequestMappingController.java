package com.atguigu.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/hello")
public class RequestMappingController {

  // 此时请求映射所映射的请求的请求路径为: /hello/testRequestMapping
  @RequestMapping(
      value = {"/testRequestMapping", "/test"},
      method = {RequestMethod.GET, RequestMethod.POST},
      // 请求携带参数必须有username，password和email并且password值为123，email值不为123，并且请求携带参数没有age
      params = {"username", "password=123", "email!=123", "!age"},
      headers = {"Host=localhost:8080"}
  )
  public String success() {
    return "success";
  }

  @GetMapping("/testGetMapping")
  public String testGetMapping() {
    return "success";
  }

  @PutMapping("/testPut")
  public String testPut() {
    return "success";
  }

  @RequestMapping({"/a?a/testAnt", "/b*b/testAnt", "/c/**/testAnt"})
  public String testAnt() {
    return "success";
  }
}

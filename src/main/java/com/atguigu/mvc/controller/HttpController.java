package com.atguigu.mvc.controller;

import com.atguigu.mvc.bean.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/http")
public class HttpController {

  @RequestMapping("/testRequestBody")
  public String testRequestBody(@RequestBody String requestBody) {
    System.out.println("requestBody = " + requestBody);
    return "success";
  }

  @RequestMapping("/testRequestEntity")
  public String testRequestEntity(RequestEntity<String> requestEntity) {
    // 当前requestEntity表示整个请求报文的信息
    System.out.println("请求头 = " + requestEntity.getHeaders());
    System.out.println("请求体 = " + requestEntity.getBody());
    return "success";
  }

  @RequestMapping("/testResponse")
  public String testResponse(HttpServletResponse response) {
    try (PrintWriter writer = response.getWriter();) {
      writer.print("hello, response");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return null;
  }

  @RequestMapping("/testResponseBody")
  @ResponseBody
  public String testResponseBody() {
    return "hello, response";
  }

  // -> 如果未配置jackson，报错500: No converter found for return value of type (HttpMessageNotWritableException)
  @RequestMapping("/testResponseUser")
  @ResponseBody
  public User testResponseUser() {
    return new User(1001, "admin", "123456", 23, "男", "abc@gmail.com");
  }

  @RequestMapping("/testAxios")
  @ResponseBody
  public String testAxios(String username, Integer password) {
    System.out.println(username + ", " + password);
    return "hello, axios";
  }
}

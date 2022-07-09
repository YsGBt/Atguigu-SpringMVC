package com.atguigu.mvc.controller;

import com.atguigu.mvc.bean.User;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

  @RequestMapping("/testPath/{id}/{username}")
  public String testPath(@PathVariable("id") Integer id,
      @PathVariable("username") String username) {
    System.out.println("id = " + id);
    System.out.println("username = " + username);
    return "success";
  }

  @RequestMapping("/testServletAPI")
  // 形参位置的request表示当前请求
  public String testServletAPI(HttpServletRequest request) {
    // session依赖于cookie，cookie是客户端会话技术，session是服务器端会话技术 (服务器通过cookie保存的JSESSIONID来确定客户端的session)
    HttpSession session = request.getSession();
    String uname = request.getParameter("username");
    String password = request.getParameter("password");
    System.out.println("uname = " + uname);
    System.out.println("password = " + password);
    return "success";
  }

  @RequestMapping("/testParam")
  public String testParam(
      // 将请求参数和形参对应 value = "user_name" -> username = req.getParameter("user_name")
      // required = true(默认) -> 设置是否必须传输此请求参数，如果必须传输但未接受到参数则报错 (400 parameter "user_name" is not present)
      // defaultValue 默认值(没有传输或为空字符串时)，使用时代表required = false
      @RequestParam(value = "user_name", required = true, defaultValue = "default username") String username,
      Integer password,
      String[] hobby,
      // 从请求头中找到对应的参数赋值给形参
      @RequestHeader(value = "Host") String host,
      // 从Cookie中找到对应的参数赋值给形参
      @CookieValue(value = "JSESSIONID")  String sessionID
      ) {
    System.out.println("uname = " + username);
    System.out.println("password = " + password);
    // 若请求参数中出现多个同名的请求参数(ex.复选框)，可以在控制器方法的形参位置设置字符串或字符串数组接受此请求参数
    // 若使用字符串类型的形参，最终结果为请求参数的每一个值之间使用逗号进行拼接
    // hobby获取复选框数据，hobby数据类型为String -> a,b,c, hobby数据类型为String[] -> [a,b,c]
    System.out.println("hobby = " + Arrays.toString(hobby));
    System.out.println("host = " + host);
    System.out.println("sessionID = " + sessionID);
    return "success";
  }

  @RequestMapping("/testBean")
  public String testBean(User user) {
    System.out.println(user);
    return "success";
  }
}

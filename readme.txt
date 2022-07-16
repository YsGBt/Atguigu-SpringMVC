1. 什么是MVC
   - Model: 模型层，指工程中的JavaBean，作用时处理数据
     JavaBean分为两类:
       - 实体类Bean: 专门存储业务数据的，如Student，User等
       - 业务处理Bean: 指Service或DAO对象，专门用于处理业务逻辑和数据访问
   - View: 视图层，指工程中的html或者jsp等页面，作用是与用户进行交互，展示数据
   - Controller: 控制层，指工程中的Servlet，作用是接收请求和响应浏览器
   - MVC工作流程: 用户通过视图层发送请求到服务器，在服务器中请求被Controller接收，Controller调用相应的Model层
     处理请求，处理完毕将结果返回到Controller，Controller再根据请求处理的就过找到相应的View视图，渲染数据后最终
     响应给浏览器

2. 创建项目
   1) 创建maven项目

   2) 添加webapp和web.xml

   3) 配置web.xml -> springMVC

      <!-- 配置SpringMVC的前端控制器，对浏览器发送的请求进行统一处理 -->
      <servlet>
        <servlet-name>springMVC</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- 配置SpringMVC配置文件的位置和名称 -->
        <init-param>
          <param-name>contextConfigLocation</param-name>
          <param-value>classpath:springMVC.xml</param-value>
        </init-param>
        <!-- 配置初始化时间，将前段控制器DispatcherServlet的初始化时间提前到服务器启动时 -->
        <load-on-startup>1</load-on-startup>
      </servlet>

      <!--
        设置SpringMVC的核心控制器所能处理的请求的请求路径
        /所匹配的请求可以是/login，/a，/b或者.html或.js或.css方式的请求路径
        但是/不能匹配.jsp请求路径的请求
        注意，/*可以匹配.jsp请求路径的请求
      -->
      <servlet-mapping>
        <servlet-name>springMVC</servlet-name>
        <url-pattern>/</url-pattern>
      </servlet-mapping>

   4） 配置Tomcat
       - war模式这种可以称之为是发布模式，看名字也知道，这是先打成war包，再发布；
       - war exploded模式是直接把文件夹、jsp页面 、classes等等移到 Tomcat 部署文件夹里面，进行加载部署。
         因此这种方式支持热部署，一般在开发的时候也是用这种方式。

   5) 配置Spring和Thymeleaf视图解析器

      <!-- 扫描组件 -->
      <context:component-scan base-package="com.atguigu.mvc"></context:component-scan>

      <!-- 配置Thymeleaf视图解析器 -->
      <bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
        <property name="order" value="1"/>
        <property name="characterEncoding" value="UTF-8"/>
        <property name="templateEngine">
          <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
            <property name="templateResolver">
              <bean
                class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">
                <!-- 视图前缀 -->
                <property name="prefix" value="/WEB-INF/templates/"/>
                <!-- 视图后缀 -->
                <property name="suffix" value=".html"/>
                <property name="templateMode" value="HTML5"/>
                <property name="characterEncoding" value="UTF-8"/>
              </bean>
            </property>
          </bean>
        </property>
      </bean>

3. @RequestMapping("")
   1) RequestMapping请求地址必须是唯一的，即相同的请求地址只有一个RequestMapping进行映射

   2) RequestMapping注解的位置
      - @RequestMapping标识一个类: 设置映射请求的请求路径的初始信息
      - @RequestMapping标识一个方法: 设置映射请求请求路径的具体信息

        @Controller
        @RequestMapping("/hello")
        public class RequestMappingController {

          // 此时请求映射所映射的请求的请求路径为: /hello/testRequestMapping
          @RequestMapping("/testRequestMapping")
          public String success() {
            return "success";
          }
        }

   3) RequestMapping注解的value属性 (匹配不成功 -> 404)
      - value属性是一个字符串类型的数组，必须配置，至少通过请求地址匹配请求映射

   4) RequestMapping注解的method属性 (匹配不成功 -> 405)
      - method属性通过请求方式(post,get,delete,put等)匹配请求映射
      - 默认匹配所有请求
      - 若请求地址满足映射的value属性，但是请求方式不满足method属性，则浏览器报错405: Request method 'POST' not supported
      - 对于处理指定请求方式的控制器方法，SpringMVC中提供了@RequestMapping的派生注解
        a. 处理get请求映射 -> @GetMapping
        b. 处理post请求映射 -> @PostMapping
        c. 处理put请求映射 -> @PutMapping
        d. 处理delete请求映射 -> @DeleteMapping
      - 注意: 目前浏览器只支持get和post，若在form表单提交时，为method是这里其他请求方式的字符串，则按照默认的请求方式get处理
        若要发送put和delete请求，则需要通过spring提供的过滤器HiddenHttpMethodFilter

   5) RequestMapping注解params属性 (匹配不成功 -> 400)
      - params属性通过请求的请求参数匹配请求映射
      - params属性是一个字符串类型的数组，可以通过四种表达式设置请求参数和请求映射的匹配关系
        a. "param": 要求请求映射所匹配的请求必须携带param请求参数
        b. "!param": 要求请求映射所匹配的请求必须不能携带param请求参数
        c. "param=value": 要求请求映射所匹配的请求必须携带param请求参数且param=value
        d. "param!=value": 要求请求映射所匹配的请求必须携带param请求参数但是param!=value

   6) RequestMapping注解headers属性 (匹配不成功 -> 404)
      - headers属性通过请求的请求头信息匹配请求映射
      - headers属性是一个字符串类型的数组，可以通过四种表达式设置请求头信息和请求映射的匹配关系
           a. "header": 要求请求映射所匹配的请求必须携带header请求头信息
           b. "!header": 要求请求映射所匹配的请求必须不能携带header请求头信息
           c. "header=value": 要求请求映射所匹配的请求必须携带header请求头信息且header=value
           d. "header!=value": 要求请求映射所匹配的请求必须携带header请求头信息但是header!=value

4. SpringMVC支持ant风格的路径
   1) ?: 表示任意的单个字符 (除 '/' 和 '?' 外)
   2) *: 表示任意的0个或者多个字符
   3) **: 表示任意的0层或多层目录
      注意: 在使用**时，只能使用/**/xxx的方式

5. SpringMVC支持路径中的占位符
   - 原始方式: /deleteUser?id=1
   - rest方式： /deleteUser/1

     @RequestMapping("/testPath/{id}/{username}")
     public String testPath(@PathVariable("id") Integer id, @PathVariable("username") String userName) {
       System.out.println("id = " + id);
       System.out.println("username = " + userName);
       return "success";
     }

6. SpringMVC获取请求参数
   1) 通过ServletAPI获取 (在使用SpringMVC时，能不用原生ServletAPI就不用)

      @RequestMapping("/testServletAPI")
      // 形参位置的request表示当前请求
      public String testServletAPI(HttpServletRequest request) {
        String uname = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("uname = " + uname);
        System.out.println("password = " + password);
        return "success";
      }

   2) 通过控制器方法的形参获取请求参数 & @RequestParam()

      @RequestMapping("/testParam")
      public String testParam(
          // 将请求参数和形参对应 value = "user_name" -> username = req.getParameter("user_name")
          // required = true(默认) -> 设置是否必须传输此请求参数，如果必须传输但未接受到参数则报错 (400 parameter "user_name" is not present)
          // defaultValue 默认值(没有传输或为空字符串时)，使用时代表required = false
          @RequestParam(value = "user_name", required = true, defaultValue = "default username") String username,
          Integer password,
          String[] hobby) {
        System.out.println("uname = " + username);
        System.out.println("password = " + password);
        // 若请求参数中出现多个同名的请求参数(ex.复选框)，可以在控制器方法的形参位置设置字符串或字符串数组接受此请求参数
        // 若使用字符串类型的形参，最终结果为请求参数的每一个值之间使用逗号进行拼接
        // hobby获取复选框数据，hobby数据类型为String -> a,b,c, hobby数据类型为String[] -> [a,b,c]
        System.out.println("hobby = " + Arrays.toString(hobby));
        return "success";
      }

   3) @RequestHeader()
      // 从请求头中找到对应的参数赋值给形参
      @RequestHeader(value = "Host", required = true, defaultValue = "default host") String host,

   4) @CookieValue()
      注意: session依赖于cookie，cookie是客户端会话技术，session是服务器端会话技术 (服务器通过cookie保存的JSESSIONID来确定客户端的session)
      // 从Cookie中找到对应的参数赋值给形参
      @CookieValue(value = "JSESSIONID", required = true, defaultValue = "default id")  String sessionID

   5) 通过POJO获取请求参数
      - 可以在控制器方法的形参位置设置一个实体类类型的形参，此时若浏览器传输的请求参数的参数名和实体类中的属性名一致，那么请求参数就会为此属性赋值

        <form th:action="@{/hello/testBean}" method="post">
          用户名:<input type="text" name="username"><br>
          密码:<input type="password" name="password"><br>
          性别:<input type="radio" name="sex" value="男">男
               <input type="radio" name="sex" value="女">女<br>
          年龄:<input type="text" name="age"><br>
          邮箱:<input type="text" name="email"><br>
          <input type="submit" value="使用实体类接受请求参数">
        </form>

        @RequestMapping("/testBean")
        public String testBean(User user) {
          System.out.println(user); // -> User{id=null, username='张三', password='123', age=23, sex='男', email='123@qq.com'}
          return "success";
        }

   6) 解决获取请求参数的乱码问题 (CharacterEncodingFilter)

      <!-- 配置编码过滤器 -->
      <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <!-- 配置编码 -->
        <init-param>
          <param-name>encoding</param-name>
          <param-value>UTF-8</param-value>
        </init-param>
        <!-- 设置请求编码 -->
        <!-- 如果request.getCharacterEncoding() == null 则不需要设置forceRequestEncoding -->
        <!-- 设置响应编码 -->
        <init-param>
          <param-name>forceResponseEncoding</param-name>
          <param-value>true</param-value>
        </init-param>
      </filter>

      <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
      </filter-mapping>

7. 域对象共享数据 (request, session, application)
   1) 使用ServletAPI向request域对象共享数据

      @RequestMapping("/testRequestByServletAPI")
      public String testRequestByServletAPI(HttpServletRequest request) {
        request.setAttribute("testRequestScope", "hello, ServletAPI");
        return "success";
      }

   2) 使用ModelAndView向request域对象共享数据

      @RequestMapping("/testModelAndView")
      public ModelAndView testModelAndView() { // 注意ModelAndView要作为方法的返回值返回
        ModelAndView modelAndView = new ModelAndView();
        // 处理模型数据，即向请求域request共享数据
        modelAndView.addObject("testRequestScope", "hello from ModelAndView");
        // 设置视图名称
        modelAndView.setViewName("success");
        return modelAndView;
      }

   3) 使用Model向request域对象共享数据

      @RequestMapping("/testModel")
      public String testModel(Model model) {
        model.addAttribute("testRequestScope", "hello from Model");
        return "success";
      }

   4) 使用Map向request域对象共享数据

      @RequestMapping("/testMap")
      public String testMap(Map<String, Object> map) {
        map.put("testRequestScope", "hello from Map");
        return "success";
      }

   5) 使用ModelMap向request域对象共享数据

      @RequestMapping("/testModelMap")
      public String testModelMap(ModelMap modelMap) {
        modelMap.addAttribute("testRequestScope", "hello from ModelMap");
        return "success";
      }

   6) 注意: 本质上这里的Model，Map，和ModelMap类型的参数本质上都是 BindingAwareModelMap 类型的

   7) 向session域共享数据

      @RequestMapping("/testSession")
      public String testSession(HttpSession session) {
        session.setAttribute("testSessionScope", "hello from Session");
        return "success";
      }

   8) 向application域共享数据

      @RequestMapping("/testApplication")
      public String testApplication(HttpSession session) {
        ServletContext context = session.getServletContext();
        context.setAttribute("testApplicationScope", "hello from Application");
        return "success";
      }

8. SpringMVC的视图 (SpringMVC默认视图: 转发视图InternalResourceView 和 重定向视图RedirectView)
   1) ThymeleafView
      - 当控制器方法中所设置的视图名称没有任何前缀是，此时的视图名称会被SpringMVC配置文件中所配置的视图解析器解析，
        视图名称拼接视图前缀和视图后缀所得到的最终路径，会通过转发的方式实现跳转

        @RequestMapping("/testThymeleafView")
        public String testThymeleafView() {
          return "success";
        }

   2) 转发视图
      - SpringMVC默认的转发视图是 InternalResourceView
      - 当控制器方法中所设置的视图名称以"forward:"为前缀时，创建InternalResourceView视图，此时的视图名称不会被
        SpringMVC配置文件中所配置的视图解析器解析，而是会将前缀"forward:"去掉，剩余部分作为最终路径通过转发的方式
        实现跳转
      - 例如: "forward:/", "forward:/success"

        @RequestMapping("/testForward")
        public String testForward() {
          return "forward:/testThymeleafView";
        }

   3) 重定向视图
      - SpringMVC默认的重定向视图是 RedirectView
      - 当控制器方法中所设置的视图名称以"redirect:"为前缀时，创建RedirectView视图，此时的视图名称不会被
         SpringMVC配置文件中所配置的视图解析器解析，而是会将前缀"redirect:"去掉，剩余部分作为最终路径通过重定向的方式
         实现跳转
      - 例如: "redirect:/", "redirect:/success"

        @RequestMapping("/testRedirect")
        public String testRedirect() {
          return "redirect:/testThymeleafView";
        }

   4) 视图控制器 view-controller
      - 当控制器方法中，仅仅用来实现页面跳转，即只需要设置视图名称时，可以将处理器方法使用view- controller标签进行表示
        <!--
          path:设置处理的请求地址
          view-name:设置请求地址所对应的视图名称
        -->
        <mvc:view-controller path="/" view-name="index"></mvc:view-controller>

        <!-- 开启MVC的注解驱动 -->
        <mvc:annotation-driven></mvc:annotation-driven>

9. RESTFul (Representational State Transfer 表现层资源状态转移)
   1) RESTFul的实现
      - Get 获取
      - Post 添加
      - Put 更新
      - Delete 删除
      - URL地址从前到后各个单词使用斜线分开，不使用问号键值对方式携带请求参数，而是将要发送给服务器的数据作为url地址
        的一部分:
        getUserById?id=1 ---更改为---> user/1

   2) HiddenHttpMethodFilter
      - 由于网页只能发送get和post请求所以需要使用HiddenHttpMethodFilter来对请求进行包装

        <!-- 配置HiddenHttpMethodFilter 注意要放到编码过滤器之后执行 因为HiddenHttpMethodFilter调用了getParameter() -->
        <filter>
          <filter-name>HiddenHttpMethodFilter</filter-name>
          <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
        </filter>

        <filter-mapping>
          <filter-name>HiddenHttpMethodFilter</filter-name>
          <url-pattern>/*</url-pattern>
        </filter-mapping>

      - 在html中需要使用post请求并添加_method隐藏域来确定实际请求方式:
        <form th:action="@{/user}" method="post">
            <input type="hidden" name="_method" value="PUT">
            用户名: <input type="text" name="username"><br>
            密码: <input type="password" name="password"><br>
            <input type="submit" value="修改">
          </form>

   3) 开放对静态资源的访问
      - 首先DispatcherServlet处理，如果DispatcherServlet无法找到资源则交给
        DefaultServletHttpRequestHandler (然后通过调用DefaultServlet)处理
        <mvc:default-servlet-handler></mvc:default-servlet-handler>

10. HttpMessageConverter 报文信息转换器
    - 将请求报文转换成Java对象，或将Java对象转换成响应报文
    - @RequestBody RequestEntity
    - @ResponseBody ResponseEntity

    1) @RequestBody
       - 可以获取请求体，需要在控制器方法中设置形参，使用@RequestBody进行表示，当前请求的请求体就会为当前注解所标识
         的形参赋值

         @RequestMapping("/testRequestBody")
           public String testRequestBody(@RequestBody String requestBody) {
             System.out.println("requestBody = " + requestBody); // -> requestBody = username=admin&password=123456
             return "success";
           }

    2) RequestEntity

       @RequestMapping("/testRequestEntity")
         public String testRequestEntity(RequestEntity<String> requestEntity) {
           // 当前requestEntity表示整个请求报文的信息
           System.out.println("请求头 = " + requestEntity.getHeaders());
           System.out.println("请求体 = " + requestEntity.getBody());
           return "success";
         }

       --> 请求头 = [host:"localhost:8080", connection:"keep-alive", content-length:"27", cache-control:"max-age=0", ... ]
       --> 请求体 = username=admin&password=123456

    3) @ResponseBody

       @RequestMapping("/testResponseBody")
       @ResponseBody
       public String testResponseBody() {
         return "hello, response"; // -> response.getWriter().print("hello, response");
       }

    4) SpringMVC处理json
       a. 导入jackson的依赖
       b. 在SpringMVC的核心配置文件中开启mvc的注解驱动，此时在HandlerAdaptor中会自动装配一个消息转换器:
          MappingJackson2HttpMessageConverter，可以将响应到浏览器的Java对象转换为json格式的字符串
          <mvc:annotation-driven />
       c. 在处理器方法上使用@ResponseBody注解进行标识
       d. 将Java对象直接作为控制器方法的返回值返回，就会自动转换为json格式的字符串

          @RequestMapping("/testResponseUser")
          @ResponseBody
          public User testResponseUser() {
            return new User(1001, "admin", "123456", 23, "男", "abc@gmail.com");
          }

          --> 浏览器的页面中展示的结果:
          {"id":1001,"username":"admin","password":"123456","age":23,"sex":"男","email":"abc@gmail.com"}

    5) SpringMVC处理ajax

       window.onload = function () {
         let vue = new Vue({
           el:"#app",
           methods: {
             testAxios:function (event) {
               axios({
                 method:"post",
                 url:event.target.href,
                 params: {
                   username:"admin",
                   password:123456
                 }
               }).then(function (response) {
                 alert(response.data);
               }).catch();
               event.preventDefault();
             }
           }
         })
       }
       <div id="app">
         <a th:href="@{/http/testAxios}" @click="testAxios">SpringMCV处理ajax</a>
       </div>

       @RequestMapping("/testAxios")
       @ResponseBody
       public String testAxios(String username, Integer password) {
         System.out.println(username + ", " + password);
         return "hello, axios";
       }

    6) @RestController 注解
       - @RestController注解是SpringMVC提供的一个复合注解，标识在控制器的类上，就相当于为类添加了@Controller注解，
         并且为其中的所有方法添加了@ResponseBody注解

    7) ResponseEntity
       - ResponseEntity用于控制器方法的返回值类型，该控制器方法的返回值就是响应到浏览器的响应报文

11. 文件上传和下载
    1) 文件下载
       @RequestMapping("/testDownload")
       public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws
           IOException {
         // 获取ServletContext对象
         ServletContext servletContext = session.getServletContext();
         // 获取服务器中文件的真实路径
         String realPath = servletContext.getRealPath("/static/img/1.jpeg");
         // 创建输入流
         InputStream is = new FileInputStream(realPath);
         // 创建字节数组
         byte[] bytes = new byte[is.available()];
         // 将流读到字节数组中
         is.read(bytes);
         // 创建HttpHeaders对象设置响应头信息
         MultiValueMap<String, String> headers = new HttpHeaders();
         // 设置要下载方式以及下载文件的名字
         headers.add("Content-Disposition", "attachment;filename=1.jpeg");
         // 设置响应状态码
         HttpStatus statusCode = HttpStatus.OK;
         // 创建ResponseEntity对象
         ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers,
             statusCode);
         // 关闭输入流
         is.close();
         return responseEntity;
       }

    2) 文件上传
       a. 文件上传要求form表单的请求方式必须为post，并且添加属性enctype="multipart/form-data"

       b. 添加依赖
          <!-- https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload -->
          <dependency>
              <groupId>commons-fileupload</groupId>
              <artifactId>commons-fileupload</artifactId>
              <version>1.3.1</version>
          </dependency>

       c. 在SpringMVC的配置文件中添加配置
          <!-- 配置文件上传解析器，将上传的文件封装为MultipartFile -->
          <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
          </bean>

       d. SpringMVC中将上传的文件封装到MultipartFile对象中，通过此对象可以获取文件相关信息
          - 有关路径: https://blog.csdn.net/freelk/article/details/79280021

    3)  解决文件重名问题
        @RequestMapping("/testUpload")
        public String testUpload(MultipartFile photo, HttpSession session) throws IOException {
          // 获取上传文件的文件名
          String filename = photo.getOriginalFilename();
          // 获取上传文件的后缀名
          String suffix = filename.substring(filename.lastIndexOf("."));
          // 将UUID作为文件名
          String uuid = UUID.randomUUID().toString();
          // 将uuid和文件后缀名拼接后的结果作为最终的文件名
          filename = uuid + suffix;
          // 通过ServletContext获取服务器中photo目录的路径
          ServletContext servletContext = session.getServletContext();
          String photoPath = servletContext.getRealPath("/photo");
          File file = new File(photoPath);
          // 判断photoPath所对应路径是否存在
          if (!file.exists()) {
            // 若不存在，则创建目录
            file.mkdir();
          }
          String finalPath = photoPath + File.separator + filename;
          photo.transferTo(new File(finalPath));
          return "success";
        }





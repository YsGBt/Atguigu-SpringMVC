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
     public String testPath(@PathVariable("id") Integer id, @PathVariable("username") String username) {
       System.out.println("id = " + id);
       System.out.println("username = " + username);
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

7. 域对象共享数据 (request.setAttribute())
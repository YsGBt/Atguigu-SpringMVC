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

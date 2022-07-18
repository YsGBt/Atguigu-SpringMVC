package com.atguigu.mvc.config;

import com.atguigu.mvc.interceptor.FirstInterceptor;
import com.atguigu.mvc.interceptor.SecondInterceptor;
import java.util.List;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * 代替SpringMVC的配置文件springMVC.xml: 1. 扫描组件 2. 视图解析器 3. view-controller 4. default-servlet-handler 5.
 * mvc注解驱动 6. 文件上传解析器 7. 拦截器 8. 异常处理
 */

@Configuration // 将当前类标识为一个配置类
@ComponentScan(basePackages = "com.atguigu.mvc") // 1. 扫描组件
@EnableWebMvc // 5. mvc注解驱动
public class WebConfig implements WebMvcConfigurer {

  // 3. view-controller
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/viewController").setViewName("success");
  }

  // 4. default-servlet-handler
  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  // 6. 文件上传解析器
  @Bean
  public MultipartResolver multipartResolver() {
    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
    return multipartResolver;
  }

  // 7. 拦截器
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    FirstInterceptor firstInterceptor = new FirstInterceptor();
    SecondInterceptor secondInterceptor = new SecondInterceptor();
    registry.addInterceptor(firstInterceptor).addPathPatterns("/interceptor/*")
        .excludePathPatterns("/interceptor/exclude");
    registry.addInterceptor(secondInterceptor).addPathPatterns("/**");
  }

  // 8. 异常处理
  @Override
  public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
    SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
    Properties properties = new Properties();
    properties.setProperty("java.lang.ArithmeticException", "error");
    exceptionResolver.setExceptionMappings(properties);
    exceptionResolver.setExceptionAttribute("ex");
    resolvers.add(exceptionResolver);
  }

  //2. 视图解析器 - 配置生成模板解析器
  @Bean
  public ITemplateResolver templateResolver() {
    WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
    // ServletContextTemplateResolver需要一个ServletContext作为构造参数，可通过 WebApplicationContext 的方法获得
    ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(
        webApplicationContext.getServletContext());

    templateResolver.setPrefix("/WEB-INF/templates/");
    templateResolver.setSuffix(".html");
    templateResolver.setCharacterEncoding("UTF-8");
    templateResolver.setTemplateMode(TemplateMode.HTML);

    return templateResolver;
  }

  //2. 视图解析器 - 生成模板引擎并为模板引擎注入模板解析器
  @Bean
  public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);
    return templateEngine;
  }

  //2. 视图解析器 - 生成视图解析器并未解析器注入模板引擎
  @Bean
  public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    viewResolver.setCharacterEncoding("UTF-8");
    viewResolver.setTemplateEngine(templateEngine);
    return viewResolver;
  }
}

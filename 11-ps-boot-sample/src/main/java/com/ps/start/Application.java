package com.ps.start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by iuliana.cosmina on 8/22/16.
 */
@RestController
@SpringBootApplication(scanBasePackages = {"com.ps.start"})
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("Started ...");
    }


    @Value("${app.port}")
    private Integer value;

    @Value("${app.context}")
    private String contextPath;


    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(value);
        factory.setSessionTimeout(10, TimeUnit.MINUTES);
        factory.setContextPath(contextPath);
        return factory;
    }

    @Autowired
    ApplicationContext ctx;

    @RequestMapping("/")
    public String index() {
        StringBuilder sb = new StringBuilder("<html><body>");

        sb.append("Hello there dear developer, here are the beans you were looking for: </br>");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            sb.append("</br>").append(beanName);
        }
        sb.append("</body></htm>");
        return sb.toString();
    }
}

package com.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.test.web.interceptor.TestInterceptor;

/**
 * Spring Boot provides auto-configuration for Spring MVC that works well with
 * most applications. If you want to keep Spring Boot MVC features and you want
 * to add additional MVC configuration (interceptors, formatters, view
 * controllers, and other features), you can add your own @Configuration class
 * of type WebMvcConfigurer but without @EnableWebMvc. If you want to take
 * complete control of Spring MVC, you can add your own @Configuration annotated
 * with @EnableWebMvc.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	TestInterceptor testInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		 The path doesn't contains the context root 'TM'.
//		registry.addInterceptor(testInterceptor).addPathPatterns("/*Controller/**").excludePathPatterns("/index.html", "/static/**");
	}

	@Bean
	public InternalResourceViewResolver resourceViewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/pages/");
		internalResourceViewResolver.setSuffix(".jsp");
		return internalResourceViewResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/my/**").addResourceLocations("classpath:/my/");

	}
	
	/**
	 * don't need controller.
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/hello").setViewName("welcome");
	}
	
    @Bean(name = "messageSource")
    public ResourceBundleMessageSource messageSource() {
    	ResourceBundleMessageSource messageBundle = new ResourceBundleMessageSource();
        messageBundle.setBasename("config/config");
        messageBundle.setDefaultEncoding("UTF-8");
        messageBundle.setUseCodeAsDefaultMessage(true);
        return messageBundle;
    }
}

package com.configuration;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.test.cxfwebservice.UserService;

@Configuration
public class CXFConfig {
	
	/** Server Side **/
	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {
		return new SpringBus();
	}

	@Bean
	public ServletRegistrationBean<CXFServlet> cxfDispatcherServlet() {
		return new ServletRegistrationBean<CXFServlet>(new CXFServlet(), "/ws/*");
	}

	/**
	 * Create Endpoint.
	 * 
	 * @param springBus
	 * @param userService
	 * @return
	 */
	@Bean
	public Endpoint endpointPurchase(SpringBus springBus, UserService userService) {
		EndpointImpl endpoint = new EndpointImpl(springBus, userService);
		endpoint.publish("/userService");
		System.out.println("success: http://localhost:8080/tm/ws/userService?wsdl");
		return endpoint;
	}
}

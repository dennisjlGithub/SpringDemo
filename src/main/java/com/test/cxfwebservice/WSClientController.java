package com.test.cxfwebservice;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.bean.Customer;

@Controller
@RequestMapping("/WSClientController")
public class WSClientController {

	@RequestMapping("/callWS")
	@ResponseBody
	public String callWS() {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(UserService.class);
		factory.setAddress("http://localhost:8080/tm/ws/userService");
		UserService client = (UserService) factory.create();
		Customer customer = new Customer();
		customer.setAge(10);
		customer.setId("1");
		customer.setName("Dennis");
		customer.setBooksArray(new String[] {"JAVA", "C++", ".NET"});
		return client.sayHello("userID", customer);
	}

}

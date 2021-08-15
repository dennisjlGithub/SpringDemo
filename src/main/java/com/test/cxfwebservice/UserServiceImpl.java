package com.test.cxfwebservice;

import java.util.Arrays;

import javax.jws.WebService;

import org.springframework.stereotype.Component;

import com.test.bean.Customer;
import com.test.cxfwebservice.exception.EMMSException;
import com.test.cxfwebservice.vo.HASSDoubleBenefitRequestVO;
import com.test.cxfwebservice.vo.HASSDoubleBenefitResultVO;

/**
 * targetNamespace：目标命名控件，一般由接口所在包路径命名，不过是由里往外写.
 * 
 * @author DD
 *
 */

@Component
@WebService(endpointInterface = "com.test.cxfwebservice.UserService", serviceName = "userService"
		, targetNamespace = "cxfwebservice.test.com")
public class UserServiceImpl implements UserService {

	@Override
	public String sayHello(String userId, Customer customer) {
		return "say hello || userId = " + userId + " || userName = " + customer.getName() + " || booksArray = "
				+ Arrays.toString(customer.getBooksArray());
	}

	@Override
	public HASSDoubleBenefitResultVO hm06DoubleBenefitCheck(HASSDoubleBenefitRequestVO reqCtx)
			throws EMMSException, Exception {
		return new HASSDoubleBenefitResultVO();
	}

}
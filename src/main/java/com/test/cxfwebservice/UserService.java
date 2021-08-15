package com.test.cxfwebservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.test.bean.Customer;
import com.test.cxfwebservice.exception.EMMSException;
import com.test.cxfwebservice.vo.HASSDoubleBenefitRequestVO;
import com.test.cxfwebservice.vo.HASSDoubleBenefitResultVO;

/**
 * 这个接口，类似于普通接口，只是接口上方使用的是webservice注解。
 * 
 * @author DD
 *
 */

@WebService(targetNamespace = "cxfwebservice.test.com")
public interface UserService {

	@WebMethod
	public String sayHello(@WebParam(name = "userId") String userId, @WebParam(name = "customer") Customer customer);

	@WebMethod
	@WebResult(name = "hassDoubleBenefitResultVO")
	public HASSDoubleBenefitResultVO hm06DoubleBenefitCheck(
			@WebParam(name = "hassDoubleBenefitRequestVO") HASSDoubleBenefitRequestVO reqCtx)
			throws EMMSException, Exception;

}
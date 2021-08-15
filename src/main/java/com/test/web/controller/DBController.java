package com.test.web.controller;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.bean.CustomerVO;
import com.test.mapper.TestDAO;

/**
 * Spring Boot 会自动加载 spring.datasource.* 相关配置，数据源就会自动注入到 sqlSessionFactory 中，sqlSessionFactory 会自动注入到 Mapper 中，
 * 你一切都不用管了，直接拿起来使用就行了。
 * 
 * H2 console: http://localhost:8080/tm/h2-console
 *
 */
@RestController
@RequestMapping("/DBController")
public class DBController {

	@Autowired
	private TestDAO testDAO;
	
	@RequestMapping("/testDBConnection")
	public String testDBConnection() throws Exception {
		boolean accessdb = false;
		try {
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:file:D:\\H2\\testdb", "sa", "");
			Statement stmt = conn.createStatement();
			accessdb = stmt.execute("select * from Customer");
			conn.commit();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException ex) {
			System.out.println("ERROR: Class not found: " + ex.getMessage());

		}
		return String.valueOf(accessdb);
	}
	
	@RequestMapping("/getUsers")
	public String getUsers() throws Exception {
		List<CustomerVO> list = testDAO.getUsers();
		String result = "";
		for (CustomerVO vo : list) {
			result += vo.toString() + "<br/>";
		}
		return result;
	}
	
	@RequestMapping("/updateUser")
	public String updateUser() throws Exception {
		CustomerVO user = new CustomerVO();
		user.setSeq(1);
		user.setUserName("dennis");
		user.setModifyTime(new Date());
		user.setSalary(new BigDecimal("100000"));
		testDAO.updateUser(user);
		return "successfully";
	}
	
	@RequestMapping("/insertUser")
	public String insertUser() throws Exception {
		CustomerVO user = new CustomerVO();
		user.setIdNum("A123456");
		user.setUserName("dennis");
		user.setCreateTime(new Date());
		testDAO.insertUser(user);
		return "successfully";
	}
	
	@RequestMapping("/deleteUser")
	public String deleteUser(@RequestParam int seq) throws Exception {
		testDAO.deleteUser(seq);
		return "successfully";
	}
}

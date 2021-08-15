package com.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.test.bean.CustomerVO;

public interface TestDAO {

	@Select("SELECT * FROM Customer")
	@Results({
		@Result(property = "seq", column = "SEQUENCE"),
		@Result(property = "userName", column = "USER_NAME"),
		@Result(property = "sex", column = "USER_SEX"),
		@Result(property = "age", column = "USER_AGE"),
		@Result(property = "idNum", column = "USER_ID_NUM"),
		@Result(property = "phoneNum", column = "USER_PHONE_NUM"),
		@Result(property = "email", column = "USER_EMAIL"),
		@Result(property = "createTime", column = "CREATE_TIME"),
		@Result(property = "modifyTime", column = "MODIFY_TIME"),
		@Result(property = "state", column = "USER_STATE"),
		@Result(property = "salary", column = "USER_SALARY")
		})
	public List<CustomerVO> getUsers();
	
	@Update("UPDATE Customer SET USER_NAME = #{userName}, MODIFY_TIME = #{modifyTime} WHERE SEQUENCE = #{seq}")
	public void updateUser(CustomerVO user);
	
	@Insert("INSERT into Customer (USER_ID_NUM, USER_NAME, CREATE_TIME) VALUES(#{idNum}, #{userName}, #{createTime})")
	void insertUser(CustomerVO user);
	
	@Delete("DELETE FROM Customer WHERE SEQUENCE =#{seq}")
	void deleteUser(int seq);
}

package com.test.util;

import java.lang.reflect.Field;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.test.bean.CustomerVO;

public class DBUtil {
	
	public static void main(String[] args) {
		DBUtil.getResultsStr(CustomerVO.class);
	}
	
	public static void getResultsStr(Class<?> origin) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("@Results({\n");
        for (Field field : origin.getDeclaredFields()) {
            String property = field.getName();
            String column = new PropertyNamingStrategy.SnakeCaseStrategy().translate(field.getName()).toUpperCase();
            stringBuilder.append(String.format("@Result(property = \"%s\", column = \"%s\"),\n", property, column));
        }
        stringBuilder.append("})");
        System.out.println(stringBuilder.toString());
    }
	
}

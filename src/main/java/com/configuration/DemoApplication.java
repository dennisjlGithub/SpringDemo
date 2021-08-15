package com.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Start spring-boot with command line:  java -jar C:\Users\DD\git\demo\target\demo-0.0.1-SNAPSHOT.jar
 * Warnning: jar cannot support jsp pages.
 * 
 * curl can call HTTP:  
 * curl -XPOST http://localhost:8080/tm/JobLauncherController/fixLengthFileItemIOTest_Job -H "Content-Type: application/json" -d "{\"testID\":\"ID01\"}"
 *
 */
@SpringBootApplication(scanBasePackages = { "com.test", "com.configuration" })
//@EnableScheduling
@MapperScan(value = { "com.test.mapper" })
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

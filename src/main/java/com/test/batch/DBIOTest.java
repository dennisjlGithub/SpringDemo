package com.test.batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import com.test.bean.CustomerVO;

@Configuration
public class DBIOTest {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DataSource dataSource;
	
	private final static String writeSql = "UPDATE Customer set MODIFY_TIME=:modifyTime where SEQUENCE = :seq";

	private final static String readerSql = "select * from Customer order by USER_ID_NUM";

	@Bean
	public Job DBIOTest_Job() throws Exception {
		return jobBuilderFactory.get("DBIOTest_Job").start(DBIOTest_Step()).build();
	}

	@Bean
	public Step DBIOTest_Step() throws Exception {
		return stepBuilderFactory.get("DBIOTest_Step").<CustomerVO, CustomerVO>chunk(999999999).reader(DBIOTest_Reader())
				.processor(DBIOTest_Processor()).writer(DBIOTest_Writer()).build();
	}

	/**
	 * option 1: JdbcCursorItemReaderBuilder: 从数据库读取数据流标准方法. ResultSet一直都会指向结果集中的某一行数据， 
	 * 		使用next方法可以让游标跳转到下一行数据。 
	 * option 2: JdbcPagingItemReader: 相对于游标，还有一个办法是进行分页查询。分页查询意味着再进行批处理的过程中同一个SQL会多次执行。
	 * 		在联机型事物系统中分页查询常用于列表功能，每一次查询需要指定开始位置和结束位置。
	 */
	@Bean
	@StepScope
	public JdbcCursorItemReader<CustomerVO> DBIOTest_Reader() {
		JdbcCursorItemReader<CustomerVO> reader = new JdbcCursorItemReader<>();
		reader.setDataSource(dataSource);
		reader.setSql(readerSql);
		reader.setFetchSize(1); // 预通知JDBC驱动全量数据的个数
		reader.setMaxRows(0); // 设置ResultSet从数据库中读取记录的上限, zero means there is no limit.
		reader.setQueryTimeout(5 * 60); // 设置执行SQL语句的等待超时时间，单位秒。
		reader.setVerifyCursorPosition(true);
		reader.setRowMapper(new RowMapper<CustomerVO>() {
			@Override
			public CustomerVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				CustomerVO user = new CustomerVO();
				user.setSeq(rs.getInt(1));
				user.setUserName(rs.getString(2));
				user.setSex(rs.getString(3));
				user.setAge(rs.getInt(4));
				user.setIdNum(rs.getString(5));
				user.setPhoneNum(rs.getString(6));
				user.setEmail(rs.getString(7));
				user.setCreateTime(rs.getDate(8));
				user.setModifyTime(rs.getDate(9));
				user.setState(rs.getString(10));
				return user;
			}
		});
		return reader;
	}
	
	@Bean
	@StepScope
	public ItemProcessor<CustomerVO, CustomerVO> DBIOTest_Processor() {
		return new ItemProcessor<CustomerVO, CustomerVO>() {
			@Override
			public CustomerVO process(CustomerVO item) throws Exception {
				item.setModifyTime(new Date());
				return item;
			}
		};
	}

	@Bean
	@StepScope
	public JdbcBatchItemWriter<CustomerVO> DBIOTest_Writer() {
		JdbcBatchItemWriter<CustomerVO> writer = new JdbcBatchItemWriter<>();
		writer.setDataSource(dataSource);
		writer.setSql(writeSql);
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		writer.afterPropertiesSet();
		return writer;
	}

}

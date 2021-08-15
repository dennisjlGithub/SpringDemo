package com.test.batch;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.test.bean.CustomerVO;
import com.test.mapper.TestDAO;

@Configuration
public class ListItemReaderTest {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	TestDAO testDAO;

	@Bean
	public Job ListItemReaderTest_Job() {
		return jobBuilderFactory.get("ListItemReaderTest_Job").start(ListItemReaderTest_Step()).build();
	}

	@Bean
	public Step ListItemReaderTest_Step() {
		return stepBuilderFactory.get("ListItemReaderTest_Step").<CustomerVO, CustomerVO>chunk(20)
				.reader(ListItemReaderTest_Reader()).processor(ListItemReaderTest_Processor())
				.writer(ListItemReaderTest_Writer()).build();

	}

	@Bean
	@StepScope
	public ItemReader<CustomerVO> ListItemReaderTest_Reader() {
		return new ListItemReader<CustomerVO>(testDAO.getUsers());
	}

	@Bean
	@StepScope
	public ItemProcessor<CustomerVO, CustomerVO> ListItemReaderTest_Processor() {
		return new ItemProcessor<CustomerVO, CustomerVO>() {
			@Override
			public CustomerVO process(CustomerVO item) throws Exception {
				return item;
			}
		};
	}

	@Bean
	@StepScope
	public ItemWriter<CustomerVO> ListItemReaderTest_Writer() {
		return new ItemWriter<CustomerVO>() {
			@Override
			public void write(List<? extends CustomerVO> items) throws Exception {
				for (Object item : items) {
					System.out.println("writer -> " + item);
				}
			}
		};
	}
}

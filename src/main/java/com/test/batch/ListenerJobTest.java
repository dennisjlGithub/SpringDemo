package com.test.batch;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.test.batch.listener.MyChunkListener;
import com.test.batch.listener.MyItemProcessListener;
import com.test.batch.listener.MyItemReadListener;
import com.test.batch.listener.MyJobListener;
import com.test.batch.listener.MyStepExecutionListener;

@Configuration
public class ListenerJobTest {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job listenerJobTest_Job() {
		return jobBuilderFactory.get("listenerJobTest_Job")
				.start(listenerJobTest_Step())
				.listener(new MyJobListener())
				.build();
	}
	
	@Bean
	public Step listenerJobTest_Step() {
		return stepBuilderFactory.get("listenerJobTest_Step")
				.<String, String>chunk(2)
				.faultTolerant()
				.listener(new MyStepExecutionListener())
				.listener(new MyChunkListener())
				.listener(new MyItemProcessListener())
				.listener(new MyItemReadListener())
				.reader(listenerJobTest_Reader())
				.processor(listenerJobTest_Processor())
				.writer(listenerJobTest_Writer())
				.build();

	}

	@Bean
	public ItemReader<String> listenerJobTest_Reader() {
		return new ListItemReader<String>(Arrays.asList("aa", "bb", "cc"));
	}
	
	@Bean
	public ItemProcessor<String, String> listenerJobTest_Processor() {
		return new ItemProcessor<String, String>() {
			@Override
			public String process(String item) throws Exception {
				return item.toUpperCase();
			}
		};
	}

	@Bean
	public ItemWriter<String> listenerJobTest_Writer() {
		return new ItemWriter<String>() {
			@Override
			public void write(List<? extends String> items) throws Exception {
				for (Object item : items) {
					System.out.println("writer -> "+ item);
				}
			}
		};
	}
}

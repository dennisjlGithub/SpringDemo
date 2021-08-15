package com.test.batch;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

//@Configuration
public class ItemReaderJobTest {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<String, String>chunk(2)
				.reader(myItemReader())
				.writer(list -> {
					for (String item : list) {
						System.out.println("Item = " + item);
					}
				}).build();
	}
	
	@Bean
	public Job ItemReaderJob1() {
		// we can run flow or step.
		return jobBuilderFactory.get("ItemReaderJob1").start(step1()).build();
	}

	private MyItemReader myItemReader() {
		List<String> data = Arrays.asList("11", "22", "33", "44");
		return new MyItemReader(data);
	}
}

class MyItemReader implements ItemReader<String> {

	private Iterator<String> iterator;

	public MyItemReader(List<String> list) {
		this.iterator = list.iterator();
	}

	/**
	 * Read data one by one.
	 */
	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (iterator.hasNext())
			return iterator.next();
		else
			return null;
	}

}

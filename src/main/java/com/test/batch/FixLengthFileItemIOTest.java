package com.test.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.FormatterLineAggregator;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.validation.BindException;

import com.test.bean.Customer;

@Configuration
public class FixLengthFileItemIOTest {

	@Autowired
	JobBuilderFactory jobBuilderFactory;

	@Autowired
	StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job fixLengthFileItemIOTest_Job() throws Exception {
		return jobBuilderFactory.get("fixLengthFileItemIOTest_Job").start(fixLengthFileItemIOTest_Step()).build();
	}
	
	@Bean
	public Step fixLengthFileItemIOTest_Step() throws Exception {
		return stepBuilderFactory.get("fixLengthFileItemIOTest_Step")
				.<Customer, Customer>chunk(999999999)
				.reader(fixLengthFileItemIOTest_Reader())
				.processor(fixLengthFileItemIOTest_Processor())
				.writer(fixLengthFileItemIOTest_Writer())
				.taskExecutor(new SimpleAsyncTaskExecutor("FixLengthFileItemIOTest")) // Multi Thread Step
				.build();
	}

	@Bean
	@StepScope
	public FlatFileItemReader<Customer> fixLengthFileItemIOTest_Reader() {
		FlatFileItemReader<Customer> fileReader = new FlatFileItemReader<Customer>();
		fileReader.setResource(new ClassPathResource("si/customerInputFixLength.txt"));
		fileReader.setLinesToSkip(1); // bypass header.

		FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
		tokenizer.setNames(new String[] { "id", "name", "age" });
		tokenizer.setColumns(new Range[] { new Range(1, 4), new Range(5, 14), new Range(15, 17) });

		DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<Customer>();
		lineMapper.setLineTokenizer(tokenizer);
		lineMapper.setFieldSetMapper(new FieldSetMapper<Customer>() {
			@Override
			public Customer mapFieldSet(FieldSet fieldSet) throws BindException {
				Customer customer = new Customer();
				customer.setId(fieldSet.readString("id"));
				customer.setName(fieldSet.readString("name"));
				customer.setAge(fieldSet.readInt("age"));
				return customer;
			}
		});
		lineMapper.afterPropertiesSet();
		fileReader.setLineMapper(lineMapper);
		return fileReader;
	}
	
	@Bean
	@StepScope
	public ItemProcessor<Customer, Customer> fixLengthFileItemIOTest_Processor() {
		return new ItemProcessor<Customer, Customer>() {
			@Override
			public Customer process(Customer item) throws Exception {
				Customer cus = new Customer();
				cus.setName(item.getName().toUpperCase());
				cus.setId(item.getId());
				cus.setAge(item.getAge());
				return cus;
			}
		};
	}

	@Bean
	@StepScope
	public FlatFileItemWriter<Customer> fixLengthFileItemIOTest_Writer() throws Exception {
		FlatFileItemWriter<Customer> myWriter = new FlatFileItemWriter<Customer>();
		myWriter.setEncoding("UTF-8");
		myWriter.setResource(new FileSystemResource("D:\\Temp\\FixLengthFileItemIOTest_O.txt"));

		myWriter.setLineAggregator(new FormatterLineAggregator<Customer>() {
			{
				setFormat("%-4s%-10s%-3s");
				setFieldExtractor(new BeanWrapperFieldExtractor<Customer>() {
					{
						setNames(new String[] { "id", "name", "age" });
					}
				});
			}
		});
		myWriter.afterPropertiesSet();
		return myWriter;
	}
}

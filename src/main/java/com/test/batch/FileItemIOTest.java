package com.test.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.BindException;

import com.test.bean.Customer;

@Configuration
public class FileItemIOTest {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job fileItemIOTestJob() throws Exception {
		return jobBuilderFactory.get("fileItemIOTestJob").start(step1()).build();
	}

	public Step step1() throws Exception {
		return stepBuilderFactory.get("step1").<Customer, Customer>chunk(20).reader(myReader()).writer(myWriter())
				.build();
	}

	private FlatFileItemReader<Customer> myReader() {
		FlatFileItemReader<Customer> fileReader = new FlatFileItemReader<Customer>();
		fileReader.setResource(new ClassPathResource("si/customerInput.txt"));
		fileReader.setLinesToSkip(1); // bypass header.

		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames(new String[] { "id", "name", "age" });

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

	private FlatFileItemWriter<Customer> myWriter() throws Exception {
		FlatFileItemWriter<Customer> myWriter = new FlatFileItemWriter<Customer>();
		//fileWriter.setAppendAllowed(false);
		myWriter.setEncoding("UTF-8");
		myWriter.setResource(new ClassPathResource("si/customerOutput.txt"));  // be careful: the file output is target:/classes/... not the src:/
//		myWriter.setResource(new FileSystemResource("D:\\test.txt"));
		myWriter.setLineAggregator(new DelimitedLineAggregator<Customer>() {{
	        setDelimiter(",");
	        setFieldExtractor(new BeanWrapperFieldExtractor<Customer>() {{
	            setNames(new String[]{"id", "name", "age"});
	        }});
	    }});
		myWriter.afterPropertiesSet();
		return myWriter;
	}
}

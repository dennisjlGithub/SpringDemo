package com.test.batch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineCallbackHandler;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.FormatterLineAggregator;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.BindException;

import com.test.bean.Address;
import com.test.bean.IC;

/**
 * writer: FlatFileHeaderCallback, FlatFileFooterCallback??
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Configuration
public class MultipleRecordTypesIOTest {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	@StepScope
	public Step multipleRecordTypesIOTest_step() throws Exception {
		return stepBuilderFactory.get("multipleRecordTypesIOTest_step").chunk(20)
				.reader(multipleRecordTypesIOTest_Reader())
				.processor(multipleRecordTypesIOTest_Processor())
				.writer(multipleRecordTypesIOTest_Writer()).build();
	}

	@Bean
	@StepScope
	public FlatFileItemReader multipleRecordTypesIOTest_Reader() throws Exception {
		FlatFileItemReader fileReader = new FlatFileItemReader();
		fileReader.setResource(new ClassPathResource("si/customerInputComposite.txt"));
		fileReader.setLinesToSkip(1); // bypass header.

		FixedLengthTokenizer icTokenizer = new FixedLengthTokenizer();
		icTokenizer.setNames(new String[] { "type", "ictype", "icNum" });
		icTokenizer.setColumns(new Range[] { new Range(1, 1), new Range(2, 4), new Range(5, 13) });

		FixedLengthTokenizer addressTokenizer = new FixedLengthTokenizer();
		addressTokenizer.setNames(new String[] { "type", "estateCode", "blockCode", "flatNum" });
		addressTokenizer
				.setColumns(new Range[] { new Range(1, 1), new Range(2, 5), new Range(6, 8), new Range(9, 12) });

		Map<String, LineTokenizer> tokenizers = new HashMap<>(2);
		tokenizers.put("I*", icTokenizer);
		tokenizers.put("A*", addressTokenizer);

		Map<String, FieldSetMapper<?>> mappers = new HashMap<>(2);
		mappers.put("I*", multipleRecordTypesIOTest_ICFieldSetMapper());
		mappers.put("A*", multipleRecordTypesIOTest_AddressFieldSetMapper());

		PatternMatchingCompositeLineMapper lineMapper = new PatternMatchingCompositeLineMapper();
		lineMapper.setTokenizers(tokenizers);
		lineMapper.setFieldSetMappers(mappers);

		lineMapper.afterPropertiesSet();
		fileReader.setLineMapper(lineMapper);

		LineCallbackHandler skippedLineCallback = new LineCallbackHandler() {
			public void handleLine(String line) {
				System.out.println(line);
			}
		};
		fileReader.setSkippedLinesCallback(skippedLineCallback);

		return fileReader;
	}

	@Bean
	public FieldSetMapper<Address> multipleRecordTypesIOTest_AddressFieldSetMapper() {
		return new FieldSetMapper<Address>() {
			@Override
			public Address mapFieldSet(FieldSet fieldSet) throws BindException {
				Address obj = new Address();
				obj.setType(fieldSet.readString("type"));
				obj.setEstateCode(fieldSet.readString("estateCode"));
				obj.setBlockCode(fieldSet.readString("blockCode"));
				obj.setFlatNum(fieldSet.readString("flatNum"));
				return obj;
			}
		};
	}

	@Bean
	public FieldSetMapper<IC> multipleRecordTypesIOTest_ICFieldSetMapper() {
		return new FieldSetMapper<IC>() {
			@Override
			public IC mapFieldSet(FieldSet fieldSet) throws BindException {
				IC obj = new IC();
				obj.setType(fieldSet.readString("type"));
				obj.setIctype(fieldSet.readString("ictype"));
				obj.setIcNum(fieldSet.readString("icNum"));
				return obj;
			}
		};
	}

	@Bean
	public ItemProcessor<IC, IC> multipleRecordTypesIOTest_ICProcessor() {
		return new ItemProcessor<IC, IC>() {
			@Override
			public IC process(IC item) throws Exception {
				if (item.getType().equals("I")) {
					return item;
				} else
					return null; // return null means bypass this item, process next item.
			}
		};
	}

	@Bean
	public ItemProcessor<Address, Address> multipleRecordTypesIOTest_AddressProcessor() {
		return new ItemProcessor<Address, Address>() {
			@Override
			public Address process(Address item) throws Exception {
				if (item.getType().equals("A")) {
					return item;
				} else
					return null; // return null means bypass this item, process next item.
			}
		};
	}

	@Bean
	public CompositeItemProcessor multipleRecordTypesIOTest_Processor() {
		CompositeItemProcessor processor = new CompositeItemProcessor();
		List<ItemProcessor> delegates = new ArrayList();
		delegates.add(multipleRecordTypesIOTest_ICProcessor());
		delegates.add(multipleRecordTypesIOTest_AddressProcessor());
		processor.setDelegates(delegates);
		return processor;
	}

	@Bean
	public CompositeItemWriter multipleRecordTypesIOTest_Writer() throws Exception {
		CompositeItemWriter myWriter = new CompositeItemWriter();
		myWriter.setDelegates(Arrays.asList(multipleRecordTypesIOTest_AddressWriter()));
		myWriter.afterPropertiesSet();
		return myWriter;
	}

	@Bean
	public FlatFileItemWriter<IC> multipleRecordTypesIOTest_ICWriter() throws Exception {
		FlatFileItemWriter<IC> myWriter = new FlatFileItemWriter<>();
		myWriter.setEncoding("UTF-8");
		myWriter.setResource(new ClassPathResource("si/customerOutput.txt"));
		myWriter.setLineAggregator(new FormatterLineAggregator<IC>() {
			{
				setFormat("%-1s%-3s%-9s");
				setFieldExtractor(new BeanWrapperFieldExtractor<IC>() {
					{
						setNames(new String[] { "type", "ictype", "icNum" });
					}
				});
			}
		});
		myWriter.afterPropertiesSet();
		return myWriter;
	}

	@Bean
	public FlatFileItemWriter<Address> multipleRecordTypesIOTest_AddressWriter() throws Exception {
		FlatFileItemWriter<Address> myWriter = new FlatFileItemWriter<>();
		myWriter.setEncoding("UTF-8");
		myWriter.setResource(new ClassPathResource("si/customerOutput.txt"));
		myWriter.setLineAggregator(new FormatterLineAggregator<Address>() {
			{
				setFormat("%-1s%-4s%-3s%-4s");
				setFieldExtractor(new BeanWrapperFieldExtractor<Address>() {
					{
						setNames(new String[] { "type", "estateCode", "blockCode", "flatNum" });
					}
				});
			}
		});
		myWriter.afterPropertiesSet();
		return myWriter;
	}

	@Bean
	public Job multipleRecordTypesIOTest_Job() throws Exception {
		return jobBuilderFactory.get("multipleRecordTypesIOTest_Job").start(multipleRecordTypesIOTest_step()).build();
	}
}

package com.test.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

//@Configuration
public class DeciderTest {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("DeciderTest -> step1");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("DeciderTest -> step2");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Step step3() {
		return stepBuilderFactory.get("step3").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("DeciderTest -> step3");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public JobExecutionDecider myDecider() {
		return new MyDecider();
	}
	
	@Bean
	public Job DeciderTestJob() {
		// we can run flow or step.
		return jobBuilderFactory.get("DeciderTestJob")
				.start(step1())
				.next(myDecider())
				.from(myDecider()).on("even").to(step2())
				.from(myDecider()).on("odd").to(step3())
				.from(step3()).on("*").to(myDecider()) // 再去到 .next(myDecider())
				.end().build();
	}
}

class MyDecider implements JobExecutionDecider {
	
	private int count;

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
		count ++;
		if (count%2 == 0)
			return new FlowExecutionStatus("even");
		else
			return new FlowExecutionStatus("odd");
	}
	
}

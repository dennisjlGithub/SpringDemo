package com.test.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * Flow contains step, and could be used for other jobs.
 */
//@Configuration
public class JobFlowTest {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step flowStep1() {
		return stepBuilderFactory.get("flowStep1").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Start JobFlowTest -> flowStep1");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Step flowStep2() {
		return stepBuilderFactory.get("flowStep2").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Start JobFlowTest -> flowStep2");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Step flowStep3() {
		return stepBuilderFactory.get("flowStep3").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Start JobFlowTest -> flowStep3");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Flow flow1() {
		return new FlowBuilder<Flow>("flow1").start(flowStep1()).next(flowStep2()).build();
	}
	
	@Bean
	public Job TestFLowJob() {
		// we can run flow or step.
		return jobBuilderFactory.get("TestFLowJob").start(flow1()).next(flowStep3()).end().build();
	}
}

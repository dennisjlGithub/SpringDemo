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
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * Parallel run the jobs.
 *
 */
@Configuration
public class ParallelStepTest {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job parallelStepTest_Job() {
		return jobBuilderFactory.get("parallelStepTest_Job")
				.start(splitFlow())
				.next(step4())
				.end().build();
	}
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Start SplitJob -> step1");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Start SplitJob -> step2");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Step step3() {
		return stepBuilderFactory.get("step3").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Start SplitJob -> step3");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Step step4() {
		return stepBuilderFactory.get("step4").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Start SplitJob -> step4");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Flow flow1() {
		return new FlowBuilder<Flow>("flow1").start(step1()).next(step2()).build();
	}
	
	@Bean
	public Flow flow2() {
		return new FlowBuilder<Flow>("flow2").start(step3()).build();
	}
	
	@Bean
	public Flow splitFlow() {
	    return new FlowBuilder<Flow>("splitFlow")
	        .split(new SimpleAsyncTaskExecutor())
	        .add(flow1(), flow2())
	        .build();
	}
}

package com.test.batch;

import java.util.Map;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.test.batch.listener.MyJobListener;

//@Configuration
public class ParameterTest implements StepExecutionListener{

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	private Map<String, JobParameter> parameters;
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
			.listener(this)
			.tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Use listener to get the parameters -> " + parameters.get("jobID"));
				// or 
				System.out.println("The other way = " +
				contribution.getStepExecution().getJobExecution().getJobParameters().getParameters().get("jobID"));
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Job parameterTestJob1() {
		return jobBuilderFactory.get("parameterTest2")
				.start(step1())
				.listener(new MyJobListener())
				.build();
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		parameters = stepExecution.getJobParameters().getParameters();
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		return null;
	}
}

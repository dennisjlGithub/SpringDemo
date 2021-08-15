package com.test.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 需要在application.properties指定 spring.batch.job.names=parentJobTest1
 *
 */
//@Configuration
public class ParentJobTest {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private Job ChildJobTest1;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	private Step childJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new JobStepBuilder(new StepBuilder("childJobTest1"))
				.job(ChildJobTest1)
				.launcher(jobLauncher)
				.repository(jobRepository)
				.transactionManager(transactionManager)
				.build();
	}
	
	@Bean
	public Job ParentJobTest1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return jobBuilderFactory.get("parentJobTest1")
				.start(childJob(jobRepository, transactionManager))
				.build();
	}
}

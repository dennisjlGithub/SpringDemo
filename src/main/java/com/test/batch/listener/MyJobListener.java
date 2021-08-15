package com.test.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class MyJobListener implements JobExecutionListener{

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("MyJobListener -> beforeJob: " + jobExecution.getJobInstance().getJobName());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		System.out.println("MyJobListener -> afterJob: " + jobExecution.getJobInstance().getJobName());
	}

}

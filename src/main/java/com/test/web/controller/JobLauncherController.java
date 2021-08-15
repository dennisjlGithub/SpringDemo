package com.test.web.controller;

import java.util.Date;

import javax.batch.runtime.BatchStatus;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/JobLauncherController")
public class JobLauncherController {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job fileItemIOTestJob;

	@Autowired
	private Job multipleRecordTypesIOTest_Job;
	
	@Autowired
	private Job fixLengthFileItemIOTest_Job;
	
	@Autowired
	private Job listenerJobTest_Job;
	
	@Autowired
	private Job parallelStepTest_Job;
	
	@Autowired
	private Job ListItemReaderTest_Job;
	
	@Autowired
	private Job DBIOTest_Job;
	
	@RequestMapping("/ListItemReaderTest_Job")
	public String ListItemReaderTest_Job() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder().addDate("date", new Date()).toJobParameters();
		return jobLauncher.run(ListItemReaderTest_Job, jobParameters).getStatus().toString();
	}
	
	@RequestMapping("/DBIOTest_Job")
	public String DBIOTest_Job() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder().addDate("date", new Date()).toJobParameters();
		return jobLauncher.run(DBIOTest_Job, jobParameters).getStatus().toString();
	}
	
	@RequestMapping("/listenerJobTest_Job")
	public String listenerJobTest_Job() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder().addDate("date", new Date()).toJobParameters();
		return jobLauncher.run(listenerJobTest_Job, jobParameters).getStatus().toString();
	}
	
	@RequestMapping("/fileItemIOTestJob")
	public String fileItemIOTestJob() throws Exception {
		String result = null;
		try {
			JobParametersBuilder jb = new JobParametersBuilder();
			jb.addDate("date", new Date());
			result = jobLauncher.run(fileItemIOTestJob, jb.toJobParameters()).getStatus().toString();
		}catch (Exception e) {
			e.printStackTrace();
			return BatchStatus.FAILED.toString();
		}
		return result;
	}
	
	@RequestMapping("/fixLengthFileItemIOTest_Job")
	public String fixLengthFileItemIOTest_Job(@RequestBody String jSonStr) throws Exception {
		String result = null;
		try {
			System.out.println("params = " + jSonStr);
			JobParametersBuilder jb = new JobParametersBuilder();
			jb.addString("params", jSonStr);
			jb.addDate("date", new Date());
			result = jobLauncher.run(fixLengthFileItemIOTest_Job, jb.toJobParameters()).getStatus().toString();
		}catch (Exception e) {
			e.printStackTrace();
			return BatchStatus.FAILED.toString();
		}
		return result;
	}
	
	@RequestMapping("/multipleRecordTypesIOTest_Job")
	public String multipleRecordTypesIOTest_Job() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder().addDate("date", new Date()).toJobParameters();
		return jobLauncher.run(multipleRecordTypesIOTest_Job, jobParameters).getStatus().toString();
	}
	
	@RequestMapping("/parallelStepTest_Job")
	public String parallelStepTest_Job() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder().addDate("date", new Date()).toJobParameters();
		return jobLauncher.run(parallelStepTest_Job, jobParameters).getStatus().toString();
	}
}

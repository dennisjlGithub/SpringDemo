package com.test.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * spring batch schema: https://github.com/spring-projects/spring-batch/tree/master/spring-batch-core/src/main/resources/org/springframework/batch/core
 * 
 * Spring Batch 批处理原则与建议:
 * 1. 通常情况下，批处理的过程对系统和架构的设计要够要求比较高，因此尽可能的使用通用架构来处理批量数据处理，降低问题发生的可能性。
 * Spring Batch是一个是一个轻量级的框架，适用于处理一些灵活并没有到海量的数据。
 * 2. 批处理应该尽可能的简单，尽量避免在单个批处理中去执行过于复杂的任务。我们可以将任务分成多个批处理或者多个步骤去实现。
 * 3. 保证数据处理和物理数据紧密相连。笼统的说就是我们在处理数据的过程中有很多步骤，在某些步骤执行完时应该就写入数据，而不是等所有都处理完。
 * 4. 尽可能减少系统资源的使用、尤其是耗费大量资源的IO以及跨服务器引用，尽量分配好数据处理的批次。
 * 5. 定期分析系统的IO使用情况、SQL语句的执行情况等，尽可能的减少不必要的IO操作。优化的原则有：
 * 	 尽量在一次事物中对同一数据进行读取或写缓存。
 *   一次事物中，尽可能在开始就读取所有需要使用的数据。
 *   优化索引，观察SQL的执行情况，尽量使用主键索引，尽量避免全表扫描或过多的索引扫描。
 *   SQL中的where尽可能通过主键查询。
 * 6. 不要在批处理中对相同的数据执行2次相同的操作。
 * 7. 对于批处理程序而言应该在批处理启动之前就分配足够的内存，以免处理的过程中去重新申请新的内存页。
 * 8. 对数据的完整性应该从最差的角度来考虑，每一步的处理都应该建立完备的数据校验。
 * 9. 对于数据的总量我们应该有一个和数据记录在数据结构的某个字段 上。
 * 10. 所有的批处理系统都需要进行压力测试。
 * 11. 如果整个批处理的过程是基于文件系统，在处理的过程中请切记完成文件的备份以及文件内容的校验。
 * 
 * Spring Batch提供了2种执行方式：命令行方式或Java内嵌方式(Running Jobs from within a Web Container)
 * 1. 命令行方式是直到需要执行批处理任务的时候才启动程序, 从命令行来启动job，会为每一个job初始化一个JVM，因此每个job会有一个自己的 JobLauncher.
 * CommandLineJobRunner
 * 
 * 2. 内嵌方式是结合Web工程或其他外部化框架来使用。从web容器的HttpRequest来启动job，一般只是用一个 JobLauncher 来异步启动job，
 * 		http请求会调用这个 JobLauncher 来启动它们需要的job。许多例子都表明，从HttpRequest启动是一个更好的选择.
 * 		commandline> curl http://localhost:8080/tm/JobLauncherController/fixLengthFileItemIOTest_Job
 * SpringApplication::callRunner -> JobLauncherCommandLineRunner::execute
 * 
 * JobLauncher: 为Job的启动运行提供了一个边界的入口，在启动Job的同时还可以定制JobParameters.  一个Job对应一个启动的JobLauncher.
 * 
 * 在Job或Step的任何位置，都可以获取到统一配置的数据: @Value("${input.file.name}"). 此外，也可以从JobParameters从获取到Job运行的上下文参数.
 * 
 * <Job> 代表着一个任务，包含: 1. Job的名称; 2.定义和排序Step执行实例; 3.标记每个Step是否可以重启。
 * 一个Job与一个或者多个JobInstance相关联，而一个JobInstance又与一个或者多个JobExecution相关联.
 * 考虑到任务可能不是只执行一次就再也不执行了，更多的情况可能是定时任务，如每天执行一次，每个星期执行一次等等，那么为了区分每次执行的任务，框架使用了JobInstance。
 * 如果Job是一个EndOfDay（每天最后时刻执行的任务），那么其中一个JobInstance就代表着2007年5月5日那天执行的任务实例。
 * 框架通过在执行JobLauncher.run(Job, JobParameters)方法时传入的JobParameters来区分是哪一天的任务。
 * 执行的任务可能不会一次就执行完成，比如中途被停止，或者出现异常导致中断，需要多执行几次才能完成，所以框架使用了JobExecution来表示每次执行的任务。
 * 
 * Job 控制Step执行流程: 
 * .start(step1()).on("FAILED").end() //执行失败直接退出
 * .next() //顺序执行.
 * .from(step1()).on("*").to(step2()) //默认情况下执行 Step2
 * .from(stepA()).on("FAILED").to(stepC())  //当返回的ExitStatus为"FAILED"时，执行stepC
 * .from(step1()).on("COMPLETED WITH SKIPS").to(errorPrint1()) //有跳过元素执行 errorPrint1()
 * .incrementer(new RunIdIncrementer()) // 使每个job的运行id都唯一
 * Step的退出机制: 
 * .end(): 这个时候，BatchStatus=COMPLETED、ExitStatus=COMPLETED，表示成功执行。
 * .fail(): 这个时候，BatchStatus=FAILED、ExitStatus=EARLY TERMINATION，表示执行失败。
 * .stopAndRestart(): 在指定的节点退出，退出后下次重启会从中断的点继续执行。中断的作用是某些批处理到某个步骤后需要人工干预，当干预完之后又接着处理.
 * 可以直接进行编码来控制Step之间的扭转，Spring Batch提供了JobExecutionDecider接口来协助分支管理.
 * 
 * Spring Batch有以下四种模式:
 * Multi-threaded Step
 * Parallel Step
 * Remote Chunking of Step
 * Partitioning Step
 * 
 * JobInstance是指批处理作业运行的实例. 例如一个批处理必须在每天执行一次.
 * 
 * JobParameters: 一个JobParameters对象中包含了一系列Job运行相关的参数，这些参数可以用于参考或者用于实际的业务使用。
 * 可以简单的认为一个JobInstance的标识就是Job + JobParameters。如果同一个job 启动参数有变化，会重新创建一个job实例。
 * 
 * JobExecution: 可以理解为单次运行Job的容器。一次JobInstance执行的结果可能是成功、也可能是失败。但是对于Spring Batch框架而言，
 * 只有返回运行成功才会视为完成一次批处理。例如2019-05-01执行了一次JobInstance，但是执行的过程失败，因此第二次还会有一个“相同的”的JobInstance被执行。
 * Job用于定义批处理如何执行，JobInstance纯粹的就是一个处理对象，把所有的运行内容和信息组织在一起，主要是为了当面临问题时定义正确的重启参数。
 * 而JobExecution是运行时的“容器”，记录动态运行时的各种属性和上线文。(可以看数据库.)
 * 
 * 一个Job任务可以分为几个Step步骤，与JobExection相同，每次执行Step的时候使用StepExecution来表示执行的步骤。
 * 每个Job可以视作由一个或多个多个Step组成。
 * 
 * <Step> 批处理重复运行的最小单元，它按照顺序定义了一次执行的必要过程。与JobExecution的概念类似，Step也有特定的StepExecution. (可以看数据库.)
 * 每一个Step还包含着一个ItemReader、ItemProcessor、ItemWriter.
 * ItemReader代表着读操作, 框架已经提供了多种ItemReader接口的实现类，包括对文本文件、XML文件、数据库、JMS消息等读的处理，当然我们也可以自己实现该接口。
 * ItemProcessor代表着处理操作.
 * ItemWriter代表着写操作.
 * Item：条目。一条数据记录。
 * Chunk：Item集合。它给定数量Item的集合，可以定义对Chunk的读操作、处理操作、写操作，提交间隔等。
 * .startLimit(1): 配置Step重启: 可以通过设置来限定某个Step重启的次数。当设置为1时候表示仅仅运行一次，而出现重启时将不再执行.
 * .allowStartIfComplete(true): 告知框架每次重启该Step都要执行. 在单个JobInstance的上下文中，如果某个Step已经处理完毕（COMPLETED）
 * 			那么在默认情况下重启之后这个Step并不会再执行.
 * .faultTolerant(): 配置跳过机制. 
 * .skip(FlatFileParseException.class): 单个记录时中出现FlatFileParseException异常并不应该停止任务，而应该跳过继续处理下一条数据.
 * .noSkip(FileNotFoundException.class): 指定某些异常不能跳过。
 * .skipLimit(10): 当跳过的次数超过数值时则会导致整个Step失败.
 * .retryLimit(3), .retry(DeadlockLoserDataAccessException.class): 出个异常之后并不希望他立即跳过或者停止运行，而是希望可以多次尝试执行直到失败.
 * .noRollback(ValidationException.class): 默认情况下，只要从Writer抛出一个异常都会导致事物回滚。noRollback提供了Writer抛出一个异常不必进行事物回滚的异常配置.
 * 
 * StepExecution表示单次执行Step的容器，每次Step执行时都会有一个新的StepExecution被创建。与JobExecution不同的是，当某个Step执行失败后默认并不会重新执行。
 * 
 * ExecutionContext: 可以简单的认为ExecutionContext提供了一个Key/Value机制，在StepExecution和JobExecution对象的任何位置都可以获取到
 * ExecutionContext中的任何数据。最有价值的作用是记录数据的执行位置，以便发生重启时候从对应的位置继续执行.
 * ExecutionContext是根据JobInstance进行管理的，因此只要是相同的实例都会具备相同的ExecutionContext（无论是否停止）。
 * 此外通过以下方法都可以获得一个ExecutionContext：
 * ExecutionContext ecStep = stepExecution.getExecutionContext();
 * ExecutionContext ecJob = jobExecution.getExecutionContext();
 * 但是这2个ExecutionContext并不相同，前者是在一个Step中每次Commit数据之间共享，后者是在Step与Step之间共享。
 * 
 * JobRepository是对象实例的持久化机制。
 * 在BatchConfigurer中的setIsolationLevelForCreate方法中可以指定事物的隔离等级. factory.setIsolationLevelForCreate("ISOLATION_REPEATABLE_READ")
 * Spring Batch支持将运行时的状态数据（元数据）仅保存在内存中。重载JobRepository不设置DataSource即可. 需要注意的是，内存级存储无法满足分布式系统。
 * 内存中的存储库:
 * 在某些情况下，您可能不想将域对象持久保存到数据库中。原因之一可能是速度。在每个提交点存储域对象会花费额外的时间。
 * 另一个原因可能是您不需要为特定工作保留状态。因此，Spring 批处理提供了作业存储库的内存 Map 版本：
 * XML Configuration
 * <bean id="jobRepository"
 *   class="org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean">
 *     <property name="transactionManager" ref="transactionManager"/>
 * </bean>
 * Java Configuration
 * // This would reside in your BatchConfigurer implementation
 * @Override
 * protected JobRepository createJobRepository() throws Exception {
 *     JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
 *     factory.setDataSource(dataSource);
 *     factory.setTransactionManager(transactionManager);
 *     factory.setIsolationLevelForCreate("ISOLATION_REPEATABLE_READ");
 *     return factory.getObject();
 * }
 * 请注意，内存中的存储库是易失性的，因此不允许在 JVM 实例之间重新启动。它还不能保证同时启动两个具有相同参数的作业实例，
 * 并且不适合在多线程 Job 或本地分区Step中使用。因此，在需要这些功能的地方，请使用存储库的数据库版本。
 * 出于测试目的，许多人认为ResourcelessTransactionManager有用。
 * 
 * Spring Batch提供了2种Step的处理方式：1）面向分片的ChunkStep，2）面向过程的TaskletStep.
 * ChunkStep: Chunk-oriented processing
 * 每条记录处理完毕之后马上提交事物反而会导致IO的巨大压力。因此Spring Batch提供了数据处理的Chunk功能。
 * 设置了Chunk，一个job会从Read开始，然后交由给Processor处理。处理完毕后会进行聚合，待聚合到一定的数量的数据之后一次性调用Write将数据提交到物理数据库。
 * transactionManager：使用默认的 PlatformTransactionManager 对事物进行管理。当配置好事物之后Spring Batch会自动对事物进行管理，无需开发人员显示操作。
 * chunk：指定一次性数据提交的记录数，因为任务是基于Step分次处理的，当累计到chunk配置的次数则进行一次提交。如果没有明确的评估目标，size设置为10~20较为合适。
 * TaskletStep:
 * ChunkStep并不是Step的唯一执行方式. 比如用数据库的存储过程来处理数据，这个时候使用标准的Reader、Processor、Writer会很奇怪，针对这些情况框架提供了TaskletStep.
 * TaskletStep是一个非常简单的接口，仅有一个方法 execute。TaskletStep会反复的调用这个方法直到获取一个RepeatStatus.FINISHED或者抛出一个异常。
 * 所有的Tasklet调用都会包装在一个事物中。
 * 
 * <持久化数据库>
 * Spring Batch支持不使用任何持久化数据库，仅仅将数据放到内存中，不设置DataSource即可.
 * BATCH_JOB_INSTANCE: 用于记录JobInstance.
 * 		JOB_INSTANCE_ID: 主键与单个JobInstance相关。当获取到某个JobInstance实例后，通过getId方法可以获取到此数据
 * 		JOB_KEY: JobParameters的序列化数值。在数据批处理概念中介绍了一个JobInstance相当于Job+JobParameters。他用于标记同一个Job不同的实例.
 * BATCH_JOB_EXECUTION_PARAMS: 对应的是JobParameters对象。其核心功能是存储Key-Value结构的各种状态数值。
 * 		JOB_EXECUTION_ID: 与BATCH_JOB_EXECUTION表关联的外键
 * BATCH_JOB_EXECUTION: 每当运行一个Job都会产生一个新的JobExecution，对应的在表中都会新增一行数据。
 * 		JOB_INSTANCE_ID: 关联到JobInstace的外键.
 * 		STATUS: JobExecute的运行状态:COMPLETED、STARTED或者其他状态。此数值对应Java中BatchStatus枚举值
 * 		EXIT_CODE: obExecute执行完毕之后的退出返回值.
 * BATCH_STEP_EXECUTION: 结构和BATCH_JOB_EXECUTION基本相似.
 * 		COMMIT_COUNT: 事物提交的次数.
 * 		READ_COUNT: 读取数据的次数.
 * 		ROLLBACK_COUNT: 回滚的次数.
 * BATCH_JOB_EXECUTION_CONTEXT: 记录所有与Job相关的ExecutionContext信息.
 * BATCH_STEP_EXECUTION_CONTEXT: Step中ExecutionContext相关的数据表, 结构与BATCH_JOB_EXECUTION_CONTEXT完全一样.
 * 
 * <文件数据读取>
 * FlatFileItemReader: 文件读取. 文件往往需要使用FTP等方式从其他位置获取。
 * 		如何迁移文件已经超出了Spring Batch框架的范围，在Spring的体系中可以参考Spring Integration项目.
 * Resource resource = new FileSystemResource("resources/trades.csv");
 * LineMapper: 传递一行字符串以及行号给LineMapper::mapLine，方法处理后返回一个映射的对象.
 * LineTokenizer: 将一行数据转换为一个FieldSet结构. DelimitedLineTokenizer, FixedLengthTokenizer, PatternMatchingCompositeLineTokenizer.
 * FieldSetMapper:
 * 
 * <文件数据输出>
 * 将数据写入到文件与读取的过程正好相反：将对象转换为字符串。
 * LineAggregator: 将对象转换为字符串. PassThroughLineAggregator, DelimitedLineAggregator, FieldExtractor
 * FlatFileWriter: 文件写入.
 * 
 * <Databse数据读取>
 * JdbcCursorItemReader: 它使用JdbcTemplate中的DataSource控制ResultSet,其过程是将ResultSet的每行数据转换为所需要的实体类。
 * HibernateCursorItemReader: 在Java体系中数据库操作常见的规范有JPA或ORM，HibernateCursorItemReader来实现HibernateTemplate,通过Hibernate框架进行游标控制。
 * StoredProcedureItemReader: 存储过程是在同一个数据库中处理大量数据的常用方法.
 * JdbcPagingItemReader: 分页查询.
 * 
 */
//@Configuration
public class JobTest {

	// inject job factory，for creating job
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	// inject Step factory, for creating step
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Start JobTest1 -> step1");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Start JobTest1 -> step2");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Step step3() {
		return stepBuilderFactory.get("step3").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Start JobTest1 -> step3");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Job Job1() {
		return jobBuilderFactory.get("job1")
				.incrementer(new RunIdIncrementer())
				.start(step1()).next(step2()).next(step3()).build();
//				.start(step1())
//				.on("COMPLETED").to(step2())
//				.from(step2()).on("COMPLETED").stopAndRestart(step2()).on("COMPLETED").to(step3())
//				.from(step3()).end().build();
	}
}

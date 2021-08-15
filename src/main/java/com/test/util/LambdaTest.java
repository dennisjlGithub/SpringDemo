package com.test.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 为引入Lambda表达式，Java8新增了java.util.funcion包，里面包含常用的函数接口，这是Lambda表达式的基础.
 * 能够使用Lambda的依据是必须有相应的函数接口（函数接口，是指内部只有一个抽象方法的接口）。
 * 这一点跟Java是强类型语言吻合，也就是说你并不能在代码的任何地方任性的写Lambda表达式。实际上Lambda的类型就是对应函数接口的类型。
 * 
 * <Stream> Java函数式编程的主角. stream跟函数接口关系非常紧密，没有函数接口stream就无法工作。
 * 1. 代码简洁函数式编程写出的代码简洁且意图明确，使用stream接口让你从此告别for循环。
 * 2. 多核友好，Java函数式编程使得编写并行程序从未如此简单，你需要的全部就是调用一下parallel()方法。
 * 4种stream接口继承自BaseStream:
 * IntStream, LongStream, DoubleStream对应三种基本类型（int, long, double），Stream对应所有剩余类型的stream视图。
 * 
 * 
 * stream和collections有以下不同：
 * 无存储。stream不是一种数据结构，它只是某种数据源的一个视图，数据源可以是一个数组，Java容器或I/O channel等。
 * 为函数式编程而生。对stream的任何修改都不会修改背后的数据源，比如对stream执行过滤操作并不会删除被过滤的元素，而是会产生一个不包含被过滤元素的新stream。
 * 惰式执行。stream上的操作并不会立即执行，只有等到用户真正需要结果的时候才会执行。
 * 可消费性。stream只能被“消费”一次，一旦遍历过就会失效，就像容器的迭代器那样，想要再次遍历必须重新生成。
 *
 * <方法引用>（method references）: 如果Lambda表达式的全部内容就是调用一个已有的方法，那么可以用方法引用来替代Lambda表达式。
 * 方法引用可以细分为四类: 
 * 		引用静态方法: Integer::sum
 * 		引用某个对象的方法: list::add
 * 		引用某个类的方法: String::length
 * 		引用构造方法: HashMap::new
 * 
 * <Collector> 收集器. 是为Stream.collect()方法量身打造的工具接口（类）.
 * 
 * <Stream操作分类汇总>  (区分中间操作和结束操作最简单的方法，就是看方法的返回值，返回值为stream的大都是中间操作，否则是结束操作。)
 * 中间操作(Intermediate operations) : 中间操作总是会惰式执行，调用中间操作只会生成一个标记了该操作的新stream，仅此而已。
 * 		无状态(Stateless): 元素的处理不受前面元素的影响
 * 			unordered() filter() map() mapToInt() mapToLong() mapToDouble() flatMap() flatMapToInt() flatMapToLong() flatMapToDouble() peek()
 * 		有状态(Stateful): 中间操作必须等到所有元素处理之后才知道最终结果
 * 			distinct() sorted() sorted() limit() skip()
 * 结束操作(Terminal operations): 触发实际计算，计算发生时会把所有中间操作积攒的操作以pipeline的方式执行，这样可以减少迭代次数。计算完成之后stream就会失效。
 * 		非短路操作: 逆.短路
 * 			forEach() forEachOrdered() toArray() reduce() collect() max() min() count()
 * 		短路操作(short-circuiting): 不用处理完全部元素就可以返回结果, 比如找到第一个满足条件的元素。
 * 			anyMatch() allMatch() noneMatch() findFirst() findAny()
 */
public class LambdaTest {
	
	public static void main(String[] args) {
		LambdaTest test = new LambdaTest();
		
		
		System.out.println("========== Lambda Test ===========");
		test.lambdaInterface();
		test.sort();
		test.listForEach();
		test.removeIf();
		test.replaceAll();
		test.spliterator();
		test.mapForEach();
		test.getOrDefault();
		test.putIfAbsent();
		test.remove();
		test.replace();
		test.mapReplaceAll();
		test.mapMerge();
		test.mapCompute();
		System.out.println("\n========== Stream Test ===========");
		test.forEach();
		test.filter();
		test.distinct();
		test.sorted();
		test.streamMap();
		test.flatMap();
		test.reduce();
		test.collect();
		test.pipelines();
		
	}

	public void startThread() {
		System.out.println("\n==== startThread() ====");
		new Thread(() -> System.out.println("Thread run()")).start();
	}

	public void lambdaInterface() {
		System.out.println("\n==== lambdaInterface() ====");
		LambdaInterface<String> test = str -> System.out.println(str);
		test.accept("test LambdaInterface");
	}

	/**
	 * 方法签名为void sort(Comparator<? super E> c)，该方法根据c指定的比较规则对容器元素进行排序。
	 * Comparator接口我们并不陌生，其中有一个方法int compare(T o1, T o2)需要实现，显然该接口是个函数接口。
	 */
	public void sort() {
		System.out.println("\n==== sort() ====");
		List<String> list = Arrays.asList("I", "love", "you", "too");
		list.sort((str1, str2) -> str1.length()-str2.length());
		System.out.println(list.toString());
	}

	public void listForEach() {
		System.out.println("\n==== forEach() ====");
		ArrayList<String> list = new ArrayList<>(Arrays.asList("I", "love", "you", "too"));
		list.forEach(str -> {
			if (str.length() > 3)
				System.out.println(str);
		});
	}

	/**
	 * 删除容器中所有满足filter指定条件的元素. 该方法签名为boolean removeIf(Predicate<? super E> filter)
	 * Predicate是一个函数接口，里面只有一个待实现方法boolean test(T t)，
	 * 同样的这个方法的名字根本不重要，因为用的时候不需要书写这个名字。
	 */
	public void removeIf() {
		System.out.println("\n==== removeIf() ====");
		ArrayList<String> list = new ArrayList<>(Arrays.asList("I", "love", "you", "too"));
		list.removeIf(str -> str.length() > 3); // 删除长度大于3的元素
		System.out.println(list);
	}

	/**
	 * 该方法签名为void replaceAll(UnaryOperator<E> operator)，作用是对每个元素执行operator指定的操作，
	 * 并用操作结果来替换原来的元素。其中UnaryOperator是一个函数接口，里面只有一个待实现函数T apply(T t)。
	 */
	public void replaceAll() {
		System.out.println("\n==== replaceAll() ====");
		ArrayList<String> list = new ArrayList<>(Arrays.asList("I", "love", "you", "too"));
		list.replaceAll(str -> {
			if (str.length() > 3)
				return str.toUpperCase();
			return str;
		});
		System.out.println(list);
	}
	
	/**
	 * 方法签名为Spliterator<E> spliterator()，该方法返回容器的可拆分迭代器。
	 * Spliterator既可以像Iterator那样逐个迭代，也可以批量迭代。批量迭代可以降低迭代的开销。
	 * Spliterator是可拆分的，一个Spliterator可以通过调用Spliterator<T> trySplit()方法来尝试分成两个。
	 * 一个是this，另一个是新返回的那个，这两个迭代器代表的元素没有重叠。
	 */
	public void spliterator() {
		System.out.println("\n==== spliterator() ====");
		ArrayList<String> list = new ArrayList<>(Arrays.asList("I", "love", "you", "too"));
		list.replaceAll(str -> {
			if (str.length() > 3)
				return str.toUpperCase();
			return str;
		});
		System.out.println(list);
	}
	
	/**
	 * 该方法签名为void forEach(BiConsumer<? super K,? super V> action)， 作用是对Map中的每个映射执行action指定的操作，
	 * 其中BiConsumer是一个函数接口. BinConsumer接口名字和accept()方法名字都不重要，请不要记忆他们。
	 * 
	 */
	public void mapForEach() {
		System.out.println("\n==== mapForEach() ====");
		HashMap<Integer, String> map = new HashMap<>();
		map.put(1, "one");
		map.put(2, "two");
		map.put(3, "three");
		map.forEach((k, v) -> System.out.println(k + "=" + v));
	}
	
	/**
	 * 该方法跟Lambda表达式没关系，但是很有用。方法签名为V getOrDefault(Object key, V defaultValue)，
	 * 作用是按照给定的key查询Map中对应的value，如果没有找到则返回defaultValue。
	 */
	public void getOrDefault() {
		System.out.println("\n==== getOrDefault() ====");
		HashMap<Integer, String> map = new HashMap<>();
		map.put(1, "one");
		System.out.println(map.get(1));
		System.out.println(map.getOrDefault(2, "NoValue"));
	}
	
	/**
	 * 只有在不存在key值的映射或映射值为null时，才将value指定的值放入到Map中，否则不对Map做更改.
	 */
	public void putIfAbsent() {
		System.out.println("\n==== putIfAbsent() ====");
		HashMap<Integer, String> map = new HashMap<>();
		map.put(1, "one");
		map.put(1, "two");
		map.putIfAbsent(1, "xxx");
		System.out.println(map);
	}
	
	/**
	 * 只有在当前Map中key正好映射到value时才删除该映射
	 */
	public void remove() {
		System.out.println("\n==== remove() ====");
		HashMap<Integer, String> map = new HashMap<>();
		map.put(1, "one");
		map.put(2, "two");
		map.put(3, "three");
		map.remove(2);
		map.remove(4);
		System.out.println(map);
	}
	
	/**
	 * replace(K key, V value)，只有在当前Map中key的映射存在时才用value去替换原来的值，否则什么也不做．
	 * replace(K key, V oldValue, V newValue)，只有在当前Map中key的映射存在且等于oldValue时才用newValue去替换原来的值，否则什么也不做．
	 */
	public void replace() {
		System.out.println("\n==== replace() ====");
		HashMap<Integer, String> map = new HashMap<>();
		map.put(1, "one");
		map.put(2, "two");
		map.put(3, "three");
		map.replace(4, "four");
		map.replace(2, "t", "tt");
		System.out.println(map);
	}
	
	/**
	 * 对Map中的每个映射执行function指定的操作，并用function的执行结果替换原来的value
	 */
	public void mapReplaceAll() {
		System.out.println("\n==== mapReplaceAll() ====");
		HashMap<Integer, String> map = new HashMap<>();
		map.put(1, "one");
		map.put(2, "two");
		map.put(3, "three");
		map.replaceAll((k, v) -> v.toUpperCase());
		System.out.println(map);
	}
	
	/**
	 * 如果Map中key对应的映射不存在或者为null，则将value（不能是null）添加; 否则执行remappingFunction.
	 * 如果执行结果非null则用该结果跟key关联，否则在Map中删除key的映射．
	 */
	public void mapMerge() {
		System.out.println("\n==== mapMerge() ====");
		HashMap<Integer, String> map = new HashMap<>();
		map.put(1, "one");
		map.put(2, "two");
		map.put(3, "three");
		map.merge(4, "four", (v1, v2) -> v1+v2);
		System.out.println(map);
	}
	
	/**
	 * 把remappingFunction的计算结果关联到key上，如果计算结果为null，则在Map中删除key的映射．
	 */
	public void mapCompute() {
		System.out.println("\n==== mapCompute() ====");
		HashMap<Integer, String> map = new HashMap<>();
		map.put(1, "one");
		map.put(2, "two");
		map.put(3, "three");
		map.compute(4, (k,v) -> v==null ? "four" : v.concat("four"));
		System.out.println(map);
	}
	
	public void forEach() {
		System.out.println("\n==== forEach() ====");
		Stream<String> stream = Stream.of("I", "love", "you", "too");
		stream.forEach(str -> System.out.println(str));
	}
	
	public void filter() {
		System.out.println("\n==== filter() ====");
		Stream<String> stream = Stream.of("I", "love", "you", "too");
		stream.filter(str -> str.length()==3)
	    	.forEach(str -> System.out.println(str));
	}
	
	/**
	 * 去除重复元素
	 */
	public void distinct() {
		System.out.println("\n==== distinct() ====");
		Stream<String> stream = Stream.of("I", "love", "you", "too", "love");
		stream.distinct()
	    	.forEach(str -> System.out.println(str));
	}
	
	/**
	 * 排序
	 */
	public void sorted() {
		System.out.println("\n==== sorted() ====");
		Stream<String> stream= Stream.of("I", "love", "you", "too");
		stream.sorted((str1, str2) -> str1.length()-str2.length())
		    .forEach(str -> System.out.println(str));
	}
	
	/**
	 * 返回一个对当前所有元素执行执行mapper之后的结果组成的Stream.
	 * 直观的说，就是对每个元素按照某种操作进行转换，转换前后Stream中元素的个数不会改变，但元素的类型取决于转换之后的类型。
	 */
	public void streamMap() {
		System.out.println("\n==== streamMap() ====");
		Stream<String> stream= Stream.of("I", "love", "you", "too");
		stream.map(str -> str.toUpperCase())
	    	.forEach(str -> System.out.println(str));
	}
	
	/**
	 * flatMap()的作用就相当于把原stream中的所有元素都”摊平”之后组成的Stream，转换前后元素的个数和类型都可能会改变。
	 */
	public void flatMap() {
		System.out.println("\n==== flatMap() ====");
		Stream<List<Integer>> stream = Stream.of(Arrays.asList(1,2), Arrays.asList(3, 4, 5));
		stream.flatMap(list -> list.stream())
	    	.forEach(i -> System.out.println(i));
	}
	
	/**
	 * 规约操作（reduction operation）又被称作折叠操作（fold），是通过某个连接动作将所有元素汇总成一个汇总结果的过程。
	 * reduce操作可以实现从一组元素中生成一个值，sum()、max()、min()、count()等都是reduce操作，将他们单独设为函数只是因为常用。
	 * Optional是（一个）值的容器，使用它可以避免null值的麻烦.
	 */
	public void reduce() {
		System.out.println("\n==== reduce() ====");
		
		//最长的单词
		Stream<String> stream = Stream.of("I", "love", "you", "too");
		Optional<String> longest = stream.reduce((s1, s2) -> s1.length()>=s2.length() ? s1 : s2);
		System.out.println("最长的单词: " + longest.get());
		
		stream = Stream.of("I", "love", "you", "too");
		longest = stream.max((s1, s2) -> s1.length()-s2.length());
		System.out.println("max 效果相同: " + longest.get());
		
		//求出一组单词的长度之和。这是个“求和”操作，操作对象输入类型是String，而结果类型是Integer。
		stream = Stream.of("I", "love", "you", "too");
		Integer lengthSum = stream.reduce(0,	// 初始值　
		        (sum, str) -> sum+str.length(), // 累加器 
		        (a, b) -> a+b); 				// 部分和拼接器，并行执行时才会用到
		System.out.println("长度之和=" + lengthSum);
		stream = Stream.of("I", "love", "you", "too");
		int lengthSum_ = stream.mapToInt(str -> str.length()).sum();
		System.out.println("效果相同=" + lengthSum_);
	}
	
	/**
	 * reduce()擅长的是生成一个值, collect生成一个集合或者Map等复杂的对象. 如果你发现某个功能在Stream接口中没找到，十有八九可以通过collect()方法实现。
	 * collect()是Stream接口方法中最灵活的一个.
	 */
	public void collect() {
		System.out.println("\n==== collect() ====");
		
		// 将Stream转换成容器或Map
		Stream<String> stream = Stream.of("I", "love", "you", "too");
		List<String> list = stream.collect(Collectors.toList());
		System.out.println("将Stream转换成 List: " + list);
		stream = Stream.of("I", "love", "you", "too");
		Set<String> set = stream.collect(Collectors.toSet());
		System.out.println("将Stream转换成 Set: " + set);
		stream = Stream.of("I", "love", "you", "too");
		Map<String, Integer> map = stream.collect(Collectors.toMap(Function.identity(), String::length));
		System.out.println("将Stream转换成 Map: " + map);
		
		//将Stream规约成List
		stream = Stream.of("I", "love", "you", "too");
		//三个参数：1.目标容器。2.新元素如何添加到元素中。 3. 如果并行的进行规约，需要告诉collect() 多个部分结果如何合并成一个.
		list = stream.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		System.out.println("将Stream规约成List: " + list);
		// or 
		stream = Stream.of("I", "love", "you", "too");
		// 收集器Collector就是对这三个参数的简单封装, Collectors工具类可通过静态方法生成各种常用的Collector.
		list = stream.collect(Collectors.toList());
		System.out.println("效果相同=" + list);
		
		// 使用toCollection()人为指定容器的实际类型
		stream = Stream.of("I", "love", "you", "too");
		ArrayList<String> arrayList = stream.collect(Collectors.toCollection(ArrayList::new));
		System.out.println("arrayList=" + arrayList);
		stream = Stream.of("I", "love", "you", "too");
		HashSet<String> hashSet = stream.collect(Collectors.toCollection(HashSet::new));
		System.out.println("hashSet=" + hashSet);
		
		// 使用collect()生成Map:
		/**
		 * 使用collect()生成Map:
		 * 1. 使用Collectors.toMap()生成的收集器，用户需要指定如何生成Map的key和value
		 *Map<Student, Double> studentToGPA = students.stream().collect(
		 *	    Collectors.toMap(Function.identity(),// 如何生成key
		 *	    student -> computeGPA(student)));// 如何生成value
		 */
		
		/**
		 * 2. 使用partitioningBy()生成的收集器，这种情况适用于将Stream中的元素依据某个二值逻辑（满足条件，或不满足）分成互补相交的两部分，
		 * 比如男女性别、成绩及格与否等。
		 * Map<Boolean, List<Student>> passingFailing = students.stream()
         * .collect(Collectors.partitioningBy(s -> s.getGrade() >= PASS_THRESHOLD));
		 */
		
		/**
		 * 3. 使用groupingBy()生成的收集器，这是比较灵活的一种情况。跟SQL中的group by语句类似，这里的groupingBy()也是按照某个属性对数据进行分组，
		 * 属性相同的元素会被对应到Map的同一个key上。
		 * Map<Department, List<Employee>> byDept = employees.stream()
         *  .collect(Collectors.groupingBy(Employee::getDepartment));
		 */
		
		/**
		 * 1. 先将员工按照部门分组，2. 然后统计每个部门员工的人数。Java类库设计者也考虑到了这种情况，增强版的groupingBy()能够满足这种需求。
		 * 增强版的groupingBy()允许我们对元素分组之后再执行某种运算，比如求和、计数、平均值、类型转换等。这种先将元素分组的收集器叫做上游收集器，
		 * 之后执行其他运算的收集器叫做下游收集器(downstream Collector)。
		 * Map<Department, Integer> totalByDept = employees.stream()
		 * .collect(Collectors.groupingBy(Employee::getDepartment, 
		 * Collectors.counting()));// 下游收集器
		 */
		
		/**
		 * 考虑将员工按照部门分组的场景，如果我们想得到每个员工的名字（字符串），而不是一个个Employee对象，可通过如下方式做到
		 * Map<Department, List<String>> byDept = employees.stream()
		 * .collect(Collectors.groupingBy(Employee::getDepartment, 
		 * Collectors.mapping(Employee::getName,// 下游收集器 
		 * Collectors.toList())));// 更下游的收集器
		 */
		
		// 使用collect()做字符串join.  字符串拼接时使用Collectors.joining()生成的收集器，从此告别for循环。
		// 使用Collectors.joining()拼接字符串
		stream = Stream.of("I", "love", "you");
		String joined = stream.collect(Collectors.joining());
		System.out.println("字符串拼接 = " + joined);
		
		stream = Stream.of("I", "love", "you");
		joined = stream.collect(Collectors.joining(","));
		System.out.println("字符串拼接 = " + joined);
		
		stream = Stream.of("I", "love", "you");
		joined = stream.collect(Collectors.joining(",", "{", "}"));
		System.out.println("字符串拼接 = " + joined);
	}
	
	/**
	 * Pipelines: 比较复杂. 
	 * 不是所有的Stream结束操作都需要返回结果，有些操作只是为了使用其副作用(Side-effects)，
	 * 比如使用Stream.forEach()方法将结果打印出来就是常见的使用副作用的场景（事实上，除了打印之外其他场景都应避免使用副作用）
	 * 也许你会觉得在Stream.forEach()里进行元素收集是个不错的选择，就像下面代码中那样，但遗憾的是这样使用的正确性和效率都无法保证，
	 * 因为Stream可能会并行执行。大多数使用副作用的地方都可以使用归约操作更安全和有效的完成。
	 */
	public void pipelines() {
		/*
		// 错误的收集方式
		ArrayList<String> results1 = new ArrayList<>();
		stream.filter(s -> pattern.matcher(s).matches())
		      .forEach(s -> results1.add(s));  // Unnecessary use of side-effects!
		// 正确的收集方式
		List<String> results2 =
		     stream.filter(s -> pattern.matcher(s).matches())
		             .collect(Collectors.toList());  // No side-effects!
		*/
	}
}

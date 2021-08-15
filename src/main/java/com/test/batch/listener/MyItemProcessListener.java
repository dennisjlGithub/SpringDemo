package com.test.batch.listener;

import org.springframework.batch.core.ItemProcessListener;

public class MyItemProcessListener implements ItemProcessListener<Object, Object> {

	@Override
	public void beforeProcess(Object item) {
		System.out.println("MyItemProcessListener -> beforeProcess : " + item.toString());
		
	}

	@Override
	public void afterProcess(Object item, Object result) {
		System.out.println("MyItemProcessListener -> afterProcess");
	}

	@Override
	public void onProcessError(Object item, Exception e) {
		System.out.println("MyItemProcessListener -> onProcessError");
	}

	
}

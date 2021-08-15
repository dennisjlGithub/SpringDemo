package com.test.batch.listener;

import org.springframework.batch.core.ItemReadListener;

public class MyItemReadListener implements ItemReadListener<Object> {

	@Override
	public void beforeRead() {
		System.out.println("MyItemReadListener -> beforeRead");
	}

	@Override
	public void afterRead(Object item) {
		System.out.println("MyItemReadListener -> afterRead");
	}

	@Override
	public void onReadError(Exception ex) {
		System.out.println("MyItemReadListener -> onReadError");
	}

}

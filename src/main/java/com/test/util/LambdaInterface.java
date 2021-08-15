package com.test.util;

@FunctionalInterface
public interface LambdaInterface<T> {
	void accept(T t);
}

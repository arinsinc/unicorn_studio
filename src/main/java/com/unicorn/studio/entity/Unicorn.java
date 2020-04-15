package com.unicorn.studio.entity;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.unicorn.studio.config.UnicornStudioConfig;

public class Unicorn {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(UnicornStudioConfig.class);
	}

}
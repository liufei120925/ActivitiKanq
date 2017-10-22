package com.kanq.platform.util.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.activiti.engine.ProcessEngines;

public class ProcessEnginesServletContextListener implements ServletContextListener {
	
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		System.out.println("*****************************************************************");
		System.out.println("*****************************************************************");
		System.out.println("*****************************************************************");
		System.out.println("*****************************************************************");
		System.out.println("**********************ProcessEngines.init************************");
		System.out.println("*****************************************************************");
		System.out.println("*****************************************************************");
		System.out.println("*****************************************************************");
		System.out.println("*****************************************************************");
		System.out.println("*****************************************************************");
		System.out.println("*****************************************************************");
		System.out.println("*****************************************************************");
		System.out.println("*****************************************************************");
		ProcessEngines.init();
	}
	
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ProcessEngines.destroy();
	}
}
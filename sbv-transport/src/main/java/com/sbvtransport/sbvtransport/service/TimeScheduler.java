package com.sbvtransport.sbvtransport.service;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

public class TimeScheduler {

	
	private TaskScheduler scheduler;

	Runnable exampleRunnable = new Runnable(){
	    @Override
	    public void run() {
	        System.out.println("Working");
	    }
	};

	@Async
	public void executeTaskT() {
	    ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
	    scheduler = new ConcurrentTaskScheduler(localExecutor);

	    Calendar cal = Calendar.getInstance(); 
	    cal.setTime(new Date()); 
	    cal.add(Calendar.HOUR_OF_DAY, 2); 
	    
	    long milliseconds = cal.getTimeInMillis();
	        
	    scheduler.schedule(exampleRunnable,
	            new Date(milliseconds));
	}
	
}

package com.sbvtransport.sbvtransport.service;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import com.sbvtransport.sbvtransport.model.Ticket;

public class TimeScheduler {

	TicketService ticketService;
	
	private TaskScheduler scheduler;

	Runnable exampleRunnable = new Runnable(){
	    @Override
	    public void run() {
	        System.out.println("Working");
	    }
	};

	@Async
	public void changeActiveForOneUse(Long ticketId) {
	    ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
	    scheduler = new ConcurrentTaskScheduler(localExecutor);

	    Calendar cal = Calendar.getInstance(); 
	    cal.setTime(new Date()); 
	    cal.add(Calendar.MINUTE, 2); 
	    
	    long milliseconds = cal.getTimeInMillis();
	        
	    scheduler.schedule(exampleRunnable,
	            new Date(milliseconds));
	    
	    Ticket ticket = ticketService.getOne(ticketId);
	    ticket.setActive(false);
	    ticketService.update(ticket);
	    
	}
	
}

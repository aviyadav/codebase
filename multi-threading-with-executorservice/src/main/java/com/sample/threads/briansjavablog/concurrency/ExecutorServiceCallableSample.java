package com.sample.threads.briansjavablog.concurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

public class ExecutorServiceCallableSample {
		
	public void submitCallablesWithExecutor() throws InterruptedException, ExecutionException, TimeoutException {
	
		ExecutorService executorService = null;
		
		try {		
			executorService = Executors.newFixedThreadPool(2);
		
			Future<Double> task1Future = executorService.submit(new Callable<Double>() {
			
				public Double call() throws Exception {					
					
					System.out.println(String.format("starting expensive task thread %s", 
												Thread.currentThread().getName()));
					Double returnedValue = someExpensiveRemoteCall();				
					System.out.println(String.format("finished expensive task thread %s", 
												Thread.currentThread().getName()));
					return returnedValue;
				}											
			});
			
			Future<Double> task2Future = executorService.submit(()->{
				
				System.out.println(String.format("starting expensive task thread %s", 
													Thread.currentThread().getName()));
				Double returnedValue = someExpensiveRemoteCall();				
				System.out.println(String.format("finished expensive task thread %s", 
													Thread.currentThread().getName()));
				return returnedValue;
			});
			
			Double value1 = task1Future.get(4,  TimeUnit.SECONDS);
			Double value2 = task2Future.get(4,  TimeUnit.SECONDS);
			System.out.println(String.format("TaskFuture1 returned value %s and " +
					 						  "TaskFuture2 returned value %s", value1, value2));			
		} finally {
			executorService.shutdown();				
		}		
	}
	
	public void submitMultipleCallablesWithExecutor() throws InterruptedException, ExecutionException, TimeoutException {
	
		ExecutorService executorService = null;
		
		try {		
			executorService = Executors.newFixedThreadPool(8);
		
			Collection<Callable<Double>> callables = new ArrayList<>();
			IntStream.rangeClosed(1, 8).forEach(i-> {
				callables.add(createCallable());
			});
			
			/* invoke all supplied Callables */ 
			List<Future<Double>> taskFutureList = executorService.invokeAll(callables);
			
			/* call get on Futures to retrieve result when it becomes available.
			 * If specified period elapses before result is returned a TimeoutException
			 * is thrown
			 */
			for (Future<Double> future : taskFutureList) {
				
				/* get Double result from Future when it becomes available */
				Double value = future.get(4, TimeUnit.SECONDS);
				System.out.println(String.format("TaskFuture returned value %s", value));	
			}
		} finally {
			executorService.shutdown();				
		}		
	}
	
	private Double someExpensiveRemoteCall() {
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}		
		
		return ThreadLocalRandom.current().nextDouble() * 100;
	}
	
	private Callable<Double> createCallable() {
		
		return new Callable<Double>() {
				
			public Double call() throws Exception {					
				
				System.out.println(String.format("starting expensive task thread %s", 
											Thread.currentThread().getName()));
				Double returnedValue = someExpensiveRemoteCall();				
				System.out.println(String.format("finished expensive task thread %s", 
											Thread.currentThread().getName()));
				return returnedValue;
			}											
		};
	}			
	
}
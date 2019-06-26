package com.sample.threads.briansjavablog.concurrency;

public class SimpleThreadSample {
	public void doSingleThreadedWork() {
		System.out.println("starting expensive task");
		doSomethingExpensive();
		System.out.println("finishing expensive task");

		System.out.println("starting expensive task");
		doSomethingExpensive();
		System.out.println("finishing expensive task");
	}

	public void doMultiThreadedWork() throws InterruptedException {

		Thread t1 = new Thread(new Runnable() {
			public void run() {
				System.out.println("starting expensive task thread 1");
				doSomethingExpensive();
				System.out.println("finishing expensive task thread 1");
			}
		});

		Thread t2 = new Thread(()-> {
			System.out.println("starting expensive task thread 2");
			doSomethingExpensive();
			System.out.println("finishing expensive task thread 2");
		});

		t1.start();
		t2.start();

		t1.join();
		t2.join();
	}

	private void doSomethingExpensive() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
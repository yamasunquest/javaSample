package top.sunquest.thread;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadOperation {
	private static ExecutorService es  = Executors.newFixedThreadPool(2);

	public static Queue<String> mailQ  = new LinkedBlockingQueue<>();
	private AtomicInteger ati = new AtomicInteger(1);

	public Runnable checkThread(){
		return new Runnable() {
			@Override
			public void run() {
				while(ati.get() < 10){
					try {
						Thread.sleep(1000);
					}catch (Exception e){
					}
					ati.getAndIncrement();

					System.out.println("timeCount value is :" + ati.get());
					if(ati.get() > 3){
						System.out.println("send stop command");
						mailQ.add("stop");
						break;
					}
				}

				System.out.println("timeCount quit");
			}
		};
	}

	public Runnable mailThread(){
		return new Runnable() {
			@Override
			public void run() {
				String mail = "";


				while(true){
					//watch
					if(mailQ.iterator().hasNext()){
						mail =mailQ.iterator().next();
					}else{
						continue;
					}

					if(mail.equalsIgnoreCase("stop")){
						//unwatch
						ati.set(20);
						break;
					}else if(mail.length() > 0){
						ati.set(1);
						try {
							Thread.sleep(ati.get() * 1000);
						}catch (Exception e){
						}
						System.out.println(mail);
						mailQ.remove();
					}
				}

				System.out.println("mail send was finished.");
			}
		};
	}

	public static void main(String args[]){
		mailQ.add("mail-1");
		mailQ.add("mail-2");
		mailQ.add("mail-3");
		mailQ.add("mail-4");
		mailQ.add("mail-5");
		mailQ.add("mail-6");

		ThreadOperation to = new ThreadOperation();
		es.submit(to.checkThread());
		es.submit(to.mailThread());

		es.shutdown();
		System.out.println("asd");
	}
}

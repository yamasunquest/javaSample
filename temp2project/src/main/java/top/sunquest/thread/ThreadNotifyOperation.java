package top.sunquest.thread;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadNotifyOperation {
	private static ExecutorService es  = Executors.newFixedThreadPool(2);
	public static Queue<String> mailQ  = new LinkedBlockingQueue<>();

	//private static PipedOutputStream checkPos = new PipedOutputStream();
	private static PipedInputStream checkPis = new PipedInputStream();

	private static PipedOutputStream mailPos = new PipedOutputStream();
	//private static PipedInputStream mailPis = new PipedInputStream();

	public Runnable checkThread(){
		return new Runnable() {
			@Override
			public void run() {
				int i = 0;

				while(i < 3){
					try {
						Thread.sleep(1000);
					}catch (Exception e){
					}

					try {
						byte[] inbyte = new byte[4];
						checkPis.read(inbyte);
						if("decr".equalsIgnoreCase(new String(inbyte))){
							i = 0;
						}
					}catch (Exception e){
					}

					System.out.println("timeCount value is :" + i);
				}

				System.out.println("send stop command");
				mailQ.add("stop");
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
						break;
					}else if(mail.length() > 0){
						try {
							Thread.sleep(1000);
						}catch (Exception e){
						}

						System.out.println(mail);
						mailQ.remove();
						try{
							mailPos.write("decr".getBytes());
						}catch (Exception e){
						}
					}
				}

				System.out.println("mail send was finished.");
			}
		};
	}

	public static void main(String args[]) throws IOException {
		mailQ.add("mail-1");
		mailQ.add("mail-2");
		mailQ.add("mail-3");
		mailQ.add("mail-4");
		mailQ.add("mail-5");
		mailQ.add("mail-6");

		checkPis.connect(mailPos);

		ThreadOperation to = new ThreadOperation();
		es.submit(to.checkThread());
		es.submit(to.mailThread());

		es.shutdown();

		checkPis.close();
		mailPos.close();
		System.out.println("asd");
	}
}

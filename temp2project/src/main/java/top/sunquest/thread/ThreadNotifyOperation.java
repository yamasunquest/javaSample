package top.sunquest.thread;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.*;

public class ThreadNotifyOperation {
	private static ExecutorService es  = Executors.newFixedThreadPool(2);
	// 消息存储体，用于存储消息
	public static Queue<String> mailQ  = new LinkedBlockingQueue<>();

	public static void main(String args[]) throws IOException {
		SendThread sendThread = new SendThread(mailQ);
		ReceiveThread receiveThread = new ReceiveThread(mailQ);

		PipedOutputStream out = sendThread.getOutputPiped();
		PipedInputStream in = receiveThread.getInputPiped();
		out.connect(in);

		// 线程加入队列
		es.submit(sendThread);
		Future<String> result = es.submit(receiveThread);

		// 接受线程结束后，结束主线程
		while(true){
			if (result.isDone()){
				break;
			}

			Scanner input=new Scanner(System.in);
			String str=input.nextLine();
			mailQ.add(str);
		}

		es.shutdown();
		out.close();
		in.close();
		System.out.println("886");


	}
}

/**
 * 接收，输出消息的线程
 */
class ReceiveThread implements Callable<String> {
	//输入管道
	private PipedInputStream inputPiped = new PipedInputStream();

	Queue<String> mailQ;
	public PipedInputStream getInputPiped() {
		return inputPiped;
	}

	public ReceiveThread(Queue<String> mailQ){
		this.mailQ = mailQ;
	}

	@Override
	public String call() throws Exception {
		String mail = "";
		byte[] buf = new byte[1024];

		while(true){
			try {
				// 接收消息，并且打印
				int len = inputPiped.read(buf);
				mail = new String(buf, 0, len);
				System.out.println(mail);

				// 如果接收到的消息是 end 结束进程
				if (mail.equalsIgnoreCase("end")){
					System.out.println("ReceiveThread finish.");
					return "end";
				}

			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
}

/**
 * 生成，发送消息的线程
 */
class SendThread implements Runnable{
	//输出管道
	private PipedOutputStream outputPiped = new PipedOutputStream();
	//private static PipedInputStream mailPis = new PipedInputStream();

	public Queue<String> mailQ;

	public PipedOutputStream getOutputPiped() {
		return outputPiped;
	}

	public SendThread(Queue<String> mailQ){
		this.mailQ = mailQ;
	}

	@Override
	public void run() {
		// 预先生成一部分数据
		mailQ.add("mail-1");
		mailQ.add("mail-2");
		mailQ.add("mail-3");

		while(mailQ.size() > 0){
			try {
				Thread.sleep(5000);
			}catch (Exception e){
			}

			try {
				// 把数据写入到通道中，发送出去
				outputPiped.write(mailQ.poll().getBytes());
			}catch (Exception e){
			}
		}

		System.out.println("SendThread finish.");
	};
}

package com.test.util.mail;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * java -classpath .;d:\mail\activation.jar;d:\mail\javax.mail.jar; mail.SendMail
 *
 */

public class SendMail {

	private static long sleepTime = 15*1000;  // milliseconds
	private static String mailPath = "D:\\mail\\mail.txt";
	private static String contentPath = "D:\\mail\\content.txt";
	private static String sender = "329262025@qq.com";

	public static void main(String[] args) throws Exception {
		
		Properties props = new Properties();
		props.setProperty("mail.debug", "false");
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.host", "smtp.qq.com");
		props.setProperty("mail.transport.protocol", "smtp");
		
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");

		InputStreamReader read = new InputStreamReader(new FileInputStream(mailPath), "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;

        while ((lineTxt = bufferedReader.readLine()) != null)
        {
        	String receiver[] = lineTxt.split(",");
        	
			Session session = Session.getInstance(props);
			session.setDebug(false);
			Message msg = new MimeMessage(session);
			StringBuilder content = new StringBuilder();

			InputStreamReader readC = new InputStreamReader(new FileInputStream(contentPath), "UTF-8");
	        BufferedReader bufferedReaderC = new BufferedReader(readC);
	        String lineTxtC = null;
	        boolean isSubject = true;
	        while ((lineTxtC = bufferedReaderC.readLine()) != null) {
	        	if (isSubject) {
	        		msg.setSubject(lineTxtC.replaceAll("Subject=", ""));
	        		isSubject = false;
	        	}
	        	for (int i = 1; i < receiver.length; i++) {
	        		lineTxtC = lineTxtC.replaceAll("%"+i, receiver[i]);
				}
	        	content.append(lineTxtC + "\n");
	        }
	        	
	        bufferedReaderC.close();
	        readC.close();
			
			msg.setText(content.toString());
			msg.setFrom(new InternetAddress(sender));

			System.out.println("Sent mail to: " + receiver[0].trim());

			Transport transport = session.getTransport();
			transport.connect("smtp.qq.com", sender, "aribmobircfdcajd");
			transport.sendMessage(msg, new Address[] { new InternetAddress(receiver[0].trim()) });
			transport.close();

			Thread.currentThread();
			Thread.sleep(sleepTime);
        }
        
        bufferedReader.close();
        read.close();
	}
}

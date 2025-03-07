package board.ETCClass;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.Data;

@Service
public class MyMailSender {

	private JavaMailSender javaMailSender;
	
	private int maxThread = 10;
	private int lastIndex = 0;
	private List<senderTask> threadList = new ArrayList<senderTask>();;
	
	public MyMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public void sendEmailNotice(String email,String code){
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
			mimeMessageHelper.setTo(email); // 메일 수신자
			mimeMessageHelper.setSubject("Today's Overview on NESS"); // 메일 제목
			mimeMessageHelper.setText("인증 번호는"+code+"입니다.", true); // 메일 본문 내용, HTML 여부
			javaMailSender.send(mimeMessage);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void add(String email,String header,String form) {
		synchronized(this) {
			sender_DTO s = new sender_DTO(email, header, form);
			if(threadList.size() <= maxThread-1) {
				senderTask oneThread = new senderTask(javaMailSender,threadList);
				threadList.add(oneThread);
				oneThread.add(s);
				oneThread.start();
			}
			else {
				threadList.get(getIndex()).add(s);
			}
		}
	}
	
	
	public int getIndex() {
		synchronized(this) {
			if(lastIndex < maxThread-1)lastIndex+=1;
			else lastIndex = 0;
			return lastIndex; 
		}
	}
	
	public void waitQueue() {
		int i = 1;
		for(senderTask s : threadList) {
			System.out.println((i++)+" "+s.queue.size());
		}
	}
	
	
	
	public String createCode() {
    	int type = 0; 
    	int firstASCII = 0;
    	int length = 0;
    	StringBuilder sb = new StringBuilder();
    	for(int i = 0; i < 6; i++) {
    		type =(int)(Math.random()*3);
    		switch(type) {
    		case 0:
    			firstASCII = 65;
    			length = 26;
    			break;
    		case 1:
    			firstASCII = 97;
    			length = 26;
    			break;
    		case 2:
    			firstASCII = 48;
    			length = 10;
    			break;
    		}
    		sb.append((char)((Math.random()*length)+firstASCII));
    	}
    	return sb.toString();
    }

    
	
}
class senderTask extends Thread{
	
	private JavaMailSender javaMailSender;
	 Queue<sender_DTO> queue = new LinkedList<sender_DTO>();
	private List<senderTask> list;
	
	public senderTask(JavaMailSender javaMailSender,List<senderTask> list) {
		this.javaMailSender = javaMailSender;
		this.list = list;
	}
	
	public void sendEmailNotice(String email,String code){
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
			mimeMessageHelper.setTo("vavogmlfkr4@gmail.com"); // 메일 수신자
			mimeMessageHelper.setSubject("Today's Overview on NESS"); // 메일 제목
			mimeMessageHelper.setText("인증 번호는"+code+"입니다.", true); // 메일 본문 내용, HTML 여부
			javaMailSender.send(mimeMessage);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	@Override
	public void run() {
		while(!queue.isEmpty()) {
			queue.poll();
			sendEmailNotice(null,10000+"");
		}
		list.remove(this);
	}
	public void add(sender_DTO dto) {
		queue.add(dto);
	}
	
	
	
}
@Data
class sender_DTO{
	private String email;
	private String header;
	private String form;
	public sender_DTO(String email, String header, String form) {
		super();
		this.email = email;
		this.header = header;
		this.form = form;
	}
}
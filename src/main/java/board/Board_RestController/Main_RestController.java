package board.Board_RestController;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import board.Board_Redis.emailCheck_Redis;
import board.Board_Service.Main_Service;
import board.ETCClass.MyMailSender;
import board.ETCClass.StaticPath;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class Main_RestController implements StaticPath{
	
	private Main_Service main_service;
	private MyMailSender mailSender;
	private emailCheck_Redis emailCheck_Redis;
	
	public Main_RestController(
			Main_Service main_service,
			MyMailSender mailSender,
			emailCheck_Redis emailCheck_Redis
	) 
	{
		this.main_service = main_service;
		this.mailSender = mailSender;
		this.emailCheck_Redis = emailCheck_Redis;
	}
	
	
	@PostMapping("/files/upload")
	public ResponseEntity<String> saveFile
	(
			@RequestParam("file") MultipartFile mutipart,
			@RequestParam("no") int no
	) throws IllegalStateException, IOException{
		return ResponseEntity.ok(main_service.saveFile(mutipart,no));
	}
	
	@PostMapping("/imageToString")
	public String toImage(@RequestBody String s)  {
		return main_service.proxy("https://ja.dict.naver.com/ja/recognize", s);
	}
	
	
	@PostMapping("/auth/email/send-code")
	public ResponseEntity<String> sendCode(@RequestBody Map<String,Object> body,HttpServletRequest req) {
		boolean check = emailCheck_Redis.isPossibilitySned(emailCheck_Redis.getClientIP(req));
		if(!check) 
			return ResponseEntity.status(403).body("{\"msg\":\"메일 인증은 하루에 "+emailCheck_Redis.maxSendCnt+"번만 가능합니다. 내일 다시 시도해 주세요.\"}");
			
		HttpSession s = req.getSession();
		String email = (String)body.get("email");
		String code = mailSender.createCode();
		mailSender.sendEmailNotice(email, code);
		s.setAttribute("certifiedCode", code);
		s.setAttribute("email", email);
		s.setMaxInactiveInterval(300000);
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/auth/email/check-code")
	public ResponseEntity<String> codeCheck(@RequestBody Map<String,Object> body,HttpSession s) {
		String code = (String)body.get("code");
		
		if(s.getAttribute("certifiedCode") == null)
			return ResponseEntity.status(404).body("{\"msg\":\"먼저 인증번호를 전송해야 합니다.\"}");
		if(!((String)s.getAttribute("certifiedCode")).equals(code))
			return ResponseEntity.status(400).body("{\"msg\":\"인증 번호가 일치하지 않습니다.\"}");
		return ResponseEntity.ok().build();
	}
	
	
	@PostMapping("/test")
	public void csrfTest() {
		System.out.println("hi");
	}
	
}

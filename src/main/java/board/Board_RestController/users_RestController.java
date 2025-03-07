package board.Board_RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import board.Board_Service.users_Service;

@RestController
public class users_RestController {

	private users_Service user_Service;
	
	public users_RestController(users_Service user_Service) {
		this.user_Service = user_Service;
	}
	
	@GetMapping("/user/check/id")
	public ResponseEntity<Object> checkId(@RequestParam("id")String id) {
		if(user_Service.getUserById(id) != null) return ResponseEntity.status(400).body("{\"msg\":\"이미 사용중인 id입니다.\"}");
		return ResponseEntity.ok().build();
	}
	@GetMapping("/user/check/email")
	public ResponseEntity<Object> checkEmail(@RequestParam("email")String email) {
		if(user_Service.getUserByEmail(email) != null) return ResponseEntity.status(400).body("{\"msg\":\"이미 사용중인 이메일입니다.\"}");
		return ResponseEntity.ok().build();
	}
	@GetMapping("/user/check/nickname")
	public ResponseEntity<Object> checkNickname(@RequestParam("nickname")String nickname) {
		if(user_Service.getUserNoByNickname(nickname) != -1) return ResponseEntity.status(400).body("{\"msg\":\"이미 사용중인 닉네임입니다.\"}");
		return ResponseEntity.ok().build();
	}
	
}

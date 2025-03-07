package board.Board_mappingController;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import board.Board_Service.role_Service;
import board.Board_Service.users_Service;
import board.DTO.join_DTO;
import board.DTO.users_DTO;
import board.security.MySecurityMethods;
import jakarta.servlet.http.HttpSession;

@Controller 
public class user_Controller {
	
	private users_Service users_Service;
	private role_Service role_Service;
	
	public user_Controller
	(
			users_Service users_Service,
			role_Service role_Service
	) 
	{
		this.users_Service = users_Service;
		this.role_Service = role_Service;
	}
	
	
	@PostMapping("/join")
	public String join(join_DTO dto,HttpSession s) {
		String sendEmail = (String)s.getAttribute("email");
		String code = (String)s.getAttribute("certifiedCode");
		if(!dto.checkInput(sendEmail, code)) {
			System.out.println("통과못함");
			return "";
		}
		
		users_DTO user = dto.toUsers_DTO();
		user.setUser_no(users_Service.nextNo());
		if(users_Service.insertUser(user))
			role_Service.insertRole(user.getUser_no(), user.getTarget(),"board_write","comment_write","login");
			
		return "redirect:/login";
	}
	
	@PostMapping("/user/nickname")
	public String updateNickname(@RequestParam("nickname")String nickname) {
		if(users_Service.getUserNoByNickname(nickname) != -1) {
			return "redirect:/error";
		}
		if(users_Service.updateNickname(nickname))
			MySecurityMethods.getObject(users_DTO.class).setNickname(nickname);
		return "redirect:/user/data";
	}

	@PostMapping("/user/email")
	public String updateEmail(@RequestParam("email")String email) {
		if(users_Service.getUserByEmail(email) != null) {
			return "redirect:/error";
		}
		if(users_Service.updateEmail(email))
			MySecurityMethods.getObject(users_DTO.class).setEmail(email);
		return "redirect:/user/data";
	}
	
	@PostMapping("/user/password")
	public String updatePassword(
			@RequestParam("currentPassword")String currentPassword,
			@RequestParam("newPassword")String newPassword
	)
	{
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if(!encoder.matches(currentPassword, MySecurityMethods.getObject(users_DTO.class).getPassword())) {
			return "error:404";
		} 
		
		if(users_Service.updatePassword(encoder.encode(newPassword)))
			MySecurityMethods.getObject(users_DTO.class).setPw(newPassword);
		return "redirect:/user/data";
	}
	
}

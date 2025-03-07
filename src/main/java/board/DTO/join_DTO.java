package board.DTO;

import java.util.regex.Pattern;

import lombok.Data;

@Data
public class join_DTO {
	private String id;
	private String pw;
	private String ckpw;
	private String nickname;
	private String email;
	private String certified;
	
	public users_DTO toUsers_DTO() {
		users_DTO dto = new users_DTO();
		dto.setId(id);
		dto.setPw(pw);
		dto.setNickname(nickname);
		dto.setEmail(email);
		return dto;
	}
	
	public boolean checkInput(String sendEmail,String code) {
		if(id == null || !Pattern.matches("^[A-Za-z][A-za-z0-9]{4,14}$",id))return false;
		if(pw == null || !Pattern.matches("^[a-zA-z0-9!@#$%^&*`~]{8,}$",pw) || !pw.equals(ckpw))return false;
		if(nickname == null || !Pattern.matches("^[°¡-ÆRA-Z-a-z0-9]{3,15}$",nickname))return false;
		if(email == null || !Pattern.matches("^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",email) || !sendEmail.equals(email))return false;
		if(certified == null || !certified.equals(code))return false;
		return true;
	}
	
}

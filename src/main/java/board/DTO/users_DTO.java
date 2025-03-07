package board.DTO;

import java.sql.Date;

import board.security.MyUserDetails;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class users_DTO extends MyUserDetails{
	private static final long serialVersionUID = 1L;

	private int user_no;
	private String id;
	private String pw;
	private String nickname;
	private String email;
	private Date join_date;
	
	
	@Override
	public String getPassword() {
		return pw;
	}
	@Override 
	public String getUsername() {
		return id;
	}
	
	
	
	@Override
	public String toString() {
		return "users_DTO [user_no=" + user_no + ", id=" + id + ", pw=" + pw + ", nickName=" + nickname + ", email="
				+ email + "]";
	}
	@Override
	public int getNo() {
		return user_no;
	}
	@Override
	public char getTarget() {
		return 'u';
	}
	
	
}

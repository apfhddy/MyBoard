package board.DTO;

import board.security.MyUserDetails;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class admin_DTO extends MyUserDetails{
	private static final long serialVersionUID = 1L;

	private int admin_no;
	private String id;
	private String pw;
	private String nickname;
	
	

	@Override
	public String getPassword() {
		return pw;
	}

	@Override
	public String getUsername() {
		return id;
	}

	@Override
	public int getNo() {
		return admin_no;
	}

	@Override
	public char getTarget() {
		return 'a';
	}

	@Override
	public String toString() {
		return "admin_DTO [admin_no=" + admin_no + ", id=" + id + ", pw=" + pw + ", nickname=" + nickname + "]";
	}
	
	

	
}

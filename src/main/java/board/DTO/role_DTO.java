package board.DTO;

import lombok.Data;

@Data
public class role_DTO {
	private int role_no;
	private int target_no;
	private char target;
	private String role;
	
	public role_DTO() {}

	public role_DTO(int role_no, int target_no, char target, String role) {
		super();
		this.role_no = role_no;
		this.target_no = target_no;
		this.target = target;
		this.role = role;
	};
	
}

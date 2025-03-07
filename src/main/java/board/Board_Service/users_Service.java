package board.Board_Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import board.Board_DAO.users_DAO;
import board.DTO.users_DTO;
import board.security.MySecurityMethods;

@Service
public class users_Service {
	
	private users_DAO users_DAO;
	
	public users_Service
	(
			users_DAO users_DAO
	
	) 
	{
		this.users_DAO = users_DAO;
	}
	
	public boolean insertUser(users_DTO dto) {
		dto.setPw(new BCryptPasswordEncoder().encode(dto.getPw()));
		int result = users_DAO.insertUser(dto);
		return result != 0;
	}
	
	public int nextNo() {
		return users_DAO.nextNo();
	}
	
	public users_DTO getUserById(String id) {
		return users_DAO.getUserById(id);
	}
	
	public int getUserNoByNickname(String nickname) {
		return users_DAO.getUserNoByNickname(nickname);
	}
	
	public boolean updateNickname(String nickname) {
		users_DTO dto = new users_DTO();
		dto.setUser_no(MySecurityMethods.getObject(users_DTO.class).getUser_no());
		dto.setNickname(nickname);
		return users_DAO.updateNickname(dto) != 0;
	}
	
	public boolean updateEmail(String email) {
		users_DTO dto = new users_DTO();
		dto.setUser_no(MySecurityMethods.getObject(users_DTO.class).getUser_no());
		dto.setEmail(email);
		return users_DAO.updateEmail(dto) != 0;
	}
	
	public boolean updatePassword(String newPassword) {
		users_DTO dto = new users_DTO();
		dto.setUser_no(MySecurityMethods.getObject(users_DTO.class).getUser_no());
		dto.setPw(newPassword);
		return users_DAO.updatePassword(dto) != 0;
	}
	
	public users_DTO getUserByEmail(String email) {
		return users_DAO.getUserByEmail(email);
	}
}

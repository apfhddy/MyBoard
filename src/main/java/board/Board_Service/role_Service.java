package board.Board_Service;

import java.util.List;

import org.springframework.stereotype.Service;

import board.Board_DAO.role_DAO;
import board.DTO.role_DTO;

@Service
public class role_Service {
	private role_DAO role_DAO;
	
	public role_Service(role_DAO role_DAO) {
		this.role_DAO = role_DAO;
	}
	
	
	public boolean insertRole(int target_no,char target,String... roles) {
		role_DTO[] role_DTOs = new role_DTO[roles.length];
		
		for(int i = 0; i < roles.length; i++) {
			role_DTOs[i] = new role_DTO(0,target_no,target,roles[i]);
		}
		return role_DAO.insertRoles(role_DTOs) != 0;
	}
	
	public List<String> getRoles(int target_no,char target){
		role_DTO dto = new role_DTO(0, target_no, target, null);
		return role_DAO.getRoles(dto);
	}
	
}

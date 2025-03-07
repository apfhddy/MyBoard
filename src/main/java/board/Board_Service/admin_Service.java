package board.Board_Service;

import org.springframework.stereotype.Service;

import board.Board_DAO.admin_DAO;
import board.DTO.admin_DTO;

@Service
public class admin_Service {
	
	private admin_DAO admin_DAO;
	
	public admin_Service(admin_DAO admin_DAO) {
		this.admin_DAO = admin_DAO;
	}
	
	public admin_DTO getAdminById(String id) {
		return admin_DAO.getAdminById(id);
	}
	
}

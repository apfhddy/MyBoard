package board.Board_DAO;

import org.apache.ibatis.annotations.Mapper;

import board.DTO.admin_DTO;

@Mapper
public interface admin_DAO {
	public int nextNo();
	public int insertAdmin(admin_DTO dto);
	public admin_DTO getAdminById(String id);
}

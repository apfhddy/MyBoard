package board.Board_DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import board.DTO.role_DTO;

@Mapper
public interface role_DAO {
	public int insertRoles(role_DTO[] roles);
	public List<String> getRoles(role_DTO dto);
}

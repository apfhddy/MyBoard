package board.Board_DAO;

import org.apache.ibatis.annotations.Mapper;

import board.DTO.users_DTO;

@Mapper
public interface users_DAO {
	public int nextNo();
	public int insertUser(users_DTO dto);
	public users_DTO getUserById(String id);
	public int getUserNoByNickname(String nickname);
	public int updateNickname(users_DTO dto);
	public int updateEmail(users_DTO dto);
	public int updatePassword(users_DTO dto);
	public users_DTO getUserByEmail(String email);
}

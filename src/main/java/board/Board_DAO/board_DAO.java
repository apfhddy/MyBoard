package board.Board_DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import board.DTO.board_DTO;

@Mapper
public interface board_DAO {
	public int getNextNo();
	public int insertBoard(board_DTO dto);
	public List<Map<String,Object>> getAllContents(
			@Param("getPageCnt")int getPageCnt,
			@Param("contentCnt")int contentCnt,
			@Param("pageIndex")int pageIndex,
			@Param("target")String target,
			@Param("keyword")String keyword
	);
	public Map<String,Object> getOneContent(int board_no);
	public board_DTO getOneBoard(int board_no);
	public int updateBoard(board_DTO dto);
	public int deleteBoard(int board_no);
	public int getBoardCnt(@Param("target")String target,@Param("keyword")String keyword);
	public List<Map<String,Object>> getRecentlyBoard(int user_no);
}

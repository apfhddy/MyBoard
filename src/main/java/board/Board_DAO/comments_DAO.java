package board.Board_DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import board.DTO.comments_DTO;

@Mapper
public interface comments_DAO {
	public int getNextGroupNo();
	public int getGroupMaxStep(int group_no);
	public int insertComment(comments_DTO dto);
	public List<Map<String,Object>> getCommentsByBoardNo(int board_no);
	public comments_DTO getCommentByCommentNo(int comment_no);
	public int updateGroupOrderNo(comments_DTO dto);
	public int updateChildCnt(int parent_comment_no);
	public int getParentChildCntSum(int parent_no);
	public int getGroupChildCntSum(int group_no);
	public int updateCommnet(comments_DTO dto);
	public int deleteComment(int comment_no);
	public List<Map<String,Object>> getRecentlyComment(int user_no);
}

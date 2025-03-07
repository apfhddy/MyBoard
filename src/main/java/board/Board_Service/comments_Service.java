package board.Board_Service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import board.Board_DAO.comments_DAO;
import board.DTO.comments_DTO;
import board.DTO.users_DTO;
import board.security.MySecurityMethods;

@Service
public class comments_Service {
	
	private comments_DAO comments_DAO;
	
	public comments_Service(comments_DAO comments_DAO) {
		this.comments_DAO = comments_DAO;
	}	
	
	public boolean writeComment(comments_DTO comments_DTO) {
		comments_DTO.setUser_no(MySecurityMethods.getObject(users_DTO.class).getUser_no());
		comments_DTO.setGroup_no(comments_DAO.getNextGroupNo());
		return comments_DAO.insertComment(comments_DTO) != 0;
	}
	
	public boolean replyComment(comments_DTO comment_DTO,int parent_no) {
		comments_DTO parent_DTO = comments_DAO.getCommentByCommentNo(parent_no);
		
		comment_DTO.setUser_no(MySecurityMethods.getObject(users_DTO.class).getUser_no());
		comment_DTO.setParent_no(parent_DTO.getComment_no());
		comment_DTO.setBoard_no(parent_DTO.getBoard_no());
		comment_DTO.setGroup_no(parent_DTO.getGroup_no());
		comment_DTO.setStep(parent_DTO.getStep()+1);
		comment_DTO.setOrderNum(getOrderNumAlgorithm(comment_DTO, parent_DTO));
		
		comments_DAO.updateChildCnt(parent_no);
		
		return comments_DAO.insertComment(comment_DTO) != 0;
	}
	
	public int getOrderNumAlgorithm(comments_DTO comment_DTO,comments_DTO parent_DTO) {
		
		int saveStep = comment_DTO.getStep();
		int group_no = comment_DTO.getGroup_no();
		
		int groupMaxStep = comments_DAO.getGroupMaxStep(parent_DTO.getGroup_no());
		
		int orderNum = 0;
		if(saveStep >= groupMaxStep) {
			orderNum = parent_DTO.getOrderNum()+parent_DTO.getChildCnt();
			parent_DTO.setOrderNum(orderNum);
			comments_DAO.updateGroupOrderNo(parent_DTO);
			return orderNum+1;
		}
		
		if(parent_DTO.getParent_no() == 0)
			return comments_DAO.getGroupChildCntSum(group_no)+1;
		
		orderNum = comments_DAO.getParentChildCntSum(parent_DTO.getComment_no())+parent_DTO.getOrderNum();
		parent_DTO.setOrderNum(orderNum);
		comments_DAO.updateGroupOrderNo(parent_DTO);
		return orderNum+1;
	}
	
	public boolean updateComment(comments_DTO comments_DTO) {
		return comments_DAO.updateCommnet(comments_DTO) != 0;
	}
	
	
	public boolean deleteUpdateComment(comments_DTO comment_DTO) {
		return comments_DAO.deleteComment(comment_DTO.getComment_no()) != 0;
	}
	
	
	public List<Map<String,Object>> getCommentsByBoardNo(int board_no){
		return comments_DAO.getCommentsByBoardNo(board_no);
	}
	
	public List<Map<String,Object>> getRecentlyComment(int user_no){
		return comments_DAO.getRecentlyComment(user_no);
	}
	
}	

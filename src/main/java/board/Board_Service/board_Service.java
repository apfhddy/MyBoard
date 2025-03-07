package board.Board_Service;

import java.sql.Clob;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import board.Board_DAO.board_DAO;
import board.Board_Redis.board_Redis;
import board.DTO.board_DTO;
import board.DTO.users_DTO;
import board.ETCClass.MyFileManager;
import board.ETCClass.MyMybatisMethods;
import board.ETCClass.StaticPath;
import board.security.MySecurityMethods;

@Service
public class board_Service implements StaticPath{
	private board_DAO board_DAO;
	private board_Redis board_Redis;
	private MyFileManager fileManager;
	
	public board_Service
	(
			board_DAO board_DAO,
			board_Redis board_Redis,
			MyFileManager fileManager
	) 
	{
		this.board_DAO = board_DAO;
		this.board_Redis = board_Redis;
		this.fileManager = fileManager;
	}
	
	public boolean insertBoard(board_DTO dto) {
		dto.setBoard_no(board_DAO.getNextNo());
		dto.setUser_no(MySecurityMethods.getObject(users_DTO.class).getUser_no());
		
		//저장소 옮김과 동시에 src 변경
		fileManager.changeFileDir(dto.getContent(),dto.getUser_no(),dto.getBoard_no());
		dto.setContent(fileManager.changeSrc(dto.getContent(),dto.getUser_no(),dto.getBoard_no()));
		
		//hits설정 및 임시저장소 삭제
		board_Redis.addBoardHitsByUser_no(dto.getBoard_no(),dto.getUser_no());
		
		int result = board_DAO.insertBoard(dto);
		return result != 0;
	}
	
	public List<Map<String,Object>> getAllContents(int getPageCnt,int contentCnt,int pageIndex,String target,String keyword){
		List<Map<String,Object>> list = board_DAO.getAllContents(getPageCnt,contentCnt,pageIndex,target,keyword);
		for(Map<String,Object> map : list) {
			map.put("HITS", board_Redis.getBoardHitsLength(MyMybatisMethods.MapObjectIntToInt(map.get("BOARD_NO")))-1);
		}
		return list;
	} 
	public Map<String,Object> getOneContent(int board_no,boolean isUser){
		Map<String,Object> content = board_DAO.getOneContent(board_no);
		content.put("HITS", board_Redis.getBoardHitsLength(board_no)-1);
		content.put("CONTENT",MyMybatisMethods.ClobToString((Clob)content.get("CONTENT")));
		
		if(!MySecurityMethods.isAnanimusUser() && isUser)
			board_Redis.addBoardHitsByUser_no(board_no, MySecurityMethods.getObject(users_DTO.class).getUser_no());
		
		
		return content;
	}
	
	public board_DTO getOneBoard(int board_no) {
		return board_DAO.getOneBoard(board_no);
	}
	
	public boolean updateBoard(board_DTO target) {
		fileManager.matchAndRemoveFile(target.getContent(), fileManager.getPath(target.getBoard_no()));
		return board_DAO.updateBoard(target) != 0;
	}
	
	public boolean deleteBoard(int board_no) {
		fileManager.removeAll(fileManager.getPath(board_no));
		return board_DAO.deleteBoard(board_no) != 0;
	}
	
	public int getBoardCnt(String target,String keyword) {
		return board_DAO.getBoardCnt(target,keyword);
	}
	
	public List<Map<String,Object>> getRecentlyBoard(int user_no){
		return board_DAO.getRecentlyBoard(user_no);
	}
}

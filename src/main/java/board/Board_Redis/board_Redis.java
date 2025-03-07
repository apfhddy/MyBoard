package board.Board_Redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import board.DTO.board_DTO;

@Configuration
public class board_Redis {
	private RedisTemplate<String,String> redisTemplate;
	
	private final static String boardHitsKey = "board:hits:";
	private final static String userTemporaryKey = "user:temporary:";
	
	public board_Redis(RedisTemplate<String,String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	public boolean addBoardHitsByUser_no(int board_no,int user_no) {
		return redisTemplate.opsForSet().add(boardHitsKey+board_no,user_no+"") != 0;
	}
	
	public int getBoardHitsLength(int board_no) {
		return redisTemplate.opsForSet().size(boardHitsKey+board_no).intValue();
	}
	
	public void setTemporaryBoard(String title,String content,int user_no) {
		String key = userTemporaryKey+user_no;
		redisTemplate.opsForHash().put(key, "title", title);
		redisTemplate.opsForHash().put(key, "content", content);
	}
	
	public boolean delTemporaryUser(int user_no) {
		String key = userTemporaryKey+user_no;
		return redisTemplate.delete(key);
	}
	
	public board_DTO getTemporaryBoard(int user_no) {
		String key = userTemporaryKey+user_no;
		board_DTO dto = null;
		if(redisTemplate.keys(key).size() != 0) {
			dto = new board_DTO();
			dto.setTitle((String)redisTemplate.opsForHash().get(key, "title"));
			dto.setContent((String)redisTemplate.opsForHash().get(key, "content"));
		}
		
		return dto;
	}
	
	
}

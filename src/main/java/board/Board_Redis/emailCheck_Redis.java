package board.Board_Redis;

import java.time.Duration;
import java.util.Date;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class emailCheck_Redis {
	private RedisTemplate<String,String> redisTemplate;
	
	private final String defaultKey = "email:cnt:";
	public final int maxSendCnt = 10 ;
	
	
	public emailCheck_Redis(RedisTemplate<String,String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	public boolean isPossibilitySned(String ip) {
		String key = defaultKey+ip;
		if(!haveKey(key)) {
			createKey(key);
			return true;
		}
		int cnt = getCnt(key);
		
		if(cnt < maxSendCnt) {
			upCnt(key);
			return true;
		}
		return false;
	}

	
	
	
	
	private void createKey(String key) {
		redisTemplate.opsForValue().set(key, "1");
		redisTemplate.expire(key, Duration.ofSeconds(getDayRemainTime()));
	}
	
	private void upCnt(String key) {
		redisTemplate.opsForValue().increment(key);
	}
	
	private int getCnt(String key) {
		int cnt = Integer.parseInt(redisTemplate.opsForValue().get(key));
		return cnt;
	}
	private boolean haveKey(String key) {
		return redisTemplate.keys(key).size() != 0;
	}
	
	
	@SuppressWarnings("deprecation")
	public Long getDayRemainTime() {
		Date d = new Date();
		int M = 60-d.getMinutes();
    	int H = 24-d.getHours();
    	if(M != 0)H-=1;
    	int s = (H*60*60) + (M*60);
    	return s+0L;
	}
	
	
	
	public String getClientIP(HttpServletRequest request) {
	    String ip = request.getHeader("X-Forwarded-For");
	    if (ip == null) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (ip == null) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null) {
	        ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if (ip == null) {
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if (ip == null) {
	        ip = request.getRemoteAddr();
	    }
	    return ip;
	}
	

}

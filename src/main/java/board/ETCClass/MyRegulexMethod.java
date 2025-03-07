package board.ETCClass;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyRegulexMethod {
	
	public static Map<String,Integer> matchMap(String content,String regulex) {
		Map<String,Integer> map = new HashMap<>();
		Matcher mt = Pattern.compile(regulex)
				.matcher(content);
		while(mt.find()) {
			map.put(mt.group(), 1);
		}
		return map;
	}
}

package board.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class MySecurityMethods {
	
	@SuppressWarnings("unchecked")
	public static <T> T getObject(Class<T> c){
		if(c.isInstance(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
			return (T)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		return null;
	}
	
	public static<T> boolean isInstance(Class<T> c) {
		return c.isInstance(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}
	
	public static boolean isAnanimusUser() {
		return String.class.isInstance(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}
	
	
}
